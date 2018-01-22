package com.ninetop.UB.product.UbOrder;

/**
 * Created by huangjinding on 2017/6/22.
 */

public class PayBean {

    public Double totalPay;
    public Double totalProductPay;
    public Double ubPay;
    public Double balancePay;
    public Double moneyPay;
    public String logisticsCost;
    public String orderCode;

    public Double getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(Double totalPay) {
        this.totalPay = totalPay;
    }

    public Double getTotalProductPay() {
        return totalProductPay;
    }

    public void setTotalProductPay(Double totalProductPay) {
        this.totalProductPay = totalProductPay;
    }

    public Double getUbPay() {
        return ubPay;
    }

    public void setUbPay(Double ubPay) {
        this.ubPay = ubPay;
    }

    public Double getBalancePay() {
        return balancePay;
    }

    public void setBalancePay(Double balancePay) {
        this.balancePay = balancePay;
    }

    public Double getMoneyPay() {
        return moneyPay;
    }

    public void setMoneyPay(Double moneyPay) {
        this.moneyPay = moneyPay;
    }

    public String getLogisticsCost() {
        return logisticsCost;
    }

    public void setLogisticsCost(String logisticsCost) {
        this.logisticsCost = logisticsCost;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
