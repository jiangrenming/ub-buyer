package com.ninetop.UB;

import java.io.Serializable;

/**
 * Created by huangjinding on 2017/5/9.
 */
public class UbSellerCollectBean implements Serializable {
    //
    //          {"favorshop_id":1,
    //            "name":"培培便当",
    //            "icon":"upload/unionshop/unionshipIcon11493692554746.png",
    //            "id":1,"per_consume":10,
    //            "ratio":"5:1"
    //          }


    public String favorshop_id;
    public String name;
    public String icon;
    public String id;
    public String per_consume;
    public String ratio;

    public String getFavorshop_id() {
        return favorshop_id;
    }

    public void setFavorshop_id(String favorshop_id) {
        this.favorshop_id = favorshop_id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

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

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getPer_consume() {
        return per_consume;
    }

    public void setPer_consume(String per_consume) {
        this.per_consume = per_consume;
    }

    @Override
    public String toString() {
        return "ub_collect{" +
                "favorshop_id='" + favorshop_id + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", id='" + id + '\'' +
                ", per_consume='" + per_consume + '\'' +
                ", ratio='" + ratio + '\'' +
                '}';
    }
}
