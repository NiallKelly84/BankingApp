package com.bank.controller;

import com.bank.dto.CustomerInfo;
import com.bank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by eniakel on 01/09/2015.
 */
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<CustomerInfo>> getAllCustomers() {
        List<CustomerInfo> customer = customerService.getAllCustomers();
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public ResponseEntity<CustomerInfo> getCustomerById(@PathVariable("customerId") int customerId) {
        CustomerInfo customer = customerService.getCustomerById(customerId).get();
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
