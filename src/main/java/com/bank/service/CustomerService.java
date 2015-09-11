package com.bank.service;

import com.bank.dto.CustomerInfo;
import com.bank.entity.Account;
import com.bank.entity.Customer;
import com.bank.repository.AccountRepository;
import com.bank.repository.BranchRepository;
import com.bank.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by eniakel on 01/09/2015.
 */
@Service
public class CustomerService {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BranchRepository branchRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(BranchService.class);

    public Optional<CustomerInfo> getCustomerById(int id) {
        Customer customer = customerRepository.findCustomerById(id);
        return mapToDto(customer);
    }

    public List<CustomerInfo> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerInfo> customerList = new ArrayList();

        for (Customer customer : customers) {
            customerList.add((mapToDto(customer)).get());
        }
        return customerList;
    }

    @Transactional
    public Optional<CustomerInfo> create(CustomerInfo customerInfo) {
        Customer existingCustomer = customerRepository.findOne(customerInfo.getId());

        Customer customerEntity = null;
        if (existingCustomer == null) {
            customerEntity = mapToEntity(customerInfo);
        } else {
            LOGGER.warn("Customer: " + customerInfo.getFirstName() +
                    " " + customerInfo.getSurName() + "exists in the database already");
            return null;
        }

        customerRepository.save(customerEntity);
        return mapToDto(customerEntity);
    }

    public Optional<CustomerInfo> mapToDto(Customer customer) {
        CustomerInfo customerInfo = new CustomerInfo();
        if (customer == null) {
            return Optional.empty();
        }
        customerInfo.setId(customer.getId());
        customerInfo.setFirstName(customer.getFirstName());
        customerInfo.setSurName(customer.getSurName());
        customerInfo.setAddress(addressService.mapToDto(customer.getAddress()));

        List<Account> accounts = accountRepository.findAccountByCustomerId(customer.getId());
        if (accounts != null) {
            accounts.stream()
                    .forEach(a -> customerInfo.addAccountId(a.getId()));
        }
        return Optional.of(customerInfo);
    }

    private Customer mapToEntity(CustomerInfo dto) {
        Customer customer = new Customer();
        customer.setBranch(branchRepository.findOne(dto.getBranchId()));
        customer.setFirstName(dto.getFirstName());
        customer.setSurName(dto.getSurName());
        customer.setAddress(addressService.mapToEntity(dto.getAddress()));
        return customer;
    }
}
