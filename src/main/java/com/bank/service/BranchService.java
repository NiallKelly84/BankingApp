package com.bank.service;

import com.bank.dto.BranchInfo;
import com.bank.entity.Branch;
import com.bank.repository.BranchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(BranchService.class);

    public Set<BranchInfo> getAllBranches() {
        List<Branch> branches = branchRepository.findAll();

        Set<BranchInfo> brancheInfoList = new HashSet<>();
        for (Branch branch : branches) {
            brancheInfoList.add(mapToDto(branch));
        }
        return brancheInfoList;
    }

    @Transactional
    public BranchInfo create(BranchInfo branchInfo) {
        Branch existingBranch = branchRepository.findOne(branchInfo.getId());

        Branch branchEntity = null;
        if (existingBranch == null) {
            branchEntity = mapToEntity(branchInfo);
        } else {
            LOGGER.warn("Branch: " + branchInfo + "exists in the database already");
            return null;
        }

        branchRepository.save(branchEntity);
        return mapToDto(branchEntity);
    }

    @Transactional
    public void deleteBranch(Long branchId) {
        Branch branchToDelete = branchRepository.findOne(branchId);
        branchRepository.delete(branchToDelete);
    }

    private BranchInfo mapToDto(Branch branch) {
        BranchInfo dto = new BranchInfo();
        dto.setId(branch.getId());
        dto.setBranchName(branch.getBranchName());
        return dto;
    }

    private Branch mapToEntity(BranchInfo dto) {
        Branch branch = new Branch();
        branch.setBranchName(dto.getBranchName());
        return branch;
    }
}