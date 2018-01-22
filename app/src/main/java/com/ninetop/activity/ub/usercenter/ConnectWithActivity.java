package com.ninetop.activity.ub.usercenter;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;
import com.ninetop.UB.IntroductionBean;
import com.ninetop.UB.UbSellerService;
import com.ninetop.base.BaseActivity;
import com.ninetop.common.constant.TextConstant;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/4/15.
 */
public class ConnectWithActivity extends BaseActivity {

    @BindView(R.id.hv_head)
    HeadView hvHead;

    @BindView(R.id.wv_detail)
    WebView wvDetail;
    @BindView(R.id.tv_shoper_category)
    TextView tvShoperCategory;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.tv_lijilianxi)
    TextView tvLijilianxi;

    private String mobileInfo;
    private UbSellerService ubSellerService;
    public ConnectWithActivity() {
        ubSellerService=new UbSellerService(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_woyao_hezuo;
    }

    protected void initView() {
        super.initView();
        hvHead.setTitle("我要合作");
    }
    protected void initData(){
        super.initView();
        ubSellerService.joinPlatform(new CommonResultListener<IntroductionBean>(this) {
            @Override
            public void successHandle(IntroductionBean result) throws JSONException {
                       handleData(result);

            }
        });
    }
    private void handleData(IntroductionBean result){
        initWebView(result.introduction);
        tvShoperCategory.setText(result.mobile_owner);
        mobileInfo=result.mobile;
        tvMobile.setText(mobileInfo);
    }
    private void initWebView(String html) {
        if (html == null && html.length() == 0) {
            return;
        }
        String style = "";
        if (!isHtmlContentHasStyle(html)) {
            style = TextConstant.HTTP_STYLE;
        }
        String url = TextConstant.HTTP_BODY_START + html + style + TextConstant.HTTP_BODY_END;
        wvDetail.setScrollbarFadingEnabled(true);
        wvDetail.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wvDetail.loadData(url, "text/html;charset=UTF-8", null);
    }

    private boolean isHtmlContentHasStyle(String content) {
        return content.indexOf(" style=\"") != -1;
    }

    @OnClick(R.id.tv_lijilianxi)
    public void onViewClicked() {
        call();
    }
    private void call(){
        new MyDialog(this, MyDialog.DIALOG_TWOOPTION, "温馨提示", "确定拨打" + mobileInfo + "吗？", new MyDialogOnClick() {
            @Override
            public void sureOnClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+mobileInfo));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            @Override
            public void cancelOnClick(View v) {
            }
        }).show();
    }
}