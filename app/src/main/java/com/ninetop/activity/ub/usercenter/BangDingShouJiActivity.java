package com.ninetop.activity.ub.usercenter;

import android.view.View;
import android.widget.EditText;

import com.ninetop.UB.ChangeMobileBean;
import com.ninetop.UB.UbUserService;
import com.ninetop.activity.user.LoginActivity;
import com.ninetop.base.BaseActivity;
import com.ninetop.common.LoginAction;
import com.ninetop.common.constant.AuthTypeEnum;
import com.ninetop.common.util.ValidateUtil;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/4/17.
 */
public class BangDingShouJiActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.et_change_code)
    EditText etChangeCode;
    @BindView(R.id.et_change_pwd)
    EditText etChangePwd;
    private String mobile;
    private String code;
    private String pwd;
    private UbUserService ubUserService;
    private AuthTypeEnum authType = AuthTypeEnum.REGISTER;

    public BangDingShouJiActivity() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_shoujibangding;
    }

    @Override
    protected void initView() {
        super.initView();
        hvHead.setTitle("换绑手机");
        ubUserService = new UbUserService(this);
    }

    @Override
    protected void initData() {
        super.initData();
        etNumber.setHint("请输入新的手机号码");
    }

    protected void confirmChange() {
        mobile = etNumber.getText().toString().trim();
        code = etChangeCode.getText().toString().trim();
        pwd = etChangePwd.getText().toString().trim();
        if ("".equals(code)&&"".equals(pwd)){
            showToast("请输入完整信息");
        }else if ("".equals(code)){
            showToast("验证码不能为空");
        }else if ("".equals(pwd)){
            showToast("请输入你的密码");
        }else {
            ubUserService.changeMobile(mobile, code, pwd, new CommonResultListener<ChangeMobileBean>(this) {
                @Override
                public void successHandle(ChangeMobileBean result) throws JSONException {
                    LoginAction.login(result.token, BangDingShouJiActivity.this);
                    showToast("绑定成功");
                    startActivity(LoginActivity.class);
                }
            });
        }
    }

    public String getText() {

        return etNumber.getText().toString().trim();
    }

    private void getAuthCode() {
        String mobile = etNumber.getText().toString();
        if ("".equals(getText())) {
            showToast("手机号不能为空");
        } else {
            if (mobile.length() != 11) {
                showToast("请输入11位数的手机号码");
            } else {
                boolean isMobile = ValidateUtil.isMobilePhone(mobile);
                if (!isMobile) {
                    showToast("手机号码格式不正确");
                } else {
                    ubUserService.getAuthCode(getText(), new CommonResultListener<String>(this) {
                        @Override
                        public void successHandle(String result) throws JSONException {
                            showToast("发送成功");
                        }
                    });

                }
            }
        }
    }

    @OnClick({R.id.tv_get_code, R.id.btn_bangding})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                getAuthCode();
                break;
            case R.id.btn_bangding:
                confirmChange();
                break;
        }
    }
}
