package com.ninetop.common.constant;

import youbao.shopping.R;

/**
 * Created by jill on 2016/12/22.
 */

public enum SkuPropStatusEnum {

    UNSELECTED(0, "未选中", R.drawable.bg_border_gray_radius, R.color.text_black, true),
    SELECTED(1, "已选中", R.drawable.bg_border_gray_radius_select, R.color.white, false),
    DISABLED(-1, "禁止选中", R.drawable.bg_border_gray_radius_disabled, R.color.text_disable, false);

    private int id;
    private String name;
    private int bgDrawableResId;
    private int textColorId;
    private boolean enabled;

    private SkuPropStatusEnum(int id, String name, int bgDrawableResId, int textColorId, boolean enabled){
        this.id = id;
        this.name = name;
        this.bgDrawableResId = bgDrawableResId;
        this.textColorId = textColorId;
        this.enabled = enabled;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBgDrawableResId() {
        return bgDrawableResId;
    }

    public int getTextColorId() {
        return textColorId;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
