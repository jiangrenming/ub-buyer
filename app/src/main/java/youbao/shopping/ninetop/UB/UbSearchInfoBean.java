package youbao.shopping.ninetop.UB;

import java.io.Serializable;

/**
 * Created by huangjinding on 2017/5/25.
 */
public class UbSearchInfoBean  implements Serializable{
    public String id;
    public String name;
    public String per_consume;
    public String addr_province;
    public String addr_city;
    public String addr_county;
    public String addr_detail;
    public String telephone;
    public String ratio;
    public String icon1;

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

    public String getPer_consume() {
        return per_consume;
    }

//    public void setPer_consume(String per_consume) {
//        this.per_consume = per_consume;
//    }

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

//    public String getTelephone() {
//        return telephone;
//    }
//
//    public void setTelephone(String telephone) {
//        this.telephone = telephone;
//    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getIcon1() {
        return icon1;
    }

//    public void setIcon1(String icon1) {
//        this.icon1 = icon1;
//    }
}
