package com.ninetop.service.impl;

import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.ninetop.base.Viewable;
import com.ninetop.common.constant.UrlConstant;
import com.ninetop.service.BaseService;
import com.ninetop.service.listener.BaseResponseListener;
import com.ninetop.service.listener.ResultListener;

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
    public void register(String mobile, String authCode, String password,
                         final ResultListener<String> resultListener)throws JSONException{

        Map<String, Object> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("code", authCode);
        params.put("password", password);
        params.put("create_device_type", "A");
        postJson(UrlConstant.USER_REGISTER, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                JSONObject dataJson = jsonObject.getJSONObject("data");
                String token = dataJson.getString("token");
                resultListener.successHandle(token);
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
