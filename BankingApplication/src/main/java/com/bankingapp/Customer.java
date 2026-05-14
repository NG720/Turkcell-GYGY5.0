package com.bankingapp;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final String customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private final List<Account> accounts = new ArrayList<>();

    public Customer(String customerId, String firstName, String lastName, String email, String phone) {
        this.customerId = customerId;
        this.firstName  = firstName;
        this.lastName   = lastName;
        this.email      = email;
        this.phone      = phone;
    }

    public String getCustomerId()      { return customerId; }
    public String getFirstName()       { return firstName; }
    public String getLastName()        { return lastName; }
    public String getEmail()           { return email; }
    public String getPhone()           { return phone; }
    public List<Account> getAccounts() { return accounts; }
    public String getFullName()        { return firstName + " " + lastName; }
    public void addAccount(Account a)  { accounts.add(a); }

    @Override
    public String toString() {
        return String.format("[%s] %s | E-posta: %s | Tel: %s | Hesap: %d",
                customerId, getFullName(), email, phone, accounts.size());
    }
}
