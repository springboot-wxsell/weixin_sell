package com.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 *  商品(包含类目）
 * @author WangWei
 * @Title: ProductVO
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/16 15:59
 */
@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = -3283198625552688775L;

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;

}
