package com.sell.service.impl;

import com.sell.mapper.ProductCategoryMapper;
import com.sell.pojo.ProductCategory;
import com.sell.pojo.ProductCategoryExample;
import com.sell.service.ProductCategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author WangWei
 * @Title: ProductCategoryServiceImplTest
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/16 21:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    @Autowired
    private ProductCategoryService productCategoryService;

    @Test
    public void selectOne() {
        ProductCategory productCategory = productCategoryService.selectOne(1);
        Assert.assertEquals(new Integer(1), productCategory.getCategoryId());
    }

    @Test
    public void selectAll() {
        List<ProductCategory> productCategoryList = productCategoryService.selectAll();
        Assert.assertNotEquals(0, productCategoryList.size());
    }

    @Test
    public void insert() {
    }

    @Test
    public void updateByCategoryId() {
    }

    @Test
    public void selectByCategoryType() {
        List<ProductCategory> productCategoryList = productCategoryService.selectByCategoryTypeIn(Arrays.asList(2, 3, 4));
        Assert.assertNotEquals(0, productCategoryList.size());
    }
}