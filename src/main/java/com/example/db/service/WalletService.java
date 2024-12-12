package com.example.db.service;

import com.example.db.entity.Wallet;
import com.example.db.repository.WalletRepository;
import com.example.db.vo.PayReq;
import com.example.db.vo.PayResponse;
import com.example.db.vo.WalletReq;
import com.example.db.vo.WalletResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    @Autowired
    WalletRepository walletRepository;

    // Constants for initial balance
    private static final String INITIAL_BALANCE = "100000"; // 100,000 as a string

    public WalletResp walletRegistration(WalletReq req) {
        Wallet existingWallet = walletRepository.findByMobile(req.getMobile());

        if (existingWallet != null) {
            WalletResp walletResp = new WalletResp();
            walletResp.setRc("01");
            walletResp.setDesc("Wallet Already Registered");
            return walletResp;
        } else {
            Wallet newWallet = new Wallet();
            newWallet.setBalance(INITIAL_BALANCE); // Set initial balance to 100,000
            newWallet.setMobile(req.getMobile());
            newWallet.setName(req.getName());
            newWallet.setPin(req.getPin()); // Save the PIN during registration
            walletRepository.save(newWallet);
        }

        WalletResp walletResp = new WalletResp();
        walletResp.setRc("00");
        walletResp.setDesc("Success");
        return walletResp;
    }

    // ... (rest of the WalletService methods remain unchanged)

    public String getBalanceByMobile(String mobile) {
        Wallet wallet = walletRepository.findByMobile(mobile);
        if (wallet != null) { // Check if the wallet exists
            return wallet.getBalance(); // Return the balance if wallet exists
        } else {
            throw new RuntimeException("Wallet not found for the provided mobile number"); // Handle invalid case
        }
    }

    public PayResponse reqPay(PayReq req) {
        Wallet senderWallet = walletRepository.findByMobile(req.getSendMobile());
        Wallet receiverWallet = walletRepository.findByMobile(req.getReceiveMobile());

        PayResponse response = new PayResponse();

        // Check if the sender wallet exists and the sender's PIN is correct
        if (senderWallet != null && senderWallet.getPin().equals(req.getPin())) {
            // Check if the receiver wallet exists
            if (receiverWallet == null) {
                // Receiver wallet not found, return failure response
                response.setRc("01");
                response.setDes("failure");
                return response;
            }

            // Check if sender has sufficient balance
            Integer debAmount = Integer.valueOf(senderWallet.getBalance()) - Integer.valueOf(req.getAmount());
            if (debAmount < 0) {
                response.setRc("01");
                response.setDes("Insufficient balance");
                return response; // Handle insufficient funds
            }

            // Deduct amount from sender's balance
            senderWallet.setBalance(String.valueOf(debAmount));
            walletRepository.save(senderWallet);

            // Add amount to receiver's balance
            Integer amount = Integer.valueOf(receiverWallet.getBalance()) + Integer.valueOf(req.getAmount());
            receiverWallet.setBalance(String.valueOf(amount));
            walletRepository.save(receiverWallet);

            // Successful payment response
            response.setRc("00");
            response.setDes("success");
        } else {
            // If the sender wallet is not found or PIN is incorrect
            response.setRc("01");
            response.setDes("Invalid mobile or PIN for payment");
        }

        return response;
    }

    public boolean checkMobile(String mobile) {
        Wallet wallet = walletRepository.findByMobile(mobile);
        return wallet != null;
    }
}
