package com.example.db.repository;

import com.example.db.entity.Customer;
import com.example.db.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Wallet findByMobile(String mobile);
}

