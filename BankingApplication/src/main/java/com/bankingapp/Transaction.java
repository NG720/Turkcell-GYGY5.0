package com.bankingapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

    public enum Type { DEPOSIT, WITHDRAWAL, TRANSFER_IN, TRANSFER_OUT }

    private static int counter = 1;

    private final String transactionId;
    private final Type type;
    private final double amount;
    private final double balanceAfter;
    private final String description;
    private final LocalDateTime date = LocalDateTime.now();

    public Transaction(Type type, double amount, double balanceAfter, String description) {
        this.transactionId = String.format("TXN%05d", counter++);
        this.type          = type;
        this.amount        = amount;
        this.balanceAfter  = balanceAfter;
        this.description   = description;
    }

    public String getTransactionId() { return transactionId; }
    public Type getType()            { return type; }
    public double getAmount()        { return amount; }
    public double getBalanceAfter()  { return balanceAfter; }

    public String getTypeName() {
        return switch (type) {
            case DEPOSIT       -> "Yatırma";
            case WITHDRAWAL    -> "Çekme";
            case TRANSFER_IN   -> "Gelen Transfer";
            case TRANSFER_OUT  -> "Giden Transfer";
        };
    }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | Tutar: %,.2f TL | Bakiye: %,.2f TL | %s",
                transactionId,
                date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")),
                getTypeName(), amount, balanceAfter, description);
    }
}
