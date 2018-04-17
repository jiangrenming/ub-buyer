package youbao.shopping.ninetop.activity.user;


import android.content.Intent;
import android.view.View;

import youbao.shopping.ninetop.bean.user.order.SafeGuardDetailsBean;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.service.impl.OrderService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

public class RefundBeing extends AfterScaleBaseActivity {

    private String complaintId;
    private final OrderService orderservice;

    public RefundBeing(){
        orderservice = new OrderService(this);
    }
    @Override
    protected void initView() {
        super.initView();
        hvHead.setTitle("退款中");
        tvRemind.setText("等待财务审核中");
        over.setVisibility(View.GONE);
        logistics.setVisibility(View.GONE);
        rlButton.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        complaintId =intent.getStringExtra(IntentExtraKeyConst.COMPLAINTID);
        requstData();
    }

    private void requstData() {
        orderservice.getSafaGuardDetails(complaintId, new CommonResultListener<SafeGuardDetailsBean>(this) {
            @Override
            public void successHandle(SafeGuardDetailsBean result) throws JSONException {
                initFoot(result);
            }
        });
    }
}
