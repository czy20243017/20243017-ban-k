package com.bank.test;

import com.bank.entity.BankAccount;
import com.bank.service.BankService;

public class BankServiceTest {
    public static void main(String[] args) {
        System.out.println("===== BankService 单元测试 =====");

        BankService service = new BankService();

        // 1. 开户测试
        boolean open1 = service.openAccount("10001", "123456", "张三");
        boolean open2 = service.openAccount("10001", "123456", "张三");
        System.out.println("首次开户：" + open1);
        System.out.println("重复开户：" + open2);

        // 2. 登录测试
        BankAccount account = service.login("10001", "123456");
        System.out.println("登录成功：" + (account != null));

        // 3. 存款测试
        boolean depositOk = service.deposit(account, 1000, "活期", 0);
        System.out.println("存款成功：" + depositOk);
    }
}