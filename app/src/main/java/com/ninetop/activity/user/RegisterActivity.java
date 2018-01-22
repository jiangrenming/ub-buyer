package com.ninetop.activity.user;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ninetop.base.BaseActivity;
import com.ninetop.common.LoginAction;
import com.ninetop.common.constant.AuthTypeEnum;
import com.ninetop.common.util.Tools;
import com.ninetop.common.view.ClearAbleEditText;
import com.ninetop.common.view.HeadView;
import com.ninetop.common.view.MobileEditText;
import com.ninetop.common.view.PwdEditText;
import com.ninetop.common.view.listener.DataChangeListener;
import com.ninetop.service.impl.UserService;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;


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

    private boolean readSelect = true;

    protected UserService userService;

    public RegisterActivity(){
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

    @OnClick({R.id.btn_submit, R.id.iv_read_status, R.id. tv_protocol})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:

                submitHandle();
                break;
            case R.id.tv_protocol:
                startActivity(RegisterProtocolActivity.class);
                break;
            case R.id.iv_read_status:
                readSelect = !readSelect;
                int resId = R.mipmap.square_no_select;
                if(readSelect){
                    resId = R.mipmap.square_select_login;
                }
                ivReadStatus.setImageResource(resId);
                changeSubmitStatus();
                break;
        }
    }

    private void changeSubmitStatus(){
        boolean enable = false;
        int color = R.color.gray;
        String valid  = metMobile.validate();
        boolean isMobile = valid == null;
        if(readSelect && isMobile){
            enable = true;
            color = R.color.white;
        }

        btnSubmit.setEnabled(enable);
        btnSubmit.setTextColor(Tools.getColorValueByResId(this, color));
    }

    public void submitHandle(){
        String mobile = metMobile.getText();
        String code = cetCode.getText();
        String password = petPassword.getText();

        String rs = metMobile.validate();
        if(rs != null){
            showToast(rs);
            return;
        }
        rs = petPassword.validate();
        if(rs != null){
            showToast(rs);
            return;
        }
        if(code.length() == 0) {
            showToast("验证码不能为空");
        }
        try {
          //  password = RSAEncryptUtil.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try{
            userService.register(mobile, code, password, new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) {
             showToast("注册成功");
//               startActivity(LoginActivity.class);
                LoginAction.login(result, RegisterActivity.this);
                finish();
            //    ActivityActionHelper.goToMain(RegisterActivity.this, 4);
            }
        });}catch (JSONException e){
            e.printStackTrace();
        }
    }

    protected AuthTypeEnum getAuthType(){
        return AuthTypeEnum.REGISTER;
    }
}
