package com.ninetop.activity.user;

import youbao.shopping.R;
import com.ninetop.base.BaseActivity;
import com.ninetop.common.view.HeadView;

import butterknife.BindView;

import static youbao.shopping.R.id.hv_head;

/**
 * @date: 2016/11/8
 * @author: jill
 */
public class RegisterProtocolActivity extends BaseActivity {

    @BindView(hv_head)
    HeadView hvHead;

    public RegisterProtocolActivity(){

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_protocol;
    }

    protected void initView() {
        super.initView();

        hvHead.setTitle("服务协议");
    }



}
