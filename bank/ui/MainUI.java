package com.bank.ui;

import com.bank.entity.BankAccount;
import com.bank.service.BankService;

import java.util.Scanner;

public class MainUI {
    private Scanner scanner;
    private BankService bankService;
    private BankAccount currentAccount;

    public MainUI() {
        scanner = new Scanner(System.in);
        bankService = new BankService();
        currentAccount = null;
    }

    public void showMainMenu() {
        while (true) {
            System.out.println("==================================");
            System.out.println("      居民个人储蓄存款业务系统      ");
            System.out.println("==================================");
            System.out.println("一、开户");
            System.out.println("二、登录");
            System.out.println("三、退出系统");
            System.out.println("==================================");
            System.out.print("请选择功能(输入数字1-3): ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    openAccount();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    exitSystem();
                    return;
                default:
                    System.out.println("输入错误，请输入1到3之间的数字！");
            }
        }
    }

    private void openAccount() {
        System.out.println("\n==================================");
        System.out.println("            开户业务              ");
        System.out.println("==================================");

        System.out.print("请输入账号(最多20位): ");
        String accountNo = scanner.nextLine().trim();

        System.out.print("请输入密码(最多16位): ");
        String password = scanner.nextLine().trim();

        System.out.print("请输入姓名(最多10位): ");
        String userName = scanner.nextLine().trim();

        boolean success = bankService.openAccount(accountNo, password, userName);
        if (success) {
            System.out.println("开户成功！请妥善保管您的账号和密码。");
        } else {
            System.out.println("开户失败！该账号已存在。");
        }
        System.out.println("==================================\n");
    }

    private void login() {
        System.out.println("\n==================================");
        System.out.println("            登录业务              ");
        System.out.println("==================================");

        System.out.print("请输入账号: ");
        String accountNo = scanner.nextLine().trim();

        System.out.print("请输入密码: ");
        String password = scanner.nextLine().trim();

        currentAccount = bankService.login(accountNo, password);
        if (currentAccount != null) {
            System.out.println("登录成功！欢迎您，" + currentAccount.getUserName() + "！");
            showUserMenu();
        } else {
            System.out.println("登录失败！账号或密码错误。");
        }
        System.out.println("==================================\n");
    }

    private void showUserMenu() {
        while (currentAccount != null) {
            System.out.println("\n==================================");
            System.out.println("          用户操作菜单            ");
            System.out.println("==================================");
            System.out.println("一、查看余额");
            System.out.println("二、查看存单");
            System.out.println("三、办理存款");
            System.out.println("四、办理取款");
            System.out.println("五、退出登录");
            System.out.println("==================================");
            System.out.print("请选择功能(输入数字1-5): ");

            int choice = getIntInput();
            switch (choice) {
                case 1:
                    queryBalance();
                    break;
                case 2:
                    queryCertificates();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    withdraw();
                    break;
                case 5:
                    logout();
                    return;
                default:
                    System.out.println("输入错误，请输入1到5之间的数字！");
            }
        }
    }

    private void queryBalance() {
        System.out.println("\n==================================");
        System.out.println("            余额查询              ");
        System.out.println("==================================");
        System.out.println("账号: " + currentAccount.getAccountNo());
        System.out.println("姓名: " + currentAccount.getUserName());
        System.out.printf("当前余额: %.2f 元\n", currentAccount.getBalance());
        System.out.println("==================================\n");
    }

    private void queryCertificates() {
        System.out.println("\n==================================");
        System.out.println("            存单查询              ");
        System.out.println("==================================");
        currentAccount.showAllCertificates();
        System.out.println("==================================\n");
    }

    private void deposit() {
        System.out.println("\n==================================");
        System.out.println("            存款业务              ");
        System.out.println("==================================");

        System.out.print("请输入存款金额: ");
        double amount = getDoubleInput();
        if (amount <= 0) {
            System.out.println("存款金额必须大于0！");
            return;
        }

        System.out.print("请选择存款类型(输入数字1-活期 2-定期): ");
        int typeChoice = getIntInput();
        String depositType;
        int term = 0;

        if (typeChoice == 1) {
            depositType = "活期";
        } else if (typeChoice == 2) {
            depositType = "定期";
            System.out.print("请输入存期(年): ");
            term = getIntInput();
            if (term <= 0) {
                System.out.println("存期必须大于0！");
                return;
            }
        } else {
            System.out.println("输入错误！");
            return;
        }

        boolean success = bankService.deposit(currentAccount, amount, depositType, term);
        if (success) {
            System.out.println("存款成功！");
        } else {
            System.out.println("存款失败！");
        }
        System.out.println("==================================\n");
    }

    private void withdraw() {
        System.out.println("\n==================================");
        System.out.println("            取款业务              ");
        System.out.println("==================================");

        System.out.print("请输入存单编号: ");
        String certId = scanner.nextLine().trim();

        boolean success = bankService.withdraw(currentAccount, certId);
        if (success) {
            System.out.println("取款成功！本息已转入您的账户余额。");
        } else {
            System.out.println("取款失败！存单不存在或已被支取。");
        }
        System.out.println("==================================\n");
    }

    private void logout() {
        currentAccount = null;
        System.out.println("已退出登录！");
    }

    private void exitSystem() {
        System.out.println("感谢使用居民个人储蓄存款业务系统，再见！");
        scanner.close();
    }

    private int getIntInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("输入错误，请输入数字: ");
            }
        }
    }

    private double getDoubleInput() {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.print("输入错误，请输入数字: ");
            }
        }
    }
}