package com.ninetop.UB;

import android.text.TextUtils;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.ninetop.UB.question.QuestionBean;
import com.ninetop.activity.ub.bean.product.BalanceBean;
import com.ninetop.activity.ub.bean.product.BalanceIntoBean;
import com.ninetop.activity.ub.bean.product.WalletBean;
import com.ninetop.base.GlobalInfo;
import com.ninetop.base.Viewable;
import com.ninetop.bean.order.OrderAliPayBean;
import com.ninetop.bean.order.WeChatPayInfoBean;
import com.ninetop.common.constant.UrlConstant;
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


public class UbUserCenterService extends BaseService {
    public UbUserCenterService(Viewable context) {
        super(context);
    }

    private static String SUCCESS = "SUCCESS";
    private static String PICS = "pics";

    //用户详情
    public void getUserInfo(ResultListener<UbUserDetail> responseListener) {
        get(UrlConstant.DETAIL, new HashMap<String, String>(),
                new CommonResponseListener(context, responseListener, new TypeToken<UbUserDetail>() {

                }));
    }

    //个人中心
    public void getUserCenter(ResultListener<UbUserInfo> responseListener) {
        get(UrlConstant.CENTER, new HashMap<String, String>(),
                new CommonResponseListener(context, responseListener, new TypeToken<UbUserInfo>() {

                }));
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
                if ("200".equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("添加失败");
                }
            }
        });
    }

    //删除地址
    public void deleteAddress(String id, ResultListener<String> resultListener) {
        //        HashMap<String, String> map = new HashMap();
        //        //map.put("key", token);
        //        map.put("id", id);
        get(UrlConstant.RECEIVE_DELETE + "/" + id, null, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if ("200".equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("删除失败");
                }
            }
        });
    }

    //编辑地址
    public void editAddress(String index, String username, String mobile, String addr_province, String addr_city, String addr_country
            , String detail, String isDefault, ResultListener<String> resultListener) {
        HashMap<String, Object> map = new HashMap();
        int id = Integer.parseInt(index);
        map.put("id", id);
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
                if ("200".equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("编辑失败");
                }
            }
        });
    }

    //地址管理
    public void getAddressList(ResultListener resultListener) {
        get(UrlConstant.RECEIVE_LIST, null, new CommonResponseListener(context, resultListener, new TypeToken<List<UbAddressBean>>() {
        }));
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
                if ("200".equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("修改失败");
                }
            }
        });
    }

    //个人中心
    public void postUserInfo(String avatar, String mobile, String nickname, float u_balance, float account_balance,
                             ResultListener<String> resultListener) {
        HashMap<String, Object> map = new HashMap();
        map.put("avatar", avatar);
        map.put("mobile", mobile);
        map.put("nickname", nickname);
        map.put("u_balance", u_balance);
        map.put("account_balance", account_balance);

        postJson(UrlConstant.CENTER, map, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if ("200".equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("修改失败");
                }
            }
        });
    }


    //    //福利卡余额
    //    public void freeMoney(ResultListener<String> resultListener) {
    //        get(UrlConstant.FREECAS, new HashMap<String, String>(), new BaseResponseListener(context, resultListener) {
    //            @Override
    //            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
    //                String code = jsonObject.getString("code");
    //                if (SUCCESS.equals(code)) {
    //                    JSONObject data = jsonObject.getJSONObject("data");
    //                    String balance = data.getString("balance");
    //                    resultListener.successHandle(balance);
    //                } else {
    //                    context.showToast("查询失败");
    //                }
    //            }
    //        });
    //    }

    //    //福利卡充值
    //    public void topUpFreeCharge(String freecaNumber, ResultListener<String> resultListener) {
    //        HashMap<String, String> map = new HashMap<>();
    //        map.put("freecaCode", freecaNumber);
    //        post(UrlConstant.FREECAS, map, new BaseResponseListener(context, resultListener) {
    //            @Override
    //            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
    //                String code = jsonObject.getString("code");
    //                if (SUCCESS.equals(code)) {
    //                    resultListener.successHandle(code);
    //                } else {
    //                    context.showToast("充值失败");
    //                }
    //            }
    //        });
    //    }

    //    //申请退款
    //    public void applyForReturnMoney(String orderCode, String goodsCode, String skuId, String complaintsType,
    //                                    String complaintsReason, String returnMoney,
    //                                    String remark, List<File> files, ResultListener<String> resultListener) {
    //        HashMap<String, String> map = new HashMap<>();
    //        map.put("orderCode", orderCode);
    //        map.put("goodsCode", goodsCode);
    //        map.put("skuId", skuId);
    //        map.put("complaintsType", complaintsType);
    //        map.put("complaintsReason", complaintsReason);
    //        map.put("returnMoney", returnMoney);
    //        map.put("remark", remark);
    //        postFile(UrlConstant.ORDERS_COMPLAINTS, map, PICS, files, new BaseResponseListener(context, resultListener) {
    //            @Override
    //            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
    //                String code = jsonObject.getString("code");
    //                if (SUCCESS.equals(code)) {
    //                    resultListener.successHandle(code);
    //                } else {
    //                    context.showToast("申请失败");
    //                }
    //            }
    //        });
    //    }
    //
    //    //取消申请退款
    //    public void cancelReturnMoney(String complaintId, ResultListener<String> resultListener) {
    //        HashMap<String, String> map = new HashMap<>();
    //        map.put("complaintId", complaintId);
    //        post(UrlConstant.REVO_ORDERS_COMPLAINTS, map, new BaseResponseListener(context, resultListener) {
    //            @Override
    //            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
    //                String code = jsonObject.getString("code");
    //                if (SUCCESS.equals(code)) {
    //                    resultListener.successHandle(code);
    //                }
    //            }
    //        });
    //    }

    //        //填写物流
    //        public void fillLogistics(String complaintId, String logisticsType, String logisticsCode, ResultListener<String> resultListener) {
    //            HashMap<String, String> map = new HashMap<>();
    //            map.put("complaintId", complaintId);
    //            map.put("logisticsType", logisticsType);
    //            map.put("logisticsCode", logisticsCode);
    //            post(UrlConstant.LOGISTICS_INFO, map, new BaseResponseListener(context, resultListener) {
    //                @Override
    //                public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
    //                    String code = jsonObject.getString("code");
    //                    if (SUCCESS.equals(code)) {
    //                        resultListener.successHandle(code);
    //                    }
    //                }
    //            });
    //        }

    //    //查看物流
    //    public void seeLogistics(String orderCode, ResultListener<LogisticBean> resultListener) {
    //        HashMap<String, String> map = new HashMap<>();
    //        map.put("code", orderCode);
    //        get(UrlConstant.SEE_LOGISTICS_INFO, map, new CommonResponseListener(context, resultListener, new TypeToken<LogisticBean>() {
    //        }));
    //    }

    //    //分享App
    //    public void shareApp(ResultListener<ShareAppBean> resultListener) {
    //        get(UrlConstant.SHARE_APP, null, new CommonResponseListener(context, resultListener, new TypeToken<ShareAppBean>() {
    //        }));
    //    }

    //修改用户信息
    public void fixUserInfo(String nickName, String sex, List<File> fileList, ResultListener<UbUserDetail> resultListener) {
        HashMap<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(nickName)) {
            map.put("nickName", nickName);
        }
        if (!TextUtils.isEmpty(sex)) {
            map.put("sex", sex);
        }
        postFile(UrlConstant.CENTER, map, "avatar", fileList, new CommonResponseListener(context, resultListener, new TypeToken<UbUserInfo>() {
        }));
    }

    //ub修改用户资料
    public void userInfo(String nickName, String sex, String birthday, ResultListener<UbUserDetail> resultListener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("nick_name", nickName);
        map.put("sex", sex);
        map.put("birthday", birthday);
        postJson(UrlConstant.UPDATE, map, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if ("200".equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("添加失败");
                }
            }
        });

    }

    //ub修改用户头像
    public void fixHeadImage(List<File> fileList, ResultListener resultListener) {
        Map<String, String> params = new HashMap<>();
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
//        params.put("token",token);
        postFile(UrlConstant.UPDATE_AVATAR + "?token=" + token, params, "avatar", fileList, new BaseResponseListener(context, resultListener) {
            @Override
            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
                String code = jsonObject.getString("code");
                if ("200".equals(code)) {
                    resultListener.successHandle(code);
                } else {
                    context.showToast("修改失败");
                }
            }
        });
    }
    //


