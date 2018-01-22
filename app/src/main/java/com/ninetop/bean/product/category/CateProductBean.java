package com.ninetop.bean.product.category;

/**
 * @date: 2016/12/5
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class CateProductBean {
    public String picUrl;
    public String price;
    public String qualityScore;
    public String code;
    public String name;

    @Override
    public String toString() {
        return "CateProductBean{" +
                "picUrl='" + picUrl + '\'' +
                ", price='" + price + '\'' +
                ", qualityScore='" + qualityScore + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
