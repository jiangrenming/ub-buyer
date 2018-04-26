package youbao.shopping.ninetop.activity.ub.order;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import youbao.shopping.ninetop.activity.product.MyOrderActivity;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.base.GlobalInfo;
import youbao.shopping.ninetop.common.ActivityActionHelper;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/4/24.
 */
public class UbPaySuccessActivity extends BaseActivity {
    @BindView(R.id.tv_pay_total)
    TextView tvPayTotal;
    @BindView(R.id.tv_u_cut)
    TextView tvUCut;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_order_code)
    TextView tvOrderCode;
    @BindView(R.id.tv_alert)
    TextView tvAlert;


    private String getStyleIndex;
    public UbPaySuccessActivity() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_pay_result;
    }

    protected void initView() {
        super.initView();
        getStyleIndex= GlobalInfo.saveGetStyle;
        if("0".equals(getStyleIndex)){
            tvAlert.setText("我们将尽快为您发货");
        }else {
            tvAlert.setText("请您尽快到佳优保门店兑换");
        }
        getDetail();
    }

    private void getDetail() {
        String orderCode = getIntentValue(IntentExtraKeyConst.ORDER_CODE);
        String orderU = getIntentValue(IntentExtraKeyConst.ORDER_U);
        String total = getIntentValue(IntentExtraKeyConst.PAY_MONEY);
        String orderTotal = getIntentValue(IntentExtraKeyConst.ORDER_TOTAL);
        String orderBalance = getIntentValue(IntentExtraKeyConst.ORDER_BALANCE);
        tvUCut.setText(total.substring(0,total.indexOf(".")));
        tvOrderCode.setText(orderCode);
        tvPayTotal.setText("￥"+orderTotal.substring(0,orderTotal.indexOf(".")));
        tvBalance.setText("￥"+orderBalance.substring(0,orderBalance.indexOf(".")));
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
                ActivityActionHelper.goToMain(UbPaySuccessActivity.this);
                break;
            case R.id.btn_contact_platform:
                if("0".equals(getStyleIndex)){
                    Intent intent = new Intent(UbPaySuccessActivity.this, MyOrderActivity.class);
                    intent.putExtra("order_fg", 1);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(UbPaySuccessActivity.this, MyOrderActivity.class);
                    intent.putExtra("order_fg", 3);
                    startActivity(intent);
                }
                break;
        }
    }
}