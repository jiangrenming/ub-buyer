package youbao.shopping.ninetop.activity.ub.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;
import youbao.shopping.ninetop.UB.order.UbPayBean;
import youbao.shopping.ninetop.UB.product.UbOrder.PayBean;
import youbao.shopping.ninetop.activity.product.MyOrderActivity;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.constant.TextConstant;
import youbao.shopping.ninetop.common.view.HeadView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import youbao.shopping.R;

import static youbao.shopping.R.id.tv_total_pay;

/**
 * Created by huangjinding on 2017/4/22.
 */
public class UbConfirmPayActivity extends BaseActivity {
  /*  @BindView(R.id.hv_head)
    HeadView hvHead;*/
    @BindView(R.id.hv_head)
    ImageView hv_head;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;
    @BindView(tv_total_pay)
    TextView tvTotalPay;
    @BindView(R.id.tv_u_pay)
    TextView tvUPay;
    @BindView(R.id.tv_balance_pay)
    TextView tvBalancePay;
    private String jsonOrder;
    private PayBean bean;
    private String orderCode;
    private Double ubPay;
    private Double payPrice;
    private int payType;
    private long totalPay;//花费总积分
    private long uBPiont;
    public UbConfirmPayActivity() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_order_jiesuan;
    }

    @Override
    protected void initView() {
        super.initView();
      //  hvHead.setTitle("兑换");
        getDetail();
    }

    @OnClick({R.id.btn_order_confirm,R.id.hv_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_order_confirm:
                if (String.valueOf(bean.getUbPay()).equals(String.valueOf(bean.getTotalProductPay()))){
                    UbPayBean ubPayBean = new UbPayBean();
                    ubPayBean.setOrderCode(bean.getOrderCode());
                    ubPayBean.setUbPay(bean.getUbPay());
                    ubPayBean.setPayPrice(bean.getMoneyPay());
                    ubPayBean.setBalancePay(bean.getBalancePay());
                    ubPayBean.setPayType(payType);
                    UbPayOrderUnite ubPayOrderUnite = new UbPayOrderUnite(ubPayBean, this);
                    ubPayOrderUnite.toPay();

                    Log.e("提交===========",String.valueOf(bean.getUbPay())+"++++++"+String.valueOf(bean.getTotalProductPay()));
                }else {
                    Toast.makeText(UbConfirmPayActivity.this,"积分不足",Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case R.id.hv_head:
                payCancelHandle();
                break;

            default:
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void getDetail() {
        jsonOrder = getIntentValue(IntentExtraKeyConst.JSON_ORDER);
        if (jsonOrder == null || jsonOrder.length() == 0) {
            return;
        }
        Gson gson = new Gson();
        TypeToken<PayBean> typeToken = new TypeToken<PayBean>() {
        };
        //余额支付1，微信2，支付宝3
        bean = gson.fromJson(jsonOrder, typeToken.getType());
        orderCode = bean.getOrderCode();
        ubPay = bean.getUbPay();
        payPrice = bean.getMoneyPay();
        totalPay = (long) Math.floor(bean.getTotalPay());
        uBPiont = (long) Math.floor(bean.getOwnUBPoint());
        if (payPrice > 0) {
        } else if (payPrice == 0) {
            payType = 1;
        }
        long myAll = (long) Math.floor(uBPiont);
        long myUb = (long) Math.floor(ubPay);
        tvOrderNum.setText(orderCode);//订单
        tvTotalPay.setText(myAll + "");//总积分
        tvUPay.setText(myUb + "");// 积分兑换
        tvBalancePay.setText((myAll-myUb)+"");//剩余积分

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            payCancelHandle();
            return false;
        }
        return super.onKeyUp(keyCode, event);
    }

    private void payCancelHandle() {
        new MyDialog(this, MyDialog.DIALOG_TWOOPTION, "温馨提示", "您确定取消支付!", new MyDialogOnClick() {
            @Override
            public void sureOnClick(View v) {
                Intent intent = new Intent(UbConfirmPayActivity.this, MyOrderActivity.class);
                intent.putExtra("order_fg", 0);
                startActivity(intent);
                finish();
            }

            @Override
            public void cancelOnClick(View v) {

            }
        }).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}