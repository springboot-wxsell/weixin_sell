package com.sell.utils;

import com.sell.vo.ResultVO;

/**
 * @author WangWei
 * @Title: ResultUtils
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/17 9:52
 */
public class ResultVOUtils {

    public static ResultVO success(Object object) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMessage("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success() {
        return  ResultVOUtils.success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMessage(msg);
        return resultVO;
    }
}
