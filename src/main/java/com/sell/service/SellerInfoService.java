package com.sell.service;

import com.sell.pojo.SellerInfo;

/**
 * @author WangWei
 * @Title: SellerService
 * @ProjectName weixin_sell
 * @date 2018/11/16 20:01
 * @description
 */
public interface SellerInfoService {

    SellerInfo selectByOpenid(String openid);
}
