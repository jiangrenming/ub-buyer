package com.ninetop.UB;

import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ninetop.UB.product.ProductBannerBean;
import com.ninetop.base.GlobalInfo;
import com.ninetop.base.Viewable;
import com.ninetop.bean.seller.SellerDetailBean;
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
 * Created by huangjinding on 2017/5/9.
 */

public class UbSellerService extends BaseService {
    public UbSellerService(Viewable context) {
        super(context);
    }

    private static String SUCCESS = "SUCCESS";
    private static String PICS = "pics";

    //联盟商家搜索
    public void getSellerSearch(String name, String city, ResultListener<List<UbSearchInfoBean>> resultListener) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("city", city);
        get(UrlConstant.SELLER_SEARCH, params, new CommonResponseListener<List<UbSearchInfoBean>>(context,
                resultListener, new TypeToken<List<UbSearchInfoBean>>() {
        }));
    }

    //联盟商家列表
    public void getSellerList(String city, int page, int pagesize, ResultListener resultListener) {
        Map<String, String> params = new HashMap<>();
        params.put("city", city);
        params.put("page", page + "");
        params.put("pageSize", pagesize + "");
        get(UrlConstant.SELLER_LIST, params, new CommonResponseListener<>(context,
                resultListener, new TypeToken<List<SellerBean>>() {
        }));
    }

    //联盟商家详情
    public void getSellerDetail(String latitude, String longtide, String sellerId, String userToken, ResultListener<SellerDetailBean> resultListener) {
        if (latitude == null || latitude.length() == 0) {
            latitude = "30.30589";
        }
        if (longtide == null || longtide.length() == 0) {
            longtide = "120.118026";
        }

        Map<String, String> params = new HashMap<>();
        userToken = GlobalInfo.userToken;
        if (TextUtils.isEmpty(userToken)) {
            userToken = "\"\"";
        }
        params.put("lat1", latitude);
        params.put("lng1", longtide);
        params.put("id", sellerId);
        params.put("token", userToken);
        get(UrlConstant.SELLER_DETAIL, params, new CommonResponseListener<SellerDetailBean>(context,
                resultListener, new TypeToken<SellerDetailBean>() {
        }));
    }


    //联盟商家分类列表
    public void getSellerCategory(String id, String city, String lat, String lng, ResultListener<List<SellerBean>> resultListener) {
        get(UrlConstant.SELLER_CATEGORY_LIST, null, new CommonResponseListener<List<SellerBean>>(context,
                resultListener, new TypeToken<List<SellerBean>>() {
        }));

    }

    //附近商家
    public void getNearbySellerCategory(String latitude, String longitude, String city, ResultListener<List<SellerBean>> resultListener) {
        if (latitude == null || latitude.length() == 0) {
            latitude = "30.30589";

        }
        if (longitude == null || longitude.length() == 0) {
            longitude = "120.118026";
        }
        if (city==null){
            city = "杭州市";
        }
        Map<String, String> params = new HashMap<>();
        params.put("lat1", latitude);
        params.put("lng1", longitude);
        params.put("city", city);
        get(UrlConstant.SELLER_NEARBY_LIST, params, new CommonResponseListener<List<SellerBean>>(context,
                resultListener, new TypeToken<List<SellerBean>>() {
        }));
    }

    //联盟商家具体分类入口
    public void getSellerCategoryFirst(ResultListener<List<SellerCategoryBean>> resultListener) {
        get(UrlConstant.SELLER_CATEGORY_LIST, null, new CommonResponseListener<List<SellerCategoryBean>>(context,
                resultListener, new TypeToken<List<SellerCategoryBean>>() {
        }));
    }

    //联盟商家具体分类入口
    public void getSellerCategorySecond(String sellerId, String sellerCity, String latitude, String longitude,
                                        ResultListener<List<SellerBean>> resultListener) {
        Map<String, String> params = new HashMap<>();
        params.put("id", sellerId);
        params.put("city", sellerCity);
        params.put("lat", latitude);
        params.put("lng", longitude);

        get(UrlConstant.SELLER_CATEGORY_LIST_DETAIL, params, new CommonResponseListener<List<SellerBean>>(context,
                resultListener, new TypeToken<List<SellerBean>>() {
        }));
    }

    //获取收藏列表
    public void getSellerCollectionList(ResultListener<List<UbSellerCollectBean>> resultListener) {
        get(UrlConstant.LIST_SELLER, null, new CommonResponseListener<>(context,
                resultListener, new TypeToken<List<UbSellerCollectBean>>() {
        }));
    }

    //添加收藏
    public void postSellerAdd(String id, ResultListener<String> resultListener) {
        Map<String, Object> map = new HashMap<>();
        map.put("shop_id", id);
        postJson(UrlConstant.ADD_SELLER, map, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle("");
            }
        });
    }

    //取消收藏
    public void postSellerCansel(String id, ResultListener<Object> resultListener) {
        Map<String, Object> map = new HashMap<>();
        map.put("shop_id", Integer.parseInt(id));
        postJson(UrlConstant.CANSEL_SELLER, map, new CommonResponseListener<Object>(context,
                resultListener, new TypeToken<Object>() {
        }));
    }

    //多项取消收藏
    public void postSellerMoreCansel(Number id1, Number id2, Number id3, ResultListener<String> resultListener) {
        Map<String, Object> map = new HashMap<>();
        map.put("shop_id", id1);
        map.put("shop_id", id2);
        map.put("shop_id", id3);

        postJson(UrlConstant.CANSEL_SELLER_MORE, map, new CommonResponseListener<String>(context,
                resultListener, new TypeToken<String>() {
        }));
    }

    //立即下单
    public void ComfirmSellerOrder(String shopId, String totalPrice, String remark, ResultListener<String> resultListener) {
        Map<String, Object> map = new HashMap<>();
        int id = Integer.parseInt(shopId);
        int price = Integer.parseInt(totalPrice);
        map.put("shop_id", id);
        map.put("total_price", price);
        map.put("remark", remark);

        postJson(UrlConstant.SELLER_CONFIRM_LIST, map, new CommonResponseListener<String>(context,
                resultListener, new TypeToken<String>() {
        }));
    }

    //获取消费账单
    public void getConsumeList(ResultListener<List<ConSumListBean>> resultListener) {
        get(UrlConstant.SELLER_CREATE_LIST, null, new CommonResponseListener(context,
                resultListener, new TypeToken<List<ConSumListBean>>() {
        }));
    }


    //联系客服
    public void getQuestion(ResultListener<List<OnLineAskBean>> resultListener) {
        get(UrlConstant.QUESTION_PLATFORM, null, new CommonResponseListener<List<OnLineAskBean>>(context,
                resultListener, new TypeToken<List<OnLineAskBean>>() {
        }));
    }

    //客服热线
    public void linePlatform(ResultListener<LineMobileBean> resultListener) {
        get(UrlConstant.LINE_PLATFORM, null, new CommonResponseListener<LineMobileBean>(context,
                resultListener, new TypeToken<LineMobileBean>() {
        }));
    }

    //关于平台
    public void aboutPlatform(ResultListener<AboutPlatformBean> resultListener) {
        get(UrlConstant.ABOUT_PLATFORM, null, new CommonResponseListener<AboutPlatformBean>(context,
                resultListener, new TypeToken<AboutPlatformBean>() {
        }));
    }

    //我要合作+联系方式
    public void joinPlatform(ResultListener<IntroductionBean> resultListener) {
        get(UrlConstant.JOIN_PLATFORM, null, new CommonResponseListener<IntroductionBean>(context,
                resultListener, new TypeToken<IntroductionBean>() {
        }));
    }

    //获取联盟商家首页banner
    public void getBannerImages(ResultListener<List<ProductBannerBean>> resultListener) {
        get(UrlConstant.SELLER_BANNER, null, new CommonResponseListener<List<ProductBannerBean>>(context,
                resultListener, new TypeToken<List<ProductBannerBean>>() {
        }));
    }

    //余额充值
    public void getMywalletPay(String userToken, ResultListener resultListener) {
        Map<String, String> params = new HashMap<>();

        params.put("token", userToken);
        get(UrlConstant.MYWALLET_PAY, params, new CommonResponseListener(context,
                resultListener, new TypeToken<String>() {
        }));
    }
}
