package com.ninetop.bean.order;

import com.ninetop.bean.product.ProductSkuValueBean;

import java.util.List;

/**
 * Created by jill on 2016/12/1.
 */
public class OrderItemBean {

    public String itemName;
//    public String itemCode;
    public String price;
    public String amount;
    public String pic;
    public String skuId;
    public List<ProductSkuValueBean> paramsList;

}
