package com.bank.test;

import com.bank.utils.BankUtils;
import java.util.Date;

public class BankUtilsTest {
    public static void main(String[] args) {
        System.out.println("===== BankUtils 单元测试 =====");

        // 1. 活期利息测试
        double interest1 = BankUtils.calcCurrentInterest(1000, 0.003, 365);
        System.out.println("活期利息预期：3.0，实际：" + interest1);

        // 2. 定期利息测试
        double interest2 = BankUtils.calcFixedInterest(5000, 0.015, 2);
        System.out.println("定期利息预期：150.0，实际：" + interest2);

        // 3. 到期日计算测试
        Date dueDate = BankUtils.calculateDueDate(new Date(), 1);
        System.out.println("到期日：" + dueDate);

        // 4. 存单编号生成
        String certId = BankUtils.generateCertId();
        System.out.println("存单编号：" + certId);
        System.out.println("编号合法：" + (certId.startsWith("CD") && certId.length() == 10));

        // 5. 金额格式化
        String upper = BankUtils.amountToUpper(1234.56);
        System.out.println("金额格式化：" + upper);
    }
}