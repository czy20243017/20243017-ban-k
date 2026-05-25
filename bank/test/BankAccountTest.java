package com.bank.test;

import com.bank.entity.BankAccount;
import com.bank.entity.DepositCertificate;
import java.util.Date;

public class BankAccountTest {
    public static void main(String[] args) {
        System.out.println("===== BankAccount 单元测试 =====");

        BankAccount account = new BankAccount("622202123", "123456", "张三");

        // 1. 密码验证
        System.out.println("正确密码：" + account.verifyPassword("123456"));
        System.out.println("错误密码：" + account.verifyPassword("654321"));

        // 2. 添加存单
        DepositCertificate cert = new DepositCertificate(
                "CD12345678", "622202123", "定期", 1000, 1, 0.015, new Date(), null
        );
        account.addCertificate(cert);

        // 3. 查找存单
        DepositCertificate find = account.findCertificateById("CD12345678");
        System.out.println("查找成功：" + (find != null));

        // 4. 余额操作
        account.setBalance(5000);
        System.out.println("余额：" + account.getBalance());

        // 5. 删除存单
        account.removeCertificate(cert);
        System.out.println("删除后查找：" + (account.findCertificateById("CD12345678") == null));
    }
}