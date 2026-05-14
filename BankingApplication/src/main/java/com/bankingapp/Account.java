package com.bankingapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Account {

    public enum AccountType { CHECKING, SAVINGS }

    private final String accountNumber;
    private final String customerId;
    private final AccountType accountType;
    private double balance;
    private final LocalDateTime createdAt = LocalDateTime.now();
    private final List<Transaction> transactions = new ArrayList<>();

    public Account(String accountNumber, String customerId, AccountType accountType, double initialBalance) {
        this.accountNumber = accountNumber;
        this.customerId    = customerId;
        this.accountType   = accountType;
        this.balance       = initialBalance;
    }

    public String getAccountNumber()           { return accountNumber; }
    public String getCustomerId()              { return customerId; }
    public AccountType getAccountType()        { return accountType; }
    public double getBalance()                 { return balance; }
    public List<Transaction> getTransactions() { return transactions; }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Tutar sıfırdan büyük olmalı.");
        balance += amount;
        transactions.add(new Transaction(Transaction.Type.DEPOSIT, amount, balance, "Para yatırma"));
    }

    public void withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Tutar sıfırdan büyük olmalı.");
        if (amount > balance) throw new IllegalStateException("Yetersiz bakiye.");
        balance -= amount;
        transactions.add(new Transaction(Transaction.Type.WITHDRAWAL, amount, balance, "Para çekme"));
    }

    public void addTransaction(Transaction.Type type, double amount, String desc) {
        transactions.add(new Transaction(type, amount, balance, desc));
    }

    public String getTypeName() {
        return accountType == AccountType.CHECKING ? "Vadesiz" : "Tasarruf";
    }

    @Override
    public String toString() {
        return String.format("Hesap No: %s | Tür: %-10s | Bakiye: %,.2f TL | Açılış: %s",
                accountNumber, getTypeName(), balance,
                createdAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
    }
}
