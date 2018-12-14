package com.sell.exception;

import com.sell.enums.ResultEnum;

/**
 * @author WangWei
 * @Title: WeiXinSellException
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/17 18:12
 */
public class WeiXinSellException extends RuntimeException {

    private Integer code;

    public WeiXinSellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public WeiXinSellException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}
