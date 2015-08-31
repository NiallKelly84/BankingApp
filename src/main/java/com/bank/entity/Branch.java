package com.bank.entity;

/**
 * Created by eniakel on 14/08/2015.
 */


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Branch")
public class Branch implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "branch_id", unique = true, nullable = false)
    private int id;

    @Column(name = "branch_Name")
    private String branchName;

    @OneToMany(targetEntity = Customer.class, mappedBy = "branch",
            cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Customer> customers;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }
}
