package com.sell.service.impl;

import com.sell.pojo.SellerInfo;
import com.sell.service.SellerInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author WangWei
 * @Title: SellerInfoServiceImplTest
 * @ProjectName weixin_sell
 * @date 2018/11/18 15:44
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoServiceImplTest {

    private static final String openid = "abc123";

    @Autowired
    private SellerInfoService sellerInfoService;

    @Test
    public void selectByOpenidTest() {
        SellerInfo sellerInfo = sellerInfoService.selectByOpenid(openid);
        Assert.assertEquals(openid, sellerInfo.getOpenid());
    }
}