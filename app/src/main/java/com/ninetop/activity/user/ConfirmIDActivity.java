package com.ninetop.activity.user;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.ninetop.base.BaseActivity;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.constant.AuthTypeEnum;
import com.ninetop.common.view.HeadView;
import com.ninetop.common.view.MobileEditText;
import com.ninetop.service.impl.UserCenterService;
import com.ninetop.service.listener.CommonResultListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;

/**
 * @date: 2016/11/12
 * @author: Shelton
 * @version: 1.1.3
 * @Description:验证身份界面
 */
public class ConfirmIDActivity extends BaseActivity {
    @BindView(R.id.hv_title)
    HeadView hvTitle;
    @BindView(R.id.met_mobile)
    MobileEditText metMobile;
    @BindView(R.id.et_input_verify_code)
    EditText etInputVerifyCode;
    private UserCenterService service;
    private String mobile;
    private int autoCodeType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_id;
    }
    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        hvTitle.setTitle("验证身份");
    }
    @Override
    protected void initData() {
        super.initData();
        service = new UserCenterService(this);
        etInputVerifyCode.setText("");
        Intent intent = getIntent();
        if (intent !=null){
            autoCodeType = intent.getIntExtra(IntentExtraKeyConst.AUTO_TYPE, -1);
            if (autoCodeType==6){
                metMobile.setAuthType(AuthTypeEnum.SET_PAY_PSD);
            }else if (autoCodeType==7){
                metMobile.setAuthType(AuthTypeEnum.FORGET_PAY_PSD);
            }else if (autoCodeType==8){
                metMobile.setAuthType(AuthTypeEnum.FORGET_PAY_PSD);
            }
        }
    }
    @Override
    protected void initListener() {
        super.initListener();
        etInputVerifyCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etInputVerifyCode.getText().length() == 6) {
                    mobile = metMobile.getText().trim();
                    String auto = etInputVerifyCode.getText().toString().trim();
                    if (TextUtils.isEmpty(mobile)){
                        showToast("请输入手机号码");
                        return;
                    }
                    confirmId(auto,mobile);
                }
            }
        });

    }

    private void confirmId(String auto, String mobile) {
        service.confirmID(mobile, auto, new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) {
                Intent intent=new Intent(ConfirmIDActivity.this,SettingPasswordActivity.class);
                intent.putExtra(IntentExtraKeyConst.AUTO_TYPE,autoCodeType);
                startActivity(intent);
                finish();
            }
        });
    }
}
