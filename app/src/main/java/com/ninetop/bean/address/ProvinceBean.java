package com.ninetop.bean.address;

import com.bigkoo.pickerview.model.IPickerViewData;

/**
 * Created by Sai on 15/11/22.
 */
public class ProvinceBean implements IPickerViewData {
    private String name;

    public ProvinceBean(String name){

        this.name = name;

    }


    //这个用来显示在PickerView上面的字符串,PickerView会通过IPickerViewData获取getPickerViewText方法显示出来。
    @Override
    public String getPickerViewText() {
        return name;
    }
}
