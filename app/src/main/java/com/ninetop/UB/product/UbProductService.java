package com.ninetop.UB.product;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ninetop.UB.BalancePaySellerBean;
import com.ninetop.UB.ChangeCodeBean;
import com.ninetop.UB.ProductCategoryBean;
import com.ninetop.UB.ProductListBean;
import com.ninetop.UB.QuesIsSetBean;
import com.ninetop.UB.QuestionAskBean;
import com.ninetop.UB.cartshop.UbShopCartBean;
import com.ninetop.UB.order.HotCityBean;
import com.ninetop.UB.order.MyOrderListBean;
import com.ninetop.UB.order.UbConfirmOrderAddressChangeBean;
import com.ninetop.UB.order.UbPayBean;
import com.ninetop.UB.order.UbPayInfoBean;
import com.ninetop.UB.product.New.ShopCartItemListBean;
import com.ninetop.UB.product.New.SingleProductSkuBean;
import com.ninetop.UB.product.New.SpinnerListBean;
import com.ninetop.UB.product.UbOrder.UbPreOrderBean;
import com.ninetop.base.GlobalInfo;
import com.ninetop.base.Viewable;
import com.ninetop.bean.order.WeChatPayInfoBean;
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
 * Created by huangjinding on 2017/5/23.
 */
public class UbProductService extends BaseService {
    public UbProductService(Viewable context) {
        super(context);
    }

    private static String SUCCESS = "SUCCESS";
    private static String PICS = "pics";

    //积分商城首页banner
    public void getProductBanner(ResultListener<List<ProductBannerBean>> resultListener) {
        get(UrlConstant.PRODUCT_BANNER, null, new CommonResponseListener<List<ProductBannerBean>>(context,
                resultListener, new TypeToken<List<ProductBannerBean>>() {
        }));
    }

    //热门城市
    public void getHotCity(ResultListener<List<HotCityBean>> resultListener) {
        get(UrlConstant.HOT_CITY, null, new CommonResponseListener<>(context, resultListener, new
                TypeToken<List<HotCityBean>>() {
                }));
    }

    //积分商城经销商列表
    public void getMallList(ResultListener resultListener) {

        get(UrlConstant.PRODUCT_SELLER, null, new CommonResponseListener<>(context,
                resultListener, new TypeToken<String>() {
        }));
    }

    //积分商城主题活动列表
    public void getProductMainList(int id, ResultListener<List<UbProductMainListBean>> resultListener) {
        Map<String, String> params = new HashMap<>();
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        String franchiseeId = String.valueOf(id);
        if (id == 0) {
            franchiseeId = GlobalInfo.franchiseeId;
        }
        params.put("token", token);
        params.put("franchisee_id", franchiseeId);
        get(UrlConstant.PRODUCT_MAIN, params, new CommonResponseListener<List<UbProductMainListBean>>(context,
                resultListener, new TypeToken<List<UbProductMainListBean>>() {
        }));
    }

    //积分商城分类列表接口
    public void getProductCategory(ResultListener<List<ProductCategoryBean>> resultListener) {

        get(UrlConstant.PRODUCT_CATEGORY, null, new CommonResponseListener<List<ProductCategoryBean>>(context,
                resultListener, new TypeToken<List<ProductCategoryBean>>() {
        }));
    }

    //积分商城首页推荐列表
    public void postProductRecomentList(int page, int pageSize, int id, ResultListener<List<ProductListBean>> resultListener) {
        Map<String, Object> params = new HashMap<>();

        String franchiseeId = String.valueOf(id);
        if (id == 0) {
            franchiseeId = GlobalInfo.franchiseeId;
        }
        params.put("page", page);
        params.put("pageSize", pageSize);

        params.put("franchisee_id", franchiseeId);
        postJson(UrlConstant.PRODUCT_RECOMMEND_LIST, params, new CommonResponseListener<List<ProductListBean>>(context,
                resultListener, new TypeToken<List<ProductListBean>>() {
        }));
    }

