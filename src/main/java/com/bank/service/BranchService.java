package com.bank.service;

import com.bank.dto.BranchInfo;
import com.bank.entity.Branch;
import com.bank.repository.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by eniakel on 01/09/2015.
 */
@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public Set<BranchInfo> getAllBranches() {
        List<Branch> branches = branchRepository.findAll();

        Set<BranchInfo> brancheInfoList = new HashSet<>();
        for (Branch branch : branches) {
            brancheInfoList.add(mapToDto(branch));
        }
        return brancheInfoList;
    }

    private BranchInfo mapToDto(Branch branch) {
        BranchInfo dto = new BranchInfo();
        dto.setId(branch.getId());
        dto.setBranchName(branch.getBranchName());
        return dto;
    }
}