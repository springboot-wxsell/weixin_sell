package com.sell.enums;

import lombok.Getter;

/**
 * @author WangWei
 * @Title: OrderStatusEnum
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/17 11:08
 */
@Getter
public enum  OrderStatusEnum implements CodeEnum{
    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "已取消"),
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