    //商品具体分类入口
    public void postProductCategory(int categoryId, int fseeId, int sortType, int sortMethod, int page, int pageSize, ResultListener<List<ProductRecommendBean>> resultListener) {
        Map<String, Object> params = new HashMap<>();
        String franchiseeId = GlobalInfo.franchiseeId;
        if (TextUtils.isEmpty(franchiseeId)) {
            franchiseeId = "1";
        }


        params.put("page", page);
        params.put("pageSize", pageSize);
        params.put("category_id", categoryId);
        params.put("franchisee_id", franchiseeId);
        params.put("sort_method", sortMethod);
        params.put("sort_type", sortType);
        postJson(UrlConstant.PRODUCT_CATEGORY_LIST, params, new CommonResponseListener<List<ProductRecommendBean>>(context,
                resultListener, new TypeToken<List<ProductRecommendBean>>() {
        }));
    }

    //积分商城首页商品list
    public void getProductList(int page, int pageSize, int id, ResultListener<List<FirstProductBean>> resultListener) {
        String franchiseeId = GlobalInfo.franchiseeId;
        if (TextUtils.isEmpty(franchiseeId)) {
            franchiseeId = "1";
        }

        Map<String, String> params = new HashMap<>();
        params.put("page", page + "");
        params.put("pageSize", pageSize + "");
        params.put("franchisee _id", franchiseeId);
        get(UrlConstant.PRODUCT_LIST, params, new CommonResponseListener<List<FirstProductBean>>(context,
                resultListener, new TypeToken<List<FirstProductBean>>() {
        }));
    }


    //加盟商下拉列表
    public void postFranchisee(String localCity, String localCounty, String lat, String lng, ResultListener<List<SpinnerListBean>> resultListener) {
        if (lat == null || lat.length() == 0) {
            lat = "30.30589";
        }
        if (lng == null || lng.length() == 0) {
            lng = "120.118026";
        }
        if (localCity == null || localCity.length() == 0) {
            localCity = "杭州市";
        }
        if (localCounty == null)
            localCounty = "";
        Map<String, Object> params = new HashMap<>();
        params.put("city", localCity);
        params.put("county", "");
        params.put("lat", "0");
        params.put("lng", "0");
        postJson(UrlConstant.FRANCHISEE_SELECT, params, new CommonResponseListener<List<SpinnerListBean>>(context,
                resultListener, new TypeToken<List<SpinnerListBean>>() {
        }));
    }

    //添加加盟商
    public void postAddFranchisee(int franchid, ResultListener resultListener) {
        String franchiseeId = GlobalInfo.franchiseeId;
        if (TextUtils.isEmpty(franchiseeId)) {
            franchiseeId = "\"\"";
        }

        Map<String, Object> params = new HashMap<>();
        params.put("franchiseeId", franchiseeId);
        postJson(UrlConstant.FRANCHISEE_ADD, params, new CommonResponseListener<>(context,
                resultListener, new TypeToken<Object>() {
        }));
    }

    //获取加盟商列表
    public void getFraniseeList(ResultListener<List<FranchiseeListBean>> resultListener) {
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        get(UrlConstant.FRANCHISEE_LIST, params, new CommonResponseListener<List<FranchiseeListBean>>(context, resultListener, new TypeToken<List<FranchiseeListBean>>() {
        }));
    }

    //积分商城商品搜索
    public void getSearch(String name, ResultListener<List<ProductSearchBean>> resultListener) {
        String franchiseeId = GlobalInfo.franchiseeId;
        if (TextUtils.isEmpty(franchiseeId)) {
            franchiseeId = "\"\"";
        }

        Map<String, Object> params = new HashMap<>();
        params.put("search_word", name);
        params.put("franchisee_id", franchiseeId);
        postJson(UrlConstant.PRODUCT_SEARCH, params, new CommonResponseListener<List<ProductSearchBean>>(context,
                resultListener, new TypeToken<List<ProductSearchBean>>() {
        }));
    }

