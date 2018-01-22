package com.ninetop.adatper.index;

import com.ninetop.bean.index.category.CategoryBean;
import com.ninetop.fragment.index.CategoryProductFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jill on 2016/11/26.
 */

public class CategoryFragmentManager {

    private static Map<String, CategoryProductFragment> fragmentMap = new HashMap<>();


    public static CategoryProductFragment getInstance(CategoryBean bean){

        CategoryProductFragment fragment = fragmentMap.get(bean.getCatCode());
        if(fragment == null){
            fragment = new CategoryProductFragment();
            fragment.setCategory(bean);
        }

        return fragment;
    }
}
