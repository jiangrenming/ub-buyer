package com.ninetop.base;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.hykj.myviewlib.loading.CircularProgressDialog;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jill on 2016/11/22.
 */

public class LoadingAction {

    public CircularProgressDialog loadingDialog;

    int totalDialog = 0;

    private Timer loadingTimer;
    private TimerTask loadingTimerTask;

    protected Handler handler = new Handler(Looper.getMainLooper());

    private Viewable context;

    public LoadingAction(Viewable context){
        this.context = context;

        loadingDialog = new CircularProgressDialog((Context) context);
    }

    public void add(){
        totalDialog++;
        if (!loadingDialog.isShowing() && !isFinishing()) {
            loadingDialog.show();
            loadingDialog.setColor("#C30D23"); // 设置颜色
        }
        loadingTimeOutHandle();
    }

    public void remove(){
        if (totalDialog <= 1) {
            if (loadingDialog.isShowing()) {
                loadingDialog.dismiss();
            }
        }
        totalDialog--;
    }

    private void loadingTimeOutHandle() {
        if(!loadingDialog.isShowing() || isFinishing()){
            return;
        }

        if(loadingTimer != null){
            loadingTimerTask.cancel();
            loadingTimer.cancel();
        }

        loadingTimer = new Timer();
        loadingTimerTask = new TimerTask() {

            @Override
            public void run() {
                if (loadingDialog.isShowing() && !isFinishing()) {
                    handler.post(new Runnable() {

                        @Override
                        public void run() {
                            loadingDialog.dismiss();
                            totalDialog = 0;
                            context.showToast("由于网络原因，数据加载缓慢，请等待");
                        }
                    });
                }
            }
        };

        loadingTimer.schedule(loadingTimerTask, 5000);
    }

    private boolean isFinishing(){
        if(context instanceof Activity){
            return ((Activity)context).isFinishing();
        }

        return false;
    }
}
