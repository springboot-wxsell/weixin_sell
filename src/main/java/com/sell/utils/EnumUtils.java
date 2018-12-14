package com.sell.utils;

import com.sell.enums.CodeEnum;

/**
 * @author WangWei
 * @Title: EnumUtils
 * @ProjectName weixin_sell
 * @date 2018/11/2 20:58
 * @description 通过类名的class获取枚举的code值
 */
public class EnumUtils {
    public static <T  extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (each.getCode().equals(code)) {
                return each;
            }
        }
        return null;
    }
}
