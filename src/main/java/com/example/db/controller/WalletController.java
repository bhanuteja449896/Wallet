package com.example.db.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import com.example.db.service.WalletService;
import com.example.db.vo.PayReq;
import com.example.db.vo.PayResponse;
import com.example.db.vo.WalletReq;
import com.example.db.vo.WalletResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
@CrossOrigin(origins = "http://localhost:3000")
public class WalletController {
    @Autowired
    WalletService walletService;

    @PostMapping("/add")
    public WalletResp putUsers(@RequestBody WalletReq req) {
        WalletResp walletResp = walletService.walletRegistration(req);
        return walletResp;
    }

    @GetMapping("/balance/{mobile}")
    public String getBalance(@PathVariable String mobile) {
        return walletService.getBalanceByMobile(mobile);
    }

    @PostMapping("/payreq")
    public PayResponse reqPay(@RequestBody PayReq req) {
        PayResponse response = walletService.reqPay(req);
        return response;
    }

    @PostMapping("/check-mobile")
    public boolean checkMobile(@RequestParam String mobile) {
        return walletService.checkMobile(mobile);
    }
}
