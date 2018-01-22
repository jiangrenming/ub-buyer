package com.ninetop.UB.product.UbOrder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangjinding on 2017/6/19.
 */

public class UbPreOrderBean implements Serializable {
    public String totalPay;
    public String ubPay;
    public String balancePay;
    public String moneyPay;
    public String totalProductPay;
    public String logisticsCost;
    public String detailAddress;
    public String mobile;
    public String addressOwner;
    public int addressId;
    public List<UbMainOrderBean> franchiseeList;

}