    //添加商品收藏
    public void postCollection(int productId, ResultListener resultListener) {
        String franchiseeId = GlobalInfo.franchiseeId;
        if (TextUtils.isEmpty(franchiseeId)) {
            franchiseeId = "\"\"";
        }
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("product_id", productId);
        params.put("franchisee_id", franchiseeId);
        postJson(UrlConstant.PRODUCT_COLLECTION + "?" + token, params, new CommonResponseListener<>(context,
                resultListener, new TypeToken<Object>() {
        }));
    }

    //取消商品收藏
    public void postCollectionCansel(int productId, ResultListener resultListener) {
        String franchiseeId = GlobalInfo.franchiseeId;
        if (TextUtils.isEmpty(franchiseeId)) {
            franchiseeId = "\"\"";
        }
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("product_id", productId);
        params.put("franchisee_id", franchiseeId);
        postJson(UrlConstant.PRODUCT_COLLECTION_CANSEL, params, new CommonResponseListener<>(context,
                resultListener, new TypeToken<Object>() {
        }));
    }


    //积分商城热门搜索
    public void getHotSearch(ResultListener<List<ProductHotBean>> resultListener) {

        get(UrlConstant.PRODUCT_HOT, null, new CommonResponseListener<List<ProductHotBean>>(context,
                resultListener, new TypeToken<List<ProductHotBean>>() {
        }));
    }


    //积分商城商品详情
    public void getProductDetail(int productId, int sellerId, String userToken, ResultListener<UbProductDetailBean> resultListener) {
        String franchiseeId = GlobalInfo.franchiseeId;
        if (TextUtils.isEmpty(franchiseeId)) {
            franchiseeId = "\"\"";
        }
        userToken = GlobalInfo.userToken;
        if (TextUtils.isEmpty(userToken)) {
            userToken = "\"\"";
        }
        Map<String, String> params = new HashMap<>();
        params.put("product_id", productId + "");
        params.put("franchisee_id", franchiseeId);
        params.put("token", userToken);
        get(UrlConstant.PRODUCT_DETAIL, params, new CommonResponseListener<UbProductDetailBean>(context,
                resultListener, new TypeToken<UbProductDetailBean>() {
        }));
    }

    //积分商城商品参数
    public void getProductParameter(int id, ResultListener<List<UbProductParameterBean>> resultListener) {
//        Map<String, String> params = new HashMap<>();
//        params.put("id", id+"");
        get(UrlConstant.PRODUCT_PARAMETER + id, null, new CommonResponseListener<List<UbProductParameterBean>>(context,
                resultListener, new TypeToken<List<UbProductParameterBean>>() {
        }));
    }

