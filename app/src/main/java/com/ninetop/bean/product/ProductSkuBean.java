package com.ninetop.bean.product;

import java.util.List;

/**
 * Created by jill on 2016/12/1.
 */

public class ProductSkuBean {

    public String itemCode;
    public String skuId;
    public String price;
    public String stock;
    public List<ProductSkuValueBean> valueList;

    @Override
    public String toString() {
        return "ProductSkuBean{" +
                "itemCode='" + itemCode + '\'' +
                ", skuId='" + skuId + '\'' +
                ", price='" + price + '\'' +
                ", stock='" + stock + '\'' +
                ", valueList=" + valueList +
                '}';
    }
}
