package com.sell.dao;

import com.sell.mapper.ProductCategoryMapper;
import com.sell.pojo.ProductCategory;
import com.sell.pojo.ProductCategoryExample;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author WangWei
 * @Title: ProductCategoryMapperTest
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/15 17:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Test
    public void selectByCategoryType() {
        ProductCategoryExample example= new ProductCategoryExample();
        example.createCriteria().andCategoryTypeEqualTo(2);
        List<ProductCategory> productCategories = productCategoryMapper.selectByExample(example);
        Assert.assertNotEquals(0, productCategories.size());
    }
}