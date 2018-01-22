package com.ninetop.bean.user;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddressBean implements Serializable{
    /**
     * province : 浙江省
     * city : 杭州市
     * county : 西湖区
     * detail : 西湖
     * receiver : 李欧阳
     * tel : 12563214889
     * default : no
     * index : 2
     */

    public String province;
    public String city;
    public String county;
    public String detail;
    public String receiver;
    public String tel;
    @SerializedName("default")
    public String defaultX;
    public int index;

    @Override
    public String toString() {
        return "UbAddressBean{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", detail='" + detail + '\'' +
                ", receiver='" + receiver + '\'' +
                ", tel='" + tel + '\'' +
                ", defaultX='" + defaultX + '\'' +
                ", index=" + index +
                '}';
    }
}