//            //修改登录密码
//            public void fixLoginPwd(String newPwd, ResultListener<String> resultListener) {
//                HashMap<String, String> hashMap = new HashMap();
//                hashMap.put("newPassword", newPwd);
//                post(UrlConstant.FIX_LOGIN_PWD, hashMap, new BaseResponseListener(context, resultListener) {
//                    @Override
//                    public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
//                        String code = jsonObject.getString("code");
//                        String msg = jsonObject.getString("msg");
//                        if (SUCCESS.equals(code)) {
//                            resultListener.successHandle(code);
//                        } else {
//                            context.showToast("修改失败");
//                        }
//                    }
//                });
//            }

//            //支付密码
//            public void settingPayPwd(String newPwd, ResultListener<String> resultListener) {
//                HashMap<String, String> hashMap = new HashMap();
//                hashMap.put("payPwd", newPwd);
//                post(UrlConstant.FIX_PAYPWD, hashMap, new BaseResponseListener(context, resultListener) {
//                    @Override
//                    public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
//                        String code = jsonObject.getString("code");
//                        String msg = jsonObject.getString("msg");
//                        if (SUCCESS.equals(code)) {
//                            resultListener.successHandle(code);
//                        } else {
//                            context.showToast(msg);
//                        }
//                    }
//                });
//            }

