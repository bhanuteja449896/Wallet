package com.example.db.controller;

import com.example.db.entity.Customer;
import com.example.db.repository.CustomerRepository;
import com.example.db.service.CustomerService;
import com.example.db.vo.CustRegResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.db.vo.CustRegReq;


@RestController
@RequestMapping("/v1/")
public class CustomerContoller {

    @Autowired
    CustomerService customerService;

    @PostMapping("add")
    public CustRegResp putUsers(@RequestBody  CustRegReq req ){

        CustRegResp custRegResp=customerService.custRegistration(req);

        return custRegResp;
    }
}
