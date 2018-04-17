package youbao.shopping.ninetop.UB;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import youbao.shopping.ninetop.activity.ub.bean.product.ProductFavorBean;
import youbao.shopping.ninetop.activity.ub.bean.product.SellerFavorBean;
import youbao.shopping.ninetop.base.GlobalInfo;
import youbao.shopping.ninetop.base.Viewable;
import youbao.shopping.ninetop.common.constant.UrlConstant;
import youbao.shopping.ninetop.service.BaseService;
import youbao.shopping.ninetop.service.listener.BaseResponseListener;
import youbao.shopping.ninetop.service.listener.CommonResponseListener;
import youbao.shopping.ninetop.service.listener.ResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangjinding on 2017/5/2.
 */
public class UbUserService extends BaseService {
    public UbUserService(Viewable context) {
        super(context);
    }

    private static String SUCCESS = "SUCCESS";
    private static String PICS = "pics";
    //登陆
    public void login(String name, String password, final ResultListener<String> resultListener){
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", name);
        params.put("password", password);
        params.put("device_id", "");
        params.put("device_type", "Android");
        postJson(UrlConstant.USER_LOGIN, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                JSONObject dataJson = jsonObject.getJSONObject("data");
                String token = dataJson.getString("token");
                resultListener.successHandle(token);
            }

        });
    }
    //注册
    public void register(String mobile, String authCode, String password,
                         final ResultListener<String> resultListener)throws JSONException{
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("code", authCode);
        params.put("password", password);
        postJson(UrlConstant.USER_REGISTER, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                JSONObject dataJson = jsonObject.getJSONObject("data");
                String token = dataJson.getString("token");
                resultListener.successHandle(token);
            }
        });
    }

    public void changeMobile(String mobile, String authCode, String password,
                             final ResultListener<ChangeMobileBean> resultListener){
        Map<String, Object> params = new HashMap<>();
        params.put("newmobile", mobile);
        params.put("code", authCode);
        params.put("password", password);
        postJson(UrlConstant.CHANGE_MOBILE, params, new CommonResponseListener<ChangeMobileBean>(context, resultListener,new
                TypeToken<ChangeMobileBean>(){}) {


        });
    }
    //新手机号获取验证码
    public void getAuthCode(String mobile,ResultListener<String> resultListener){
        get(UrlConstant.CODE + "/" + mobile, null, new BaseResponseListener(context,resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                JSONObject data = jsonObject.getJSONObject("data");
                String msg = jsonObject.getString("msg");
                if(!data.toString().equals("{}") && !TextUtils.isEmpty(data.toString())){
                    String authCode = data.getString("code");
                    resultListener.successHandle(authCode);
                }else{
                    context.showToast(msg);
                }
            }
        });
    }
    //wangji密码验证码
    public void getPwdCode(String mobile,ResultListener<String> resultListener){
//        Map<String,String> params=new HashMap<>();
//        params.put("mobile",mobile);
         get(UrlConstant.PWD_CODE +"/"+mobile,null, new BaseResponseListener(context,resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                JSONObject data = jsonObject.getJSONObject("data");
                Log.i("json===========",data.toString());
                String msg = jsonObject.getString("msg");
                if(!data.toString().equals("{}") && !TextUtils.isEmpty(data.toString())){
                    String authCode = data.getString("code");
                    resultListener.successHandle(authCode);
                    Log.i("json===========",authCode);
                }else{
                    context.showToast(msg);
                }
//                resultListener.successHandle("");
            }
        });
    }
     //忘记密码
    public void forgetPassword(String mobile, String authCode, String password1,String password2,
                               final ResultListener<String> resultListener){
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("code", authCode);
        params.put("password1", password1);
        params.put("password2", password2);
        postJson(UrlConstant.PWD_PWD, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {

                JSONObject dataJson = jsonObject.getJSONObject("data");
                String token = dataJson.getString("token");
                resultListener.successHandle(token);
            }
        });

    }
    //修改密码
    public void modifyPassword(String mobile, String authCode, String password1,String password2,
                               final ResultListener<String> resultListener){
        Map<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("code", authCode);
        params.put("password1", password1);
        params.put("password2", password2);
        Log.i("json===========",authCode+"="+mobile+"="+password1+"="+password2);
        postJson(UrlConstant.MODIFY_PWD, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                Log.i("json===========",jsonObject.toString());
//                JSONObject dataJson = jsonObject.getJSONObject("data");
//                String token = dataJson.getString("token");
                resultListener.successHandle("");
            }
        });

    }
    //取消商品收藏
    public void postProductfavorAdd(int productId,int franchiseeId,ResultListener<Object> resultListener){
        String token= GlobalInfo.userToken;
        if(TextUtils.isEmpty(token)){
            token="\"\"";
        }
        Map<String,Object> params=new HashMap<>();
        params.put("product_id",productId);
        params.put("franchisee_id",franchiseeId);
        postJson(UrlConstant.PRODUCT_FAVOR_DELETE_ALL+"?"+token,params,new CommonResponseListener<Object>(context,
                resultListener,new TypeToken<Object>(){}));
    }

    //获取商品收藏列表
    public void getProductfavorList(ResultListener<List<ProductFavorBean>> resultListener){
        String token= GlobalInfo.userToken;
        if(TextUtils.isEmpty(token)){
            token="\"\"";
        }
        get(UrlConstant.PRODUCT_FAVOR_LIST+"?"+token,null,new CommonResponseListener<List<ProductFavorBean>>(context,
                resultListener,new TypeToken<List<ProductFavorBean>>(){}));
    }
    //取消商品收藏
    public void postProductfavorCansel(int productId,int franchiseeId,ResultListener<Object> resultListener){
        String token= GlobalInfo.userToken;
        if(TextUtils.isEmpty(token)){
            token="\"\"";
        }
        Map<String,Object> params=new HashMap<>();
        params.put("product_id",productId);
        params.put("franchisee_id",franchiseeId);
        postJson(UrlConstant.PRODUCT_FAVOR_DELETE_ALL+"?"+token,params,new CommonResponseListener<Object>(context,
                resultListener,new TypeToken<Object>(){}));
    }
    //多项取消商品收藏接口
    public void postProductCollectionListCasel(Object id_list,ResultListener resultListener){
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("id_list", id_list);
        postJson(UrlConstant.PRODUCT_COLLECTION_LIST_CANSEL, params, new BaseResponseListener(context,resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle("");
            }
        });
    }
     //多项取消商家收藏
    public void postSellerCollectionListCansel(Object id_list,ResultListener resultListener){
        Map<String,Object> params=new HashMap<>();
        params.put("id_list",id_list);
        postJson(UrlConstant.SELLER_COLLECTION_LIST_CANSEL, params, new BaseResponseListener(context,resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle("");
            }
        });
    }
    //多项取消加盟商收藏
    public void postFranchiseeCollectionListCansel(Object id_list,ResultListener resultListener){
        Map<String,Object> params=new HashMap<>();
        params.put("idList",id_list);
        postJson(UrlConstant.FRANCHISEE_COLLECTION_LIST_CANSEL, params, new BaseResponseListener(context,resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle("");
            }
        });

    }

    //获取商家收藏列表
    public void getSellerfavorList(ResultListener<SellerFavorBean> resultListener){
        String token= GlobalInfo.userToken;
        if(TextUtils.isEmpty(token)){
            token="\"\"";
        }
        get(UrlConstant.PRODUCT_FAVOR_LIST+"?"+token,null,new CommonResponseListener<SellerFavorBean>(context,
                resultListener,new TypeToken<SellerFavorBean>(){}));
    }
    //取消商家收藏
    public void postSellerfavorCansel(int productId,int franchiseeId,ResultListener<Object> resultListener){
        String token= GlobalInfo.userToken;
        if(TextUtils.isEmpty(token)){
            token="\"\"";
        }
        Map<String,Object> params=new HashMap<>();
        params.put("product_id",productId);
        params.put("franchisee_id",franchiseeId);
        postJson(UrlConstant.PRODUCT_FAVOR_DELETE_ALL+"?"+token,params,new CommonResponseListener<Object>(context,
                resultListener,new TypeToken<Object>(){}));
    }

    //多项取消商家收藏
    public void getSellerfavorCanselList(List<Integer> listId,ResultListener<Object> resultListener){
        String token= GlobalInfo.userToken;
        if(TextUtils.isEmpty(token)){
            token="\"\"";
        }
        Map<String,Object> params=new HashMap<>();
        params.put("id_list",listId);
        postJson(UrlConstant.PRODUCT_FAVOR_DELETE_ALL+"?"+token,params,new CommonResponseListener<Object>(context,
                resultListener,new TypeToken<Object>(){}));
    }

}
