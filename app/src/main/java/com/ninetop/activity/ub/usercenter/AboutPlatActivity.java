package com.ninetop.activity.ub.usercenter;

import android.annotation.SuppressLint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ninetop.UB.AboutPlatformBean;
import com.ninetop.UB.UbSellerService;
import com.ninetop.base.BaseActivity;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import butterknife.BindView;
import youbao.shopping.R;


/**
 * Created by huangjinding on 2017/4/17.
 */
public class AboutPlatActivity extends BaseActivity {

    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.wv_detail)
    WebView wvDetail;
    public final static String CSS_STYLE ="<style>* {font-size:25px;}p {color:#000000;}</style>";
    private UbSellerService ubSellerService;

    public AboutPlatActivity() {
        ubSellerService = new UbSellerService(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_about_platform;
    }


    @SuppressLint("SetJavaScriptEnabled")
    protected void initView() {
        super.initView();
        hvHead.setTitle("关于平台");
        WebSettings mSettings = wvDetail.getSettings();
        mSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        mSettings.setUseWideViewPort(true);//关键点
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mSettings.setDisplayZoomControls(false);
        mSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
        mSettings.setAllowFileAccess(true); // 允许访问文件
        mSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
        mSettings.setSupportZoom(true); // 支持缩放
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setTextSize(WebSettings.TextSize.LARGER);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        Log.d("maomao", "densityDpi = " + mDensity);
        if (mDensity == 240) {
            mSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == 160) {
            mSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        } else if (mDensity == 120) {
            mSettings.setDefaultZoom(WebSettings.ZoomDensity.CLOSE);
        } else if (mDensity == DisplayMetrics.DENSITY_XHIGH) {
            mSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else if (mDensity == DisplayMetrics.DENSITY_TV) {
            mSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
        } else {
            mSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        }
        //去掉默认滚动条
        wvDetail.setHorizontalScrollBarEnabled(false);//水平不显示
        wvDetail.setVerticalScrollBarEnabled(false); //垂直不显示
    }

    protected void initData() {
        super.initData();
        getSeverData();
    }

    private void getSeverData() {
        ubSellerService.aboutPlatform(new CommonResultListener<AboutPlatformBean>(this) {
            @SuppressLint("SetJavaScriptEnabled")
            @Override
            public void successHandle(AboutPlatformBean result) throws JSONException {
//                initWebView(result.introduction);
                String introduction = result.introduction;
                if (introduction == null) {
                    return;
                }
                Log.i("获取webview的Html=",introduction);
                wvDetail.loadDataWithBaseURL(null, CSS_STYLE+introduction, "text/html", "utf-8", null);
            }
        });
    }

//    private void initWebView(String html) {
//        if (html == null && html.length() == 0) {
//            return;
//        }
//        String style = "";
////        if (!isHtmlContentHasStyle(html)) {
////            style = TextConstant.HTTP_STYLE;
////        }
//        String url = TextConstant.HTTP_BODY_START + html + style + TextConstant.HTTP_BODY_END;
//        wvDetail.setScrollbarFadingEnabled(true);
//        wvDetail.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        wvDetail.loadData(html, "text/html;charset=UTF-8", null);
//    }
//
//    private boolean isHtmlContentHasStyle(String content) {
//        return content.indexOf(" style=\"") != -1;
//    }

}
