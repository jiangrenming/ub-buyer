package com.ninetop.UB.product;

/**
 * Created by huangjinding on 2017/6/23.
 */

public class ProductRecommendBean {
    public int franchisee_id;
    public int count_month;
    public String price;
    public int product_id;
    public String product_name;
    public String icon;

    public int getFranchisee_id() {
        return franchisee_id;
    }

    public void setFranchisee_id(int franchisee_id) {
        this.franchisee_id = franchisee_id;
    }

    public int getCount_month() {
        return count_month;
    }

    public void setCount_month(int count_month) {
        this.count_month = count_month;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
