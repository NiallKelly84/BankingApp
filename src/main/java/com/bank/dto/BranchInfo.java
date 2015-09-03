package com.bank.dto;

import java.util.Set;

/**
 * Created by eniakel on 31/08/2015.
 */
public class BranchInfo {
    private long id;

    private String branchName;

    private Set<CustomerInfo> customers;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Set<CustomerInfo> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<CustomerInfo> customers) {
        this.customers = customers;
    }
}
