package com.ninetop.UB.product.New;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangjinding on 2017/6/9.
 */

public class ShopCartItemListBean implements Serializable {
    public int amount;
    public int createUserId;
    public int productId;
    public String createTime;
    public String icon;
    public int shopCartId;
    public int stock;
    public String productName;
    public double productPrice;
    public int skuId;
    public List<SingleSkuBean> attrList;

}