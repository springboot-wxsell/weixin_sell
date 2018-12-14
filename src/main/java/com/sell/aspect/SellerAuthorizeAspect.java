package com.sell.aspect;

import com.sell.constant.CookieConstant;
import com.sell.constant.RedisConstant;
import com.sell.exception.SellerAuthorizeException;
import com.sell.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author WangWei
 * @Title: SellerAuthorizeAspect
 * @ProjectName weixin_sell
 * @date 2018/11/26 14:55
 * @description
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Pointcut("execution(public * com.sell.controller.Seller*.*(..))"  +
            "&& !execution(public * com.sell.controller.SellerUserController.*(..))")
    public void verify() {}

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 去 cookie中查询
        Cookie[] cookies = request.getCookies();
        Cookie cookie = CookieUtils.get(request, CookieConstant.TOKEN);
        if (cookies == null) {
            log.warn("【登录校验】 Cookie中查不到token");
            throw new SellerAuthorizeException();
        }
        // 去redis 中查询
        String tokenValue = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
        if (StringUtils.isEmpty(tokenValue)) {
            log.warn("【登录校验】 Redis中查不到token");
            throw new SellerAuthorizeException();
        }
    }
}
