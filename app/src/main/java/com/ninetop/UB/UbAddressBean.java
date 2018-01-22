package com.ninetop.UB;

import java.io.Serializable;

/**
 * Created by huangjinding on 2017/5/13.
 */
public class UbAddressBean implements Serializable {
    public String id;
    public String name;
    public String mobile;
    public String addr_province;
    public String addr_county;
    public String addr_city;
    public String addr_detail;
    public String post_code;
    public String is_default;

    public String getAddr_city() {
        return addr_city;
    }

    public void setAddr_city(String addr_city) {
        this.addr_city = addr_city;
    }

    public String getAddr_county() {
        return addr_county;
    }

    public void setAddr_county(String addr_county) {
        this.addr_county = addr_county;
    }

    public String getAddr_detail() {
        return addr_detail;
    }

    public void setAddr_detail(String addr_detail) {
        this.addr_detail = addr_detail;
    }

    public String getAddr_province() {
        return addr_province;
    }

    public void setAddr_province(String addr_province) {
        this.addr_province = addr_province;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    @Override
    public String toString() {
        return "UbAddressBean{" +
                "addr_city='" + addr_city + '\'' +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", addr_province='" + addr_province + '\'' +
                ", addr_county='" + addr_county + '\'' +
                ", addr_detail='" + addr_detail + '\'' +
                ", post_code='" + post_code + '\'' +
                ", is_default='" + is_default + '\'' +
                '}';
    }
}
