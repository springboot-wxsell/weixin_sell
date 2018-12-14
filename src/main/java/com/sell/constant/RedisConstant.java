package com.sell.constant;

/**
 * @author WangWei
 * @Title: RedisConstant
 * @ProjectName weixin_sell
 * @date 2018/11/26 11:26
 * @description
 */
public interface RedisConstant {

    String TOKEN_PREFIX = "token_%S";

    Integer EXPIRE = 7200; // 以秒为单位，过期时长为2hour
}
