package com.ninetop.activity.user;

import android.view.View;

import com.ninetop.common.LoginAction;
import com.ninetop.common.constant.AuthTypeEnum;
import com.ninetop.common.util.RSAEncryptUtil;
import com.ninetop.common.util.Tools;
import com.ninetop.common.view.listener.DataChangeListener;
import com.ninetop.service.impl.UserCenterService;
import com.ninetop.service.listener.CommonResultListener;

import butterknife.OnClick;
import youbao.shopping.R;


/**
 * @date: 2016/11/15
 * @author: Shelton
 * @version: 1.1.3
 * @Description:修改登录密码
 */
public class ModifyLoginPwdActivity extends RegisterActivity {

    private UserCenterService service;

    @Override
    protected void initView() {
        super.initView();
        service = new UserCenterService(this);
    }

    protected void initData() {
        super.initData();
        metMobile.setAuthType(AuthTypeEnum.UPDATE_PASSWORD);
        btnSubmit.setText("保存");
        cetCode.setMaxLength(6);
        //hvHead.setTitle("修改登录密码");
        metMobile.setDataChangeListener(new DataChangeListener() {
            @Override
            public void handle(Object data) {
                changeSubmitStatus();
            }
        });
    }

    private void changeSubmitStatus(){
        boolean enable = false;
        int color = R.color.gray;
        String valid  = metMobile.validate();
        boolean isMobile = valid == null;
        if(isMobile){
            enable = true;
            color = R.color.white;
        }

        btnSubmit.setEnabled(enable);
        btnSubmit.setTextColor(Tools.getColorValueByResId(this, color));
    }

    @OnClick({R.id.btn_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                modifyLoginPwd();
                break;
        }
    }

    private void modifyLoginPwd() {
        String mobile = metMobile.getText();
        String code = cetCode.getText();
        if(code.length() == 0) {
            showToast("验证码不能为空");
            return;
        }
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
        confirmId(code,mobile);
    }

    private void modifyPassword(String password) {
        service.fixLoginPwd(password, new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) {
                showToast("修改登录密码成功");
                startActivity(LoginActivity.class);
                LoginAction.logout(ModifyLoginPwdActivity.this);
                finish();
            }
        });
    }

    private void confirmId(String auto, String mobile) {
        service.confirmID(mobile, auto, new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result){
                String encrypt="";
                try {
                     encrypt = RSAEncryptUtil.encrypt(petPassword.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                modifyPassword(encrypt);
            }
        });
    }
}
