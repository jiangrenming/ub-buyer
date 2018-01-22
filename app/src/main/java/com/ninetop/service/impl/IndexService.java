package com.ninetop.service.impl;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ninetop.base.Viewable;
import com.ninetop.bean.index.BannerBean;
import com.ninetop.bean.index.NewsBean;
import com.ninetop.bean.index.SalePagingBean;
import com.ninetop.bean.index.category.CategoryBean;
import com.ninetop.bean.index.category.CategoryListBean;
import com.ninetop.bean.index.gift.UserGiftBean;
import com.ninetop.bean.index.message.MessageListBean;
import com.ninetop.bean.index.message.MessagePagingBean;
import com.ninetop.bean.index.recommend.RecommendBean;
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
 * Created by jill on 2016/11/24.
 */

public class IndexService extends BaseService{
    public IndexService(Viewable context) {
        super(context);
    }

    public void getBannerList(final ResultListener<List<BannerBean>> resultListener){
        get(UrlConstant.INDEX_BANNER, new HashMap<String, String>(), new CommonResponseListener(context, resultListener, new TypeToken<List<BannerBean>>(){}));
    }

    public void getNewsList(final ResultListener<List<NewsBean>> resultListener){
        get(UrlConstant.INDEX_NEWS, new HashMap<String, String>(), new CommonResponseListener(context, resultListener, new TypeToken<List<NewsBean>>(){}));
    }

    public void getRecommendList(final ResultListener<RecommendBean> resultListener){
        get(UrlConstant.INDEX_RECOMMEND_LIST, new HashMap<String, String>(), new CommonResponseListener(context, resultListener, new TypeToken<RecommendBean>(){}));
    }

    public void getCategoryList(final ResultListener<List<CategoryBean>> resultListener){
        get(UrlConstant.INDEX_CATEGORY_LIST, new HashMap<String, String>(), new CommonResponseListener(context, resultListener, new TypeToken<List<CategoryBean>>(){}));
    }

    public void getProductByCategory(String catCode, final ResultListener<List<CategoryBean>> resultListener){
        get(UrlConstant.INDEX_CATEGORY_LIST + "/" + catCode, new HashMap<String, String>(), new CommonResponseListener(context, resultListener, new TypeToken<List<CategoryBean>>(){}));
    }

    public void getIndexMenu(ResultListener<CategoryListBean> resultListener){
        get(UrlConstant.INDEX_TOP_MENU, null, new CommonResponseListener(context, resultListener, new TypeToken<CategoryListBean>(){}));
    }

    public void getSaleDetailList(String page, ResultListener<SalePagingBean> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("page", page);
        get(UrlConstant.PRODUCT_SECKILL_LIST, map, new CommonResponseListener(context, resultListener, new TypeToken<SalePagingBean>(){}));
    }

    public void getMessage(ResultListener<MessageListBean> resultListener){
        get(UrlConstant.MESSAGE_LIST, null, new CommonResponseListener(context, resultListener, new TypeToken<MessageListBean>(){}));
    }

    public void getMessageList(String url, ResultListener<MessagePagingBean> resultListener){
        get(url, null, new CommonResponseListener(context, resultListener, new TypeToken<MessagePagingBean>(){}));
    }

    public void getNewUserGiftList(ResultListener<List<UserGiftBean>> resultListener){
        get(UrlConstant.INDEX_NEW_USER_GIFT, null, new CommonResponseListener(context, resultListener, new TypeToken<List<UserGiftBean>>(){}));
    }

    public void receiveUserGift(String id, ResultListener<String> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("id",id);

        post(UrlConstant.INDEX_NEW_USER_GIFT_GET, map, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle("");
            }
        });
    }

    public void getAppVersion(){

    }


}
