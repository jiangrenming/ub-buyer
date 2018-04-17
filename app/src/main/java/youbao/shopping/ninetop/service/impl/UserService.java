package youbao.shopping.ninetop.service.impl;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;

import youbao.shopping.ninetop.base.Viewable;
import youbao.shopping.ninetop.bean.user.RegisterBean;
import youbao.shopping.ninetop.common.constant.UrlConstant;
import youbao.shopping.ninetop.config.AppConfig;
import youbao.shopping.ninetop.service.BaseService;
import youbao.shopping.ninetop.service.listener.BaseResponseListener;
import youbao.shopping.ninetop.service.listener.ResponseListener;
import youbao.shopping.ninetop.service.listener.ResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by jill on 2016/11/22.
 */

public class UserService extends BaseService {
    public UserService(Viewable context) {
        super(context);
    }

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


    private RegisterBean bean;
    public void register(String mobile, String authCode, String password, String invitationCode,
                         final ResultListener<String> resultListener)throws JSONException{

        Map<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("code", authCode);
        params.put("password", password);
        params.put("invitationCode",invitationCode);
        postInvitationCodeJson(UrlConstant.USER_REGISTER, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
//                {"msg":"","code":2000,"data":{"money":"7.2","token":"d9ed235d86d673f567ced67ea31afe3eda8ef95f"}}
//                {"msg":"邀请码不存在","code":211}

                JSONObject dataJson = jsonObject.getJSONObject("data");
                String token = dataJson.getString("token");
                Log.e("注册数据=", "数据=" + jsonObject.toString());
                resultListener.successHandle(jsonObject.toString());
            }
        });

    }




//    public void getAuthCode(String mobile,int type, ResultListener<String> resultListener){
//        Map<String, String> params = new HashMap<>();
//        params.put("mobile", mobile);
//        params.put("type", type+"");
//        get(UrlConstant.AUTHCODE, params, new BaseResponseListener(context, resultListener) {
//            @Override
//            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
//                String code = jsonObject.getString("code");
//                String msg = jsonObject.getString("msg");
//                if ("SUCCESS".equals(code)){
//                    JSONObject data = jsonObject.getJSONObject("data");
//                    if (!data.toString().equals("{}")&&!TextUtils.isEmpty(data.toString())){
//                        String authCode = data.getString("authCode");
//                        resultListener.successHandle(authCode);
//                    }
//                }else {
//                    context.showToast(msg);
//                }
//            }
//        });
//    }

    public void getAuthCode(String mobile,int type,ResultListener<String> resultListener){
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

    public void getPwdCode(String mobile,int type,ResultListener<String> resultListener){
        get(UrlConstant.PWD_CODE + "/"+ mobile,null, new BaseResponseListener(context,resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                JSONObject data=jsonObject.getJSONObject("data");
                String msg=jsonObject.getString("msg");
                if(!data.toString().equals("{}")&&!TextUtils.isEmpty(data.toString())){
                    String code=data.getString("code");
                    resultListener.successHandle(code);
                }else {
                    context.showToast(msg);
                }
            }
        });
    }

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

}
