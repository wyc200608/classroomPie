package com.learn.backend.util;

import java.util.Random;

/**
 * 随机数生成工具类
 */
public final class RandomUtil {

    private static final Random RANDOM = new Random();

    private RandomUtil() {
    }

    /**
     * 生成指定长度的数字字符串
     */
    public static String generateNumericString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 生成8位邀请码
     */
    public static String generateInviteCode() {
        return generateNumericString(8);
    }
}
