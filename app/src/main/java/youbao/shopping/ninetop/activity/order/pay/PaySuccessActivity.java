package youbao.shopping.ninetop.activity.order.pay;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import youbao.shopping.ninetop.activity.product.MyOrderActivity;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.common.ActivityActionHelper;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.constant.TextConstant;
import youbao.shopping.ninetop.common.view.HeadView;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * Created by jill on 2016/11/15.
 */

public class PaySuccessActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.tv_order_no)
    TextView tvOrderNo;
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;

    private String orderNo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void initData() {
        super.initData();
        hvHead.setTitle("支付成功");
        hvHead.setBackImageVisible(View.GONE);

        orderNo = getIntentValue(IntentExtraKeyConst.ORDER_NO);
        String orderTotal = getIntentValue(IntentExtraKeyConst.ORDER_TOTAL);
        tvOrderNo.setText("订单编号：" + orderNo);
        tvOrderPrice.setText(TextConstant.MONEY + orderTotal);
    }

    @OnClick({R.id.tv_show_detail, R.id.tv_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_show_detail:
                Intent intent = new Intent(this, MyOrderActivity.class);
                intent.putExtra("order_fg", 1);
                startActivity(intent);
                break;
            case R.id.tv_buy:
                buyMore();
                break;
        }
    }

    private void buyMore(){
        ActivityActionHelper.goToMain(this);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyUp(keyCode, event);
    }
}
