package youbao.shopping.ninetop.activity.user;
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
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.impl.UserCenterService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import youbao.shopping.R;

public class FillInReturnGoods extends BaseActivity {
    @BindView(R.id.et_exit_money_reason)
    EditText etExitMoneyReason;
    @BindView(R.id.et_logistics_number)
    EditText etLogisticsNumber;
    @BindView(R.id.head_view)
    HeadView head_view;
    private String complaintId="";
    private final UserCenterService userCenterService;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_return_goods_write;
    }
    public FillInReturnGoods(){
        userCenterService = new UserCenterService(this);
    }
    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        initTitle();
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        complaintId=intent.getStringExtra(IntentExtraKeyConst.COMPLAINTID);
    }

    private void initTitle() {
        head_view.setTitle("填写退货物流");
        head_view.setSearchImageVisible(View.GONE);
        head_view.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @OnClick({R.id.bt_commit, R.id.iv_down, R.id.iv_logistics_down})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_commit:
                //提交
               requestLogistic();
                break;
        }
    }

    private void requestLogistic() {
        String logisticsType = etExitMoneyReason.getText().toString().trim();
        String logisticsCode = etLogisticsNumber.getText().toString().trim();
        if (TextUtils.isEmpty(logisticsType)){
            showToast("请填写物流公司");
            return;
        }
        if (TextUtils.isEmpty(logisticsCode)){
            showToast("请填写物流单号");
            return;
        }
        userCenterService.fillLogistics(complaintId, logisticsType, logisticsCode, new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) {
                showToast("提交成功");
                finish();
            }
        });
    }
}
