package com.ninetop.config;


import android.Manifest;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;

public class Power {
    static boolean isHasPermission = false;
    public static String[] mPermissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.INTERNET,
//            Manifest.permission.ACCESS_FINE_LOCATION,
//            Manifest.permission.CALL_PHONE,
//            Manifest.permission.READ_LOGS,
//            Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            Manifest.permission.SET_DEBUG_APP,
//            Manifest.permission.SYSTEM_ALERT_WINDOW,
//            Manifest.permission.GET_ACCOUNTS,
//            Manifest.permission.WRITE_APN_SETTINGS,
//            Manifest.permission.CLEAR_APP_CACHE,
            Manifest.permission.CAMERA
    };

    public static void jionPower(Activity ac) {
        isHasPermission = true;
        ActivityCompat.requestPermissions(ac, mPermissionList, 123);
    }
}
