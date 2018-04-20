package youbao.shopping.ninetop.service.impl;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import youbao.shopping.ninetop.base.GlobalInfo;
import youbao.shopping.ninetop.base.Viewable;
import youbao.shopping.ninetop.bean.order.OrderAliPayBean;
import youbao.shopping.ninetop.bean.order.OrderConfirmResponseBean;
import youbao.shopping.ninetop.bean.order.ShopCartItemBean;
import youbao.shopping.ninetop.bean.order.WeChatPayInfoBean;
import youbao.shopping.ninetop.bean.product.ProductBannerListBean;
import youbao.shopping.ninetop.bean.product.ProductCommentPagingBean;
import youbao.shopping.ninetop.bean.product.ProductDetailBean;
import youbao.shopping.ninetop.bean.product.ProductPagingBean;
import youbao.shopping.ninetop.bean.product.ProductPropBean;
import youbao.shopping.ninetop.bean.product.ProductRecommendPagingBean;
import youbao.shopping.ninetop.bean.product.ProductSaleDetailBean;
import youbao.shopping.ninetop.bean.product.ProductSkuBean;
import youbao.shopping.ninetop.bean.product.SearchHistoryListBean;
import youbao.shopping.ninetop.bean.user.CouponBean;
import youbao.shopping.ninetop.common.constant.UrlConstant;
import youbao.shopping.ninetop.common.util.RSAEncryptUtil;
import youbao.shopping.ninetop.service.BaseService;
import youbao.shopping.ninetop.service.listener.BaseResponseListener;
import youbao.shopping.ninetop.service.listener.CommonResponseListener;
import youbao.shopping.ninetop.service.listener.CommonResultListener;
import youbao.shopping.ninetop.service.listener.ResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jill on 2016/11/30.
 */

public class ProductService extends BaseService {
    public ProductService(Viewable context) {
        super(context);
    }

    public void getProductDetail(String code, ResultListener<ProductDetailBean> resultListener){
        get(UrlConstant.PRODUCT_DETAIL + "/" + code, null, new CommonResponseListener<ProductDetailBean>(context,
                resultListener, new TypeToken<ProductDetailBean>(){}));
    }

    public void getProductSeckillDetail(String id, ResultListener<ProductSaleDetailBean> resultListener){
        get(UrlConstant.PRODUCT_SECKILL_DETAIL + "/" + id, null, new CommonResponseListener<>(context,
                resultListener, new TypeToken<ProductSaleDetailBean>(){}));
    }

    public void getProductBanner(String code, ResultListener<ProductBannerListBean> resultListener){
        get(UrlConstant.PRODUCT_BANNER + "/" + code, null, new CommonResponseListener<ProductBannerListBean>(context,
                resultListener, new TypeToken<ProductBannerListBean>(){}));
    }

    public void getProductProp(String code, ResultListener<ProductPropBean> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        get(UrlConstant.PRODUCT_PROPERTY , map, new CommonResponseListener<ProductPropBean>(context,
                resultListener, new TypeToken<ProductPropBean>(){}));
    }

    public void getProductRecommend(String code, String page, ResultListener<ProductRecommendPagingBean> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("page", page);
        get(UrlConstant.PRODUCT_RECOMMEND , map, new CommonResponseListener<ProductRecommendPagingBean>(context,
                resultListener, new TypeToken<ProductRecommendPagingBean>(){}));
    }

