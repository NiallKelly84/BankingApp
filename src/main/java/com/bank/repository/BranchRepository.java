package com.bank.repository;

import com.bank.entity.Branch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by eniakel on 01/09/2015.
 */
@Service
public interface BranchRepository extends CrudRepository<Branch, Long> {

    List<Branch> findAll();
}
