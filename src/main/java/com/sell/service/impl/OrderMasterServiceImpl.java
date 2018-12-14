package com.sell.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sell.converter.OrderMaster2OrderDTOConvert;
import com.sell.dto.CartDTO;
import com.sell.dto.OrderDTO;
import com.sell.enums.OrderStatusEnum;
import com.sell.enums.PayStatusEnum;
import com.sell.enums.ResultEnum;
import com.sell.exception.WeiXinSellException;
import com.sell.mapper.OrderDetailMapper;
import com.sell.mapper.OrderMasterMapper;
import com.sell.pojo.*;
import com.sell.service.OrderMasterService;
import com.sell.service.ProductInfoService;
import com.sell.service.WebSocket;
import com.sell.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WangWei
 * @Title: OrderMasterServiceImpl
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/17 15:43
 */
@Service
@Slf4j
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderMasterMapper orderMasterMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        // 生成订单号
        String orderId = KeyUtils.genUniqueKey();
        // 订单总金额为0
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        // 1. 查询商品（数量、库存）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productInfoService.selectOne(orderDetail.getProductId());
            if (null == productInfo) {
                throw new WeiXinSellException(ResultEnum.PRODUCT_NOT_EXIT);
            }
            // 2.计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
            // 保存订单详情（orderDetail)
            orderDetail.setDetailId(KeyUtils.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail );
            orderDetailMapper.insert(orderDetail);
        }


        // 3. 保存订单数据（orderMaster)
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus( OrderStatusEnum.NEW.getCode().byteValue());
        orderMaster.setPayStatus( PayStatusEnum.WAIT.getCode().byteValue());
        orderMasterMapper.insert(orderMaster);

        // 4. 扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
            new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.decrementStock(cartDTOList);

        // 发送websocket消息
        webSocket.sendMessage("您有新的消息");
        return orderDTO;
    }

    @Override
    public OrderDTO selectOne(String orderId) {
        OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderId);
        if (null == orderMaster) {
             throw new WeiXinSellException(ResultEnum.ORDER_NOT_EXIST);
        }
        OrderDetailExample example = new OrderDetailExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<OrderDetail> orderDetailList = orderDetailMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new WeiXinSellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public List<OrderDTO> selectOrderList(String buyerOpenid, int pageNum, int pageSize) {
        OrderMasterExample example = new OrderMasterExample();
        example.createCriteria().andBuyerOpenidEqualTo(buyerOpenid);
        PageHelper.startPage(pageNum, pageSize);
        List<OrderMaster> orderMasterList = orderMasterMapper.selectByExample(example);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConvert.convert(orderMasterList);
        return orderDTOList;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode().byteValue())) {
            log.error(" 【取消订单】 订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new WeiXinSellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode().byteValue());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        int updateResult = orderMasterMapper.updateByPrimaryKeySelective(orderMaster);
        if (updateResult < 0) {
            log.error(" 【取消订单】 订单更新失败，orderMaster={}", orderMaster);
            throw new WeiXinSellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        // 返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error(" 【取消订单】 订单中无商品详情, orderDTO={}", orderDTO);
            throw new WeiXinSellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.incrementStock(cartDTOList);
        // 如果已支付，需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode().byteValue())) {
            //TODO
            System.out.println(1);
        }

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finished(OrderDTO orderDTO) {
        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode().byteValue())) {
            log.error(" 【完结订单】 订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new WeiXinSellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode().byteValue());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        int updateResult = orderMasterMapper.updateByPrimaryKeySelective(orderMaster);
        if (updateResult < 0) {
            log.error(" 【完结订单】 完结订单跟新失败, orderMaster={}", orderMaster);
            throw new WeiXinSellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode().byteValue())) {
            log.error(" 【支付订单】 订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new WeiXinSellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode().byteValue())) {
            log.error(" 【支付订单】 支付状态不正确, orderId={}, payStatus={}", orderDTO.getOrderId(), orderDTO.getPayStatus());
            throw new WeiXinSellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        // 修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode().byteValue());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        int updateResult = orderMasterMapper.updateByPrimaryKey(orderMaster);
        if (updateResult < 0) {
            log.error(" 【支付订单】 支付订单更新失败, orderMaster={}", orderMaster);
            throw new WeiXinSellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public PageInfo<OrderDTO> selectOrderList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        OrderMasterExample example = new OrderMasterExample();
        List<OrderMaster> orderMasterList =  orderMasterMapper.selectByExample(example);
        PageInfo<OrderMaster> orderMasterPageInfo = new PageInfo<>(orderMasterList);
        return OrderMaster2OrderDTOConvert.convert(orderMasterPageInfo);
    }
}
