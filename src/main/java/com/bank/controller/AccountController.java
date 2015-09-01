package com.bank.controller;

import com.bank.dto.AccountInfo;
import com.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created by eniakel on 31/08/2015.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Set<AccountInfo>> getAccounts(@RequestParam(value = "firstName", required = false) String firstName,
                                                        @RequestParam(value = "surName", required = false)  String surName) {
        Set<AccountInfo> accounts = accountService.getAccounts(firstName, surName);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
}
