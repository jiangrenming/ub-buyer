package com.ninetop.common.constant;

/**
 * Created by jill on 2017/1/16.
 */

public enum PayTypeEnum {
    TYPE_BALANCE(1, "余额"),
    TYPE_ALIPAY(2,"支付宝"),
    TYPE_WECHAT(3, "微信");

    public int code;
    public String name;

    private PayTypeEnum(int code, String name){
        this.code = code;
        this.name = name;
    }
}
