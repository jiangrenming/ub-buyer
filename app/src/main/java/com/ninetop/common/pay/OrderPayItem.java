package com.ninetop.common.pay;

/**
 * Created by jill on 2017/1/16.
 */

public class OrderPayItem {

    public int payType;
    public String orderNo;
    public String orderFrom;
    public String orderPrice;
    public boolean isSettingPWD;

    public OrderPayItem(int payType, String orderNo, String orderFrom, String orderPrice,boolean isSettingPWD) {
        this.payType = payType;
        this.orderNo = orderNo;
        this.orderFrom = orderFrom;
        this.orderPrice = orderPrice;
        this.isSettingPWD= isSettingPWD;
    }
}
