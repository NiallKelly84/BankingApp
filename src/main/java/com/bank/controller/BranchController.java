package com.bank.controller;

import com.bank.dto.BranchInfo;
import com.bank.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BranchInfo> create(@RequestBody BranchInfo branch) {
        BranchInfo created = branchService.create(branch);
        if (created == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{branchId}", method = RequestMethod.DELETE)
    public void deleteSchedule(@PathVariable("branchId") long branchId) {
        branchService.deleteBranch(branchId);
    }
}
