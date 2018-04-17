package youbao.shopping.ninetop.service.impl;

import com.google.gson.reflect.TypeToken;
import youbao.shopping.ninetop.base.Viewable;
import youbao.shopping.ninetop.common.constant.UrlConstant;
import youbao.shopping.ninetop.service.BaseService;
import youbao.shopping.ninetop.service.listener.CommonResponseListener;
import youbao.shopping.ninetop.service.listener.ResultListener;

/**
 * Created by ${} on 2017/9/14.
 */

public class UpdateService extends BaseService {
    public UpdateService(Viewable context) {
        super(context);
    }

//    public void update(final ResultListener<UpdateBean> resultListener) {
//        Map<String, Object> params = new HashMap<>();
//        postJson(UrlConstant.APP_UPDATE, params, new BaseResponseListener(context, resultListener) {
//            @Override
//            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
//                JSONObject dataJson = jsonObject.getJSONObject("data");
//                resultListener.successHandle(dataJson);
//            }
//        });
//        get(UrlConstant.APP_UPDATE, null, new CommonResponseListener<UpdateBean>(context,
//                resultListener, new TypeToken<UpdateBean>() {
//        }));
//    }
}
