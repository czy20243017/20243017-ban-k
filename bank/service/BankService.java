package com.bank.service;

import com.bank.BankSystem;
import com.bank.entity.BankAccount;
import com.bank.entity.DepositCertificate;
import com.bank.utils.BankUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankService {
    private List<BankAccount> accounts;
    private int accountCount;

    public BankService() {
        accounts = new ArrayList<>();
        accountCount = 0;
    }

    // 开户
    public boolean openAccount(String accountNo, String password, String userName) {
        for (BankAccount account : accounts) {
            if (account.getAccountNo().equals(accountNo)) {
                return false;
            }
        }
        BankAccount newAccount = new BankAccount(accountNo, password, userName);
        accounts.add(newAccount);
        accountCount++;
        return true;
    }

    // 登录
    public BankAccount login(String accountNo, String password) {
        for (BankAccount account : accounts) {
            if (account.getAccountNo().equals(accountNo) && account.verifyPassword(password)) {
                return account;
            }
        }
        return null;
    }

    // 存款
    public boolean deposit(BankAccount account, double amount, String depositType, int term) {
        if (account == null || amount <= 0) return false;

        String certId = BankUtils.generateCertId();
        double rate;
        Date dueDate = null;

        if (depositType.equals("活期")) {
            rate = BankSystem.SAVING_RATE;
        } else if (depositType.equals("定期")) {
            rate = BankSystem.FIXED_RATE;
            dueDate = BankUtils.calculateDueDate(new Date(), term);
        } else {
            return false;
        }

        DepositCertificate cert = new DepositCertificate(
                certId, account.getAccountNo(), depositType, amount, term, rate, new Date(), dueDate
        );
        account.addCertificate(cert);
        return true;
    }

    // ====================== 关键：取款 + 到期判断 + 利息计算 ======================
    public boolean withdraw(BankAccount account, String certId) {
        if (account == null || certId == null || certId.isEmpty()) return false;

        DepositCertificate cert = account.findCertificateById(certId);
        if (cert == null) {
            System.out.println("存单不存在！");
            return false;
        }

        double interest = 0.0;
        Date now = new Date();
        double principal = cert.getAmount();
        String type = cert.getDepositType();

        System.out.println("==================================");
        System.out.println("          正在处理取款业务         ");
        System.out.println("==================================");

        // -------------------- 活期利息计算 --------------------
        if (type.equals("活期")) {
            long days = (now.getTime() - cert.getStartDate().getTime()) / (1000 * 60 * 60 * 24);
            interest = BankUtils.calcCurrentInterest(principal, cert.getRate(), days);
            System.out.println("存款类型：活期");
            System.out.println("已存天数：" + days);
        }

        // -------------------- 定期：判断是否到期 --------------------
        else if (type.equals("定期")) {
            System.out.println("存款类型：定期");
            if (now.after(cert.getDueDate())) {
                System.out.println("状态：已到期 → 按定期利率计算利息");
                interest = BankUtils.calcFixedInterest(principal, cert.getRate(), cert.getTerm());
            } else {
                System.out.println("状态：未到期 → 按活期利率计算利息");
                long days = (now.getTime() - cert.getStartDate().getTime()) / (1000 * 60 * 60 * 24);
                interest = BankUtils.calcCurrentInterest(principal, BankSystem.SAVING_RATE, days);
            }
        }

        // 本息合计
        double total = principal + interest;

        // 加入余额
        account.setBalance(account.getBalance() + total);

        // 删除存单
        account.removeCertificate(cert);

        // 打印凭证（实验报告必须截图）
        System.out.println("——————————————————————————————————");
        System.out.println("本金：" + principal + " 元");
        System.out.println("利息：" + interest + " 元");
        System.out.println("本息合计：" + total + " 元");
        System.out.println("==================================");
        System.out.println("取款成功！本息已转入账户余额！");

        return true;
    }
}