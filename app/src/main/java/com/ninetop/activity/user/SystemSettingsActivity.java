package com.ninetop.activity.user;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;
import com.ninetop.base.BaseActivity;
import com.ninetop.bean.ShareAppBean;
import com.ninetop.common.LoginAction;
import com.ninetop.common.util.cleanCatchUtils;
import com.ninetop.common.view.HeadView;
import com.ninetop.config.Power;
import com.ninetop.service.impl.UserCenterService;
import com.ninetop.service.listener.CommonResultListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import youbao.shopping.R;

//import com.umeng.socialize.ShareAction;
//import com.umeng.socialize.UMShareAPI;
//import com.umeng.socialize.UMShareListener;
//import com.umeng.socialize.bean.SHARE_MEDIA;
//import com.umeng.socialize.media.UMImage;

/**
 * @date: 2016/11/11
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class SystemSettingsActivity extends BaseActivity {
    private  UserCenterService userCenterService;
    @BindView(R.id.hv_title)
    HeadView hvTitle;
    @BindView(R.id.tv_buffer)
    TextView tvBuffer;
//    private UMShareAPI mShareAPI;
    private String content;
    private String url;
    private com.ninetop.common.util.cleanCatchUtils cleanCatchUtils;

    public SystemSettingsActivity(){
        userCenterService = new UserCenterService(this);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_settings;
    }
    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        hvTitle.setTitle("系统设置");
    }
    private void getAppCatch() {
        cleanCatchUtils = new cleanCatchUtils();
        cleanCatchUtils.getAppCatch(this, new com.ninetop.common.util.cleanCatchUtils.OnGetCleanSizeCallBack() {
            @Override
            public void size(String size) {
                tvBuffer.setText(size);
            }
        });
    }
    @Override
    protected void initListener() {
        super.initListener();
    }
    @Override
    protected void initData() {
        super.initData();
//        mShareAPI = UMShareAPI.get(SystemSettingsActivity.this);
        Power.jionPower(SystemSettingsActivity.this);
        //获取应用缓存
        getAppCatch();
    }

    @Override
    protected void onResume() {
        super.onResume();
        shareApp();
    }

    private void shareApp() {
        userCenterService.shareApp(new CommonResultListener<ShareAppBean>(this) {
            @Override
            public void successHandle(ShareAppBean result) {
                content=result.content;
                url=result.downloadUrl;
            }
        });
    }

//    private UMShareListener umShareListener = new UMShareListener() {
//        @Override
//        public void onResult(SHARE_MEDIA platform) {
//            if (platform.name().equals("WEIXIN_FAVORITE")) {
//                showToast("收藏成功啦");
//            } else {
//                showToast("分享成功啦");
//            }
//        }
//        @Override
//        public void onError(SHARE_MEDIA platform, Throwable t) {
//            showToast("分享失败啦");
//        }
//        @Override
//        public void onCancel(SHARE_MEDIA platform) {
//            showToast("分享取消了");
//        }
//    };
    @OnClick({R.id.rl_personal_data,
            R.id.rl_account_guard,
            R.id.rl_clear_buffer,
            R.id.rl_comment_feedback,
            //R.id.rl_about_us,
            R.id.rl_share_app,
            R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_personal_data:
                startActivity(PersonalDataActivity.class);
                break;
            case R.id.rl_account_guard:
                startActivity(AccountGuardActivity.class);
                break;
            case R.id.rl_clear_buffer:
                new MyDialog(this, MyDialog.DIALOG_TWOOPTION, "温馨提示", "您确定要清除缓存吗?", new MyDialogOnClick() {
                    @Override
                    public void sureOnClick(View v) {
                        ActivityCompat.requestPermissions(SystemSettingsActivity.this, new String[]{Manifest.permission.CLEAR_APP_CACHE}, 1);
                        clearBuffer();
                    }
                    @Override
                    public void cancelOnClick(View v) {
                    }
                }).show();
                break;
            case R.id.rl_comment_feedback:
                startActivity(CommentFeedbackActivity.class);
                break;
         /*   case R.id.rl_about_us:
                Toast.makeText(this, "关于我们", Toast.LENGTH_SHORT).show();
                break;*/
            case R.id.rl_share_app:
                //分享
                share();
                break;
            case R.id.button:
                new MyDialog(this, MyDialog.DIALOG_TWOOPTION, "温馨提示", "您确定要退出登录吗?", new MyDialogOnClick() {
                    @Override
                    public void sureOnClick(View v) {
                        logOff();
                    }

                    @Override
                    public void cancelOnClick(View v) {
                    }
                }).show();

                break;
        }
    }

    private void share() {
        if (TextUtils.isEmpty(content)){
            showToast("内容为空");
            return;
        }
        if (TextUtils.isEmpty(url)){
            showToast("分享链接为空");
            return;
        }
//        SHARE_MEDIA.SINA,
//        new ShareAction(this).setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
//                .withTitle("——")
//                .withText(content)
//                .withMedia(new UMImage(SystemSettingsActivity.this, R.drawable.umeng_socialize_qq))
//                .withTargetUrl("https://www.pgyer.com/MasL")
//                .setCallback(umShareListener)
//                .open();
    }
    private void clearBuffer() {
        cleanCatchUtils.cleanCatch(this, new com.ninetop.common.util.cleanCatchUtils.ClearCache() {
            @Override
            public void cleanFinish() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvBuffer.setText("0.0B");
                        Toast.makeText(SystemSettingsActivity.this, "清空成功", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    private void logOff() {
        LoginAction.logout(this);
        startActivity(LoginActivity.class);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

}
