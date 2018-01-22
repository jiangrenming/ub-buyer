package com.ninetop.service.listener;

import com.ninetop.base.Viewable;

import okhttp3.Response;

/**
 * Created by jill on 2016/11/22.
 */

public abstract class BaseResponseListener implements ResponseListener {

    protected Viewable context;
    public ResultListener resultListener;

    public BaseResponseListener(Viewable context,ResultListener resultListener){
        this.context = context;
        this.resultListener = resultListener;
    }

    @Override
    public void fail(String errCode, String msg) {
        resultListener.failHandle(errCode, msg);
    }

    @Override
    public void error(Response response, Exception e) {
        resultListener.errorHandle(response, e);
    }

    public boolean showToast(){
        if(resultListener instanceof ShowableResultListener){
            return ((ShowableResultListener)resultListener).isShowToast();
        }

        return true;
    }

    public boolean showLoading(){
        if(resultListener instanceof ShowableResultListener){
            return ((ShowableResultListener)resultListener).isShowLoading();
        }
        return true;
    }

    public boolean readCache(){
        return resultListener instanceof CacheCommonResultListener;
    }
}
