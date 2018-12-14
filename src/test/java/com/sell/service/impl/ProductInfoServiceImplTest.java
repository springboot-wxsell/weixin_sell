package com.sell.service.impl;

import com.github.pagehelper.PageInfo;
import com.sell.enums.ProductStatusEnum;
import com.sell.pojo.ProductInfo;
import com.sell.pojo.ProductInfoExample;
import com.sell.service.ProductInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author WangWei
 * @Title: ProductInfoServiceImplTest
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/16 11:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoService productInfoService;

    @Test
    public void selectAll() {
        PageInfo<ProductInfo> productInfos = productInfoService.selectAll(0, 10);
        Assert.assertNotEquals(0, productInfos.getList().size());
    }

    @Test
    public void selectByProductId() {
        ProductInfo productInfo = productInfoService.selectOne("123456");
        Assert.assertEquals("123456", productInfo.getProductId());
    }

    @Test
    public void selectByProductStatus() {
        List<ProductInfo> productInfos = productInfoService.selectByProductStatus((byte) ProductStatusEnum.UP.getCode().intValue());
        Assert.assertNotEquals(0, productInfos.size());
    }

    @Test
    public void insert() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123458");
        productInfo.setProductName("芒果冰");
        productInfo.setProductPrice(new BigDecimal(10));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("冰冰的真的很爽");
        productInfo.setProductIcon("https://gss3.bdstatic.com/-Po3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=57fcd535e1f81a4c323fe49bb6430b3c/4034970a304e251fce0583b9ad86c9177e3e5384.jpg");
        productInfo.setProductStatus((byte)ProductStatusEnum.UP.getCode().intValue());
        productInfo.setCategoryType(3);
        int result = productInfoService.insert(productInfo);
        Assert.assertNotEquals(0, result);
    }


    @Test
    public void offSale() {
        int result = productInfoService.offSale("123456");
        Assert.assertTrue("更新数据条数", result >= 1);
    }

    @Test
    public void onSale() {
        int result = productInfoService.onSale("123456");
        Assert.assertTrue("更新数据条数", result >= 1);
    }
}