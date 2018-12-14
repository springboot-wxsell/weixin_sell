package com.sell.converter;

import com.github.pagehelper.PageInfo;
import com.sell.dto.OrderDTO;
import com.sell.pojo.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author WangWei
 * @Title: OrderMaster2OrderDTOConvert
 * @ProjectName weixin_sell
 * @date 2018/10/20 16:11
 */
public class OrderMaster2OrderDTOConvert {

    public static OrderDTO convert(OrderMaster orderMaster) {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
        return orderMasterList.stream().map(e ->
            convert(e)
        ).collect(Collectors.toList());
    }
    public static PageInfo<OrderDTO> convert(PageInfo<OrderMaster> orderMasterPageInfo) {
        PageInfo<OrderDTO> orderDTOPageInfo = new PageInfo<>();
        BeanUtils.copyProperties(orderMasterPageInfo, orderDTOPageInfo);
        orderDTOPageInfo.setList(convert(orderMasterPageInfo.getList()));
        return orderDTOPageInfo;
    }

}
