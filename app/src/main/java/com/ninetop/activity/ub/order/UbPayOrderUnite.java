package com.ninetop.activity.ub.order;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;
import com.hykj.myviewlib.gridview.GridPasswordView;
import com.ninetop.UB.QuesIsSetBean;
import com.ninetop.UB.order.UbPayBean;
import com.ninetop.UB.order.UbPayInfoBean;
import com.ninetop.UB.product.UbProductService;
import com.ninetop.activity.ub.question.QuestionActivity;
import com.ninetop.activity.ub.seller.SellerPayActivity;
import com.ninetop.base.BaseActivity;
import com.ninetop.base.GlobalInfo;
import com.ninetop.bean.order.WeChatPayBean;
import com.ninetop.bean.order.WeChatPayInfoBean;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.pay.AlipayCallBack;
import com.ninetop.common.pay.AlipayProcessor;
import com.ninetop.common.pay.WXPayHelper;
import com.ninetop.common.util.ToastUtils;
import com.ninetop.service.listener.CommonResultListener;
import com.ninetop.update.StringUtil;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/6/24.
 */

public class UbPayOrderUnite {
    private UbPayBean ubPayBean;
    private BaseActivity activity;
    private UbProductService ubProductService;
    private int pwdIsSet;

    public UbPayOrderUnite(UbPayBean ubPayBean, BaseActivity activity) {
        super();
        this.activity = activity;
        this.ubPayBean = ubPayBean;
        ubProductService = new UbProductService(activity);
    }

    public void toPay() {
        int payType = ubPayBean.payType;
        GlobalInfo.ubLastPayItem = ubPayBean;
        if (payType == 1) {
            ubProductService.getPwdIsSet(new CommonResultListener<QuesIsSetBean>(activity) {
                @Override
                public void successHandle(QuesIsSetBean result) throws JSONException {
                    pwdIsSet = result.is_set;
                    if (pwdIsSet == 0) {
                        //余额支付，设置支付密码
                        toSettingPWD();
                    }else if(pwdIsSet == 1){
                        showBalancePayDialog();
                    }
                }
            });
        } else if (payType == 2) {
            wechatPay();
        } else if (payType == 3) {
            aliPay();
        }
    }

    private void toSettingPWD() {
        new MyDialog(activity, MyDialog.DIALOG_TWOOPTION, "温馨提示", "您需要先去设置支付密码", new MyDialogOnClick() {
            @Override
            public void sureOnClick(View v) {
                activity.startActivity(QuestionActivity.class);
            }

            @Override
            public void cancelOnClick(View v) {

            }
        }).show();
    }

    private void showBalancePayDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.show();
        View view = View.inflate(activity.getApplicationContext(), R.layout.activity_pay_enter_password, null);
        final GridPasswordView psdView = (GridPasswordView) view.findViewById(R.id.gps_view);
        View closeView = view.findViewById(R.id.iv_close);
        View button = view.findViewById(R.id.btn_submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = psdView.getPassWord();
                if (password.length() < 6) {
                    activity.showToast("请输入6位支付密码");
                    return;
                }
                balancePay(password);
            }
        });
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setContentView(view);
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth());
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(R.color.transparent);
    }

    private void balancePay(String pwd) {
        ubProductService.postBalancePay(ubPayBean, pwd, new CommonResultListener(activity) {
            @Override
            public void successHandle(Object result) throws JSONException {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                UbPayBean payItem = GlobalInfo.ubLastPayItem;
                Map<String, String> map = new HashMap<String, String>();
                if (payItem != null) {
                    map.put(IntentExtraKeyConst.ORDER_CODE, payItem.orderCode);
                    map.put(IntentExtraKeyConst.ORDER_TOTAL, String.valueOf(payItem.payPrice));
                    map.put(IntentExtraKeyConst.ORDER_U, String.valueOf(payItem.ubPay));
                    map.put(IntentExtraKeyConst.ORDER_BALANCE,String.valueOf(payItem.balancePay));
                }
                activity.startActivity(UbPaySuccessActivity.class, map);
            }
        });
    }

    private void aliPay() {
        ubProductService.postAlipay(ubPayBean, new CommonResultListener<UbPayInfoBean>(activity) {
            @Override
            public void successHandle(UbPayInfoBean result) throws JSONException {
                new AlipayProcessor(activity).alipay(result.payinfo, new AlipayCallBack() {
                    @Override
                    public void paySuccess() {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        intent.putExtras(bundle);
                        UbPayBean payItem = GlobalInfo.ubLastPayItem;
                        Map<String, String> map = new HashMap<String, String>();
                        if (payItem != null) {
                            map.put(IntentExtraKeyConst.ORDER_CODE, payItem.orderCode);
                            map.put(IntentExtraKeyConst.ORDER_TOTAL, String.valueOf(payItem.payPrice));
                            map.put(IntentExtraKeyConst.ORDER_U, String.valueOf(payItem.ubPay));
                            map.put(IntentExtraKeyConst.ORDER_BALANCE,String.valueOf(payItem.balancePay));
                        }
                        activity.startActivity(UbPaySuccessActivity.class, map);
                    }

                    @Override
                    public void payFailure() {
                    }
                });
            }
        });
    }

    private void wechatPay() {
        ubProductService.postWechatPay(ubPayBean, new CommonResultListener<WeChatPayInfoBean>(activity) {
            @Override
            public void successHandle(WeChatPayInfoBean result) throws JSONException {
            //    WXPayHelper.pay(activity, result.payinfo);
                if (!StringUtil.isEmpty(result.toString())){
                    Log.i("商品支付=",result.getPayinfo().getAppid());
                    final WeChatPayBean payinfo = result.getPayinfo();
                    if (payinfo != null){
                        if (checkNessaryVlaue(payinfo)){
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String appid = payinfo.getAppid();
                                    IWXAPI api = WXAPIFactory.createWXAPI(activity,appid,true);
                                    api.registerApp(appid);
                                    PayReq req = new PayReq();
                                    req.appId = appid;
                                    req.partnerId = payinfo.getPartnerid();
                                    req.prepayId = payinfo.getPrepayid();
                                    req.packageValue = payinfo.getPackageinfo();
                                    req.nonceStr = payinfo.noncestr;
                                    req.timeStamp = payinfo.timestamp;
                                    req.sign = payinfo.sign;
                                    api.sendReq(req);
                                }
                            });
                        }else {
                            ToastUtils.showCenter("商户未注册appID或加签失败或商户id不存在，请检查返回的参数");
                            return;
                        }
                    }else {
                        Log.i("TAG","返回数据为空，请检查");
                    }
                }
            }
        });
    }


    private  boolean checkNessaryVlaue(WeChatPayBean payinfo){
        if (StringUtil.isEmpty(payinfo.getAppid()) ||
                StringUtil.isEmpty(payinfo.getPartnerid())
                || StringUtil.isEmpty(payinfo.getSign())){
            return false;
        }
        return true;
    }
}
