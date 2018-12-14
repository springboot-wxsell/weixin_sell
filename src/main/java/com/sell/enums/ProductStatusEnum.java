package com.sell.enums;

import lombok.Getter;

/**
 * @author WangWei
 * @Title: ProductStatusEnum
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/16 11:49
 */
@Getter
public enum ProductStatusEnum implements CodeEnum {
    UP(0, "上架"),
    DOWN(1, "下架")
    ;
    private Integer code;
    private String message;

    ProductStatusEnum (Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
