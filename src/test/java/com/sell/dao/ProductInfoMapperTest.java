package com.sell.dao;

import com.sell.mapper.ProductInfoMapper;
import com.sell.pojo.ProductInfo;
import com.sell.pojo.ProductInfoExample;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author WangWei
 * @Title: ProductInfoMapperTest
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/15 18:23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoMapperTest {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Test
    public void selectByProductStatus() {
        ProductInfoExample example = new ProductInfoExample();
        example.createCriteria().andProductStatusEqualTo((byte)0);
        List<ProductInfo> productInfos = productInfoMapper.selectByExample(example);
        Assert.assertNotEquals(0,productInfos.size());
    }
}