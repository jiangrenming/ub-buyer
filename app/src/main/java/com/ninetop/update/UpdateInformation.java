package com.ninetop.update;

import com.ninetop.base.MyApplication;

import youbao.shopping.R;

/**
 * Created by ${} on 2017/9/14.
 */

public class UpdateInformation {
    public static String appName = MyApplication.getInstance()
            .getResources().getString(R.string.app_name);
    public static int localVersion = 1;// 本地版本
    public static String versionName = ""; // 本地版本名
    public static int serverVersion = 1;// 服务器版本
    public static int serverFlag = 0;// 服务器标志
    public static int lastForce = 0;// 之前强制升级版本
    public static String updateurl = "";// 升级包获取地址
    public static String upgradeinfo = "";// 升级信息
    public static String downloadDir = "JiaYouBao";// 下载目录
}
