package com.ninetop.service.impl;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ninetop.base.Viewable;
import com.ninetop.bean.ShareAppBean;
import com.ninetop.bean.user.AddressBean;
import com.ninetop.bean.user.CouponBean;
import com.ninetop.bean.user.LogisticBean;
import com.ninetop.bean.user.OrderCountBean;
import com.ninetop.bean.user.UserInfo;
import com.ninetop.common.constant.SharedKeyConstant;
import com.ninetop.common.constant.UrlConstant;
import com.ninetop.common.util.MySharedPreference;
import com.ninetop.service.BaseService;
import com.ninetop.service.listener.BaseResponseListener;
import com.ninetop.service.listener.CommonResponseListener;
import com.ninetop.service.listener.ResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserCenterService extends BaseService {
    public UserCenterService(Viewable context) {
        super(context);
    }

    private static String SUCCESS = "SUCCESS";
    private static String PICS = "pics";

    public void getUserInfo(ResultListener<UserInfo> responseListener) {
        get(UrlConstant.CENTER, new HashMap<String, String>(),
                new CommonResponseListener(context, responseListener, new TypeToken<UserInfo>() {

                }));
    }

    public void getOrderCount(ResultListener<OrderCountBean> responseListener) {
        get(UrlConstant.ORDER_COUNT, new HashMap<String, String>(), new CommonResponseListener(context, responseListener, new TypeToken<OrderCountBean>() {
        }));
    }

    public void postFeedBack(String content, final ResultListener<String> resultListener) {
        Map<String, String> params = new HashMap<>();
        params.put("content", content);
        post(UrlConstant.POST_FEEDBACK, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if ("SUCCESS".equals(code)) {
                    resultListener.successHandle(code);
                }
            }
        });
    }

    public void confirmID(String mobile, String authCode, final ResultListener<String> resultListener) {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        params.put("authCode", authCode);
        post(UrlConstant.CONFIRM_ID, params, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if ("SUCCESS".equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("验证码错误");
                }
            }
        });
    }

    //优惠券
    public void getCouponList(ResultListener<List<CouponBean>> responseListener) {
        get(UrlConstant.COUPONS, new HashMap<String, String>(), new CommonResponseListener(context, responseListener, new TypeToken<List<CouponBean>>() {
        }));
    }

    //地址管理
    public void getAddressList(ResultListener<List<AddressBean>> responseListener) {
        get(UrlConstant.RECEIVE_LIST + "/" + MySharedPreference.get(SharedKeyConstant.TOKEN, null, (Context) context),
                new HashMap<String, String>(), new CommonResponseListener(context, responseListener, new TypeToken<List<AddressBean>>() {
                }));
    }

    //删除地址
    public void deleteAddress(String token, String id, ResultListener<String> resultListener) {
        HashMap<String, String> map = new HashMap();
        map.put("key", token);
        map.put("id", id);
        get(UrlConstant.RECEIVE_DELETE + "/" + id, map, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if (SUCCESS.equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("删除失败");
                }
            }
        });
    }

    //添加地址
    public void addAddress(String username, String mobile, String addr_province, String addr_city, String addr_country
            , String detail, String isDefault, ResultListener<String> resultListener) {
        HashMap<String, Object> map = new HashMap();
        map.put("name", username);
        map.put("mobile", mobile);
        map.put("addr_province", addr_province);
        map.put("addr_city", addr_city);
        map.put("addr_county", addr_country);
        map.put("addr_detail", detail);
        map.put("post_code", "");
        map.put("is_default", isDefault);
        postJson(UrlConstant.RECEIVE_ADD, map, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if (SUCCESS.equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("添加失败");
                }
            }
        });
    }

    //修改用户资料，昵称，性别，生日
    public void updateUserInfo(String nickname, int sex, String birthday,
                               ResultListener<String> resultListener) {
        HashMap<String, Object> map = new HashMap();
        map.put("nickname", nickname);
        map.put("sex", sex);
        map.put("birthday", birthday);

        postJson(UrlConstant.UPDATE, map, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if (SUCCESS.equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("修改失败");
                }
            }
        });
    }

    //个人中心
