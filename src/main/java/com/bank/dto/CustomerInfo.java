package com.bank.dto;

import java.util.Set;

/**
 * Created by eniakel on 31/08/2015.
 */
public class CustomerInfo {
    private int id;

    private String firstName;

    private String surName;

    private BranchInfo branch;

    private AddressInfo address;

    private Set<AccountInfo> accounts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public BranchInfo getBranch() {
        return branch;
    }

    public void setBranch(BranchInfo branch) {
        this.branch = branch;
    }

    public Set<AccountInfo> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<AccountInfo> accounts) {
        this.accounts = accounts;
    }

    public AddressInfo getAddress() {
        return address;
    }

    public void setAddress(AddressInfo address) {
        this.address = address;
    }
}
