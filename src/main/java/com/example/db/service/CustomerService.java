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

    // Register a new customer
    public CustRegResp custRegistration(CustRegReq req) {
        // Check if the customer already exists based on mobile number
        Customer c = customerRepository.findByMobile(req.getMobile());
        CustRegResp custRegResp = new CustRegResp();

        if (c != null) {
            custRegResp.setRc("01"); // Return code for failure
            custRegResp.setDesc("Customer Already Registered");
            return custRegResp;
        } else {
            // Create a new customer entity and save it to the database
            c = new Customer();
            c.setDob(req.getDob());
            c.setGmail(req.getGmail());
            c.setName(req.getName());
            c.setMobile(req.getMobile());
            c.setPassword(req.getPassword());
            customerRepository.save(c);
        }

        // Return success response
        custRegResp.setRc("00"); // Return code for success
        custRegResp.setDesc("Success");
        return custRegResp;
    }

    // Method to check if the provided password matches the stored password
    public boolean checkPassword(String mobile, String password) {
        Customer customer = customerRepository.findByMobile(mobile);
        if (customer != null) {
            return customer.getPassword().equals(password);
        }
        return false; // Customer not found, return false
    }

    public Customer findCustomerByMobile(String mobile) {
        return customerRepository.findByMobile(mobile);
    }

}
