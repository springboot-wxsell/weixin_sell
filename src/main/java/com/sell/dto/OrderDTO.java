package com.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sell.enums.OrderStatusEnum;
import com.sell.enums.PayStatusEnum;
import com.sell.pojo.OrderDetail;
import com.sell.utils.EnumUtils;
import com.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  订单信息（包含订单详情）
 *
 * @author WangWei
 * @Title: OrderDTO
 * @ProjectName weixin_sell
 * @date 2018/10/17 15:31
 */
@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) 过期用法
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    /** 订单id. */
    private String orderId;

    /** 买家名称. */
    private String buyerName;

    /** 买家电话. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为新下单 */
    private Byte orderStatus;

    /** 支付状态, 默认为未支付 */
    private Byte payStatus;

    /** 创建时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间. */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /** 订单详情. */
    private List<OrderDetail> orderDetailList;

    /** 获取订单状态枚举值，用于前台页面. */
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtils.getByCode(orderStatus.intValue(), OrderStatusEnum.class);
    }

    /** 获取支付状态枚举值，用于前台页面. */
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtils.getByCode(payStatus.intValue(), PayStatusEnum.class);
    }
}
