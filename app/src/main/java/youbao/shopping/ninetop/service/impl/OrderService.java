package youbao.shopping.ninetop.service.impl;

import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;

import youbao.shopping.ninetop.base.GlobalInfo;
import youbao.shopping.ninetop.base.Viewable;
import youbao.shopping.ninetop.bean.user.ChangingOrRefundBean;
import youbao.shopping.ninetop.bean.user.order.EvaluateBean;
import youbao.shopping.ninetop.bean.user.order.GoodsBean;
import youbao.shopping.ninetop.bean.user.order.OrderBean;
import youbao.shopping.ninetop.bean.user.order.OrderDetailsBean;
import youbao.shopping.ninetop.bean.user.order.SafeGuardDetailsBean;
import youbao.shopping.ninetop.common.constant.UrlConstant;
import youbao.shopping.ninetop.config.AppConfig;
import youbao.shopping.ninetop.service.BaseService;
import youbao.shopping.ninetop.service.listener.BaseResponseListener;
import youbao.shopping.ninetop.service.listener.CommonResponseListener;
import youbao.shopping.ninetop.service.listener.ResponseListener;
import youbao.shopping.ninetop.service.listener.ResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Response;

import static youbao.shopping.ninetop.common.constant.UrlConstant.EVALUAT;

/**
 * @date: 2016/12/1
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class OrderService extends BaseService {
    private static String SUCCESS = "SUCCESS";

    public OrderService(Viewable context) {
        super(context);
    }

    //我的订单
    public void getOrderList(String state, String page,ResultListener<OrderBean> resultListener) {
        HashMap<String, String> params = new HashMap<>();
        params.put("state", state);
        params.put("page", page);
        get(UrlConstant.ORDERS, params, new CommonResponseListener(context, resultListener, new TypeToken<OrderBean>() {
        }));

    }

    //我的订单
    public void getOrderListBean(String state, String page, ResultListener<OrderBean> resultListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("state", state);
        map.put("page", page);
        get(UrlConstant.ORDERS, map, new CommonResponseListener(context, resultListener, new TypeToken<OrderBean>() {
        }));
    }


    public void getGoodsList(ResultListener<List<GoodsBean>> resultListener) {
        get(UrlConstant.ORDERS, new HashMap<String, String>(), new CommonResponseListener(context, resultListener, new TypeToken<List<GoodsBean>>() {
        }));
    }

    //订单详情
    public void getOrderDetailList(String orderCode, ResultListener<OrderDetailsBean> resultListener) {
        get(UrlConstant.ORDERS + "/" + orderCode, new HashMap<String, String>(), new CommonResponseListener(context, resultListener, new TypeToken<OrderDetailsBean>() {
        }));
    }

    //去评价，获得可以评价的商品
    public void getEvaluateGoods(String code, ResultListener<EvaluateBean> resultListener) {
        HashMap<String, String> params = new HashMap<>();
        params.put("code", code);
        get(UrlConstant.TO_EVALUAT, params, new CommonResponseListener(context, resultListener, new TypeToken<EvaluateBean>() {
        }));
    }

    //取消订单
    public void cancelOrders(String code, ResultListener<String> resultListener) {
        HashMap<String, String> params = new HashMap();
        params.put("code", code);
        post(UrlConstant.CANCEL_ORDERS, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if (SUCCESS.equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("服务器异常");
                }
            }
        });
    }

    //删除订单
    public void deleteOrders(String code, ResultListener<String> resultListener) {
        HashMap<String, String> params = new HashMap();
        params.put("code", code);
        post(UrlConstant.DELETE_ORDERS, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if (SUCCESS.equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("服务器异常");
                }
            }
        });
    }

    //确认收货
    public void confirmGoods(String code,String password, ResultListener<String> resultListener) {
        HashMap<String, String> params = new HashMap();
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        params.put("order_id", code);
        params.put("pay_password", password);
        post(UrlConstant.UB_ORDER_CONFIRM_GET+ "?" + token,params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if (SUCCESS.equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("服务器异常");
                }
            }
        });
    }

    //取消退款申请
    public void cancelRefundApplication(String complaintId, ResultListener<String> resultListener) {
        HashMap<String, String> params = new HashMap();
        params.put("complaintId", complaintId);
        post(UrlConstant.CANCEL_REFUND_APPLICATION, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if (SUCCESS.equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("服务器异常");
                }
            }
        });
    }

    //评价晒单
    public void commentShow(String orderCodes, String[] goodsCodes,String[] skuDesc, String[] levels, String[] contentTxt, List<File> files, final ResultListener<String> resultListener) {
        context.addLoading();
        Map<String, String> params = new HashMap();
        params = assembleParam(params);
        params.put("orderCodes", orderCodes);

        Map<String, String[]> map = new HashMap();
        map = assembleArrayParam(map);
        map.put("goodsCodes", goodsCodes);
        map.put("skuDesc",skuDesc);
        map.put("levels", levels);
        map.put("contentTxt", contentTxt);
        //params.put("complaintId", complaintId);
        //params.put("complaintId", complaintId);
        postArray(UrlConstant.EVALUAT, params, map, "pic", files, new BaseResponseListener(context,resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {

            }
        });
        postFile(UrlConstant.EVALUAT, params, "pic1", files, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle("");
            }
        });
        PostRequest postrequest = OkGo.post(AppConfig.BASE_URL + EVALUAT).params(params);

       /* if (files == null) {
            postrequest.params("pic1", "");
        } else {
            postrequest.addFileParams("pic1", files);
        }
        postrequest.tag(EVALUAT).execute(new DefaultJsonObjectCallback(new ResponseListener() {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle("");
            }

            @Override
            public void fail(String errCode, String msg) {
                resultListener.failHandle(errCode, msg);
                context.showToast("解析失败");
            }

            @Override
            public void error(Response response, Exception e) {
                resultListener.errorHandle(response, e);
            }
        }));
*/
    }

    public void commentAndShow(String orderCode,List<String> goodsCodes,List<String> skuDesc,List<String> levels,List<String> contentTxt,List<File> files1,List<File> files2,List<File> files3,List<File> files4,List<File> files5,final ResultListener<String> resultListener){
        postComment(orderCode,goodsCodes, skuDesc, levels, contentTxt,files1,files2,files3,files4,files5, new ResponseListener() {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                resultListener.successHandle("");
            }

            @Override
            public void fail(String errCode, String msg) {
                resultListener.failHandle(errCode, msg);
                context.showToast(msg);
            }

            @Override
            public void error(Response response, Exception e) {
                resultListener.errorHandle(response, e);
                context.showToast("解析失败");
            }

            @Override
            public boolean showToast() {
                return false;
            }

            @Override
            public boolean showLoading() {
                return false;
            }
        });
    }
    /**
     * @param
     * @return
     */
    private String strings2JsonArray(String[] strings) {

        // 使用StringBuffer拼接

        StringBuffer sb = new StringBuffer();
        if (strings != null) {
            sb.append("[");
            for (int i = 0; i < strings.length; i++) {
                sb.append("\"");
                sb.append(strings[i]);
                sb.append("\"");
                sb.append(",");
            }
            if (sb.length() > 0) {
                // 去除最后一个逗号
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append("]");
        }
        return sb.toString();
    }

    //退换货
    public void getChangeOrRefundLis(String page, ResultListener<ChangingOrRefundBean> resultListener) {
        HashMap<String, String> params = new HashMap();
        params.put("page", page);
        get(UrlConstant.CHANGE_REFUND, params, new CommonResponseListener(context, resultListener, new TypeToken<ChangingOrRefundBean>() {
        }));
    }

    //维权单明细
    public void getSafaGuardDetails(String complaintId, ResultListener<SafeGuardDetailsBean> resultListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("complaintId", complaintId);
        get(UrlConstant.SAFE_GUARD_DETAILS, map, new CommonResponseListener(context, resultListener, new TypeToken<SafeGuardDetailsBean>() {
        }));

    }
}
