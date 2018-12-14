package com.sell.enums;

import lombok.Getter;

/**
 * @author WangWei
 * @Title: PayStatusEnum
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/17 11:16
 */
@Getter
public enum  PayStatusEnum implements CodeEnum {

    WAIT(0, "待支付"),
    SUCCESS(1, "已支付"),
    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
