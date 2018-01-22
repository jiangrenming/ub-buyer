package com.ninetop.activity.user;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ninetop.base.BaseActivity;
import com.ninetop.bean.user.UserInfo;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.impl.UserCenterService;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;




/**
 * @date: 2016/11/11
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class AccountGuardActivity extends BaseActivity {
    @BindView(R.id.hv_title)
    HeadView hvTitle;
    @BindView(R.id.tv_set_pwd_word)
    TextView tvSetPwdWord;
    @BindView(R.id.rl_forget_pwd)
    RelativeLayout rlForgetPwd;
    private static int AUTO_CODE_TYPE=-1;
    private boolean isSettingPwd=false;
    private UserCenterService userCenterService;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_guard;
    }

    @Override
    protected void initView() {
        super.initView();
        hvTitle.setTitle("账号安全");
    }

    @Override
    protected void onResume() {
        super.onResume();
        userCenterService = new UserCenterService(this);
        userCenterService.getUserInfo(new CommonResultListener<UserInfo>(this) {
            @Override
            public void successHandle(UserInfo result) throws JSONException {
                initConfig(result);
            }
        });
    }

    private void initConfig(UserInfo result) {
        isSettingPwd=result.havePayPwd;
        isSetPwdSuccess();
    }

    private void isSetPwdSuccess() {
        if (isSettingPwd) {
            tvSetPwdWord.setText("修改支付密码");
            rlForgetPwd.setVisibility(View.VISIBLE);
            AUTO_CODE_TYPE=7;
        }else {
            tvSetPwdWord.setText("设置支付密码");
            AUTO_CODE_TYPE=6;
            rlForgetPwd.setVisibility(View.GONE);
        }
    }
    @OnClick({R.id.rl_set_pwd, R.id.rl_modify_login_pwd, R.id.rl_forget_pwd})
            public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_set_pwd:
                if (isSettingPwd){
                    AUTO_CODE_TYPE=7;
                }else {
                    AUTO_CODE_TYPE=6;
                }
                Intent intent=new Intent(this,ConfirmIDActivity.class);
                intent.putExtra(IntentExtraKeyConst.AUTO_TYPE,AUTO_CODE_TYPE);
                startActivity(intent);
                break;
            case R.id.rl_modify_login_pwd:
                startActivity(ModifyLoginPwdActivity.class);
                break;
            case R.id.rl_forget_pwd:
                AUTO_CODE_TYPE=8;
                Intent intent2=new Intent(this,ConfirmIDActivity.class);
                intent2.putExtra(IntentExtraKeyConst.AUTO_TYPE,AUTO_CODE_TYPE);
                startActivity(intent2);
                break;

        }
    }
}
