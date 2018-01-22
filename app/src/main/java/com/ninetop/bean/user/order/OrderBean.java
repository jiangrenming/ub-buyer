package com.ninetop.bean.user.order;

import java.util.List;

/**
 * @date: 2016/11/16
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class OrderBean {
    /**
     * code : SUCCESS
     * data : {"count":8,"dataList":[{"code":"201612010919","complaintId":36,"complaintstatus":"C","complaintstype":"B","goodsCount":"2","goodsList":[{"count":"2","goodsCode":"182","goodsName":"方便速食2","icon":"http://oh309wv8t.bkt.clouddn.com/image/idnexgoods.png","price":"300.00"}],"indexcount":"2","indexicon":"http://oh309wv8t.bkt.clouddn.com/image/idnexgoods.png","indexname":"方便速食2","indexprice":"300.00","isCommented":"N","orderStatus":"A","orderedTime":"2016-11-29 11:49:01","realPay":"800.00"},{"code":"201612011529","complaintId":"","complaintstatus":"","complaintstype":"","goodsCount":"4","goodsList":[{"count":"2","goodsCode":"126","goodsName":"手机商品1","icon":"http://oh309wv8t.bkt.clouddn.com/image/idnexgoods.png","price":"300.00"},{"count":"2","goodsCode":"182","goodsName":"方便速食2","icon":"http://oh309wv8t.bkt.clouddn.com/image/idnexgoods.png","price":"300.00"}],"indexcount":"2","indexicon":"http://oh309wv8t.bkt.clouddn.com/image/idnexgoods.png","indexname":"手机商品1","indexprice":"300.00","isCommented":"N","orderStatus":"F","orderedTime":"2016-11-29 11:49:01","realPay":"800.00"}],"next":"0"}
     */


    /**
     * count : 8
     * dataList : [{"code":"201612010919","complaintId":36,"complaintstatus":"C","complaintstype":"B","goodsCount":"2","goodsList":[{"count":"2","goodsCode":"182","goodsName":"方便速食2","icon":"http://oh309wv8t.bkt.clouddn.com/image/idnexgoods.png","price":"300.00"}],"indexcount":"2","indexicon":"http://oh309wv8t.bkt.clouddn.com/image/idnexgoods.png","indexname":"方便速食2","indexprice":"300.00","isCommented":"N","orderStatus":"A","orderedTime":"2016-11-29 11:49:01","realPay":"800.00"},{"code":"201612011529","complaintId":"","complaintstatus":"","complaintstype":"","goodsCount":"4","goodsList":[{"count":"2","goodsCode":"126","goodsName":"手机商品1","icon":"http://oh309wv8t.bkt.clouddn.com/image/idnexgoods.png","price":"300.00"},{"count":"2","goodsCode":"182","goodsName":"方便速食2","icon":"http://oh309wv8t.bkt.clouddn.com/image/idnexgoods.png","price":"300.00"}],"indexcount":"2","indexicon":"http://oh309wv8t.bkt.clouddn.com/image/idnexgoods.png","indexname":"手机商品1","indexprice":"300.00","isCommented":"N","orderStatus":"F","orderedTime":"2016-11-29 11:49:01","realPay":"800.00"}]
     * next : 0
     */
    public String count;
    public String next;
    /**
     * code : 201612010919
     * complaintId : 36
     * complaintstatus : C
     * complaintstype : B
     * goodsCount : 2
     * goodsList : [{"count":"2","goodsCode":"182","goodsName":"方便速食2","icon":"http://oh309wv8t.bkt.clouddn.com/image/idnexgoods.png","price":"300.00"}]
     * indexcount : 2
     * indexicon : http://oh309wv8t.bkt.clouddn.com/image/idnexgoods.png
     * indexname : 方便速食2
     * indexprice : 300.00
     * isCommented : N
     * orderStatus : A
     * orderedTime : 2016-11-29 11:49:01
     * realPay : 800.00
     */

    public List<OrderListBean> dataList;



        /**
         * count : 2
         * goodsCode : 182
         * goodsName : 方便速食2
         * icon : http://oh309wv8t.bkt.clouddn.com/image/idnexgoods.png
         * price : 300.00
         */




    @Override
    public String toString() {
        return "UbOrderBean{" +
                "count=" + count +
                ", next='" + next + '\'' +
                ", dataList=" + dataList +
                '}';
    }
}
   /* code	String	订单code
    orderedTime	String	下单时间
    goodsCount	String	商品数量
    realPay	String	实际支付总金额
    orderStatus	String	订单状态
    isCommented	String	是否已经评价(Y:已经评价;N:未评价)
    complaintstatus	String	维权状态
    complaintId	String	维权单id

     goodsList	List	商品列表
    *//*
   *//* public String time;
    public String type;
    public int money;
    public int count;
    public int totalCount;*//*

    public String code;
    public String orderedTime;
    public String goodsCount;
    public String realPay;
    public String orderStatus;
    public String isCommented;
    public String complaintstatus;
    public String complaintId;

    public List<GoodsBean> goodsList;

    @Override
    public String toString() {
        return "UbOrderBean{" +
                ", code='" + code + '\'' +
                ", orderedTime='" + orderedTime + '\'' +
                ", goodsCount='" + goodsCount + '\'' +
                ", realPay='" + realPay + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", isCommented='" + isCommented + '\'' +
                ", complaintstatus='" + complaintstatus + '\'' +
                ", complaintId='" + complaintId + '\'' +
                ", goodsList=" + goodsList +
                '}';
    }*/


