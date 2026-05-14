package com.bankingapp;

import java.util.*;

public class CustomerRepository {
    private final Map<String, Customer> store = new LinkedHashMap<>();
    private int seq = 1;

    public String generateId()          { return String.format("CUS%04d", seq++); }
    public void save(Customer c)        { store.put(c.getCustomerId(), c); }
    public Optional<Customer> findById(String id) { return Optional.ofNullable(store.get(id)); }
    public Optional<Customer> findByEmail(String email) {
        return store.values().stream().filter(c -> c.getEmail().equalsIgnoreCase(email)).findFirst();
    }
    public List<Customer> findAll()     { return new ArrayList<>(store.values()); }
    public int count()                  { return store.size(); }
}

class AccountRepository {
    private final Map<String, Account> store = new LinkedHashMap<>();
    private int seq = 1;

    public String generateNumber()       { return String.format("TR%010d", seq++); }
    public void save(Account a)          { store.put(a.getAccountNumber(), a); }
    public Optional<Account> findByNumber(String no) { return Optional.ofNullable(store.get(no)); }
    public List<Account> findByCustomer(String cId) {
        return store.values().stream().filter(a -> a.getCustomerId().equals(cId)).toList();
    }
    public List<Account> findAll()       { return new ArrayList<>(store.values()); }
    public int count()                   { return store.size(); }
}
