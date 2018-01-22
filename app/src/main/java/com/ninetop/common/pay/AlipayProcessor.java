package com.ninetop.common.pay;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.ninetop.base.BaseActivity;

public class AlipayProcessor {
	private BaseActivity activity;
	private AlipayCallBack callback;

	public AlipayProcessor(BaseActivity activity) {
		this.activity = activity;
	}

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			String strRet = (String) msg.obj;
			switch (msg.what) {
			case 1:
				try {
					// 获取交易状态码，具体状态代码请参看文档
					String tradeStatus = "resultStatus={";
					int imemoStart = strRet.indexOf("resultStatus=");
					imemoStart += tradeStatus.length();
					int imemoEnd = strRet.indexOf("};memo=");
					tradeStatus = strRet.substring(imemoStart, imemoEnd);
					// 支付成功,处理结果
					if (tradeStatus.equals("9000")) {
						callback.paySuccess();
						// activity.finish();
					} else {
						activity.showToast("支付失败!");
						callback.payFailure();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case -2:
				activity.showToast("您尚未安装支付宝！！");
				break;
			case -1:
				activity.showToast("交易状态获取失败！！");
			default:
				break;
			}
		};
	};

	/**
	 * com.eg.android.AlipayGphone 根据包名判断应用程序是否已经存在
	 * 
	 * @param context
	 * @param packageName
	 * @return
	 */
	public boolean checkApkExist(Context context, String packageName) {
		if (packageName == null || "".equals(packageName))
			return false;
		try {
			ApplicationInfo info = context.getPackageManager()
					.getApplicationInfo(packageName,
							PackageManager.GET_UNINSTALLED_PACKAGES);
			return true;
		} catch (NameNotFoundException e) {
			return false;
		}
	}


	public void alipay(String payInfo,
			AlipayCallBack callback) {
		this.callback = callback;
		if (!checkApkExist(activity, "com.eg.android.AlipayGphone")) {
			Message msg = new Message();
			msg.what = -2;
			mHandler.sendMessage(msg);
		} else {
			alipay(payInfo);
		}
	}

	public void alipay(final String payInfo) {
		Runnable payRunnable = null;
		try {
			 payRunnable = new Runnable() {

				@Override
				public void run() {
					// 构造PayTask 对象
					PayTask alipay = new PayTask(activity);
					// 调用支付接口，获取支付结果
					String result = alipay.pay(payInfo, true);

					Message msg = new Message();
					msg.what = 1;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			};

		} catch (Exception e) {
			// TODO: handle exception
			Message msg = new Message();
			msg.what = -1;
			mHandler.sendMessage(msg);
		}
		// 必须异步调用
		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}
	
}
