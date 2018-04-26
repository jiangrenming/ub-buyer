package youbao.shopping.ninetop.UB.order;

/**
 * Created by huangjinding on 2017/6/24.
 */

public class UbPayBean {
    public String orderCode;
    public double ubPay;
    public double balancePay;
    public double payPrice;
    public int payType;
    public  double totalPay;

    public double getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(double totalPay) {
        this.totalPay = totalPay;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public double getUbPay() {
        return ubPay;
    }

    public void setUbPay(double ubPay) {
        this.ubPay = ubPay;
    }

    public double getBalancePay() {
        return balancePay;
    }

    public void setBalancePay(double balancePay) {
        this.balancePay = balancePay;
    }

    public double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(double payPrice) {
        this.payPrice = payPrice;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }
}
