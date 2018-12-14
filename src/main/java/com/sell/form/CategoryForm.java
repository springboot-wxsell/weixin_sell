package com.sell.form;

import lombok.Data;

/**
 *  类目表单
 * @author WangWei
 * @Title: CategoryForm
 * @ProjectName weixin_sell
 * @date 2018/11/13 21:00
 * @description
 */
@Data
public class CategoryForm {
    private Integer categoryId;

    private String categoryName;

    private Integer categoryType;
}
