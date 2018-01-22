package com.ninetop.bean.seller;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by huangjinding on 2017/5/8.
 */
public class SellerDetailBean implements Serializable {
//           "id": "1",
//            "name": "外婆家(城西印象城店)",
//            "per_consume": "60",
//            "addr_province": "浙江",
//            "addr_city": "杭州",
//            "addr_county":"西湖区",
//            "addr_detail":"古墩路181号二楼202室",
//            "telephone":"0571-86541234",
//            "ratio":"10:1",
//            "icon":"{"/upload/unoin_shop/4545.jpg",
//            "/upload/unoin_shop/4545.jpg",
//            "/upload/unoin_shop/4545.jpg",
//            "/upload/unoin_shop/4545.jpg",
//            "/upload/unoin_shop/4545.jpg"},
//        "longitude":"120.12796",
//        "latitude":"30.305859",
//        "distance":"650.61"
//        "is_favor":"1"

    public String id;
    public String name;
    public Double per_consume;
    public String addr_province;
    public String addr_city;
    public String addr_county;
    public String addr_detail;

    public String telephone;
    public String ratio;
    public Double ratio4Count;
    public String[] icon;
    public String html_content;
    public String longitude;
    public String latitude;
    public String distance;
    public String is_favor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPer_consume() {
        return per_consume;
    }

    public void setPer_consume(Double per_consume) {
        this.per_consume = per_consume;
    }

    public String getAddr_province() {
        return addr_province;
    }

    public void setAddr_province(String addr_province) {
        this.addr_province = addr_province;
    }

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

    public Double getRatio4Count() {
        return ratio4Count;
    }

    public void setRatio4Count(Double ratio4Count) {
        this.ratio4Count = ratio4Count;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String[] getIcon() {
        return icon;
    }

    public void setIcon(String[] icon) {
        this.icon = icon;
    }

    public String getHtml_content() {
        return html_content;
    }

    public void setHtml_content(String html_content) {
        this.html_content = html_content;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getIs_favor() {
        return is_favor;
    }

    public void setIs_favor(String is_favor) {
        this.is_favor = is_favor;
    }

    @Override
    public String toString() {
        return "SellerDetailBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", per_consume=" + per_consume +
                ", addr_province='" + addr_province + '\'' +
                ", addr_city='" + addr_city + '\'' +
                ", addr_county='" + addr_county + '\'' +
                ", addr_detail='" + addr_detail + '\'' +
                ", telephone='" + telephone + '\'' +
                ", ratio='" + ratio + '\'' +
                ", ratio4Count=" + ratio4Count +
                ", icon=" + Arrays.toString(icon) +
                ", html_content='" + html_content + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", distance='" + distance + '\'' +
                ", is_favor='" + is_favor + '\'' +
                '}';
    }
}
