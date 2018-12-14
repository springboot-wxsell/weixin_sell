package com.sell.controller;

import com.github.pagehelper.PageInfo;
import com.sell.dto.OrderDTO;
import com.sell.enums.ResultEnum;
import com.sell.exception.WeiXinSellException;
import com.sell.pojo.OrderMaster;
import com.sell.service.OrderMasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 *  卖家端订单
 * @author WangWei
 * @Title: SellerOrderController
 * @ProjectName weixin_sell
 * @date 2018/10/31 22:05
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderMasterService orderMasterService;

    /**
     * 订单列表
     * @param page
     * @param size
     * @param result
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list (@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "size", defaultValue = "10") Integer size,
                              Map<String, Object> result) {
        PageInfo<OrderDTO> orderDTOPage = orderMasterService.selectOrderList(page, size);
        result.put("orderDTOPage", orderDTOPage);
        result.put("currentPage", page);
        result.put("size", size);
        return  new ModelAndView("order/list", result);
    }

    /**
     * 取消订单
     * @param orderId
     * @param result
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String, Object> result) {
        try {
            OrderDTO orderDTO = orderMasterService.selectOne(orderId);
            orderMasterService.cancel(orderDTO);
        } catch (WeiXinSellException e) {
            log.error(" 【卖家端取消订单】 查询订单异常{}", e);
            result.put("msg", e.getMessage());
            result.put("url", "/weixin_sell/seller/order/list");
            return new ModelAndView("common/error", result);
        }
        result.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        result.put("url", "/weixin_sell/seller/order/list");
        return new ModelAndView("common/success", result);
    }

    /**
     * 订单详情
     *
     * @param orderId
     * @param result
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> result) {
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderMasterService.selectOne(orderId);
        }catch (WeiXinSellException e) {
            log.error(" 【卖家端查询订单详情】 查询订单异常{}", e);
            result.put("msg", e.getMessage());
            result.put("url", "/weixin_sell/seller/order/list");
            return new ModelAndView("common/error", result);
        }
        result.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", result);
    }

    /**
     * 完结订单
     * @param orderId
     * @param result
     * @return
     */
    @GetMapping("/finished")
    public ModelAndView finished(@RequestParam("orderId") String orderId,
                               Map<String, Object> result) {
        OrderDTO orderDTO = new OrderDTO();
        try {
            orderDTO = orderMasterService.selectOne(orderId);
            orderMasterService.finished(orderDTO);
        } catch (WeiXinSellException e) {
            log.error(" 【卖家端完结订单】 查询订单异常{}", e);
            result.put("msg", e.getMessage());
            result.put("url", "/weixin_sell/seller/order/list");
            return new ModelAndView("common/error", result);
        }
        result.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMessage());
        result.put("url", "/weixin_sell/seller/order/list");
        return new ModelAndView("common/success", result);
    }
}
