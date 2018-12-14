package com.sell.service.impl;

import com.sell.WeixinSellApplication;
import com.sell.exception.WeiXinSellException;
import com.sell.service.RedisLock;
import com.sell.service.SecondsSkillService;
import com.sell.utils.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WangWei
 * @Title: SecondsSkillServiceImpl
 * @ProjectName weixin_sell
 * @date 2018/11/27 14:49
 * @description
 */
@Service
public class SecondsSkillServiceImpl implements SecondsSkillService{

    private static int TIMEOUT = 10 * 1000; // 超时时间 10s

    @Autowired
    private RedisLock redisLock;

    /**
     * 元旦活动，皮蛋瘦肉粥特价，限量10000分
     */
    static Map<String, Integer> products;
    static Map<String, Integer> stock;
    static Map<String, String> orders;
    static {
        /**
         * 模拟多张表，商品信息表，库存表，秒杀成功订单表
         */
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456", 10000);
        stock.put("123456", 10000);
    }
    private String queryMap(String productId) {
        return "元旦活动，皮蛋瘦肉粥特价，限量份"
                + products.get(productId)
                + " 还剩：" + stock.get(productId) + " 份"
                + " 该商品成功下单用户数目："
                + orders.size() + " 人";
    }

    @Override
    public String querySecondsSkillProductInfo(String productId) {
        return this.queryMap(productId);
    }

    @Override
    public void orderPorductMockDiffUser(String productId) {
        // 加锁
        long time = System.currentTimeMillis() + TIMEOUT;
        if (!redisLock.lock(productId, String.valueOf(time))){
            throw new WeiXinSellException(101, "哎呦喂，人也太多了，换个姿势试试～～");
        }

        // 查询该商品的库存数，为0则活动结束
        Integer stockNum = stock.get(productId);
        if (stockNum.intValue() == 0) {
            throw new WeiXinSellException(100, "活动结束了");
        } else {
            // 2. 下单（模拟不同用户openid不同）
            orders.put(KeyUtils.genUniqueKey(), productId);
            // 3. 减库存
            stockNum = stockNum - 1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock.put(productId, stockNum);
        }

        // 解锁
        redisLock.unlock(productId, String.valueOf(time));
    }
}