    //积分商城商品规格
    public void getProductSpecifications(String id, ResultListener<List<SingleProductSkuBean>> resultListener) {
        String franchiseeId = GlobalInfo.franchiseeId;
        if (TextUtils.isEmpty(franchiseeId)) {
            franchiseeId = "\"\"";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("product_id", id);
        params.put("franchisee_id", franchiseeId);
        postJson(UrlConstant.PRODUCT_SPECIFICATIONS, params, new CommonResponseListener<List<SingleProductSkuBean>>(context,
                resultListener, new TypeToken<List<SingleProductSkuBean>>() {
        }));
    }

    //    //商品详情立即兑换,
//    public void postEMSOrder(int orderFrom,int takeType,int receiverId,String remark,
//                             List<Map> productList,ResultListener<List<UbPreOrderBean>> resultListener ){
//        String token=GlobalInfo.userToken;
//        if(TextUtils.isEmpty(token)){
//            token="\"\"";
//        }
//
//        Map<String,Object> params=new HashMap<>();
//        params.put("orderFrom",orderFrom);
//        params.put("takeType",takeType);
//        params.put("receiverId",receiverId);
//        params.put("remark",remark);
//        params.put("productList",productList);
//
//        postJson(UrlConstant.UB_ORDER_EMS,params,new CommonResponseListener<>(context,
//                resultListener, new TypeToken<List<UbPreOrderBean>>(){}));
//    }
    //商品详情立即兑换,
    public void postEMSOrder(int orderFrom, int takeType, int receiverId, String remark,
                             List<Map> productList, ResultListener<JSONObject> resultListener) {
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }

        Map<String, Object> params = new HashMap<>();
        params.put("orderFrom", orderFrom);
        params.put("takeType", takeType);
        params.put("receiverId", receiverId);
        params.put("remark", remark);
        params.put("productList", productList);

        postJson(UrlConstant.UB_ORDER_EMS, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle(jsonObject);
            }
        });
    }

    //商品详情立即兑换,
    public void postOrderConfirm(int orderFrom, int takeType, int receiverId, String remark,
                                 List<Map> productList, ResultListener<UbPreOrderBean> resultListener) {
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }

        Map<String, Object> params = new HashMap<>();
        params.put("orderFrom", orderFrom);
        params.put("takeType", takeType);
        params.put("receiverId", receiverId);
        params.put("remark", remark);
        params.put("productList", productList);

        postJson(UrlConstant.UB_ORDER_EMS, params, new CommonResponseListener(context, resultListener, new TypeToken<UbPreOrderBean>() {
        }));
    }

    //添加购物车
    public void postShopcartAdd(String token, int id, int providerNum, int skuId, int amount, Double price, ResultListener<String> resultListener) {
        String franchiseeId = GlobalInfo.franchiseeId;
        if (TextUtils.isEmpty(franchiseeId)) {
            franchiseeId = "\"\"";
        }
        Map<String, Object> map = new HashMap<>();
        token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        map.put("productId", id);
        map.put("providerNum", franchiseeId);
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
    public void getShopList(String token, ResultListener<List<UbShopCartBean>> resultListener) {
        Map<String, String> params = new HashMap<>();
        token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        params.put("token", token);
        get(UrlConstant.UB_SHOP_LIST, params, new CommonResponseListener<List<UbShopCartBean>>(context, resultListener, new
                TypeToken<List<UbShopCartBean>>() {
                })
        );
    }

    //增减购物车商品数量
    public void postShopcartCount(int id, ShopCartItemListBean bean, final ResultListener<String> resultListener) {
        String franchiseeId = GlobalInfo.franchiseeId;
        if (TextUtils.isEmpty(franchiseeId)) {
            franchiseeId = "\"\"";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("franchiseeId", franchiseeId);
        params.put("id", bean.shopCartId);
        params.put("amount", bean.amount);
        params.put("skuId", bean.skuId);
        postJson(UrlConstant.UB_SHOP_AMOUNT, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle("");
            }
        });
    }

    //删除购物车
    public void postShopcartDelete(List<Integer> idList, final ResultListener<String> resultListener) {
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
//        List<Integer> idList=new ArrayList<>();
//        idList.add(itemBean.shopCartId);
        Map<String, Object> params = new HashMap<>();
        params.put("idList", idList);
        postJson(UrlConstant.UB_SHOP_DELETE, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle("");
            }
        });
    }

    //订单确认下单
    public void postConfirmOrder(int orderFrom, int takeType, int receiverId, String remark,
                                 List<Map> productList, ResultListener<JSONObject> resultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderFrom", orderFrom);
        params.put("takeType", takeType);
        params.put("receiverId", receiverId);
        params.put("remark", remark);
        params.put("productList", productList);

        postJson(UrlConstant.UB_ORDER_CONFIRM, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle(jsonObject);
            }
        });
    }

    //
