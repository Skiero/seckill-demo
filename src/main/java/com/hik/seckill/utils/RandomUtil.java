package com.hik.seckill.utils;

import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

/**
 * Created by wangJinChang on 2019/11/11 16:20
 * 随机数相关的工具类
 */
public class RandomUtil {

    private static SecureRandom random = new SecureRandom();

    /**
     * 通过random数字生成无-分隔符的UUID
     *
     * @return 随机uuid
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用SecureRandom随机生成Long
     *
     * @return 随机long
     */
    public static long randomLong() {
        return Math.abs(random.nextLong());
    }

    /**
     * 获取指定范围内的随机数
     *
     * @param min 随机数的范围下限
     * @param max 随机数的范围上限
     * @return 随机int；若入参不符合要求，则返回 -1
     */
    public static int randomLong(int min, int max) {
        if (min >= max) {
            return -1;
        }
        return new Random().nextInt(max - min) + min;
    }
}
