package com.sell.service;

import com.sell.pojo.ProductCategory;

import java.util.List;

/**
 * @author WangWei
 * @Title: ProductCategoryService
 * @ProjectName sell
 * @Description: TODO
 * @date 2018/10/12 11:12
 */
public interface ProductCategoryService {

    ProductCategory selectOne(Integer categoryId);

    List<ProductCategory> selectAll();

    List<ProductCategory> selectByCategoryTypeIn(List<Integer> categoryTypeList);

    int insert(ProductCategory productCategory);

    int update(ProductCategory productCategory);

}
