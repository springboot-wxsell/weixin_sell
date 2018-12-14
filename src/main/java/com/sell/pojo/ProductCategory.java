package com.sell.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class ProductCategory implements Serializable {
    /** 类目id. */
    private Integer categoryId;

    /** 类目名称. */
    private String categoryName;

    /** 类目编号. */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

}