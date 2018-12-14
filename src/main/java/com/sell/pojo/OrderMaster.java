package com.sell.pojo;

import com.sell.enums.OrderStatusEnum;
import com.sell.enums.PayStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单
 */
@Data
public class OrderMaster implements Serializable {
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
    private Byte orderStatus = (byte) OrderStatusEnum.NEW.getCode().intValue();

    /** 支付状态, 默认为未支付 */
    private Byte payStatus = (byte) PayStatusEnum.WAIT.getCode().intValue();

    /** 创建时间. */
    private Date createTime;

    /** 更新时间. */
    private Date updateTime;

}