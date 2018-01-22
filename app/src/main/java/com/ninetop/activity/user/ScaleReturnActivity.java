package com.ninetop.activity.user;


import android.content.Intent;
import android.view.View;

import com.ninetop.bean.user.order.SafeGuardDetailsBean;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.service.impl.OrderService;
import com.ninetop.service.impl.UserCenterService;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

public class ScaleReturnActivity extends AfterScaleBaseActivity {

    private final UserCenterService userCenterService;
    private final OrderService orderService;
    private String complaintId="";

    public ScaleReturnActivity(){
        userCenterService = new UserCenterService(this);
        orderService = new OrderService(this);
    }
    @Override
    protected void initView() {
        super.initView();
        hvHead.setTitle("退货中");
        over.setVisibility(View.GONE);
        btLogisticsMessage.setVisibility(View.GONE);
        tvRemind.setText("买家已经退货，等待商家确认收货中");
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        complaintId=intent.getStringExtra(IntentExtraKeyConst.COMPLAINTID);
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
        tvLogisticsStyle.setText(result.logisticname);
        tvLogisticsStyle.setText(result.logisticcode);
        initFoot(result);
    }

    @Override
    public void revoke() {
        userCenterService.cancelReturnMoney(complaintId, new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) throws JSONException {
                showToast("撤销成功");
                finish();
            }
        });
    }
}
