package com.ninetop.common.version;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;


import youbao.shopping.R;
import com.ninetop.common.util.ToastUtils;

import java.lang.reflect.Field;


public class VersionUpdateDialog {

    private Activity activity;
    private String updateMessage;
    private String downUrl;
    private boolean isForce;
    
    private boolean isDowning = false;
    
    public VersionUpdateDialog(Activity activity, String updateMessage, String downUrl, boolean isForce){
        this.activity = activity;
        this.updateMessage = updateMessage;
        this.downUrl = downUrl;
        this.isForce = isForce;
    }

    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.newUpdateAvailable);
        builder.setMessage(updateMessage)
                .setPositiveButton(R.string.dialogPositiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    	if(isDowning){
                    		ToastUtils.showLongCenter(activity, "正在下载中，请稍后");
                    	}else{
	                    	isDowning = true;
	                    	ToastUtils.showLongCenter(activity, "正在后台进行下载，稍后会自动安装");
	                        goToDownload();
                    	}
                        if(isForce){
                        	notCloseDialog(dialog);
                        }
                    }
                });
        if(isForce){
        	builder.setCancelable(false);
        }
        if(!isForce){
                builder.setNegativeButton(R.string.dialogNegativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        }
        builder.show();
    }

    private void goToDownload() {
    	Intent intent=new Intent(activity.getApplicationContext(),DownloadService.class);
    	intent.putExtra(Constants.APK_DOWNLOAD_URL, downUrl);
    	activity.startService(intent);
    }
    
    private void notCloseDialog(DialogInterface dialogInterface) {  
        try {  
            Field field = dialogInterface.getClass().getSuperclass().getDeclaredField("mShowing");  
            field.setAccessible(true);  
            field.set(dialogInterface, false);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}
