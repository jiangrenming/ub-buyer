package com.ninetop.bean.user.order;

import java.io.Serializable;

/**
 * @date: 2016/12/1
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class GoodsBean implements Serializable {
    /*    goodsCode	String	商品code
      icon	String	商品图标
      goodsName	String	商品名字
      price	String	价格
      count	String	商品数量*/
    public String goodsCode;
    public String icon;
    public String goodsName;
    public String price;
    public String count;

    public String skuId;

    public String complaintId;
    public String complaintstatus;
    public String complaintstype;
    public String realPay;

    @Override
    public String toString() {
        return "GoodsBean{" +
                "goodsCode='" + goodsCode + '\'' +
                ", icon='" + icon + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", price='" + price + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
