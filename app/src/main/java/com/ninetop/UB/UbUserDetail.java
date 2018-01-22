package com.ninetop.UB;

/**
 * Created by huangjinding on 2017/5/9.
 */
public class UbUserDetail {

    public String nick_name;
    public String mobile;
//    public String real_name;
    public String sex;
    public String status;
    public String birthday;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}