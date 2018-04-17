package youbao.shopping.ninetop.activity.index;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.view.HeadView;

import butterknife.BindView;
import youbao.shopping.R;

/**
 * Created by jill on 2017/1/4.
 */

public class WebViewActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.wv_detail)
    WebView wvDetail;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initData() {
        super.initData();

        String title = getIntentValue(IntentExtraKeyConst.WEB_VIEW_TITLE);
        if(title != null){
            hvHead.setTitle(title);
        }
        String url = getIntentValue(IntentExtraKeyConst.WEB_VIEW_URL);
        if(url != null && url.length() > 0){
            wvDetail.setScrollbarFadingEnabled(true);
            wvDetail.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            WebSettings settings = wvDetail.getSettings();
            settings.setJavaScriptEnabled(true);
            wvDetail.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, url);
                }

                //设置加载时显示
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    addLoading();
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    removeLoading();
                    removeLoading();
                }
            });
            wvDetail.loadUrl(url);
        }
    }
}
