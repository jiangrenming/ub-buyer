package com.ninetop.common.constant;

/**
 * Created by jill on 2016/12/22.
 */

public enum AuthTypeEnum {

    REGISTER(1, "注册"),
    UPDATE_PASSWORD(2, "修改登陆密码"),
    FORGET_PASSWORD(3, "忘记登陆密码"),
    BIND_MOBILE(4, "绑定手机号"),
    UPDATE_BIND_MOBILE(5, "更改已绑定手机号"),
    SET_PAY_PSD(6, "设置支付密码"),
    FORGET_PAY_PSD(8, "忘记支付密码"),
    UPDATE_PAY_PSD(7, "修改支付密码"),
    VERIFY_IDENTITY(9, "验证身份");

    private int id;
    private String name;

    private AuthTypeEnum(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
