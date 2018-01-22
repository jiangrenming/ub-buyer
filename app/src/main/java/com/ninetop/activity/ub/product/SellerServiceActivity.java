package com.ninetop.activity.ub.product;

import android.os.Bundle;
import android.webkit.WebView;

import com.ninetop.UB.product.UbProductService;
import com.ninetop.base.BaseActivity;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import youbao.shopping.R;

public class SellerServiceActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView mSellerServiceHead;
    @BindView(R.id.web_service)
    WebView mWebService;
    UbProductService mProductService;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_seller_service;
    }

    @Override
    protected void initView() {
        super.initView();
        mSellerServiceHead.setTitle("售后申请");
        mProductService = new UbProductService(this);
//        WebSettings mSettings = mWebService.getSettings();
//        mSettings.setUseWideViewPort(true);
//        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        mSettings.setUseWideViewPort(true);//关键点
//        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        mSettings.setDisplayZoomControls(false);
//        mSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
//        mSettings.setAllowFileAccess(true); // 允许访问文件
//        mSettings.setBuiltInZoomControls(false); // 设置显示缩放按钮
//        mSettings.setSupportZoom(true); // 支持缩放
//        mSettings.setLoadWithOverviewMode(true);
//        DisplayMetrics metrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int mDensity = metrics.densityDpi;
//        Log.d("maomao", "densityDpi = " + mDensity);
//        if (mDensity == 240) {
//            mSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        } else if (mDensity == 160) {
//            mSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//        } else if (mDensity == 120) {
//            mSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
//        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
//            mSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
//            mSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
//        } else {
//            mSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
//        }
//        //去掉默认滚动条
//        mWebService.setHorizontalScrollBarEnabled(false);//水平不显示
//        mWebService.setVerticalScrollBarEnabled(false); //垂直不显示
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = this.getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        int franchiseeId = bundle.getInt(IntentExtraKeyConst.SERVICE_FRANCHISEE_ID);
        mProductService.sellerService(franchiseeId, new CommonResultListener<JSONObject>(this) {
            @Override
            public void successHandle(JSONObject result) throws JSONException {
                String introduction = result.getString("introduction");
                if (introduction == null) {
                    return;
                }
                mWebService.loadDataWithBaseURL(null, introduction, "text/html", "utf-8", null);
            }
        });
    }
}
