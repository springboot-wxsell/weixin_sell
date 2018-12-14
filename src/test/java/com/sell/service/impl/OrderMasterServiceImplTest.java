package com.sell.service.impl;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.pagehelper.PageInfo;
import com.sell.dto.OrderDTO;
import com.sell.enums.OrderStatusEnum;
import com.sell.enums.PayStatusEnum;
import com.sell.pojo.OrderDetail;
import com.sell.pojo.OrderMaster;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author WangWei
 * @Title: OrderMasterServiceImplTest
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/19 16:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderMasterServiceImplTest {

    @Autowired
    private OrderMasterServiceImpl orderMasterService;

    private final String BUYER_OPENID = "abc123";

    private final String ORDER_ID = "1539942607477247677";

    @Test
    public void createOrder() {

        // 订单信息
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("赵睿");
        orderDTO.setBuyerAddress("my family");
        orderDTO.setBuyerPhone("17688998866");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        // 组装订单详情
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("123458");
        o1.setProductQuantity(1);
        orderDetailList.add(o1);
        OrderDetail o2 = new OrderDetail();
        o2.setProductId("123457");
        o2.setProductQuantity(2);
        orderDetailList.add(o2);
        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderMasterService.create(orderDTO);
        log.info(" 【创建订单】 result={}", result);
        Assert.assertNotNull(result);
    }

    @Test
    public void selectOne() {
        OrderDTO orderDTO = orderMasterService.selectOne(ORDER_ID);
        log.info("【查询单个订单】 result={}", orderDTO);
        Assert.assertEquals(ORDER_ID, orderDTO.getOrderId());
    }

    @Test
    public void selectOrderList() {
        List<OrderDTO> orderDTOList = orderMasterService.selectOrderList(BUYER_OPENID, 0, 10);
        Assert.assertNotEquals(0, orderDTOList.size());
    }

    @Test
    public void cancelOrder() {
        OrderDTO orderDTO = orderMasterService.selectOne(ORDER_ID);
        OrderDTO result = orderMasterService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(), new Integer(result.getOrderStatus()));
    }

    @Test
    public void finishOrder() {
        OrderDTO orderDTO = orderMasterService.selectOne(ORDER_ID);
        OrderDTO result = orderMasterService.finished(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(), new Integer(result.getOrderStatus()));
    }

    @Test
    public void payOrder() {
        OrderDTO orderDTO = orderMasterService.selectOne(ORDER_ID);
        OrderDTO result = orderMasterService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), new Integer(result.getPayStatus().byteValue()));
    }


    @Test
    public void selectAllOrderList() {
        PageInfo<OrderDTO> orderDTOPageInfo = orderMasterService.selectOrderList(0, 1);
        //Assert.assertNotEquals(0, orderDTOList.size());
        Assert.assertTrue("查询所有的订单列表", orderDTOPageInfo.getList().size() > 0);
        System.out.println(orderDTOPageInfo.getPages());
        System.out.println(orderDTOPageInfo.getTotal());
    }
}