//    public void postFrnchiseeInfo(ResultListener<UbConfirmOrderAddressChangeBean> resultListener) {
//        Map<String, Object> params = new HashMap<>();
//
//        params.put("franchisee_id", 5);
//        postJson(UrlConstant.UB_ORDER_EMS,params,new BaseResponseListener(context, resultListener) {
//            @Override
//            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
//                resultListener.successHandle(jsonObject);
//            }
//        });
//    }
    //到店自取获取商家信息
    public void postFrnchiseeInfo(String franchiseeId,ResultListener<UbConfirmOrderAddressChangeBean> resultListener) {
        if (TextUtils.isEmpty(franchiseeId)) {
            franchiseeId = "\"\"";
        }
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("franchisee_id", franchiseeId);
        params.put("token", token);
        postJson(UrlConstant.UB_SHOP_CHINESEE, params, new CommonResponseListener<>(context,
                resultListener, new TypeToken<UbConfirmOrderAddressChangeBean>() {
        }));
    }

    //订单详情
    public void getOrderAllDetail(String orderId, ResultListener<MyOrderListBean> resultListener) {
        Map<String, String> params = new HashMap<>();
        int id = Integer.parseInt(orderId);
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        params.put("token", token);
        params.put("order_id", orderId);
        get(UrlConstant.UB_ORDER_DETAIL, params, new CommonResponseListener<MyOrderListBean>(context, resultListener, new
                TypeToken<MyOrderListBean>() {
                }));
    }

    //订单列表
    public void getOrderList(String status, ResultListener<List<MyOrderListBean>> resultListener) {
        Map<String, String> params = new HashMap<>();
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        params.put("token", token);
        params.put("status", status);
        get(UrlConstant.UB_ORDER_LIST, params, new CommonResponseListener<List<MyOrderListBean>>(context, resultListener, new
                TypeToken<List<MyOrderListBean>>() {
                }));
    }

    public void getCanselOrder(int id, final ResultListener resultListener) {
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("order_id", id + "");
        get(UrlConstant.UB_ORDER_CANSEL, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if ("200".equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("服务器异常");
                }
            }
        });
    }

    //确认收货
    public void postConfirmReceive(int id, ResultListener resultListener) {
        Map<String, Object> params = new HashMap<>();
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        params.put("order_id", id);
        params.put("pay_password", "123456");
        postJson(UrlConstant.UB_ORDER_CONFIRM_GET + "?" + token, params, new CommonResponseListener<>(context,
                resultListener, new TypeToken<UbConfirmOrderAddressChangeBean>() {
        }));
    }

    //删除订单
    public void getDeleteOrder(int id, final ResultListener resultListener) {
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("order_id", id + "");
        get(UrlConstant.UB_ORDER_DELETE, params, new CommonResponseListener<>(context,
                resultListener, new TypeToken<Object>() {
        }));
    }


    public void getOrderPay(int id, ResultListener<JSONObject> resultListener) {
        Map<String, String> params = new HashMap<>();
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        params.put("token", token);
        params.put("order_id", id + "");
        get(UrlConstant.UB_ORDER_PAY, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle(jsonObject);
            }
        });

    }

    //商品alipay接口支付宝支付
    public void postAlipay(UbPayBean ubPayBean, ResultListener<UbPayInfoBean> resultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderCode", ubPayBean.orderCode);
        params.put("ubPay", ubPayBean.ubPay);
        params.put("balancePay", ubPayBean.balancePay);
        params.put("payPrice", ubPayBean.payPrice);
        params.put("payType", ubPayBean.payType);
        postJson(UrlConstant.ORDER_ALIPAY, params, new CommonResponseListener<UbPayInfoBean>(context, resultListener,
                new TypeToken<UbPayInfoBean>() {
                }));
    }

    //商品微信接口支付宝支付
    public void postWechatPay(UbPayBean ubPayBean, ResultListener<WeChatPayInfoBean> resultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderCode", ubPayBean.orderCode);
        params.put("ubPay", ubPayBean.ubPay);
        params.put("balancePay", ubPayBean.balancePay);
        params.put("payPrice", ubPayBean.payPrice);
        params.put("payType", ubPayBean.payType);
        postJson(UrlConstant.ORDER_WECHAT_PAY, params, new CommonResponseListener<WeChatPayInfoBean>(context, resultListener,
                new TypeToken<WeChatPayInfoBean>() {
                }));
    }

    //商家alipay接口支付宝支付
    public void postSellerAlipay(double totalFee, int shopId, String remark, ResultListener<UbPayInfoBean> resultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("totalFee", totalFee);
        params.put("shopId", shopId);
        params.put("remark", remark);
        postJson(UrlConstant.SELLER_ALIPAY, params, new CommonResponseListener<UbPayInfoBean>(context, resultListener,
                new TypeToken<UbPayInfoBean>() {
                }));
    }

    //商家微信接口支付
    public void postSellerWechatPay(double totalFee, int shopId, String remark, ResultListener<WeChatPayInfoBean> resultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("totalFee", totalFee);
        params.put("shopId", shopId);
        if (TextUtils.isEmpty(remark)){
            params.put("remark", "");
        }else {
            params.put("remark", remark);
        }
        postJson(UrlConstant.SELLER_WECHAT_PAY, params, new CommonResponseListener<>(context, resultListener,
                new TypeToken<WeChatPayInfoBean>() {
                }));
    }

    public void postFranchiseeCategory(int themeId, int franchisee_id, int page, int pageSize, ResultListener<List<ProductSearchBean>> resultListener) {
        Map<String, Object> params = new HashMap<>();
      //  String franchiseeId = GlobalInfo.franchiseeId;
        /*if (TextUtils.isEmpty(franchisee_id)) {
            franchisee_id = "\"\"";
        }*/

        params.put("theme_id", themeId);
        params.put("franchisee_id", franchisee_id);
        params.put("page", page);
        params.put("pageSize", pageSize);

        postJson(UrlConstant.PRODUCT_FRANCHISEE_DETAIL, params, new CommonResponseListener<List<ProductSearchBean>>(context, resultListener,
                new TypeToken<List<ProductSearchBean>>() {
                }));
    }

    //余额支付（用户-联盟商家）
    public void postBalancePaySeller(double total, String shopId, String pwd, String remark, ResultListener<BalancePaySellerBean> resultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("totalFee", total);
        params.put("shopId", shopId);
        params.put("payPassword", pwd);
        params.put("remark", remark);

        postJson(UrlConstant.BALANCE_SELLER_ALIPAY, params, new CommonResponseListener<BalancePaySellerBean>(context, resultListener,
                new TypeToken<BalancePaySellerBean>() {
                }));
    }

    //余额支付
    public void postBalancePay(UbPayBean ubPayBean, String pwd, ResultListener resultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderCode", ubPayBean.orderCode);
        params.put("ubPay", ubPayBean.ubPay);
        params.put("balancePay", ubPayBean.balancePay);
        params.put("payPrice", ubPayBean.payPrice);
        params.put("payType", ubPayBean.payType);
        params.put("balancePassword", pwd);
        postJson(UrlConstant.BALANCE_ALIPAY, params, new CommonResponseListener(context, resultListener,
                new TypeToken<UbPayInfoBean>() {
                }));
    }

    //兑换码
    public void getChangeCode(String orderId, ResultListener<ChangeCodeBean> resultListener) {
        Map<String, String> params = new HashMap<>();
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        params.put("token", token);
        params.put("order_id", orderId);
        get(UrlConstant.CHANGE_CODE, params, new CommonResponseListener<ChangeCodeBean>(context,
                resultListener, new TypeToken<ChangeCodeBean>() {
        }));
    }

    //获取商品分类搜索列表
    public void getProductCategoryList(ResultListener<List<UbProductCategoryClassBean>> resultListener) {
        get(UrlConstant.UB_PRODUCT_CATEGORY, null, new CommonResponseListener<List<UbProductCategoryClassBean>>(context,
                resultListener, new TypeToken<List<UbProductCategoryClassBean>>() {
        }));
    }

    //商品上新，销量，价格     当进入分类是，默认排序：sort_type=3，sort_method=0,
    public void postProductInfoSelect(int categoryId, int sortType, int sortMethod, int page, int pageSize, ResultListener<List<ProductSearchBean>> resultListener) {
        Map<String, Object> params = new HashMap<>();
        String franchiseeId = GlobalInfo.franchiseeId;
        if (TextUtils.isEmpty(franchiseeId)) {
            franchiseeId = "\"\"";
        }
        params.put("category_id", categoryId);
        params.put("franchisee_id", franchiseeId);
        params.put("sort_type", sortType);
        params.put("sort_method", sortMethod);
        params.put("page", page);
        params.put("pageSize", pageSize);

        postJson(UrlConstant.PRODUCT__CATEGORY_SELECT, params, new CommonResponseListener<List<ProductSearchBean>>(context,
                resultListener, new TypeToken<List<ProductSearchBean>>() {
        }));
    }

    public void postBalancePwd(int q1, String a1, int q2, String a2, int q3, String a3, ResultListener resultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("q1_id", q1);
        params.put("q1_answer", a1);
        params.put("q2_id", q2);
        params.put("q2_answer", a2);
        params.put("q3_id", q3);
        params.put("q3_answer", a3);
        postJson(UrlConstant.QUESTION_SET_PWD, params, new CommonResponseListener(context, resultListener,
                new TypeToken<UbPayInfoBean>() {
                }));
    }

    public void getPwdIsSet(ResultListener<QuesIsSetBean> resultListener) {
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        get(UrlConstant.QUESTION_IS_SET, null, new CommonResponseListener<QuesIsSetBean>(context,
                resultListener, new TypeToken<QuesIsSetBean>() {
        }));
    }

    //用户安全问题（供回答）
    public void getPwdAsk(ResultListener<QuestionAskBean> resultListener) {
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        get(UrlConstant.QUESTION_ASK_PWD, null, new CommonResponseListener<QuestionAskBean>(context,
                resultListener, new TypeToken<QuestionAskBean>() {
        }));
    }

    //
    //•用户回答安全问题接口
    public void postAlreadyAnswer(String q1, String q2, String q3, ResultListener resultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("q1_answer", q1);
        params.put("q2_answer", q2);
        params.put("q3_answer", q3);
        postJson(UrlConstant.QUESTION_DOWN_PWD, params, new CommonResponseListener(context, resultListener,
                new TypeToken<Object>() {
                }));
    }

    //
//    //设置支付密码
//    public void postSetPwd(String num, ResultListener<Object> resultListener) {
//        int number = Integer.parseInt(num);
//        Map<String, Object> params = new HashMap<>();
//        params.put("pay_password", number);
//        postJson(UrlConstant.QUESTION_CHANGE_PWD, params, new CommonResponseListener<Object>(context, resultListener,
//                new TypeToken<Object>() {
//                }));
//    }
    //设置支付密码
    public void postSetPwd(String num, ResultListener<Object> resultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("pay_password", num);
        postJson(UrlConstant.QUESTION_CHANGE_PWD, params, new CommonResponseListener<Object>(context, resultListener,
                new TypeToken<Object>() {
                }));
    }

    //申请售后
    public void sellerService(int franchiseeId, ResultListener<JSONObject> resultListener) {
        Map<String, String> params = new HashMap<>();
        String franchiseeID = String.valueOf(franchiseeId);
        params.put("franchiseeId", franchiseeID);
        get(UrlConstant.SELLER_SERVICE, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                JSONObject jsonData = jsonObject.getJSONObject("data");
                resultListener.successHandle(jsonData);
            }
        });
    }
}