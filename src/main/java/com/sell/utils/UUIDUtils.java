package com.sell.utils;

import java.util.UUID;

/**
 * @author WangWei
 * @Title: UUIDUtils
 * @ProjectName weixin_sell
 * @date 2018/11/26 11:19
 * @description 将uuid的"-" 去掉
 */
public class UUIDUtils {

    /**
     * 返回唯一串，格式32位字符
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
