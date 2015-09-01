package com.bank.service;

import com.bank.dto.AccountInfo;
import com.bank.entity.Account;
import com.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by eniakel on 31/08/2015.
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerService customerService;

    public Set<AccountInfo> getAccounts(String firstName, String surName) {
        List<Account> accounts = new ArrayList<>();
        if (surName != null && firstName != null) {
            accounts = accountRepository.findAccountByName(firstName, surName);
        } else if (surName != null) {
            accounts = accountRepository.findBySurname(surName);
        } else {
            accounts = accountRepository.findAll();
        }
        Set<AccountInfo> accountInfo = new HashSet<>();
        for (Account account : accounts) {
            accountInfo.add(mapToDto(account));
        }
        return accountInfo;
    }

    private AccountInfo mapToDto(Account account) {
        AccountInfo dto = new AccountInfo();
        dto.setId(account.getId());
        dto.setBalance(account.getBalance());
        dto.setRate(account.getRate());
        dto.setType(account.getType());
        dto.setCustomer(customerService.mapToDto(account.getCustomer()).get());
        return dto;
    }
}
