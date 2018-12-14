package com.sell.test;

/**
 * @author WangWei
 * @Title: NumberSum
 * @ProjectName weixin_sell
 * @date 2018/11/21 12:33
 * @description 两个大整数求和
 */
public class NumberSum {

    public static String bigNumberSum(String bigNumberSumA, String bigNumberSumB) {
        // 1. 将两个大整数用数组逆序存储，整数长度等于较大整数+1
        int maxLength = bigNumberSumA.length() > bigNumberSumB.length() ? bigNumberSumA.length() : bigNumberSumB.length();
        int[] arrayA = new int[maxLength + 1];
        for (int i = 0; i < bigNumberSumA.length(); i++) {
            arrayA[i] = bigNumberSumA.charAt(bigNumberSumA.length() - i - 1) - '0';
        }
        int[] arrayB = new int[maxLength + 1];
        for (int i = 0; i < bigNumberSumB.length(); i++) {
            arrayB[i] = bigNumberSumB.charAt(bigNumberSumB.length() - i - 1) - '0';
        }
        // 2. 构建result数组，数组长度等于较大整数位数+1
        int[] result = new int[maxLength + 1];
        // 3. 遍历数组，按位相加
        for (int i = 0; i < result.length; i++) {
            int temp = result[i];
            temp += arrayA[i];
            temp += arrayB[i];
            // 判断是否进位
            if (temp >= 10) {
                temp = temp - 10;
                result[i + 1] = 1;
            }
            result[i] = temp;
        }
        // 4. 把result数组再次逆序并转为String
        StringBuilder sb = new StringBuilder();
        // 是否找到大整数的最高有效位
        boolean findFirst = false;
        for (int i = result.length - 1; i >= 0; i--) {
            if (!findFirst) {
                if (result[i] == 0) {
                    continue;
                }
                findFirst = true;
            }
            sb.append(result[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(bigNumberSum("426709752318", "95481253129"));
    }
}