//            //明细
//                    public void getDetails(ResultListener resultListener) {
//                        get(UrlConstant.DETAILS, new HashMap<String, String>(), new BaseResponseListener(context, resultListener) {
//                            @Override
//                            public void success(JSONObject jsonObject) throws JsonSyntaxException, JSONException {
//                                String code = jsonObject.getString("code");
//                                if (SUCCESS.equals(code)) {
//                                    String string = jsonObject.toString();
//                                    resultListener.successHandle(string);
//                                }
//                            }
//                        });
//
//                    }
//                }

    //ub交易记录
    public void getUbRecord(String date, ResultListener<List<WalletBean>> resultListener) {
        Map<String, String> params = new HashMap<>();
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        params.put("token", token);
        params.put("date", date);
        get(UrlConstant.MY_WALLET_UB_RECORD, params, new CommonResponseListener<List<WalletBean>>(context, resultListener,
                new TypeToken<List<WalletBean>>() {
                }));
    }

    //余额交易记录
    public void getBalanceRecord(String date, ResultListener<List<BalanceBean>> resultListener) {
        Map<String, String> params = new HashMap<>();
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        params.put("token", token);
        params.put("date", date);
        get(UrlConstant.MY_WALLET_BALANCE_RECORD, params, new CommonResponseListener<List<BalanceBean>>(context, resultListener,
                new TypeToken<List<BalanceBean>>() {
                }));
    }

    //余额充值面额
    public void getBalanceRecord(ResultListener<List<BalanceIntoBean>> resultListener) {
        Map<String, String> params = new HashMap<>();
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        params.put("token", token);
        get(UrlConstant.MY_WALLET_BALANCE_PAY, params, new CommonResponseListener<List<BalanceIntoBean>>(context, resultListener,
                new TypeToken<List<BalanceIntoBean>>() {
                }));
    }

    //余额充值支付宝
    public void getBalanceAlpay(int id, ResultListener<OrderAliPayBean> resultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        postJson(UrlConstant.MY_WALLET_BALANCE_ALPAY, params, new CommonResponseListener<OrderAliPayBean>(context, resultListener,
                new TypeToken<OrderAliPayBean>() {
                }));
    }

    //余额充值支付宝
    public void getBalanceWechatPay(int id, ResultListener<WeChatPayInfoBean> resultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        postJson(UrlConstant.MY_WALLET_BALANCE_WECHAT_PAY, params, new CommonResponseListener<WeChatPayInfoBean>(context, resultListener,
                new TypeToken<WeChatPayInfoBean>() {
                }));
    }


    //安全问题（所有）列表
    public void getQuestionList(ResultListener<List<QuestionBean>> resultListener) {
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        get(UrlConstant.UB_QUESTION_LIST, params, new CommonResponseListener<List<QuestionBean>>(context, resultListener,
                new TypeToken<List<QuestionBean>>() {

                }));
    }

    //用户设置{重置}安全问题
    public void postQuestionReset(int id1, String q1, int id2, String q2, int id3, String q3, ResultListener resultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("q1_id", id1);
        params.put("q1_answer", q1);
        params.put("q2_id", id2);
        params.put("q2_answer", q2);
        params.put("q3_id", id3);
        params.put("q3_answer", q3);
        postJson(UrlConstant.UB_QUESTION_RESET, params, new CommonResponseListener<>(context, resultListener, new TypeToken<Object>() {
        }));
    }

    //用户安全问题（公回答）
    public void getQuestionSelect(ResultListener<List<QuestionBean>> resultListener) {
        Map<String, String> params = new HashMap<>();
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        params.put("token", token);
        get(UrlConstant.UB_QUESTION_SELECT, params, new CommonResponseListener<List<QuestionBean>>(context, resultListener, new TypeToken<List<QuestionBean>>() {
        }));

    }

    //用户回答安全问题
    public void postQuestionSafe(String answer1, String answer2, String answer3, ResultListener<List<QuestionBean>> resultListener) {
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        Map<String, Object> params = new HashMap<>();
        params.put("token", token);
        params.put("q1_answer", answer1);
        params.put("q2_answer", answer2);
        params.put("q3_answer", answer3);
        postJson(UrlConstant.UB_QUESTION_SAFE, params, new CommonResponseListener<List<QuestionBean>>(context, resultListener, new TypeToken<List<QuestionBean>>() {
        }));

    }

    //用户回答安全问题
    public void getQuestionReset(ResultListener resultListener) {
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        get(UrlConstant.UB_QUESTION_SET_IS, params, new CommonResponseListener(context, resultListener, new TypeToken<Object>() {
        }));
    }

    //支付密码设置（修改）
    public void postPaypwdReset(int pwd, ResultListener resultListener) {
        Map<String, Object> params = new HashMap<>();
        params.put("pay_password", pwd);
        postJson(UrlConstant.UB_QUESTION_SELECT, params, new CommonResponseListener(context, resultListener,
                new TypeToken<Object>() {
                }));
    }

    //用户是否设置过安全密码
    public void getIsSet(ResultListener resultListener) {
        String token = GlobalInfo.userToken;
        if (TextUtils.isEmpty(token)) {
            token = "\"\"";
        }
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        get(UrlConstant.UB_QUESTION_PWD_IS, params, new CommonResponseListener(context, resultListener, new TypeToken<Object>() {
        }));

    }
}



