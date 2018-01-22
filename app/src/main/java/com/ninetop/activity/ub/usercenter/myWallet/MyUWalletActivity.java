package com.ninetop.activity.ub.usercenter.myWallet;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.ninetop.UB.UbUserCenterService;
import com.ninetop.UB.UbUserInfo;
import com.ninetop.activity.ub.usercenter.GoodChangeActivity;
import com.ninetop.base.BaseActivity;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/5/19.
 */
public class MyUWalletActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.tv_count_number)
    TextView tvCountNumber;
    private UbUserCenterService ubUserCenterService;
    private String uMoney;

    public MyUWalletActivity(String uMoney) {
        this.uMoney = uMoney;
    }

    public MyUWalletActivity() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_ubizx;
    }

    /**
     * 初始化视图
     */
    @Override
    protected void initView() {
        super.initView();
        hvHead.setTitle("积分中心");
    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        super.initData();
        ubUserCenterService = new UbUserCenterService(this);
        ubUserCenterService.getUserCenter(new CommonResultListener<UbUserInfo>(this) {
            @Override
            public void successHandle(UbUserInfo result) throws JSONException {
                String mUbAccount = result.ub_point + "";
                String mUbNum = mUbAccount.substring(0,mUbAccount.indexOf("."));
                tvCountNumber.setText(mUbNum);
            }
        });
    }


    @OnClick({R.id.rl_check_good, R.id.rl_check_change})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_check_good:
                startActivity(GoodChangeActivity.class);
                break;
            case R.id.rl_check_change:
                Intent intent=new Intent(MyUWalletActivity.this,MyUInfoWalletActivity.class);
                intent.putExtra(IntentExtraKeyConst.U_MONEY,uMoney);
                startActivity(intent);
                break;
        }
    }
}
