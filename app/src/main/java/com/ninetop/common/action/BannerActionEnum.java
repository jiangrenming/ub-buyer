package com.ninetop.common.action;

/**
 * Created by jill on 2016/12/29.
 */

public enum BannerActionEnum {
    TYPE_PRODUCT("1", null),
    TYPE_NEW_USER_GIFT("2", new NewUserGiftAction()),
    TYPE_SALE("3", new ProductSaleAction()),
    TYPE_LINK("4", new BannerWebViewAction()),
    TYPE_NONE("5", null);


    private String type;
    public BannerAction bannerAction;

    private BannerActionEnum(String type, BannerAction bannerAction){
        this.type = type;
        this.bannerAction = bannerAction;
    }

    public static BannerActionEnum getByKey(String key){
        for(BannerActionEnum value : values()){
            if(value.type.equals(key)){
                return value;
            }
        }

        return null;
    }


}
