package com.sell.service;

import com.sell.dto.OrderDTO;

/**
 * @author WangWei
 * @Title: BuyerService
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/25 15:47
 */
public interface BuyerService {
    // 查询一个订单
    OrderDTO selectOrderOne(String openid, String orderId);

    // 取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
