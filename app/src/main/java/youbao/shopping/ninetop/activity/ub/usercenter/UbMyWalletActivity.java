package youbao.shopping.ninetop.activity.ub.usercenter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import youbao.shopping.ninetop.UB.UbUserCenterService;
import youbao.shopping.ninetop.UB.UbUserInfo;
import youbao.shopping.ninetop.activity.ub.usercenter.myWallet.MyBWalletActivity;
import youbao.shopping.ninetop.activity.ub.usercenter.myWallet.MyUWalletActivity;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/4/17.
 */
public class UbMyWalletActivity extends BaseActivity {

    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.tv_u)
    TextView tvU;
    @BindView(R.id.rl_ubi)
    RelativeLayout rlUbi;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.rl_yue)
    RelativeLayout rlYue;
    private UbUserCenterService ubUserCenterService;

    public UbMyWalletActivity() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_my_wallet;
    }

    protected void initView() {
        super.initView();
        hvHead.setTitle("我的钱包");
    }

    @Override
    protected void initData() {
        super.initData();
        ubUserCenterService = new UbUserCenterService(this);
        ubUserCenterService.getUserCenter(new CommonResultListener<UbUserInfo>(this) {
            @Override
            public void successHandle(UbUserInfo result) throws JSONException {
                String mUbAccount = result.ub_point + "";
                String mUbNum = mUbAccount.substring(0,mUbAccount.indexOf("."));
                tvU.setText(mUbNum);
                tvBalance.setText(result.balance + "");
            }
        });
    }
    @OnClick({R.id.rl_yue,R.id.rl_ubi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_ubi:
                startActivity(MyUWalletActivity.class);
                break;
            case R.id.rl_yue:
                startActivity(MyBWalletActivity.class);
                break;
        }
    }
}