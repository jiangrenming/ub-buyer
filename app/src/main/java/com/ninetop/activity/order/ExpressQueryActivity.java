package com.ninetop.activity.order;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ninetop.base.BaseActivity;
import com.ninetop.common.constant.UrlConstant;
import com.ninetop.common.view.HeadView;

import butterknife.BindView;
import youbao.shopping.R;

public class ExpressQueryActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView mHead;
    @BindView(R.id.web_express)
    WebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_express_query;
    }

    @Override
    protected void initView() {
        super.initView();
        mHead.setTitle("快递查询");
    }

    @Override
    protected void initData() {
        super.initData();
        mWebView.loadUrl(UrlConstant.ORDER_EXPRESS);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
    }
}
