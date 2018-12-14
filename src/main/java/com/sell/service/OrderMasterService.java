package com.sell.service;

import com.github.pagehelper.PageInfo;
import com.sell.dto.OrderDTO;
import com.sell.pojo.OrderMaster;

import java.util.List;

/**
 * @author WangWei
 * @Title: OrderMasterService
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/17 11:30
 */
public interface OrderMasterService {

    /** 创建订单. */
    OrderDTO create(OrderDTO orderDTO);

    /** 查询单个订单. */
    OrderDTO selectOne(String orderId);

    /** 查询openid订单列表. */
    List<OrderDTO>  selectOrderList(String buyerOpenid, int pageNum, int pageSize);

    /** 取消订单. */
    OrderDTO cancel(OrderDTO orderDTO);

    /** 完结订单. */
    OrderDTO finished(OrderDTO orderDTO);

    /** 支付订单. */
    OrderDTO paid(OrderDTO orderDTO);

    /** 查询所有订单列表. */
    PageInfo<OrderDTO> selectOrderList(int pageNum, int pageSize);
}
