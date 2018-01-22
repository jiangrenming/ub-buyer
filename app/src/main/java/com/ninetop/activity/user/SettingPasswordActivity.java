package com.ninetop.activity.user;

import android.content.Intent;

import youbao.shopping.R;
import com.ninetop.base.BaseActivity;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.constant.SharedKeyConstant;
import com.ninetop.common.util.MySharedPreference;
import com.ninetop.common.view.HeadView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @date: 2016/11/14
 * @author: Shelton
 * @version: 1.1.3
 * @Description:系统设置--账号安全--设置支付密码--验证身份--设置密码
 */
public class SettingPasswordActivity extends BaseActivity {
    @BindView(R.id.hv_title)
    HeadView hvTitle;
    @BindView(R.id.pswView)
    com.hykj.myviewlib.gridview.GridPasswordView pswView;
    private int intExtra;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting_password;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent!=null){
            intExtra = intent.getIntExtra(IntentExtraKeyConst.AUTO_TYPE, -1);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        hvTitle.setTitle("设置密码");
    }
    @Override
    protected void initListener() {
        super.initListener();
        pswView.setOnPasswordChangedListener(new com.hykj.myviewlib.gridview.GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }
            @Override
            public void onInputFinish(String psw) {
                String passWord = pswView.getPassWord();
                Intent intent=new Intent(SettingPasswordActivity.this,ConfirmPwdActivity.class);
                intent.putExtra(IntentExtraKeyConst.AUTO_TYPE,intExtra);
                intent.putExtra(IntentExtraKeyConst.PWD,passWord);
                startActivity(intent);
                finish();
            }
        });
    }
}
