package com.example.db.controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.example.db.entity.Customer;
import com.example.db.repository.CustomerRepository;
import com.example.db.service.CustomerService;
import com.example.db.vo.CustRegResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.db.vo.CustRegReq;

import java.util.Map;


@RestController
@RequestMapping("/v1/")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerContoller {

    @Autowired
    CustomerService customerService;

    @PostMapping("add")
    public CustRegResp putUsers(@RequestBody  CustRegReq req ){

        CustRegResp custRegResp=customerService.custRegistration(req);
        return custRegResp;
    }

    @PostMapping("check-password")
    public boolean checkPassword(@RequestBody Map<String, String> credentials) {
        String mobile = credentials.get("mobile");
        String password = credentials.get("password");
        return customerService.checkPassword(mobile, password);
    }

    @GetMapping("/mobile")
    public Customer getCustomerByMobile(@RequestParam String mobile) {
        return customerService.findCustomerByMobile(mobile);
    }
}
