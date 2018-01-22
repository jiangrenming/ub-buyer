package com.ninetop.common.pay;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;
import com.ninetop.base.GlobalInfo;
import com.ninetop.bean.order.WeChatPayBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * @author jill
 *
 */
public class WXPayHelper {

	/************************************
	 * 微信支付入口，调用统一下单接口，生成预支付交易单
	 *********************************************/
	public static void pay(Activity activity, WeChatPayBean weChatPayBean) {
		GlobalInfo.wechat_app_id = weChatPayBean.appid;
		IWXAPI msgApi = WXAPIFactory.createWXAPI(activity, null);
		msgApi.registerApp(weChatPayBean.appid);

		PayReq req = new PayReq();
		req.appId = weChatPayBean.appid;
		req.partnerId = weChatPayBean.partnerid;
		req.prepayId = weChatPayBean.prepayid;
		req.packageValue = weChatPayBean.packageinfo;
		req.nonceStr = weChatPayBean.noncestr;
		req.timeStamp = weChatPayBean.timestamp;
		req.sign = weChatPayBean.sign;

		msgApi.registerApp(weChatPayBean.appid);
		msgApi.sendReq(req);

	}

}
