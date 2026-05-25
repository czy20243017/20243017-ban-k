package com.bank;

import com.bank.ui.MainUI;

public class BankSystem {
    // 全局常量：活期年利率0.3%，定期年利率1.5%
    public static final double SAVING_RATE = 0.003;
    public static final double FIXED_RATE = 0.015;

    // 程序入口方法
    public static void main(String[] args) {
        MainUI mainUI = new MainUI();
        mainUI.showMainMenu();
    }
}