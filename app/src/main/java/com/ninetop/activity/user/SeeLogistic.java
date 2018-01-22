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

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ninetop.adatper.SeeGoodsAdapter;
import com.ninetop.base.BaseActivity;
import com.ninetop.bean.user.LogisticBean;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.impl.UserCenterService;
import com.ninetop.service.listener.CommonResultListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;

public class SeeLogistic extends BaseActivity {
    private final UserCenterService userCenterService;
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_logistic_number)
    TextView tvLogisticNumber;
    @BindView(R.id.lv)
    ListView lv;
    public String orderCode="";
    private List<LogisticBean.TrackListBean> trackList;
    private String trackState;
    public SeeLogistic(){
        userCenterService = new UserCenterService(this);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_see_logistac;
    }
    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        initTitle();
    }
    private void initTitle() {
        hvHead.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        hvHead.setTitle("查看物流");
        hvHead.setSearchImageVisible(View.GONE);
    }
    @Override
    protected void onResume() {
        super.onResume();
        orderCode= getIntent().getStringExtra(IntentExtraKeyConst.ORDER_CODE);
        userCenterService.seeLogistics(orderCode, new CommonResultListener<LogisticBean>(this) {
            @Override
            public void successHandle(LogisticBean result) {
                setAdapter(result);
            }
        });
    }
    private void setAdapter(LogisticBean result) {
        tvCompany.setText(result.logisticname);
        tvLogisticNumber.setText(result.logisticcode);
        trackState = result.trackstate;
        if (trackList==null){
            trackList=new ArrayList<>();
        }
        trackList = result.trackList;
        lv.setAdapter(new SeeGoodsAdapter(trackList,this,trackState));
    }
}
