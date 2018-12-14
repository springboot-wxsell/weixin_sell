package com.sell.controller;

import com.alibaba.druid.wall.WallSQLException;
import com.sell.WeixinSellApplication;
import com.sell.converter.OrderForm2OrderDTOConvert;
import com.sell.dto.OrderDTO;
import com.sell.enums.ResultEnum;
import com.sell.exception.WeiXinSellException;
import com.sell.form.OrderForm;
import com.sell.pojo.OrderDetail;
import com.sell.service.BuyerService;
import com.sell.service.OrderMasterService;
import com.sell.utils.ResultVOUtils;
import com.sell.vo.ResultVO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WangWei
 * @Title: BuyerOrderController
 * @ProjectName weixin_sell
 * @date 2018/10/23 17:40
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private BuyerService buyerService;
    // 创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error(" 【创建订单】 参数不正确, orderForm={}", orderForm);
            throw new WeiXinSellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO = OrderForm2OrderDTOConvert.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error(" 【创建订单】 购物车不能为空");
            throw new WeiXinSellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderMasterService.create(orderDTO);

        Map<String, String> result = new HashMap<>();
        result.put("orderId", createResult.getOrderId());
        return ResultVOUtils.success(result);
    }

    // 订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDetail>> list(@RequestParam("openid") String openid,
                                            @RequestParam(value = "page", defaultValue = "0") int page,
                                            @RequestParam(value = "size", defaultValue = "10") int size) {
        if (StringUtils.isEmpty(openid)) {
            log.error(" 【订单列表】 openid为空");
            throw new WeiXinSellException(ResultEnum.PARAM_ERROR);
        }
        List<OrderDTO> orderDTOList = orderMasterService.selectOrderList(openid, page, size);
        return ResultVOUtils.success(orderDTOList);
    }
    // 订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId")String orderId) {
        if (StringUtils.isEmpty(openid)) {
            log.error(" 【订单详情】 openid为空");
            throw new WeiXinSellException(ResultEnum.PARAM_ERROR);
        }
        if (StringUtils.isEmpty(orderId)) {
            log.error(" 【订单详情】 orderId为空");
            throw new WeiXinSellException(ResultEnum.PARAM_ERROR);
        }
        // 不安全，越权访问，需要改进
        OrderDTO orderDTO = buyerService.selectOrderOne(openid, orderId);
        return ResultVOUtils.success(orderDTO);
    }

    // 取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        if (StringUtils.isEmpty(openid)) {
            log.error(" 【订单详情】 openid为空");
            throw new WeiXinSellException(ResultEnum.PARAM_ERROR);
        }
        if (StringUtils.isEmpty(orderId)) {
            log.error(" 【订单详情】 orderId为空");
            throw new WeiXinSellException(ResultEnum.PARAM_ERROR);
        }

        //  不安全，越权访问，需要改进
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtils.success();
    }

}
