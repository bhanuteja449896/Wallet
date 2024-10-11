package com.example.db.service;


import com.example.db.entity.Customer;
import com.example.db.repository.CustomerRepository;
import com.example.db.vo.CustRegReq;
import com.example.db.vo.CustRegResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public CustRegResp custRegistration(CustRegReq req) {
        Customer c=customerRepository.findByMobile(req.getMobile());
        if ( c!= null ){
            CustRegResp custRegResp = new CustRegResp();
            custRegResp.setRc("01");
            custRegResp.setDesc("Customer Already Registered");
            return custRegResp;
        }
        else {
            c = new Customer();
            c.setDob(req.getDob());
            c.setGmail(req.getGmail());
            c.setName(req.getName());
            c.setMobile(req.getMobile());
            customerRepository.save(c);
        }

        CustRegResp custRegResp = new CustRegResp();
        custRegResp.setRc("00");
        custRegResp.setDesc("Success");
        return custRegResp;
    }
}