    public void getProductHTMLContent(String code, ResultListener<String> resultListener){
        get(UrlConstant.PRODUCT_HTML_CONTENT + "/" + code, null, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                JSONObject dataJson = jsonObject.getJSONObject("data");
                String content = dataJson.getString("htmlConent");
                resultListener.successHandle(content);
            }
        });
    }

    public void getProductComment(String code, String page, ResultListener<ProductCommentPagingBean> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("page", page);
        get(UrlConstant.PRODUCT_COMMENT , map, new CommonResponseListener<ProductCommentPagingBean>(context,
                resultListener, new TypeToken<ProductCommentPagingBean>(){}));
    }

    public void getProductCommentWithPicture(String code, String page, ResultListener<ProductCommentPagingBean> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("page", page);
        get(UrlConstant.PRODUCT_COMMENT_WITH_PICTURE , map, new CommonResponseListener<ProductCommentPagingBean>(context,
                resultListener, new TypeToken<ProductCommentPagingBean>(){}));
    }

    public void getSearchHistory(ResultListener<SearchHistoryListBean> resultListener){
        get(UrlConstant.PRODUCT_SEARCH_HISTORY , null, new CommonResponseListener<SearchHistoryListBean>(context,
                resultListener, new TypeToken<SearchHistoryListBean>(){}));
    }

    public void searchByKey(String keyword, String page, String column, String order, ResultListener<ProductPagingBean> resultListener){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("keyword", keyword);
        paramMap.put("page", page);
        Map<String, String> orderMap = new HashMap<>();
        orderMap.put("column", column);
        orderMap.put("order", order);
        List<Map<String, String>> list = new ArrayList();
        list.add(orderMap);
        paramMap.put("orderList", list);

        JSONObject jsonObject = new JSONObject(paramMap);

        Map<String, String> map = new HashMap<>();

        String jsonString = jsonObject.toString();
        map.put("params", jsonString);

        get(UrlConstant.PRODUCT_SEARCH_BY_KEY , map, new CommonResponseListener(context,
                resultListener, new TypeToken<ProductPagingBean>(){}));
    }

    public void getProductSkuList(String code, ResultListener<List<ProductSkuBean>> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("code", code);

        get(UrlConstant.PRODUCT_SKU_LIST , map, new CommonResponseListener<>(context,
                resultListener, new TypeToken<List<ProductSkuBean>>(){}));
    }

    public void getShopCartList(ResultListener<List<ShopCartItemBean>> resultListener){
        get(UrlConstant.SHOPCART_LIST , null, new CommonResponseListener<>(context,
                resultListener, new TypeToken<List<ShopCartItemBean>>(){}));
    }

    public void getShopCartNum(final ResultListener<Integer> resultListener){
        getShopCartList(new CommonResultListener<List<ShopCartItemBean>>(context) {
            @Override
            public void successHandle(List<ShopCartItemBean> result) throws JSONException {
                int count = 0;
                if(result != null && result.size() > 0){
                    for(ShopCartItemBean cartItem : result){
                        count += cartItem.amount;
                    }
                }

                resultListener.successHandle(count);
            }
            public boolean isShowLoading() {
                return false;
            }
            public boolean isShowToast() {
                return false;
            }
        });
    }

    public void editCartNumber(ShopCartItemBean bean, ResultListener<String> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("itemCode", bean.goodsCode);
        map.put("skuId", bean.skuId);
        map.put("amount", bean.amount + "");

        post(UrlConstant.SHOPCART_EDIT, map, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle("");
            }
        });
    }

    public void removeCart(ShopCartItemBean bean, ResultListener<String> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("itemCode", bean.goodsCode);
        map.put("skuId", bean.skuId);

        post(UrlConstant.SHOPCART_DELETE, map, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle("");
            }
        });
    }

    public void removeCartList(String ids, ResultListener<String> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("carts", ids);
        post(UrlConstant.SHOPCART_DELETE_LIST, map, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle("");
            }
        });
    }

    public void addShopCart(String itemCode, String skuId, int amount, ResultListener<String> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("itemCode", itemCode);
        map.put("skuId", skuId);
        map.put("amount", amount + "");

        post(UrlConstant.SHOPCART_ADD, map, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle("");
            }
        });
    }

    public void submitCart(String skuIds, ResultListener<JSONObject> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("skuIds", skuIds);

        get(UrlConstant.SHOPCART_SUBMIT, map, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle(jsonObject);
            }
        });
    }

    public void buyNowProduct(String skuId, int amount, ResultListener<JSONObject> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("skuId", skuId);
        map.put("amount", amount + "");

        get(UrlConstant.PRODUCT_BUY_NOW, map,new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle(jsonObject);
            }
        });
    }

    public void buyNowProductSeckill(String activityItemId, int amount, ResultListener<JSONObject> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("activityItemId", activityItemId);
        map.put("amount", amount + "");

        get(UrlConstant.PRODUCT_SECKILL_BUY_NOW, map,new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle(jsonObject);
            }
        });
    }

    public void cartOrderSubmit(String couponCode, String skuIds, String addressIndex, ResultListener<OrderConfirmResponseBean> resultListener){
        Map<String, Object> map = new HashMap<>();
        map.put("couponCode", couponCode);
        map.put("skuIds", skuIds);
        map.put("addrIndex", addressIndex);

        postJson(UrlConstant.ORDER_CART_CONFIRM, map,new CommonResponseListener<>(context,
                resultListener, new TypeToken<OrderConfirmResponseBean>(){}));
    }

    public void buyNowOrderSubmit(String couponCode, String skuId, String amount, String addressIndex, ResultListener<OrderConfirmResponseBean> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("couponCode", couponCode);
        map.put("skuId", skuId);
        map.put("addrIndex", addressIndex);
        map.put("amount", amount);

        post(UrlConstant.ORDER_BUY_NOW_CONFIRM, map,new CommonResponseListener<>(context,
                resultListener, new TypeToken<OrderConfirmResponseBean>(){}));
    }

    public void buyNowSaleOrderSubmit(String couponCode, String activityItemId,
                                         String amount, String addressIndex, ResultListener<OrderConfirmResponseBean> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("couponCode", couponCode);
        map.put("activityItemId", activityItemId);
        map.put("addrIndex", addressIndex);
        map.put("amount", amount);

        post(UrlConstant.ORDER_SECKILL_BUY_NOW_CONFIRM, map,new CommonResponseListener<>(context,
                resultListener, new TypeToken<OrderConfirmResponseBean>(){}));
    }

    public void getProductCouponList(String code, ResultListener<List<CouponBean>> resultListener){
        get(UrlConstant.PRODUCT_COUPON_LIST + "/" + code, null,new CommonResponseListener<>(context,
                resultListener, new TypeToken<List<CouponBean>>(){}));
    }

    public void receiveCoupon(String voucherId, ResultListener<String> resultListener){
        post(UrlConstant.PRODUCT_COUPON + "/" + voucherId, null, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle("");
            }
        });
    }

    public void getOrderCouponList(Map<String, String> skuIdMap, ResultListener<List<CouponBean>> resultListener){
        Map<String, Object> paramMap = new HashMap<>();
        List<Map<String, String>> itemList = new ArrayList<>();
        for(String skuId : skuIdMap.keySet()){
            String amount = skuIdMap.get(skuId);
            Map<String, String> itemMap = new HashMap<>();
            itemMap.put("skuId", skuId);
            itemMap.put("amount", amount);
            itemList.add(itemMap);
        }

        paramMap.put("itemList", itemList);
        paramMap.put("v", GlobalInfo.appVersionName);
        paramMap.put("token",GlobalInfo.userToken );

        JSONObject jsonObject = new JSONObject(paramMap);

        Map<String, String> map = new HashMap<>();

        String jsonString = jsonObject.toString();
        map.put("params", jsonString);

        get(UrlConstant.ORDER_COUPON, map,new CommonResponseListener<>(context,
                resultListener, new TypeToken<List<CouponBean>>(){}));
    }

    public void orderPayment(String orderNo, String password, ResultListener<String> resultListener){
        String encodePsd = "";
        try {
            encodePsd = RSAEncryptUtil.encrypt(password);
        } catch (Exception e) {
            context.showToast("客户端出现异常");
            return;
        }
        Map<String, String> map = new HashMap<>();
        map.put("orderCode", orderNo);
        map.put("authType", "2");
        map.put("pwd", encodePsd);
        map.put("deviceCode", "");

        post(UrlConstant.ORDER_PAYMENT, map,new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle("");
            }
        });
    }

    public void orderAliPay(String orderNo, boolean isParent, ResultListener<OrderAliPayBean> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("orderCode", orderNo);

        String url = isParent ? UrlConstant.ORDER_PARENT_ALIPAY : UrlConstant.ORDER_SUB_ALIPAY;

        get(url, map, new CommonResponseListener<>(context,
                resultListener, new TypeToken<OrderAliPayBean>(){}));
    }

    public void orderWechatPay(String orderNo, boolean isParent, ResultListener<WeChatPayInfoBean> resultListener){
        Map<String, String> map = new HashMap<>();
        map.put("orderCode", orderNo);
        String url = isParent ? UrlConstant.ORDER_PARENT_WECHAT_PAY : UrlConstant.ORDER_SUB_WECHAT_PAY;

        get(url, map,new CommonResponseListener<>(context,
                resultListener, new TypeToken<WeChatPayInfoBean>(){}));
    }

}
