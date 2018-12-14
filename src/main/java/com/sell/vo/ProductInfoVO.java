package com.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品详情
 *
 * @author WangWei
 * @Title: ProductInfoVO
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/16 16:08
 */
@Data
public class ProductInfoVO implements Serializable {

    private static final long serialVersionUID = 6513880425277799169L;

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
