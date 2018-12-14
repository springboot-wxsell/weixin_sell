package com.sell.controller;

import com.sell.service.SecondsSkillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangWei
 * @Title: SecondsSkillController
 * @ProjectName weixin_sell
 * @date 2018/11/27 15:11
 * @description
 */
@RestController
@RequestMapping("/secskill")
@Slf4j
public class SecondsSkillController {

    @Autowired
    private SecondsSkillService secondsSkillService;

    /**
     * 查询秒杀活动的特价商品信息
     * @param productId
     * @return
     * @throws Exception
     */
    @GetMapping("/query/{productId}")
    public String query(@PathVariable String productId) throws Exception{
        return secondsSkillService.querySecondsSkillProductInfo(productId);
    }

    /**
     * 秒杀，没有抢到获得"哎呦喂, xxxx", 抢到了会返回剩余的库存量
     * @param productId
     * @return
     * @throws Exception
     */
    @GetMapping("/order/{productId}")
    public String skill(@PathVariable String productId) throws Exception {
        log.info("@secskill request, productId:" + productId);
        secondsSkillService.orderPorductMockDiffUser(productId);
        return secondsSkillService.querySecondsSkillProductInfo(productId);
    }
}
