package com.ninetop.service.impl;

import com.google.gson.reflect.TypeToken;
import com.ninetop.base.Viewable;
import com.ninetop.common.constant.UrlConstant;
import com.ninetop.service.BaseService;
import com.ninetop.service.listener.CommonResponseListener;
import com.ninetop.service.listener.ResultListener;
import com.ninetop.update.UpdateBean;

/**
 * Created by ${} on 2017/9/14.
 */

public class UpdateService extends BaseService {
    public UpdateService(Viewable context) {
        super(context);
    }

    public void update(final ResultListener<UpdateBean> resultListener) {
//        Map<String, Object> params = new HashMap<>();
//        postJson(UrlConstant.APP_UPDATE, params, new BaseResponseListener(context, resultListener) {
//            @Override
//            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
//                JSONObject dataJson = jsonObject.getJSONObject("data");
//                resultListener.successHandle(dataJson);
//            }
//        });
        get(UrlConstant.APP_UPDATE, null, new CommonResponseListener<UpdateBean>(context,
                resultListener, new TypeToken<UpdateBean>() {
        }));
    }
}
