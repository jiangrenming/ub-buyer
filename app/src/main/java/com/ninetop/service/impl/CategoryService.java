package com.ninetop.service.impl;

import com.google.gson.reflect.TypeToken;
import com.ninetop.base.Viewable;
import com.ninetop.bean.product.ProductBean;
import com.ninetop.bean.product.category.ProductCategoryBean;
import com.ninetop.bean.product.category.SecondCategoryBean;
import com.ninetop.bean.product.category.SecondProductBean;
import com.ninetop.common.constant.UrlConstant;
import com.ninetop.service.BaseService;
import com.ninetop.service.listener.CommonResponseListener;
import com.ninetop.service.listener.ResultListener;

import java.util.HashMap;
import java.util.List;

/**
 * @date: 2016/11/28
 * @author: Shelton
 * @version: 1.1.3
 * @Description:分类请求服务
 */
public class CategoryService extends BaseService {
    public CategoryService(Viewable context) {
        super(context);
    }

    public void getProductCategoryList(final ResultListener<List<ProductCategoryBean>> resultListener) {
        get(UrlConstant.PRODUCT_CATEGORY, new HashMap<String, String>(), new CommonResponseListener(context, resultListener, new TypeToken<List<ProductCategoryBean>>() {
        }));
    }

    public void getSecondCategoryList(String catCode,final ResultListener<List<SecondCategoryBean>> resultListener) {
        get(UrlConstant.PRODUCT_CATEGORY + "/" + catCode, new HashMap<String, String>(), new CommonResponseListener(context, resultListener, new TypeToken<List<SecondCategoryBean>>() {
        }));
    }

    public void getSecondCategoryList2(String catCode, ResultListener<List<ProductBean>> resultListener) {
        get(UrlConstant.PRODUCT_CATEGORY + "/" + catCode, new HashMap<String, String>(),
                new CommonResponseListener(context, resultListener, new TypeToken<List<ProductBean>>() {}));
    }

    /*public void getSecondCategoryProductList(ResultListener<List<CateProductBean>> resultListener, String page) {
        get(UrlConstant.SECOND_CATEGORY_PRODUCT + "/" + page, new HashMap<String, String>(), new CommonResponseListener(context, resultListener, new TypeToken<List<CateProductBean>>() {
        }));
    }*/

    public void getActivitySecondCategoryProductList(String code,String page,String orderCode,String orderType,ResultListener<SecondProductBean> resultListener) {
        HashMap map = new HashMap();
        map.put("page",page);
        map.put("orderCode",orderCode);
        map.put("orderType",orderType);

        get(UrlConstant.SECOND_CATEGORY_PRODUCT + "/" + code, map, new CommonResponseListener(context, resultListener, new TypeToken<SecondProductBean>() {
        }));
    }
}
