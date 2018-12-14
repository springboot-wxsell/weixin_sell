package com.sell.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author WangWei
 * @Title: ResultVO
 * @ProjectName weixin_sell
 * @Description: TODO
 * @date 2018/10/16 15:21
 */

@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = -2986439909517175453L;

    private Integer code;

    private String message;

    private T data;
}
