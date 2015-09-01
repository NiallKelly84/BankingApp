package com.bank.controller;

import com.bank.dto.BranchInfo;
import com.bank.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * Created by eniakel on 01/09/2015.
 */
@RestController
@RequestMapping("/api/branch")
public class BranchController {

    @Autowired
    private BranchService branchService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Set<BranchInfo>> getAllBranches() {
        Set<BranchInfo> accounts = branchService.getAllBranches();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }
}
