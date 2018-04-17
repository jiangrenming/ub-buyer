package youbao.shopping.ninetop.activity.order.pay;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;
import youbao.shopping.ninetop.activity.product.MyOrderActivity;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.bean.user.UserInfo;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.constant.TextConstant;
import youbao.shopping.ninetop.common.pay.OrderPayItem;
import youbao.shopping.ninetop.common.pay.PayOrderUnite;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.impl.UserCenterService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;


/**
 * Created by jill on 2016/11/15.
 */

public class OrderPayActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.iv_balance_select)
    ImageView ivBalanceSelect;
    @BindView(R.id.iv_alipay_select)
    ImageView ivAliPaySelect;
    @BindView(R.id.iv_wechat_select)
    ImageView ivWeChatSelect;
    @BindView(R.id.tv_price_balance)
    TextView tvPriceBalance;
    @BindView(R.id.tv_price_alipay)
    TextView tvPriceAliPay;
    @BindView(R.id.tv_price_wechat)
    TextView tvPriceWeChat;
    @BindView(R.id.rv_pay_balance)
    View rvPayBalance;
    int payType = 0;
    private boolean isSettingPWD=false;
    private String orderNo;
    private String orderPrice;
    private String orderFrom;
    private UserCenterService userCenterService;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_pay;
    }
    @Override
    protected void initData() {
        super.initData();
        userCenterService = new UserCenterService(this);


        hvHead.setTitle("支付");

        orderNo = getIntentValue(IntentExtraKeyConst.ORDER_NO);
        orderPrice = getIntentValue(IntentExtraKeyConst.ORDER_TOTAL);
        orderFrom = getIntentValue(IntentExtraKeyConst.ORDER_FROM);

        String priceText = TextConstant.MONEY + orderPrice;
        tvPriceAliPay.setText(priceText);
        tvPriceWeChat.setText(priceText);
        rvPayBalance.setEnabled(false);

        if(orderPrice == null || orderPrice.length() == 0)
            return;

        getBalance();

        hvHead.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payCancelHandle();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserInfo();
    }

    @OnClick({R.id.rv_pay_balance, R.id.rv_pay_alipay, R.id.rv_pay_wechat, R.id.tv_pay_now})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rv_pay_balance:
                setSelectIndex(1);
                break;
            case R.id.rv_pay_alipay:
                setSelectIndex(2);
                break;
            case R.id.rv_pay_wechat:
                setSelectIndex(3);
                break;
            case R.id.tv_pay_now:
                if(payType == 0){
                    showToast("请选择支付方式");
                    return;
                }

                OrderPayItem payItem = new OrderPayItem(payType, orderNo, orderFrom, orderPrice,isSettingPWD);
                PayOrderUnite payOrderUnite = new PayOrderUnite(payItem, this);
                payOrderUnite.toPay();
                break;
        }
    }

    private void getBalance() {
        userCenterService.freeMoney(new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) throws JSONException {
                String balance = "0";
                if (result != null && result.length() > 0) {
                    balance = result;
                }

                float balanceValue = Float.parseFloat(balance);
                float price = Float.parseFloat(orderPrice);
                if (balanceValue >= price) {
                    rvPayBalance.setEnabled(true);
                    ivBalanceSelect.setImageResource(R.mipmap.edit_unselect);
                    tvPriceBalance.setText(TextConstant.MONEY + orderPrice);
                }else {
                    ivBalanceSelect.setImageResource(R.mipmap.select_disable);
                }

                selectDefaultPay();
            }
        });
    }

    private void selectDefaultPay(){
        if(rvPayBalance.isEnabled()) {
            setSelectIndex(1);
        }else{
            setSelectIndex(2);
        }
    }

    private void setSelectIndex(int type){
        if(rvPayBalance.isEnabled()) {
            ivBalanceSelect.setImageResource(R.mipmap.edit_unselect);
        }else{
            ivBalanceSelect.setImageResource(R.mipmap.select_disable);
        }
        ivAliPaySelect.setImageResource(R.mipmap.edit_unselect);
        ivWeChatSelect.setImageResource(R.mipmap.edit_unselect);
        payType = type;
        if (type == 1) {
            ivBalanceSelect.setImageResource(R.mipmap.square_select);
        } else if (type == 2) {
            ivAliPaySelect.setImageResource(R.mipmap.square_select);
        } else {
            ivWeChatSelect.setImageResource(R.mipmap.square_select);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            payCancelHandle();
            return false;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void payCancelHandle(){
        new MyDialog(this, MyDialog.DIALOG_TWOOPTION, "温馨提示", "您确定取消支付！", new MyDialogOnClick() {
            @Override
            public void sureOnClick(View v) {
                finish();
                Intent intent = new Intent(OrderPayActivity.this, MyOrderActivity.class);
                intent.putExtra("order_fg", 1);
                startActivity(intent);
            }

            @Override
            public void cancelOnClick(View v) {
            }
        }).show();
    }


    public void getUserInfo() {
        userCenterService.getUserInfo(new CommonResultListener<UserInfo>(this) {
            @Override
            public void successHandle(UserInfo result) throws JSONException {
                isSettingPWD=result.havePayPwd;
            }
        });
    }
}
