package youbao.shopping.ninetop.activity.user;

import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import youbao.shopping.ninetop.activity.ub.product.route.util.ToastUtil;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.bean.user.RegisterBean;
import youbao.shopping.ninetop.common.LoginAction;
import youbao.shopping.ninetop.common.constant.AuthTypeEnum;
import youbao.shopping.ninetop.common.constant.UrlConstant;
import youbao.shopping.ninetop.common.util.Tools;
import youbao.shopping.ninetop.common.view.ClearAbleEditText;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.common.view.MobileEditText;
import youbao.shopping.ninetop.common.view.PwdEditText;
import youbao.shopping.ninetop.common.view.listener.DataChangeListener;
import youbao.shopping.ninetop.config.AppConfig;
import youbao.shopping.ninetop.service.impl.UserService;

import org.json.JSONException;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;
import youbao.shopping.ninetop.service.listener.CommonResultListener;


/**
 *
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.met_mobile)
    MobileEditText metMobile;
    @BindView(R.id.cet_code)
    ClearAbleEditText cetCode;
    @BindView(R.id.pet_password)
    PwdEditText petPassword;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.iv_read_status)
    ImageView ivReadStatus;
    @BindView(R.id.cet_invitationCode)
    EditText cet_invitationCode;
    private boolean readSelect = true;

    protected UserService userService;

    public RegisterActivity() {
        userService = new UserService(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    protected void initView() {
        super.initView();
        petPassword.hideLine();
    }

    @Override
    protected void initData() {
        super.initData();
        hvHead.setTitle("注册");
        cetCode.setHintText("请输入验证码");
        metMobile.setDataChangeListener(new DataChangeListener() {
            @Override
            public void handle(Object data) {
                changeSubmitStatus();
            }
        });

        metMobile.setAuthType(getAuthType());
    }

    @OnClick({R.id.btn_submit, R.id.iv_read_status, R.id.tv_protocol, R.id.cet_invitationCode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:

                try {
                    submitHandle();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_protocol:
                startActivity(RegisterProtocolActivity.class);
                break;
            case R.id.iv_read_status:
                readSelect = !readSelect;
                int resId = R.mipmap.square_no_select;
                if (readSelect) {
                    resId = R.mipmap.square_select_login;
                }
                ivReadStatus.setImageResource(resId);
                changeSubmitStatus();
                break;
        }
    }

    private void changeSubmitStatus() {
        boolean enable = false;
        int color = R.color.gray;
        String valid = metMobile.validate();
        boolean isMobile = valid == null;
        if (readSelect && isMobile) {
            enable = true;
            color = R.color.white;
        }

        btnSubmit.setEnabled(enable);
        btnSubmit.setTextColor(Tools.getColorValueByResId(this, color));
    }

    private RegisterBean bean;

    public void submitHandle()throws JSONException {
        String mobile = metMobile.getText();
        String code = cetCode.getText();
        String password = petPassword.getText();
        String invitationCode = String.valueOf(cet_invitationCode.getText());

        String rs = metMobile.validate();
        if (rs != null) {
            showToast(rs);
            return;
        }
        rs = petPassword.validate();
        if (rs != null) {
            showToast(rs);
            return;
        }
        if (code.length() == 0) {
            showToast("验证码不能为空");
        }
        try {
            //  password = RSAEncryptUtil.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();//156791
        }
        try {
            userService.register(mobile, code, password,invitationCode, new CommonResultListener<String>(this) {
                @Override
                public void successHandle(String result) {
                    showToast("注册成功");
                    Log.e("注册数据=", "数据=" + result);
//                    {"msg":"","code":2000,"data":{"money":"6.0","token":"49b5d4d68f92c6fb20195639bc72b6effcada931"}}
//
                            Gson gson = new Gson();//初始化
                            bean = gson.fromJson(result, RegisterBean.class);
                            int code = bean.getCode();
                            final String token =  bean.getData().getToken();


                    if(2000 == code){
                        new MyDialog(RegisterActivity.this, MyDialog.DIALOG_ONEOPTION, "温馨提示:", "您的奖励:" + bean.getData().getMoney() + "元已到账!", new MyDialogOnClick() {

                            @Override
                            public void sureOnClick(View v) {
                                startActivity(LoginActivity.class);
                                LoginAction.login(token, RegisterActivity.this);
                                finish();
                            }

                            @Override
                            public void cancelOnClick(View v) {
                                startActivity(LoginActivity.class);
                                LoginAction.login(token, RegisterActivity.this);
                                finish();
                            }
                        }).show();
                    }else{
                        startActivity(LoginActivity.class);
                        LoginAction.login(token, RegisterActivity.this);
                        finish();
                    }


//                    startActivity(LoginActivity.class);
//                    LoginAction.login(result, RegisterActivity.this);
//                    finish();
                    //    ActivityActionHelper.goToMain(RegisterActivity.this, 4);
                }
            });}catch (JSONException e) {
            e.printStackTrace();
        }
    }


    protected AuthTypeEnum getAuthType() {
        return AuthTypeEnum.REGISTER;
    }


}
