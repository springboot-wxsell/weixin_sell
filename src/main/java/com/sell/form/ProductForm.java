package com.sell.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author WangWei
 * @Title: ProductForm
 * @ProjectName weixin_sell
 * @date 2018/11/8 21:27
 * @description 商品表单类
 */
@Data
public class ProductForm {

    /** 商品id. */
    private String productId;

    /** 商品名称. */
    private String productName;

    /** 商品价格. */
    private BigDecimal productPrice;

    /** 商品库存. */
    private Integer productStock;

    /** 商品描述. */
    private String productDescription;

    /** 商品小图. */
    private String productIcon;

    /** 类目编号. */
    private Integer categoryType;

}
