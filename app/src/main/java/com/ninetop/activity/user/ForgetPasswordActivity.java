package com.ninetop.activity.user;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.ninetop.UB.UbUserService;
import com.ninetop.base.BaseActivity;
import com.ninetop.common.LoginAction;
import com.ninetop.common.util.ValidateUtil;
import com.ninetop.common.view.HeadView;
import com.ninetop.common.view.MobileEditText3;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

import static youbao.shopping.R.id.met_mobile;

/**
 * @date: 2016/11/8
 * @author: jill
 * @Description:
 */
public class ForgetPasswordActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(met_mobile)
    MobileEditText3 metMobile;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_pwd_again)
    EditText etPwdAgain;
    private String mobile;
    private String code;
    private String pwdFirst;
    private String pwdLast;
    private UbUserService ubUserService;
    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_forget_pwd;
    }

   @Override
   protected void initView(){
       super.initView();
       hvHead.setTitle("忘记密码");
       ubUserService=new UbUserService(this);
       etPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
       etPwdAgain.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
   }


    @OnClick({ R.id.btn_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_finish:
                finishChange();
                break;
        }
    }
//    private void getAuthCode(){
//        if(getText()==null){
//            showToast("手机号不能为空");
//            return;
//        }
//        ubUserService.getPwdCode(getText(), new CommonResultListener<String>(this) {
//            @Override
//            public void successHandle(String result) throws JSONException {
//                showToast("验证码已发送");
//            }
//        });
//    }
    private void finishChange(){

        mobile=metMobile.getText();
        code=etCode.getText().toString().trim();
        pwdFirst=etPwd.getText().toString().trim();
        pwdLast=etPwdAgain.getText().toString().trim();
        String message = null;
        if(mobile.length() == 0 || pwdLast.length() == 0){
            message = "用户名和密码不能为空";
        }else if(pwdLast.length() < 6){
            message = "密码格式不正确";
        }else{
            boolean isMobile = ValidateUtil.isMobilePhone(mobile);
            if(!isMobile){
                message = "手机号码格式不正确";
            }else{
                ubUserService.forgetPassword(mobile, code, pwdFirst, pwdLast, new CommonResultListener<String>(this) {
                    @Override
                    public void successHandle(String result) throws JSONException {
                        LoginAction.login(result,ForgetPasswordActivity.this);
                       // ActivityActionHelper.goToMain(ForgetPasswordActivity.this);
                        showToast("修改成功");
//                        startActivity(LoginActivity.class);
                        finish();
                    }
                });
            }
        }
        if(message != null){
            showToast(message);
        }
    }

}
