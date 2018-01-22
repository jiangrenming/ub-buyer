package com.ninetop.activity.user;


import android.content.Intent;
import android.view.View;
import com.ninetop.bean.user.order.SafeGuardDetailsBean;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.service.impl.OrderService;
import com.ninetop.service.listener.CommonResultListener;
import org.json.JSONException;


public class RefundDetailsActivity extends AfterScaleBaseActivity {

    private final OrderService orderService;
    private String complaintId;


    public RefundDetailsActivity(){
        orderService = new OrderService(this);
    }
    @Override
    protected void initView() {
        super.initView();
        hvHead.setTitle("退款详情");
        logistics.setVisibility(View.GONE);
        rlButton.setVisibility(View.GONE);
        llRemind.setVisibility(View.GONE);
    }
    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        complaintId =intent.getStringExtra(IntentExtraKeyConst.COMPLAINTID);
        requestData();
    }

    private void requestData() {
        orderService.getSafaGuardDetails(complaintId, new CommonResultListener<SafeGuardDetailsBean>(this) {
            @Override
            public void successHandle(SafeGuardDetailsBean result) throws JSONException {
                initLayout(result);
            }
        });
    }

    private void initLayout(SafeGuardDetailsBean result) {
        if ("R".equals(result.complaintstatus)||"C".equals(result.complaintstatus)){
            llSuccessful.setVisibility(View.GONE);
            llCloseReason.setVisibility(View.VISIBLE);
            tvCloseSuccessful.setText("退款关闭");
            if ("R".equals(result.complaintstatus)){
                //拒绝
                tvCloseReason.setText("商家拒绝您的退款申请。拒绝理由:"+result.closereason+"");
            }
            if ("C".equals(result.complaintstatus)){
                //取消
                tvCloseReason.setText(result.closereason+"");
            }
            initFoot(result);
        }else if ("F".equals(result.complaintstatus)){
            llSuccessful.setVisibility(View.VISIBLE);
            llCloseReason.setVisibility(View.GONE);
            tvCloseSuccessful.setText("退款成功");
            tvSuccessReturnMoneyCount.setText(result.realReturnMoney);
            tvSuccessReturnMoneyTime.setText(result.returntime);
            initFoot(result);
        }
    }
}
