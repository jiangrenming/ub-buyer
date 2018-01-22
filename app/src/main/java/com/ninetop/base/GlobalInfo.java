package com.ninetop.base;

import com.ninetop.UB.order.UbPayBean;
import com.ninetop.common.pay.OrderPayItem;

public class GlobalInfo {

	public static String userToken = "";

	public static String saveCity = "";
	public static String saveMobile = "";

	public static String saveGetStyle = "";

	public static String saveUB = "";
	public static String savePay = "";

	public static String appVersionName = null;
	public static UbPayBean ubLastPayItem = null;

	public static Viewable viewable = null;

	public static String wechat_app_id = "";

	public static OrderPayItem lastPayItem = null;

	public static boolean needGoLogin = false;

	public static int shopCartCount = 0;

	public static String cityLocation = "";

	public static String ubSelectCity = "";

	public static String franchiseeId = "1";

	public static String unionshopSelectCity = "";

	public static boolean isAppInit = true;
}
