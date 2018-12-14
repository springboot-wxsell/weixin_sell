package com.sell.mapper;

import com.sell.pojo.SellerInfo;
import com.sell.pojo.SellerInfoExample;
import com.sell.utils.KeyUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author WangWei
 * @Title: SellerInfoMapperTest
 * @ProjectName weixin_sell
 * @date 2018/11/16 19:41
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoMapperTest {

    @Autowired
    private SellerInfoMapper sellerInfoMapper;
    @Test
    public void save() {
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtils.genUniqueKey());
        sellerInfo.setOpenid("abc123");
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        int result = sellerInfoMapper.insertSelective(sellerInfo);
        Assert.assertTrue("插入记录数：", result > 0);

    }

    @Test
    public void selectByOpenid() {
        SellerInfoExample example = new SellerInfoExample();
        example.createCriteria().andOpenidEqualTo("abc123");
        List<SellerInfo> sellerInfoList = sellerInfoMapper.selectByExample(example);
        Assert.assertEquals("abc123", sellerInfoList.get(0).getOpenid());
    }
}