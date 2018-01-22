package com.ninetop.update;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ${} on 2017/9/14.
 */

public class AppInstallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
//            ToastUtils.showCenter(MyApplication.getContext(), "有应用被添加", 1000);
        } else if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
//            Toast.makeText(context, "有应用被删除", Toast.LENGTH_LONG).show();
        }   else  if(Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())){
//            Toast.makeText(context, "有应用被替换", Toast.LENGTH_LONG).show();
        }
    }
}
