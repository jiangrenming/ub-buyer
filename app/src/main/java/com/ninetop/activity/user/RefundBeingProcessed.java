package com.ninetop.activity.user;
/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
 * Created by Administrator on 2016/11/14.
 */


import android.content.Intent;
import android.view.View;

import com.ninetop.bean.user.order.SafeGuardDetailsBean;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.service.impl.OrderService;
import com.ninetop.service.impl.UserCenterService;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.HashMap;

public class RefundBeingProcessed extends AfterScaleBaseActivity {

    private  UserCenterService userCenterService;
    private  String complaintId="";
    private  OrderService orderservice;

    public RefundBeingProcessed(){
        userCenterService = new UserCenterService(this);
        orderservice = new OrderService(this);
    }

    @Override
    protected void initView() {
        super.initView();
        over.setVisibility(View.GONE);
        logistics.setVisibility(View.GONE);
        hvHead.setTitle("退款处理中");
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        complaintId=intent.getStringExtra(IntentExtraKeyConst.COMPLAINTID);
        //请求数据
        requstData();
    }

    private void refundAndScaleReturn(SafeGuardDetailsBean result) {
        if (result.complaintstatus.equals("P")){
            btLogisticsMessage.setVisibility(View.GONE);
            tvRemind.setText("等待商家处理退款申请");
        }else {
            btLogisticsMessage.setVisibility(View.VISIBLE);
            tvRemind.setText("商家已同意退款，请退货给商家");
        }
        initFoot(result);
    }

    private void refund(SafeGuardDetailsBean result) {
        btLogisticsMessage.setVisibility(View.GONE);
        tvRemind.setText("等待商家处理退款");
        initFoot(result);
    }

    private void requstData() {
        orderservice.getSafaGuardDetails(complaintId, new CommonResultListener<SafeGuardDetailsBean>(this) {
            @Override
            public void successHandle(SafeGuardDetailsBean result) throws JSONException {
                if(AfterScaleEum.RefundStyle.ONLY_REFUND.getStyle().equals(result.complaintstype)){
                    //退款
                    refund(result);
                }else if (AfterScaleEum.RefundStyle.REFUND_SCALERETURN.getStyle().equals(result.complaintstype)){
                    //退款又退货
                    refundAndScaleReturn(result);
                }
            }
        });
    }

    @Override
    public void fillLogistic() {
        HashMap<String, String> map = new HashMap<>();
        map.put(IntentExtraKeyConst.COMPLAINTID,complaintId);
        startActivity(FillInReturnGoods.class,map);
        finish();
    }

    @Override
    public void revoke() {
        showToast("撤销申述");
        userCenterService.cancelReturnMoney(complaintId, new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) throws JSONException {
                showToast("撤销成功");
                finish();
            }
        });
    }
}
