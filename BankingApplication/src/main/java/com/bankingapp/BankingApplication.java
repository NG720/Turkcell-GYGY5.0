package com.bankingapp;

import java.util.List;
import java.util.Scanner;

public class BankingApplication {

    private static final BankingService service =
            new BankingService(new CustomerRepository(), new AccountRepository());
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedDemoData();
        printBanner();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Seçiminiz");
            System.out.println();
            switch (choice) {
                case 1  -> menuRegisterCustomer();
                case 2  -> menuOpenAccount();
                case 3  -> menuDeposit();
                case 4  -> menuWithdraw();
                case 5  -> menuTransfer();
                case 6  -> menuAccountSummary();
                case 7  -> menuTransactionHistory();
                case 8  -> menuDatabase();
                case 0  -> running = false;
                default -> System.out.println("  Geçersiz seçim.\n");
            }
        }
        System.out.println("\n  Güle güle!");
        scanner.close();
    }

    // ── Menü metodları ──────────────────────────────────────────────────────

    private static void menuRegisterCustomer() {
        header("YENİ MÜŞTERİ KAYDI");
        try {
            Customer c = service.registerCustomer(
                    readStr("Ad"), readStr("Soyad"), readStr("E-posta"), readStr("Telefon"));
            System.out.printf("%n  ✔ Kaydedildi! Müşteri No: %s%n%n", c.getCustomerId());
        } catch (Exception e) { err(e.getMessage()); }
    }

    private static void menuOpenAccount() {
        header("HESAP AÇMA");
        try {
            String cId = readStr("Müşteri No");
            System.out.println("  Tür: 1) Vadesiz  2) Tasarruf");
            Account.AccountType type = readInt("Seçim") == 2
                    ? Account.AccountType.SAVINGS : Account.AccountType.CHECKING;
            double initial = readDouble("Başlangıç Bakiyesi (TL)");
            Account a = service.openAccount(cId, type, initial);
            System.out.printf("%n  ✔ Hesap No: %s | %s | %,.2f TL%n%n",
                    a.getAccountNumber(), a.getTypeName(), a.getBalance());
        } catch (Exception e) { err(e.getMessage()); }
    }

    private static void menuDeposit() {
        header("PARA YATIRMA");
        try {
            service.deposit(readStr("Hesap No"), readDouble("Tutar (TL)"));
            System.out.println("\n  ✔ Para yatırıldı.\n");
        } catch (Exception e) { err(e.getMessage()); }
    }

    private static void menuWithdraw() {
        header("PARA ÇEKME");
        try {
            service.withdraw(readStr("Hesap No"), readDouble("Tutar (TL)"));
            System.out.println("\n  ✔ Para çekildi.\n");
        } catch (Exception e) { err(e.getMessage()); }
    }

    private static void menuTransfer() {
        header("TRANSFER");
        try {
            service.transfer(readStr("Kaynak Hesap No"), readStr("Hedef Hesap No"), readDouble("Tutar (TL)"));
            System.out.println("\n  ✔ Transfer tamamlandı.\n");
        } catch (Exception e) { err(e.getMessage()); }
    }

    private static void menuAccountSummary() {
        header("HESAP ÖZETİ");
        try {
            String cId = readStr("Müşteri No");
            Customer c = service.findCustomer(cId)
                    .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı."));
            System.out.printf("%n  Müşteri: %s (%s)%n", c.getFullName(), c.getCustomerId());
            service.getAccountsOf(cId).forEach(a -> System.out.println("  " + a));
            System.out.printf("  Toplam Varlık: %,.2f TL%n%n", service.getTotalBalance(cId));
        } catch (Exception e) { err(e.getMessage()); }
    }

    private static void menuTransactionHistory() {
        header("İŞLEM GEÇMİŞİ");
        try {
            String accNo = readStr("Hesap No");
            List<Transaction> txns = service.getAllAccounts().stream()
                    .filter(a -> a.getAccountNumber().equals(accNo)).findFirst()
                    .orElseThrow(() -> new RuntimeException("Hesap bulunamadı."))
                    .getTransactions();
            if (txns.isEmpty()) System.out.println("  İşlem yok.\n");
            else txns.forEach(t -> System.out.println("  " + t));
            System.out.println();
        } catch (Exception e) { err(e.getMessage()); }
    }

    private static void menuDatabase() {
        header("VERİTABANI (ADMİN)");
        System.out.printf("%n  Müşteri: %d | Hesap: %d%n%n",
                service.getTotalCustomers(), service.getTotalAccounts());
        System.out.println("  ── MÜŞTERİLER ──");
        service.getAllCustomers().forEach(c -> System.out.println("  " + c));
        System.out.println("\n  ── HESAPLAR ──");
        service.getAllAccounts().forEach(a -> System.out.println("  " + a));
        System.out.println();
    }

    // ── Demo verisi ─────────────────────────────────────────────────────────

    private static void seedDemoData() {
        try {
            Customer ali  = service.registerCustomer("Ali",  "Yılmaz", "ali@demo.com",  "0532 111 0001");
            Customer ayse = service.registerCustomer("Ayşe", "Kaya",   "ayse@demo.com", "0533 222 0002");
            Customer mert = service.registerCustomer("Mert", "Çelik",  "mert@demo.com", "0534 333 0003");

            Account a1 = service.openAccount(ali.getCustomerId(),  Account.AccountType.CHECKING, 5_000);
            Account a2 = service.openAccount(ali.getCustomerId(),  Account.AccountType.SAVINGS,  20_000);
            Account a3 = service.openAccount(ayse.getCustomerId(), Account.AccountType.CHECKING, 8_500);
            service.openAccount(mert.getCustomerId(), Account.AccountType.CHECKING, 1_200);

            service.deposit(a1.getAccountNumber(), 2_500);
            service.withdraw(a2.getAccountNumber(), 3_000);
            service.transfer(a1.getAccountNumber(), a3.getAccountNumber(), 1_000);
        } catch (Exception ignored) {}
    }

    // ── Yardımcılar ─────────────────────────────────────────────────────────

    private static void printBanner() {
        System.out.println("""
                ╔══════════════════════════════════════════╗
                ║     JAVA BANKING APPLICATION             ║
                ║     In-Memory Banking Simulation         ║
                ╚══════════════════════════════════════════╝
                  Demo verisi yüklendi (3 müşteri, 4 hesap)
                """);
    }

    private static void printMenu() {
        System.out.println("  ┌────────────────────────────────┐");
        System.out.println("  │  1) Yeni Müşteri Kaydı         │");
        System.out.println("  │  2) Hesap Açma                 │");
        System.out.println("  │  3) Para Yatırma               │");
        System.out.println("  │  4) Para Çekme                 │");
        System.out.println("  │  5) Transfer                   │");
        System.out.println("  │  6) Hesap Özeti                │");
        System.out.println("  │  7) İşlem Geçmişi              │");
        System.out.println("  │  8) Veritabanı Görünümü        │");
        System.out.println("  │  0) Çıkış                      │");
        System.out.println("  └────────────────────────────────┘");
    }

    private static void header(String t) {
        System.out.println("  ╔═══════════════════════════════════╗");
        System.out.printf ("  ║  %-33s║%n", t);
        System.out.println("  ╚═══════════════════════════════════╝");
    }

    private static void err(String msg) { System.out.println("\n  ✘ Hata: " + msg + "\n"); }

    private static String readStr(String p) { System.out.print("  " + p + ": "); return scanner.nextLine().trim(); }

    private static int readInt(String p) {
        System.out.print("  " + p + ": ");
        try { return Integer.parseInt(scanner.nextLine().trim()); } catch (Exception e) { return -1; }
    }

    private static double readDouble(String p) {
        System.out.print("  " + p + ": ");
        try { return Double.parseDouble(scanner.nextLine().trim().replace(",", ".")); }
        catch (Exception e) { throw new IllegalArgumentException("Geçersiz sayı."); }
    }
}
