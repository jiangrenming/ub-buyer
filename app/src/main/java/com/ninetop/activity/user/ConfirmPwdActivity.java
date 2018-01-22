package com.ninetop.activity.user;

import android.content.Intent;
import android.widget.Toast;

import com.ninetop.base.BaseActivity;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.util.RSAEncryptUtil;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.impl.UserCenterService;
import com.ninetop.service.listener.CommonResultListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * @date: 2016/11/15
 * @author: Shelton
 * @version: 1.1.3
 * @Description:系统设置--账户安全--设置支付密码--验证身份--设置密码--确认密码
 */
public class ConfirmPwdActivity extends BaseActivity {
    private final UserCenterService userCenterService;
    @BindView(R.id.hv_title)
    HeadView hvTitle;
    @BindView(R.id.pswView)
    com.hykj.myviewlib.gridview.GridPasswordView pswView;
    private int intExtra;
    private String settingPwd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_pwd;
    }
    public ConfirmPwdActivity(){
        userCenterService = new UserCenterService(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent!=null){
            intExtra = intent.getIntExtra(IntentExtraKeyConst.AUTO_TYPE,-1);
            settingPwd = intent.getStringExtra(IntentExtraKeyConst.PWD);
        }
    }
    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        hvTitle.setTitle("确认密码");
    }
    @Override
    protected void initListener() {
        super.initListener();
    }

    @OnClick(R.id.button)
    public void onClick() {
        String pwd = pswView.getPassWord();
        if ( pwd.length()< 6) {
            Toast.makeText(this, "请输入6位密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (settingPwd.equals(pwd)) {
           if (intExtra==6||intExtra==7||intExtra==8){
                //设置支付密码
                setPwd(pwd);
            }
        } else {
            Toast.makeText(this, "两次输入密码不一致,请重新输入", Toast.LENGTH_SHORT).show();
        }
    }

    private void setPwd(String password) {
        String encrypt = "";
        try {
            encrypt= RSAEncryptUtil.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        userCenterService.settingPayPwd(encrypt, new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) {
                showToast("设置支付密码成功");
                finish();
            }
        });
    }
}
