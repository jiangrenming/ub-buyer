package com.ninetop.bean.user;


import java.util.List;

public class ChangingOrRefundBean {
    /**
     * next : 0
     * dataList : [{"complaintId":78,"complaintstatus":"P","returnMoney":45,"createdTime":"2016-12-08","goodsName":"九好优选B商品15九好优选B商品1九好优选B商品1九好优选B商品1九好优选B商品15","icon":"http://oh309wv8t.bkt.clouddn.com/image/idnexgoods.png","realPay":45,"amount":1},{"complaintId":77,"complaintstatus":"M","returnMoney":100,"createdTime":"2016-12-08","goodsName":"九好优选B商品13九好优选B商品1九好优选B商品1九好优选B商品1九好优选B商品13","icon":"http://oh309wv8t.bkt.clouddn.com/image/idnexgoods.png","realPay":800,"amount":2}]
     */

    public String next;
    /**
     * complaintId : 78
     * complaintstatus : P
     * returnMoney : 45
     * createdTime : 2016-12-08
     * goodsName : 九好优选B商品15九好优选B商品1九好优选B商品1九好优选B商品1九好优选B商品15
     * icon : http://oh309wv8t.bkt.clouddn.com/image/idnexgoods.png
     * realPay : 45
     * amount : 1
     */

    public List<DataListBean> dataList;

    public static class DataListBean {
        public String complaintId;
        public String complaintstatus;
        public String returnMoney;
        public String createdTime;
        public String price;
        public String goodsName;
        public String icon;
        public String realPay;
        public String amount;
    }
}
