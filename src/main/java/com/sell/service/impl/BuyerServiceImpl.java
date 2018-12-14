package com.sell.service.impl;

import com.sell.dto.OrderDTO;
import com.sell.enums.ResultEnum;
import com.sell.exception.WeiXinSellException;
import com.sell.service.BuyerService;
import com.sell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author WangWei
 * @Title: BuyerServiceImpl
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/25 15:53
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderMasterService orderMasterService;
    @Override
    public OrderDTO selectOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (null == orderDTO) {
            log.error(" 【取消订单】 查询不到该订单, orderId={}", openid);
            throw new WeiXinSellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderMasterService.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid, String orderId) {
        OrderDTO orderDTO = orderMasterService.selectOne(orderId);
        if (null == orderDTO) {
            return null;
        }
        // 判断当前订单是否是自己的订单
        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)) {
            log.error(" 【查询订单】 订单的openid不一致. openid={}, orderDTO={}", openid, orderDTO);
            throw new WeiXinSellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
