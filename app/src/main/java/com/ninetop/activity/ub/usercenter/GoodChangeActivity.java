package com.ninetop.activity.ub.usercenter;

import com.ninetop.base.BaseActivity;
import com.ninetop.common.view.HeadView;

import butterknife.BindView;
import youbao.shopping.R;

import static youbao.shopping.R.id.hv_head;

/**
 * Created by huangjinding on 2017/4/17.
 */
public class GoodChangeActivity extends BaseActivity {
    @BindView(hv_head)
    HeadView hvHead;

    public GoodChangeActivity(){

    }
    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_fuliduihuan;
    }

    protected void initView() {
        super.initView();
        hvHead.setTitle("福利兑换");
    }

}