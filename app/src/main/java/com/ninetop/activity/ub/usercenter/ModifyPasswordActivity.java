package com.ninetop.activity.ub.usercenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ninetop.UB.UbUserService;
import com.ninetop.activity.user.LoginActivity;
import com.ninetop.base.BaseActivity;
import com.ninetop.base.GlobalInfo;
import com.ninetop.common.util.ValidateUtil;
import com.ninetop.common.view.HeadView;
import com.ninetop.common.view.PwdEditText;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import youbao.shopping.R;

import static youbao.shopping.R.id.tv_get_code;

/**
 * Created by huangjinding on 2017/4/17.
 */
public class ModifyPasswordActivity extends BaseActivity {

    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.et_code)
    EditText tvGetCode;
    @BindView(tv_get_code)
    TextView etCode;

    @BindView(R.id.pet_new_password)
    PwdEditText petNewPassword;
    @BindView(R.id.pet_old_password)
    PwdEditText petOldPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private UbUserService ubUserService;

    public ModifyPasswordActivity() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_shuruxingmima;
    }


    protected void initView() {
        super.initView();
        ubUserService = new UbUserService(this);

        hvHead.setTitle("修改密码");
        petNewPassword.setHintText("请输入新密码");
        petOldPassword.setHintText("请再输入一次");

    }

    private void inputChangeHandle() {
        int textColor = R.color.text_gray;
        textColor = R.color.text_red;
        boolean enable = false;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({tv_get_code, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case tv_get_code:
                String mMobile = etMobile.getText().toString();
                if ("".equals(mMobile)) {
                    showToast("手机号不能为空");
                } else {
                    if (mMobile.length() != 11) {
                        showToast("请输入11位数的和手机号码");
                    } else {
                        boolean isMobile = ValidateUtil.isMobilePhone(mMobile);
                        if (!isMobile) {
                            showToast("手机号码格式不正确");
                        } else {
                            ubUserService.getPwdCode(GlobalInfo.saveMobile, new CommonResultListener<String>(this) {
                                @Override
                                public void successHandle(String result) throws JSONException {
                                    showToast("验证码已发送");
                                }
                            });
                        }
                    }
                }
                break;
            case R.id.btn_login:
                String mobile = etMobile.getText().toString();
                String code = etCode.getText().toString().trim();
                String passwordNew = petNewPassword.getText();
                String passwordOld = petOldPassword.getText();
                if ("".equals(mobile) && "".equals(code) && "".equals(passwordNew) && "".equals(passwordOld)) {
                    showToast("请输入完整信息");
                } else if ("".equals(mobile)) {
                    showToast("请输入手机号");
                } else if ("".equals(code)) {
                    showToast("请输入验证码");
                } else if ("".equals(passwordNew)) {
                    showToast("请输入新的密码");
                } else if ("".equals(passwordOld)) {
                    showToast("请再输入一次");
                } else {
                    ubUserService.modifyPassword(mobile, code, passwordNew, passwordOld, new CommonResultListener<String>(this) {
                        @Override
                        public void successHandle(String result) throws JSONException {
                            showToast("修改成功");
                            startActivity(LoginActivity.class);
                        }
                    });
                }
                break;
        }
    }
}
