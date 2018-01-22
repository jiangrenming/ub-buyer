package com.ninetop.bean.user.order;

import java.util.List;

/**
 * @date: 2016/12/6
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class OrderListBean {
    public String code;
    public String orderedTime;
    public String goodsCount;
    public String realPay;
    public String orderStatus;
    public String isCommented;

    public String complaintId;
    public String complaintstatus;
    public String complaintstype;

    public String indexicon;
    public String indexprice;
    public String indexname;
    public String indexcount;
    public String indexcomplaintstatus;




    public List<GoodsBean> goodsList;

    @Override
    public String toString() {
        return "OrderListBean{" +
                "code='" + code + '\'' +
                ", complaintId='" + complaintId + '\'' +
                ", complaintstatus='" + complaintstatus + '\'' +
                ", complaintstype='" + complaintstype + '\'' +
                ", goodsCount='" + goodsCount + '\'' +
                ", indexcount='" + indexcount + '\'' +
                ", indexicon='" + indexicon + '\'' +
                ", indexname='" + indexname + '\'' +
                ", indexprice='" + indexprice + '\'' +
                ", isCommented='" + isCommented + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderedTime='" + orderedTime + '\'' +
                ", realPay='" + realPay + '\'' +
                ", goodsList=" + goodsList +
                '}';
    }
}
