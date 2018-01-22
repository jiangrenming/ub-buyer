package com.ninetop.bean.order;

import com.ninetop.bean.product.ProductParamBean;

import java.util.List;

/**
 * Created by jill on 2016/12/1.
 */

public class ShopCartItemBean {

    public String id;
    public String goodsCode;
    public int amount;
    public float price;
    public String skuId;
    public String buyer;
    public String goodsName;
    public String icon1;
    public String icon2;
    public List<ProductParamBean> params;
    public String status;
    public String sumMoney;

    @Override
    public String toString() {
        return "ShopCartItemBean{" +
                "id='" + id + '\'' +
                ", goodsCode='" + goodsCode + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", skuId='" + skuId + '\'' +
                ", buyer='" + buyer + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", icon1='" + icon1 + '\'' +
                ", icon2='" + icon2 + '\'' +
                ", params=" + params +
                ", status='" + status + '\'' +
                ", sumMoney='" + sumMoney + '\'' +
                '}';
    }
}
