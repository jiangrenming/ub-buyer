package com.ninetop.UB;

import java.io.Serializable;

/**
 * Created by huangjinding on 2017/5/16.
 */
public class IntroductionBean implements Serializable {
    public Number id;
    public String introduction;
    public String mobile;
    public String mobile_owner;

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile_owner() {
        return mobile_owner;
    }

    public void setMobile_owner(String mobile_owner) {
        this.mobile_owner = mobile_owner;
    }
}
