package com.bank.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DepositCertificate {
    private String certId;
    private String accountNo;
    private String depositType;
    private double amount;
    private int term;
    private double rate;
    private Date startDate;
    private Date dueDate;

    public DepositCertificate(String certId, String accountNo, String depositType, double amount,
                              int term, double rate, Date startDate, Date dueDate) {
        this.certId = certId;
        this.accountNo = accountNo;
        this.depositType = depositType;
        this.amount = amount;
        this.term = term;
        this.rate = rate;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    public void showInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.printf("%s\t%s\t\t%.2f\t\t%d\t\t%.2f%%\t%s\n",
                certId, depositType, amount, term, rate * 100, sdf.format(startDate));
    }

    // Getters Only (存单信息只读)
    public String getCertId() {
        return certId;
    }

    public String getDepositType() {
        return depositType;
    }

    public double getAmount() {
        return amount;
    }

    public int getTerm() {
        return term;
    }

    public double getRate() {
        return rate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getDueDate() {
        return dueDate;
    }
}