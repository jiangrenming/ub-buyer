package com.ninetop.UB.cartshop;

import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ninetop.base.GlobalInfo;
import com.ninetop.base.Viewable;
import com.ninetop.common.constant.UrlConstant;
import com.ninetop.service.BaseService;
import com.ninetop.service.listener.BaseResponseListener;
import com.ninetop.service.listener.CommonResponseListener;
import com.ninetop.service.listener.ResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjinding on 2017/6/9.
 */

public class UbShopcartService extends BaseService {
    public UbShopcartService(Viewable context) {
        super(context);
    }

    private static String SUCCESS = "SUCCESS";
    private static String PICS = "pics";

    //添加购物车
    public void postSellerAdd(String token, int id, int providerNum, int skuId, int amount, String price, ResultListener<String> resultListener) {

        Map<String, Object> map = new HashMap<>();
        token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        map.put("token", token);
        map.put("productId", id);
        map.put("providerNum", providerNum);
        map.put("skuId", skuId);
        map.put("amount", amount);
        map.put("price", price);
        postJson(UrlConstant.UB_SHOPCART_ADD, map, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if ("200".equals(code)) {
                    context.showToast("添加成功");
                } else {
                    context.showToast("失败");
                }
            }
        });
    }

    //购物车列表
    public void getShopList(String token, ResultListener<List<UbShopcartService>> resultListener) {
        Map<String, String> params = new HashMap<>();
        token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        params.put("token", token);
        get(UrlConstant.UB_SHOP_LIST, params, new CommonResponseListener<List<UbShopcartService>>(context, resultListener, new
                TypeToken<List<UbShopcartService>>() {
                }));
    }


    //增减购物车商品数量
    public void postShopcartCount(String token, int id, int amount, int franchiseeId, int skuId, final ResultListener<String> resultListener) {
        Map<String, Object> params = new HashMap<>();
        token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        params.put("token", token);
        params.put("id", id);
        params.put("amount", amount);
        params.put("franchiseeId", franchiseeId);
        params.put("skuId", skuId);
        postJson(UrlConstant.UB_SHOP_AMOUNT, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if ("200".equals(code)) {
                    context.showToast("增减成功");
                } else {
                    context.showToast("失败");
                }
            }
        });
    }

    //删除购物车
    public void postShopcartDelete(String token, int id, final ResultListener<String> resultListener) {
        Map<String, Object> params = new HashMap<>();
        token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        params.put("token", token);
        params.put("id", id);
        postJson(UrlConstant.UB_SHOP_DELETE, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if ("200".equals(code)) {
                    context.showToast("删除成功");
                } else {
                    context.showToast("删除失败");
                }
            }
        });
    }

}
