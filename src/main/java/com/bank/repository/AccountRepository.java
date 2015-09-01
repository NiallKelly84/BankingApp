package com.bank.repository;

import com.bank.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by eniakel on 31/08/2015.
 */
@Service
public interface AccountRepository extends CrudRepository<Account, Long> {

    List<Account> findAll();
}
