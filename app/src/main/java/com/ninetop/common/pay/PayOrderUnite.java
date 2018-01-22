package com.ninetop.common.pay;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;
import com.hykj.myviewlib.gridview.GridPasswordView;
import com.ninetop.activity.order.pay.PaySuccessActivity;
import com.ninetop.activity.user.AccountGuardActivity;
import com.ninetop.base.BaseActivity;
import com.ninetop.base.GlobalInfo;
import com.ninetop.bean.order.OrderAliPayBean;
import com.ninetop.bean.order.WeChatPayInfoBean;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.constant.PayTypeEnum;
import com.ninetop.service.impl.ProductService;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import youbao.shopping.R;

public class PayOrderUnite {
	private OrderPayItem payItem;
	private BaseActivity activity;

    private ProductService productService;

	public PayOrderUnite(OrderPayItem payItem, BaseActivity activity) {
		super();
		this.payItem = payItem;
		this.activity = activity;

        productService = new ProductService(activity);
	}

	public void toPay(){
		int payType = payItem.payType;
        GlobalInfo.lastPayItem = payItem;

        if (payType == PayTypeEnum.TYPE_BALANCE.code) {
           if (payItem.isSettingPWD){
               showBalancePayDialog();
           }else {
               //去设置密码
               toSettingPWD();
           }
        } else if(payType == PayTypeEnum.TYPE_ALIPAY.code){
            alipay();
        }else if(payType == PayTypeEnum.TYPE_WECHAT.code){
            wechatPay();
        }
	}

    private void toSettingPWD() {
        new MyDialog(activity, MyDialog.DIALOG_TWOOPTION, "", "去设置支付密码", new MyDialogOnClick() {
            @Override
            public void sureOnClick(View v) {
                activity.startActivity(AccountGuardActivity.class);
            }
            @Override
            public void cancelOnClick(View v) {}
        }).show();
    }
    private void showBalancePayDialog() {
        final Dialog dialog = new Dialog(activity);
        dialog.show();
        View view = View.inflate(activity.getApplicationContext(),
                R.layout.activity_pay_enter_password, null);

        final GridPasswordView psdView = (GridPasswordView) view.findViewById(R.id.gps_view);
        View closeView =  view.findViewById(R.id.iv_close);
        View btnSubmit = view.findViewById(R.id.btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
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
        WindowManager.LayoutParams lp = dialog.getWindow()
                .getAttributes();
        lp.width = (int) (display.getWidth()); // 设置宽度
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    private void balancePay(String password) {
        final String orderNo = payItem.orderNo;

        productService.orderPayment(orderNo, password, new CommonResultListener<String>(activity) {
            @Override
            public void successHandle(String result) throws JSONException {
                Map<String, String> map = new HashMap<String, String>();
                map.put(IntentExtraKeyConst.ORDER_NO, orderNo);
                map.put(IntentExtraKeyConst.ORDER_TOTAL, payItem.orderPrice);

                activity.startActivity(PaySuccessActivity.class, map);
                activity.finish();
            }
        });
    }

    private void alipay() {
        final String orderNo = payItem.orderNo;
        String orderFrom = payItem.orderFrom;
        boolean isParent = "buy".equals(orderFrom);

        productService.orderAliPay(orderNo, isParent, new CommonResultListener<OrderAliPayBean>(activity) {
            @Override
            public void successHandle(OrderAliPayBean result) throws JSONException {
                new AlipayProcessor(activity).alipay(result.payinfo, new AlipayCallBack() {
                    @Override
                    public void paySuccess() {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        intent.putExtras(bundle);
                        OrderPayItem payItem = GlobalInfo.lastPayItem;
                        Map<String, String> map = new HashMap<String, String>();
                        if(payItem != null) {
                            map.put(IntentExtraKeyConst.ORDER_NO, payItem.orderNo);
                            map.put(IntentExtraKeyConst.ORDER_TOTAL, payItem.orderPrice);
                        }
                        activity.startActivity(PaySuccessActivity.class, map);
                    }

                    @Override
                    public void payFailure() {
                    }
                });
            }
        });
    }

    private void wechatPay(){
        final String orderNo = payItem.orderNo;
        String orderFrom = payItem.orderFrom;
        boolean isParent = "buy".equals(orderFrom);

        productService.orderWechatPay(orderNo, isParent, new CommonResultListener<WeChatPayInfoBean>(activity) {
            @Override
            public void successHandle(WeChatPayInfoBean result) throws JSONException {
//                activity.showToast(result.payinfo.appid);
                WXPayHelper.pay(activity, result.payinfo);
            }
        });
    }
}
