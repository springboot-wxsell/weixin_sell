package com.sell.handler;

import com.sell.exception.SellerAuthorizeException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author WangWei
 * @Title: ExceptionHandler
 * @ProjectName weixin_sell
 * @date 2018/11/26 18:39
 * @description
 */
@ControllerAdvice
public class SellExceptionHandler {

    // 拦截登录异常
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
        return new ModelAndView("redirect:");
    }
}
