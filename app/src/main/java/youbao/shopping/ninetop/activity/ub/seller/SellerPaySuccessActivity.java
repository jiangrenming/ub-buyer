package youbao.shopping.ninetop.activity.ub.seller;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.base.GlobalInfo;
import youbao.shopping.ninetop.common.ActivityActionHelper;
import youbao.shopping.ninetop.common.constant.SharedKeyConstant;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/7/9.
 */

public class SellerPaySuccessActivity extends BaseActivity {
    @BindView(R.id.txt_order_number)
    TextView mOrderNum;
    @BindView(R.id.tv_pay_total)
    TextView tvPayTotal;
    @BindView(R.id.tv_u_get)
    TextView tvUGet;
    public SellerPaySuccessActivity() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_pay_result_seller;
    }

    protected void initView() {
        super.initView();
        getDetail();
    }

    private void getDetail() {
        //获取订单编号
        Intent mIntent = this.getIntent();
        Bundle mBundle = mIntent.getExtras();
        if (mBundle == null) {
            return;
        }
        int ID = mBundle.getInt(SharedKeyConstant.ORDER_ID);
        String id = String.valueOf(ID);
        mOrderNum.setText("订单编号:" + id);
        mOrderNum.setVisibility(View.GONE);
        String orderU = GlobalInfo.saveUB.substring(0, GlobalInfo.saveUB.indexOf("."));
        tvUGet.setText(orderU);
        String orderTotal = GlobalInfo.savePay;
        tvPayTotal.setText("￥" + orderTotal);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityActionHelper.goToMain(this);
            return false;
        }
        return super.onKeyUp(keyCode, event);
    }

    @OnClick({R.id.iv_back, R.id.btn_contact_platform})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ActivityActionHelper.goToMain(SellerPaySuccessActivity.this);
                break;
            case R.id.btn_contact_platform:
                ActivityActionHelper.goToMain(SellerPaySuccessActivity.this);
                break;
        }
    }
}
