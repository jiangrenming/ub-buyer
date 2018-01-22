package com.ninetop.bean.product;

/**
 * Created by jill on 2016/12/5.
 */
public class ProductSkuValueBean {

    public String id;
    public String name;
    public String value;

    @Override
    public boolean equals(Object obj) {
        ProductSkuValueBean bean = (ProductSkuValueBean) obj;
        return bean.name.equals(name) && bean.value.equals(bean.value);
    }

    @Override
    public String toString() {
        return "ProductSkuValueBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
