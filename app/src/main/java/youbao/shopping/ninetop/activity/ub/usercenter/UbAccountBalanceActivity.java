package youbao.shopping.ninetop.activity.ub.usercenter;

import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.common.view.HeadView;

import butterknife.BindView;
import youbao.shopping.R;

import static youbao.shopping.R.id.hv_head;

/**
 * Created by huangjinding on 2017/4/22.
 */
public class UbAccountBalanceActivity extends BaseActivity {
    @BindView(hv_head)
    HeadView hvHead;

    public UbAccountBalanceActivity(){

    }
    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_ubizx;
    }

    protected void initView() {
        super.initView();
        hvHead.setTitle("我的余额");
    }

}