package com.sell.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sell.enums.CodeEnum;
import com.sell.enums.ProductStatusEnum;
import com.sell.utils.EnumUtils;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class ProductInfo implements Serializable {
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

    /** 商品状态. */
    private Byte productStatus = ProductStatusEnum.UP.getCode().byteValue();

    /** 类目编号. */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum() {
        return EnumUtils.getByCode(productStatus.intValue(), ProductStatusEnum.class);
    }

}