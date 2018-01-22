package com.ninetop.bean.seller;

import java.io.Serializable;

/**
 * Created by huangjinding on 2017/5/8.
 */
public class SellerSearchBean implements Serializable {
//            "id": "1",
//            "name": "外婆家(城西印象城店)",
//            "per_consume": "60",
//            "addr_province": "浙江",
//            "addr_city": "杭州",
//            "addr_county":"西湖区",
//            "addr_detail":"古墩路181号二楼202室",
//            "telephone":"0571-86541234",
//            "ratio":"10:1",
//            "icon1":"/upload/unoin_shop/4545.jpg"
   public String id;
    public String name;
    public Number per_consume;
    public String addr_province;
    public String addr_city;
    public String addr_county;
    public String addr_detail;
    public String telephone;
    public String ratio;
    public String icon1;


//    @Override
//    public String toString() {
//        return "UbAddressBean{" +
//                "province='" + province + '\'' +
//                ", city='" + city + '\'' +
//                ", county='" + county + '\'' +
//                ", detail='" + detail + '\'' +
//                ", receiver='" + receiver + '\'' +
//                ", tel='" + tel + '\'' +
//                ", defaultX='" + defaultX + '\'' +
//                ", index=" + index +
//                '}';
//    }
    @Override
    public String toString() {
        return "SellerSearchBean{" +

                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", per_consume=" + per_consume +
                ", addr_province='" + addr_province + '\'' +
                "addr_city='" + addr_city + '\'' +
                ", addr_county='" + addr_county + '\'' +
                ", addr_detail='" + addr_detail + '\'' +
                ", telephone='" + telephone + '\'' +
                ", ratio='" + ratio + '\'' +
                ", icon1='" + icon1 + '\'' +
                '}';
    }
}
