package com.bankingapp;

import java.util.List;
import java.util.Optional;

public class BankingService {

    private final CustomerRepository customerRepo;
    private final AccountRepository  accountRepo;

    public BankingService(CustomerRepository cr, AccountRepository ar) {
        this.customerRepo = cr;
        this.accountRepo  = ar;
    }

    // Müşteri işlemleri
    public Customer registerCustomer(String firstName, String lastName, String email, String phone) {
        if (customerRepo.findByEmail(email).isPresent())
            throw new IllegalArgumentException("E-posta zaten kayıtlı: " + email);
        Customer c = new Customer(customerRepo.generateId(), firstName, lastName, email, phone);
        customerRepo.save(c);
        return c;
    }

    public Optional<Customer> findCustomer(String id) { return customerRepo.findById(id); }
    public List<Customer> getAllCustomers()            { return customerRepo.findAll(); }

    // Hesap işlemleri
    public Account openAccount(String customerId, Account.AccountType type, double initial) {
        Customer c = customerRepo.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Müşteri bulunamadı: " + customerId));
        Account a = new Account(accountRepo.generateNumber(), customerId, type, initial);
        accountRepo.save(a);
        c.addAccount(a);
        if (initial > 0) a.addTransaction(Transaction.Type.DEPOSIT, initial, "Hesap açılış yatırımı");
        return a;
    }

    public List<Account> getAllAccounts()               { return accountRepo.findAll(); }
    public List<Account> getAccountsOf(String cId)     { return accountRepo.findByCustomer(cId); }

    // Para işlemleri
    public void deposit(String accNo, double amount) {
        getAccount(accNo).deposit(amount);
    }

    public void withdraw(String accNo, double amount) {
        getAccount(accNo).withdraw(amount);
    }

    public void transfer(String from, String to, double amount) {
        if (from.equals(to)) throw new IllegalArgumentException("Kaynak ve hedef aynı olamaz.");
        Account f = getAccount(from);
        Account t = getAccount(to);
        f.withdraw(amount);
        f.getTransactions().removeLast();
        f.addTransaction(Transaction.Type.TRANSFER_OUT, amount, "Transfer → " + to);
        t.deposit(amount);
        t.getTransactions().removeLast();
        t.addTransaction(Transaction.Type.TRANSFER_IN, amount, "Transfer ← " + from);
    }

    public double getTotalBalance(String cId) {
        return accountRepo.findByCustomer(cId).stream().mapToDouble(Account::getBalance).sum();
    }

    public int getTotalCustomers() { return customerRepo.count(); }
    public int getTotalAccounts()  { return accountRepo.count(); }

    private Account getAccount(String no) {
        return accountRepo.findByNumber(no)
                .orElseThrow(() -> new IllegalArgumentException("Hesap bulunamadı: " + no));
    }
}
