package com.ninetop.common;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jill on 2016/11/9.
 */

public class NinetopStatusEnum {

    public static final NinetopStatusEnum CATEGORY_TUIJIAN = new NinetopStatusEnum(0, "推荐");
    public static final NinetopStatusEnum CATEGORY_SHIPIN = new NinetopStatusEnum(1, "食品饮料");
    public static final NinetopStatusEnum CATEGORY_MUYING = new NinetopStatusEnum(2, "母婴玩具");
    public static final NinetopStatusEnum CATEGORY_CHUWEI = new NinetopStatusEnum(3, "厨卫清洁");
    public static final NinetopStatusEnum CATEGORY_JIATING = new NinetopStatusEnum(4, "家庭生活");
    public static final NinetopStatusEnum CATEGORY_YUNDONG = new NinetopStatusEnum(5, "运动户外");
    public static final NinetopStatusEnum CATEGORY_FUSHI = new NinetopStatusEnum(6, "服饰鞋靴");
    public static final NinetopStatusEnum CATEGORY_JIANGKANG = new NinetopStatusEnum(7, "健康保健");
    public static final NinetopStatusEnum CATEGORY_SHUMA = new NinetopStatusEnum(8, "数码电子");

    private static final NinetopStatusEnum[] categoryArray = new NinetopStatusEnum[] { CATEGORY_TUIJIAN, CATEGORY_SHIPIN, CATEGORY_MUYING, CATEGORY_CHUWEI, CATEGORY_JIATING,
            CATEGORY_YUNDONG,CATEGORY_FUSHI,CATEGORY_JIANGKANG, CATEGORY_SHUMA};

    private static List<NinetopStatusEnum> categoryList = null;

    private int type;
    private String name;

    private NinetopStatusEnum(int type, String name) {
        super();
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public static List<NinetopStatusEnum> getCategoryList() {
        if (categoryList == null) {
            categoryList = Arrays.asList(categoryArray);
        }
        return categoryList;
    }

    public String toString(){
        return name;
    }
}
