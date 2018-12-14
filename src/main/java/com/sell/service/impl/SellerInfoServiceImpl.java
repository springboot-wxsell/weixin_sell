package com.sell.service.impl;

import com.sell.mapper.SellerInfoMapper;
import com.sell.pojo.SellerInfo;
import com.sell.pojo.SellerInfoExample;
import com.sell.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author WangWei
 * @Title: SellerInfoServiceImpl
 * @ProjectName weixin_sell
 * @date 2018/11/16 20:02
 * @description
 */
@Service
public class SellerInfoServiceImpl implements SellerInfoService {

    @Autowired
    private SellerInfoMapper sellerInfoMapper;

    @Override
    public SellerInfo selectByOpenid(String openid) {
        if (!StringUtils.isEmpty(openid)) {
            SellerInfoExample example = new SellerInfoExample();
            example.createCriteria().andOpenidEqualTo(openid);
            List<SellerInfo> sellerInfoList = sellerInfoMapper.selectByExample(example);
            if (sellerInfoList != null || sellerInfoList.size() > 0) {
                return sellerInfoList.get(0);
            }
        }
        return null;
    }
}
