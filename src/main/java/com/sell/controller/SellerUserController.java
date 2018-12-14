package com.sell.controller;

import com.sell.constant.CookieConstant;
import com.sell.constant.RedisConstant;
import com.sell.enums.ResultEnum;
import com.sell.pojo.SellerInfo;
import com.sell.service.SellerInfoService;
import com.sell.utils.CookieUtils;
import com.sell.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author WangWei
 * @Title: SellerUserController
 * @ProjectName weixin_sell
 * @date 2018/11/26 9:28
 * @description 卖家用户
 */
@Controller
@RequestMapping("/seller/user")
public class SellerUserController {

    @Autowired
    private SellerInfoService sellerInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String, Object> result) {

        // 1. openid去数据库里面匹配数据
        SellerInfo sellerInfo = sellerInfoService.selectByOpenid(openid);
        if (sellerInfo == null) {
            result.put("msg", ResultEnum.LOGIN_FAIL.getMessage());
            result.put("url", "/weixin_sell/seller/order/list");
            return new ModelAndView("/common/error");
        }
        // 2. 设置token至redis
        String token = UUIDUtils.getUUID();
        Integer expire = RedisConstant.EXPIRE;

        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);

        // 3. 设置token值cookie
        CookieUtils.set(response, CookieConstant.TOKEN, token, expire);
        return new ModelAndView("redirect:" + "/seller/order/list");
    }
    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> result) {
        // 1. 从cookie 中查询
        Cookie cookie = CookieUtils.get(request, CookieConstant.TOKEN);
        if (cookie != null) {

            // 2. 清除redis
            stringRedisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            // 3. 清除cookie
            CookieUtils.set(response, CookieConstant.TOKEN, null, 0);
        }
        result.put("msg", ResultEnum.LOGOUT_SUCCESS.getMessage());
        result.put("url", "/weixin_sell/seller/order/list");
        return new ModelAndView("common/success", result);
    }
}
