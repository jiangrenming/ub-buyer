package com.ninetop.common.version;

import android.app.Activity;

import org.json.JSONObject;


public class VersionUpdateChecker {

	private static final String TAG = "UpdateChecker";
	private Activity targetActivity;
	private boolean showTip;

	public VersionUpdateChecker(Activity targetActivity, boolean showTip){
		this.targetActivity = targetActivity;
		this.showTip = showTip;
	}
	
	public VersionUpdateChecker(Activity targetActivity){
		this(targetActivity, false);
	}

	public void checkForUpdates() {
//		HttpClient httpClient = new HttpClient();
//		HttpClientParams parmas = new HttpClientParams();
//		String type = "1";
//
//		parmas.addParams("t", type);
//		if(showTip){
//			ToastUtils.showCenter(targetActivity, R.string.app_version_check_tip);
//		}
//
//		httpClient.doRequest("VersionInfo", parmas, new HttpClientResponse() {
//
//			@Override
//			public void doSuccess(String result) {
//				try {
//					JSONObject jsonObject = new JSONObject(result);
//					int status = Integer.valueOf(jsonObject.getString("status"));
//					JSONObject resultObj = jsonObject.getJSONObject("result");
//					if(status == 0 && resultObj != null){
//						successHandle(resultObj);
//					}
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//
//			@Override
//			public void doFailure(String result) {
//			}
//		});
	}
	
	private void successHandle(JSONObject obj) {
//		try {
//			String updateMessage = obj.getString(Constants.APK_UPDATE_CONTENT);
//			String apkUrl = obj.getString(Constants.APK_DOWNLOAD_URL);
//			String apkCode = obj.getString(Constants.APK_VERSION_CODE);
//			String versionName = targetActivity.getPackageManager().getPackageInfo(targetActivity.getPackageName(), 0).versionName;
//
//			if(apkCode == null || apkCode.length() == 0 || apkCode.startsWith("0")){
//				showNewestMsgIfNeed();
//				return;
//			}
//
//			if (VersionCompareUtil.compareVersion(apkCode, versionName) == 1) {
//				String isForce = obj.getString(Constants.APK_UPDATE_FORCE);
//				boolean force = "1".equals(isForce);
//				showDialog(updateMessage,apkUrl, force);
//			}else{
//				showNewestMsgIfNeed();
//			}
//
//		} catch (PackageManager.NameNotFoundException ignored) {
//		} catch (JSONException e) {
//			Log.e(TAG, "parse json error", e);
//		}
	}
	
	private void showNewestMsgIfNeed(){
//		if(showTip){
//			ToastUtils.showLongCenter(targetActivity, R.string.app_newest_version);
//		}
	}

	/**
	 * Show dialog
	 * 
	 */
	public void showDialog(String content,String apkUrl, boolean force) {
        VersionUpdateDialog d = new VersionUpdateDialog(targetActivity, content, apkUrl, force);
		d.showDialog();
	}

	public void showToast(String message) {
//		ToastUtils.showCenter(targetActivity, message);
	}

	
}
