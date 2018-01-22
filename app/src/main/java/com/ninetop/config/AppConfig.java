package com.ninetop.config;

public class AppConfig {
    public static final boolean IS_TEST = false;

    public static final boolean USE_CACHE = false;

    /**
     * 基地址
     */
//    public static String BASE_URL = "http://192.168.31.102:8089/youbao/app/";//小宝
//    public static String BASE_IMAGE_URL="http://192.168.31.102:8089/youbao/";
    //小宝测试
//    public static String BASE_URL = "http://118.178.182.6:8080/youbao/app/";  //测试
//    public static String BASE_IMAGE_URL = "http://118.178.182.6:8080/youbao/";
//    public static String BASE_URL = "http://118.178.182.6/youbao/app/";
//    public static String BASE_IMAGE_URL="http://118.178.182.6/youbao/";
    public static String BASE_URL = "http://116.62.162.19/youbao/app/";
    public static String BASE_IMAGE_URL = "http://116.62.162.19/youbao/";

    public static boolean isTestPay = IS_TEST;//支付是否测试

    public static String INIT_CITY = "杭州";
    public static int FRANCHISEEID = 1;
    /**
     * 支付宝回调
     */
    public static String Alipay_Notify = "";//测试

    /**
     * 微信回调
     */
    public static String Wx_Pay_Notify = "";

    static {
        //        if (!IS_TEST) {
        //            BASE_URL = "http://192.168.31.91:8080/youbao/app/";
        //        }
    }

}
