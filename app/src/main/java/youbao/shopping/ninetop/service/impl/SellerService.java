package youbao.shopping.ninetop.service.impl;

import com.google.gson.reflect.TypeToken;
import youbao.shopping.ninetop.base.Viewable;
import youbao.shopping.ninetop.bean.seller.SellerBannerBean;
import youbao.shopping.ninetop.bean.seller.SellerDetailBean;
import youbao.shopping.ninetop.bean.seller.SellerSearchBean;
import youbao.shopping.ninetop.common.constant.UrlConstant;
import youbao.shopping.ninetop.service.BaseService;
import youbao.shopping.ninetop.service.listener.CommonResponseListener;
import youbao.shopping.ninetop.service.listener.ResultListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangjinding on 2017/5/8.
 */
public class SellerService extends BaseService {
    private static String SUCCESS = "SUCCESS";

    public SellerService(Viewable context) {
        super(context);
    }

    //    //商家搜索
    public void getSellerSearch(String name, ResultListener<SellerSearchBean> resultListener) {
        get(UrlConstant.SELLER_SEARCH + "/" + name, null, new CommonResponseListener<SellerSearchBean>(
                context, resultListener, new TypeToken<SellerSearchBean>() {
        }
        ));
    }
    //商家搜索
//    public void getSellerSearch(String name, String city, ResultListener<SellerSearchBean> resultListener) {
//        Map<String, String> params = new HashMap<>();
//        params.put("name", name);
//        params.put("city", city);
//        get(UrlConstant.SELLER_SEARCH, params, new CommonResponseListener<SellerSearchBean>(
//                context, resultListener, new TypeToken<SellerSearchBean>() {
//        }
//        ));
//    }

    //联盟商家列表
    public void getSellerList(String city, ResultListener<SellerSearchBean> resultListener) {
        get(UrlConstant.SELLER_LIST + "/" + city, null, new CommonResponseListener<SellerSearchBean>(
                context, resultListener, new TypeToken<SellerSearchBean>() {
        }
        ));
    }

    //联盟商家详情
    public void getSellerDetail(String lat, String lng, String id, String token, ResultListener<SellerDetailBean> resultListener) {
        Map<String, String> map = new HashMap<>();
        map.put("lat1", lat);
        map.put("lng1", lng);
        map.put("id", id);
        map.put("token", "");
        get(UrlConstant.SELLER_DETAIL + "/" + map, null, new CommonResponseListener<SellerDetailBean>(
                context, resultListener, new TypeToken<SellerDetailBean>() {
        }));
    }

    //联盟商家banner
    public void getSellerBanner(String id, String name, String shop_id, String type,
                                String pic_value, String sort_index, ResultListener<SellerBannerBean>
                                        resultListener) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        map.put("shop_id", shop_id);
        map.put("type", type);
        map.put("pic_value", pic_value);
        map.put("sort_index", sort_index);
        get(UrlConstant.SELLER_BANNER + "/" + map, null, new CommonResponseListener<SellerBannerBean>(
                context, resultListener, new TypeToken<SellerBannerBean>() {
        }));
    }


    //联盟商家分类列表
    public void getSellerCategory(String id, String name, ResultListener<SellerDetailBean> resultListener) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        map.put("name", name);
        get(UrlConstant.SELLER_CATEGORY_LIST + "/" + map, null, new CommonResponseListener<SellerDetailBean>(
                context, resultListener, new TypeToken<SellerDetailBean>() {
        }));
    }


}