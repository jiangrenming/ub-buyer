package com.ninetop.activity.ub.usercenter.myWallet;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.ninetop.UB.UbUserCenterService;
import com.ninetop.UB.UbUserInfo;
import com.ninetop.activity.ub.usercenter.ReChargeActivity;
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
public class MyBWalletActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.tv_count_number)
    TextView tvCountNumber;
    private String uBalance;
    private UbUserCenterService ubUserCenterService;
    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_balancezx;
    }

    /**
     * 初始化视图
     */
    @Override
    protected void initView() {
        super.initView();
        hvHead.setTitle("余额中心");

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
                uBalance=result.balance + "";
                tvCountNumber.setText(uBalance);
            }
        });
    }
    /**
     * 等待提示框
     */
    @Override
    public void addLoading() {
        super.addLoading();
    }

    @OnClick({R.id.rl_check_good, R.id.rl_check_change})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_check_good:
                startActivity(ReChargeActivity.class);
                break;
            case R.id.rl_check_change:
                Intent intent=new Intent(MyBWalletActivity.this,MyBInfoWalletActivity.class);
                intent.putExtra(IntentExtraKeyConst.BALANCE_MONEY,uBalance);
                startActivity(intent);
                break;
        }
    }
}
