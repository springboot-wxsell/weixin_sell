package com.sell.dto;

import lombok.Data;

/**
 * 购物车
 *
 * @author WangWei
 * @Title: CartDTO
 * @ProjectName weixin_sell
 * @date 2018/10/17 17:50
 */
@Data
public class CartDTO {

    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
