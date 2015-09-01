package com.bank.repository;

import com.bank.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by eniakel on 01/09/2015.
 */
@Service
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findAll();

    @Query("select c from Customer c where c.id = ?")
    Customer findCustomerById(int id);
}
