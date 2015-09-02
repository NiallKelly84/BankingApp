package com.bank.service;

import com.bank.dto.CustomerInfo;
import com.bank.entity.Customer;
import com.bank.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<CustomerInfo> getCustomerById(int id) {
        Customer customer = customerRepository.findCustomerById(id);
        return mapToDto(customer);
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
        customer.getAccounts().stream()
                              .forEach(a -> customerInfo.addAccountId(a.getId()));
        return Optional.of(customerInfo);
    }
}
