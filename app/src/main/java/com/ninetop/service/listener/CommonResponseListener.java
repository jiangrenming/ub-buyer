package com.ninetop.service.listener;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ninetop.base.Viewable;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by jill on 2016/11/22.
 */

public class CommonResponseListener<T> extends BaseResponseListener {
    private TypeToken<T> typeToken = null;

    public CommonResponseListener(Viewable context, ResultListener<T> resultListener, TypeToken<T> typeToken){
        super(context, resultListener);
        this.typeToken = typeToken;
    }

    @Override
    public void success(JSONObject dataObject) throws JsonSyntaxException, JSONException {
        Gson gson = new Gson();
        Type type = typeToken.getType();
        String jsonString = "";
        String string = dataObject.getString("data");
        if(string == null || "null".equals(string)){
            resultListener.successHandle(null);
            return;
        }

        if(string.startsWith("[")){
            jsonString = string;
        }else {
            JSONObject jsonObject = dataObject.getJSONObject("data");
            jsonString = jsonObject.toString();
        }

        T result = gson.fromJson(jsonString, type);
        resultListener.successHandle(result);
    }
}
