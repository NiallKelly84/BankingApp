package com.bank.repository;

import com.bank.entity.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by eniakel on 31/08/2015.
 */
@Service
public interface AccountRepository extends CrudRepository<Account, Long> {

    List<Account> findAll();

    @Query("select a from Account a where a.customer.id = ?")
    List<Account> findAccountByCustomerId(Long ide);

    @Query("select a from Account a where a.customer.firstName = ? and a.customer.surName = ?")
    List<Account> findAccountByName(String firstName, String surname);

    @Query("select a from Account a where a.customer.surName = ?")
    List<Account> findBySurname(String surname);
    
}
