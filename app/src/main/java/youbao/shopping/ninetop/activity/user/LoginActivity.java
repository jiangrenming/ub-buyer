package youbao.shopping.ninetop.activity.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.common.ActivityActionHelper;
import youbao.shopping.ninetop.common.LoginAction;
import youbao.shopping.ninetop.common.util.ValidateUtil;
import youbao.shopping.ninetop.common.view.PwdEditText;
import youbao.shopping.ninetop.config.Power;
import youbao.shopping.ninetop.service.impl.UserService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;

import java.util.Map;

import butterknife.BindView;
import youbao.shopping.R;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.et_number)
    EditText et_number;
    @BindView(R.id.pet_password)
    PwdEditText et_pwd;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.tv_fast_register)
    TextView tv_fast_register;
    @BindView(R.id.tv_forget_pwd)
    TextView tv_forget_pwd;
    @BindView(R.id.iv_login_we_chat)
    ImageView iv_login_we_chat;
    @BindView(R.id.iv_login_qq)
    ImageView iv_login_qq;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;
    private String number;
    private String password;
    private UserService userService;

    private UMShareAPI mShareAPI = null;
    private ProgressDialog dialog;
    private boolean isHasPermission = true;

    public LoginActivity() {

    }

    private UMAuthListener umAuthListener = new UMAuthListener() {

        @Override
        public void onStart(SHARE_MEDIA share_media) {
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            //登陆成功
            Toast.makeText(LoginActivity.this, "成功了", Toast.LENGTH_LONG).show();
            ActivityActionHelper.goToMain(LoginActivity.this);
            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
            //登陆失败
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {
            //登陆失败
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
            SocializeUtils.safeCloseDialog(dialog);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        number = et_number.getText().toString().trim();
        password = et_pwd.getText().trim();
        userService = new UserService(this);
        //加权限
        if (!isHasPermission) {
            Power.jionPower(this);
        }
        //初始化友盟
        mShareAPI = UMShareAPI.get(this);
        dialog = new ProgressDialog(this);
    }

    //填充数据
    protected void initData() {
    }

    //初始化监听
    protected void initListener() {
        iv_close.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_fast_register.setOnClickListener(this);
        tv_forget_pwd.setOnClickListener(this);
        iv_login_we_chat.setOnClickListener(this);
        iv_login_qq.setOnClickListener(this);
        addLayoutListener(activityMain, btn_login);
    }

    private void addLayoutListener(final LinearLayout activityMain, final Button btn_login) {
        activityMain.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //1、获取main在窗体的可视区域
                activityMain.getWindowVisibleDisplayFrame(rect);
                //2、获取main在窗体的不可视区域高度，在键盘没有弹起时，main.getRootView().getHeight()调节度应该和rect.bottom高度一样
                int mainInvisibleHeight = activityMain.getRootView().getHeight() - rect.bottom;
                int screenHeight = activityMain.getRootView().getHeight();//屏幕高度
                //3、不可见区域大于屏幕本身高度的1/4：说明键盘弹起了
                if (mainInvisibleHeight > screenHeight / 4) {
                    int[] location = new int[2];
                    btn_login.getLocationInWindow(location);
                    // 4､获取Scroll的窗体坐标，算出main需要滚动的高度
                    int srollHeight = (location[1] + btn_login.getHeight()) - rect.bottom;
                    //5､让界面整体上移键盘的高度
                    activityMain.scrollTo(0, srollHeight);
                } else {
                    //3、不可见区域小于屏幕高度1/4时,说明键盘隐藏了，把界面下移，移回到原有高度
                    activityMain.scrollTo(0, 0);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                submit();
                break;
            case R.id.iv_close:
                finish();
                ActivityActionHelper.goToMain(this);
                break;
            case R.id.tv_forget_pwd:
                startActivity(ForgetPasswordActivity.class);
                break;
            case R.id.tv_fast_register:
                startActivity(RegisterActivity.class);
                break;
            case R.id.iv_login_we_chat:
                SHARE_MEDIA mPlatform = SHARE_MEDIA.WEIXIN;
                mShareAPI.doOauthVerify(this, mPlatform, umAuthListener);
                break;
            case R.id.iv_login_qq:
                mPlatform = SHARE_MEDIA.QQ;
                mShareAPI.doOauthVerify(this, mPlatform, umAuthListener);
                break;
//            case R.id.iv_login_sina:
//                mShareAPI.doOauthVerify(this, SHARE_MEDIA.SINA, umAuthListener);
//                break;


        }
    }

    private void submit() {
        number = et_number.getText().toString().trim();
        password = et_pwd.getText().trim();
        String message = null;
        if (number.length() == 0 || password.length() == 0) {
            message = "用户名和密码不能为空";
            btn_login.setBackgroundResource(R.drawable.bg_border_gray_radius_select2);

        } else if (password.length() < 6) {
            message = "密码格式不正确";
        } else {
            boolean isMobile = ValidateUtil.isMobilePhone(number);
            if (!isMobile) {
                message = "手机号码格式不正确";
            } else {
                userService.login(number, password, new CommonResultListener<String>(this) {
                    @Override
                    public void successHandle(String result) {
                        LoginAction.login(result, LoginActivity.this);
                        finish();
                    }
                });
            }
        }
        if (message != null) {
            showToast(message);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            ActivityActionHelper.goToMain(this);
            return false;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }
}
