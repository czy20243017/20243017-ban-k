package com.bank.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class BankUtils {
    // 私有构造方法，禁止实例化工具类
    private BankUtils() {}

    // 生成唯一存单编号
    public static String generateCertId() {
        return "CD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    // 计算定期存款到期日
    public static Date calculateDueDate(Date openDate, int term) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(openDate);
        calendar.add(Calendar.YEAR, term);
        return calendar.getTime();
    }

    // 计算活期利息（按天计算）
    public static double calcCurrentInterest(double balance, double rate, long days) {
        return balance * rate * days / 365;
    }

    // 计算定期利息
    public static double calcFixedInterest(double amount, double rate, int term) {
        return amount * rate * term;
    }

    // 金额大写转换（简化版）
    public static String amountToUpper(double amount) {
        return String.format("%.2f元", amount);
    }
}