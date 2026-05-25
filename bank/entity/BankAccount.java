package com.bank.entity;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String accountNo;
    private String password;
    private String userName;
    private double balance;
    private List<DepositCertificate> certificates;

    public BankAccount(String accountNo, String password, String userName) {
        this.accountNo = accountNo;
        this.password = password;
        this.userName = userName;
        this.balance = 0.0;
        this.certificates = new ArrayList<>();
    }

    public boolean verifyPassword(String pwd) {
        return this.password.equals(pwd);
    }

    public void addCertificate(DepositCertificate cert) {
        certificates.add(cert);
    }

    public void removeCertificate(DepositCertificate cert) {
        certificates.remove(cert);
    }

    public DepositCertificate findCertificateById(String certId) {
        for (DepositCertificate cert : certificates) {
            if (cert.getCertId().equals(certId)) {
                return cert;
            }
        }
        return null;
    }

    public void showAllCertificates() {
        if (certificates.isEmpty()) {
            System.out.println("您暂无存单记录。");
            return;
        }

        System.out.println("存单编号\t\t存款类型\t金额(元)\t存期(年)\t年利率\t存入日期");
        System.out.println("----------------------------------------------------------------------");
        for (DepositCertificate cert : certificates) {
            cert.showInfo();
        }
    }

    // Getters and Setters
    public String getAccountNo() {
        return accountNo;
    }

    public String getUserName() {
        return userName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}