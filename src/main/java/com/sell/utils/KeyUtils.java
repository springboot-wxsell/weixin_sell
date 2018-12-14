package com.sell.utils;

import java.util.Random;

/**
 *  唯一主键类
 * @author WangWei
 * @Title: KeyUtils
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/19 15:30
 */
public class KeyUtils {

    /**
     *  生成唯一主键
     *  格式： 时间戳+随机数
     * @return
     */
    public static synchronized String genUniqueKey() {
        Random random = new Random();
        Integer num = random.nextInt(900000) + 100000;
        return  System.currentTimeMillis() + String.valueOf(num);
    }
}