//    public void getUserInfo(String avatar, String mobile, String nickname, float u_balance, float account_balance,
//                            ResultListener<String> resultListener) {
//        HashMap<String, Object> map = new HashMap();
//        map.put("avatar", avatar);
//        map.put("mobile", mobile);
//        map.put("nickname", nickname);
//        map.put("u_balance", u_balance);
//        map.put("account_balance", account_balance);
//
//        getJson(UrlConstant.CENTER, map, new BaseResponseListener(context, resultListener) {
//            @Override
//            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
//                String code = jsonObject.getString("code");
//                if (SUCCESS.equals(code)) {
//                    resultListener.successHandle(code);
//                } else {
//                    context.showToast("修改失败");
//                }
//            }
//        });
//    }

    //编辑地址
    public void editAddress(String username, String mobile, String addr_province, String addr_city, String addr_country
            , String detail, String isDefault, ResultListener<String> resultListener) {
        HashMap<String, Object> map = new HashMap();
        map.put("name", username);
        map.put("mobile", mobile);
        map.put("addr_province", addr_province);
        map.put("addr_city", addr_city);
        map.put("addr_county", addr_country);
        map.put("addr_detail", detail);
        map.put("post_code", "");
        map.put("is_default", isDefault);
        postJson(UrlConstant.RECEIVE_EDIT, map, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if (SUCCESS.equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("编辑失败");
                }
            }
        });
    }

    //福利卡余额
    public void freeMoney(ResultListener<String> resultListener) {
        get(UrlConstant.FREECAS, new HashMap<String, String>(), new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if (SUCCESS.equals(code)) {
                    JSONObject data = jsonObject.getJSONObject("data");
                    String balance = data.getString("balance");
                    resultListener.successHandle(balance);
                } else {
                    context.showToast("查询失败");
                }
            }
        });
    }

    //福利卡充值
    public void topUpFreeCharge(String freecaNumber, ResultListener<String> resultListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("freecaCode", freecaNumber);
        post(UrlConstant.FREECAS, map, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if (SUCCESS.equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("充值失败");
                }
            }
        });
    }

    //申请退款
    public void applyForReturnMoney(String orderCode, String goodsCode, String skuId, String complaintsType,
                                    String complaintsReason, String returnMoney,
                                    String remark, List<File> files, ResultListener<String> resultListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("orderCode", orderCode);
        map.put("goodsCode", goodsCode);
        map.put("skuId", skuId);
        map.put("complaintsType", complaintsType);
        map.put("complaintsReason", complaintsReason);
        map.put("returnMoney", returnMoney);
        map.put("remark", remark);
        postFile(UrlConstant.ORDERS_COMPLAINTS, map, PICS, files, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if (SUCCESS.equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("申请失败");
                }
            }
        });
    }

    //取消申请退款
    public void cancelReturnMoney(String complaintId, ResultListener<String> resultListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("complaintId", complaintId);
        post(UrlConstant.REVO_ORDERS_COMPLAINTS, map, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if (SUCCESS.equals(code)) {
                    resultListener.successHandle(code);
                }
            }
        });
    }

        //填写物流
        public void fillLogistics(String complaintId, String logisticsType, String logisticsCode, ResultListener<String> resultListener) {
            HashMap<String, String> map = new HashMap<>();
            map.put("complaintId", complaintId);
            map.put("logisticsType", logisticsType);
            map.put("logisticsCode", logisticsCode);
            post(UrlConstant.LOGISTICS_INFO, map, new BaseResponseListener(context, resultListener) {
                @Override
                public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                    String code = jsonObject.getString("code");
                    if (SUCCESS.equals(code)) {
                        resultListener.successHandle(code);
                    }
                }
            });
        }

    //查看物流
    public void seeLogistics(String orderCode, ResultListener<LogisticBean> resultListener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("code", orderCode);
        get(UrlConstant.SEE_LOGISTICS_INFO, map, new CommonResponseListener(context, resultListener, new TypeToken<LogisticBean>() {
        }));
    }

    //分享App
    public void shareApp(ResultListener<ShareAppBean> resultListener) {
        get(UrlConstant.SHARE_APP, null, new CommonResponseListener(context, resultListener, new TypeToken<ShareAppBean>() {
        }));
    }

    //修改用户信息
    public void fixUserInfo(String nickName, String sex, List<File> fileList, ResultListener<UserInfo> resultListener) {
        HashMap<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(nickName)) {
            map.put("nickName", nickName);
        }
        if (!TextUtils.isEmpty(sex)) {
            map.put("sex", sex);
        }
        postFile(UrlConstant.CENTER, map, "avatar", fileList, new CommonResponseListener(context, resultListener, new TypeToken<UserInfo>() {
        }));
    }

    //ub修改用户资料
    public void userInfo(String nickName, String sex, String birthday, ResultListener<UserInfo> resultListener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("nick_name", nickName);
        map.put("sex", sex);
        map.put("birthday", birthday);
        postJson(UrlConstant.UPDATE, map, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if (SUCCESS.equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("添加失败");
                }
            }
        });

    }

    //ub修改用户头像
    public void fixHeadImage(File file, ResultListener<UserInfo> resultListener) {
        HashMap<String, String> map = new HashMap<>();
        postOneFile(UrlConstant.UPDATE_AVATAR, map, "avatar", file, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if (SUCCESS.equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("失败");
                }
            }
        });

    }

    //修改登录密码
    public void fixLoginPwd(String newPwd, ResultListener<String> resultListener) {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("newPassword", newPwd);
        post(UrlConstant.FIX_LOGIN_PWD, hashMap, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                String msg = jsonObject.getString("msg");
                if (SUCCESS.equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("修改失败");
                }
            }
        });
    }

    //支付密码
    public void settingPayPwd(String newPwd, ResultListener<String> resultListener) {
        HashMap<String, String> hashMap = new HashMap();
        hashMap.put("payPwd", newPwd);
        post(UrlConstant.FIX_PAYPWD, hashMap, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                String msg = jsonObject.getString("msg");
                if (SUCCESS.equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast(msg);
                }
            }
        });
    }

    //明细
    public void getDetails(ResultListener resultListener) {
        get(UrlConstant.DETAILS, new HashMap<String, String>(), new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if (SUCCESS.equals(code)) {
                    String string = jsonObject.toString();
                    resultListener.successHandle(string);
                }
            }
        });

    }

}
