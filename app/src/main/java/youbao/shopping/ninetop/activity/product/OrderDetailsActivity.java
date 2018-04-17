package youbao.shopping.ninetop.activity.product;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;
import youbao.shopping.ninetop.activity.order.ExpressQueryActivity;
import youbao.shopping.ninetop.activity.order.pay.OrderPayActivity;
import youbao.shopping.ninetop.activity.user.ApplyForRefundActivity;
import youbao.shopping.ninetop.activity.user.RefundBeing;
import youbao.shopping.ninetop.activity.user.RefundBeingProcessed;
import youbao.shopping.ninetop.activity.user.RefundDetailsActivity;
import youbao.shopping.ninetop.activity.user.ScaleReturnActivity;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.bean.user.order.EvaluateBean;
import youbao.shopping.ninetop.bean.user.order.EvaluateGoodsBean;
import youbao.shopping.ninetop.bean.user.order.OrderDetailsBean;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.MyActivityManager;
import youbao.shopping.ninetop.common.util.Tools;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.impl.OrderService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;

import static youbao.shopping.ninetop.common.IntentExtraKeyConst.COMPLAINTID;
import static youbao.shopping.ninetop.common.IntentExtraKeyConst.EVALUATEGOODSLIST;
import static youbao.shopping.ninetop.common.IntentExtraKeyConst.GOODS_CODE;
import static youbao.shopping.ninetop.common.IntentExtraKeyConst.ORDER_CODE;
import static youbao.shopping.ninetop.common.IntentExtraKeyConst.REALPAY;
import static youbao.shopping.ninetop.common.IntentExtraKeyConst.SKUID;
import static youbao.shopping.R.id.tv_package_type;

/**
 * @date: 2016/11/12
 * @author: Shelton
 * @version: 1.1.3
 * @Description:订单详情界面 P  待付款  A 待发货  S 已发货 F 已完成  C 取消  E 已关闭  D 删除（不管）
 */
public class OrderDetailsActivity extends BaseActivity implements OnClickListener {
    @BindView(R.id.hv_title)
    HeadView hvTitle;
    @BindView(R.id.tv_goods_state)
    TextView tvGoodsState;
    @BindView(R.id.tv_order_state)
    TextView tvOrderState;
    @BindView(R.id.tv_state_time)
    TextView tvStateTime;
    @BindView(tv_package_type)
    TextView tvPackageType;
    @BindView(R.id.tv_package_time)
    TextView tvPackageTime;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_address)
    TextView tvUserAddress;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.ll_product_list)
    LinearLayout llProductList;
    @BindView(R.id.tv_to_be_pay_total)
    TextView tvToBePayTotal;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_coupon_price)
    TextView tvCouponPrice;
    @BindView(R.id.tv_order_number)
    TextView tvOrderNumber;
    @BindView(R.id.tv_pay_type)
    TextView tvPayType;
    @BindView(R.id.tv_create_time)
    TextView tvCreateTime;
    @BindView(R.id.btn_left)
    Button btnLeft;
    @BindView(R.id.btn_right)
    Button btnRight;
    @BindView(R.id.ll_order_btn_group)
    LinearLayout llOrderBtnGroup;
    @BindView(R.id.rl_package)
    RelativeLayout rlPackage;

    private OrderService service;
    //private List<OrderDetailsBean> OrderDetailsInfo;
    //private OrderDetailsBean orderDetailsBean;
    private ImageView iv_my_order_image;
    private TextView tv_my_order_product_name;
    private TextView tv_my_order_product_price;
    private TextView tv_my_order_product_count;
    private Button btn_refund;
    private LinearLayout ll_btn_function;
    private String complaintStatus;

    private List<EvaluateGoodsBean> evaluateGoodsList;
    private OrderDetailsBean orderDetailResult;

    private String orderCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_details;
    }

    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);

        service = new OrderService(this);
        hvTitle.setTitle("订单详情");
    }

    @Override
    protected void initListener() {
        super.initListener();
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);
        rlPackage.setOnClickListener(this);

    }

    @Override
    protected void initData() {
        super.initData();
        orderCode = getIntent().getStringExtra(ORDER_CODE);
    }

    private void RequestOrderDetails(String orderCode) {

        service.getOrderDetailList(orderCode, new CommonResultListener<OrderDetailsBean>(this) {
            @Override
            public void successHandle(OrderDetailsBean result) {
                orderDetailResult = result;
                initOrderDetails(result);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RequestOrderDetails(orderCode);
    }

    private void initOrderDetails(final OrderDetailsBean result) {
        String orderStatus = result.orderStatus;
        if (orderStatus != null) {
            switch (orderStatus) {
                case "P"://等待买家付款
                    initStatusP(result);
                    break;
                case "A"://等待卖家发货
                    initStatusA(result);
                    break;
                case "S"://卖家已发货
                    initStatusS(result);
                    break;
                case "F"://交易成功
                    initStatusF(result);
                    break;
                case "C"://交易关闭
                case "E":
                    initStatusCE(result);
                    break;
            }
        }
        if (result.isCommented.equals("Y") && orderStatus.equals("F")) {
            btnLeft.setText("查看物流");
            btnRight.setText("删除订单");
            btnLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkLogistics(result);
                }
            });
            btnRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyActivityManager.getInstance().finishAllActivity();
                    deleteOrders(result);
                    finish();
                }
            });
            initStatusIsComment(result);
        }
    }

    private void initStatusIsComment(OrderDetailsBean result) {
        rlPackage.setVisibility(View.VISIBLE);
        tvOrderState.setText("交易成功");
        tvOrderState.setVisibility(View.VISIBLE);
        tvGoodsState.setVisibility(View.GONE);
        tvStateTime.setVisibility(View.GONE);

        initSameMethod(result);
        initGoodsMsg(result);

    }

    private void initStatusP(OrderDetailsBean result) {
        rlPackage.setVisibility(View.GONE);
        //tvUserLeaveMessage.setText(result.remark);//留言
        initHeadMsgP(result);
        initSameMethod(result);
        initGoodsMsg(result);
        initButtonStatusP(result);
    }

    private void initStatusA(OrderDetailsBean result) {
        rlPackage.setVisibility(View.GONE);
        //tvUserLeaveMessage.setText(result.remark);
        tvGoodsState.setText("等待卖家发货");
        tvStateTime.setText("您的包裹正整装待发");
        initSameMethod(result);
        initGoodsMsg(result);

        judgeComplaintstatusBtnVisible(result);
        initButtonStatusA(result);
    }

    private void initStatusS(OrderDetailsBean result) {
        rlPackage.setVisibility(View.VISIBLE);
        //tvUserLeaveMessage.setText(result.remark);
        // tvUserLeaveMessage.setText(result.remark);
        tvGoodsState.setText("卖家已发货");
        tvStateTime.setText(result.timeLeft);
        initSameMethod(result);
        initGoodsMsg(result);
        initButtonStatusS(result);
        judgeComplaintstatusBtnVisible(result);
    }

    private void initStatusF(OrderDetailsBean result) {
        rlPackage.setVisibility(View.VISIBLE);

        tvOrderState.setText("交易成功");
        tvOrderState.setVisibility(View.VISIBLE);
        tvGoodsState.setVisibility(View.GONE);
        tvStateTime.setVisibility(View.GONE);
        initSameMethod(result);
        initGoodsMsg(result);
        initButtonStatusF(result);

        judgeComplaintstatusBtnVisible(result);
    }

    private void initStatusCE(OrderDetailsBean result) {
        rlPackage.setVisibility(View.GONE);
        tvOrderState.setText("交易关闭");
        tvOrderState.setVisibility(View.VISIBLE);
        tvGoodsState.setVisibility(View.GONE);
        tvStateTime.setVisibility(View.GONE);
        initSameMethod(result);
        initGoodsMsg(result);
        initButtonStatusCE(result);
    }

    private void judgeComplaintstatusBtnVisible(OrderDetailsBean result) {
        for (int i = 0; i < result.goodsList.size(); i++) {
            if (result.goodsList.get(i).complaintstatus.equals("P")
                    || result.goodsList.get(i).complaintstatus.equals("W")
                    || result.goodsList.get(i).complaintstatus.equals("B")
                    || result.goodsList.get(i).complaintstatus.equals("M")) {
                llOrderBtnGroup.setVisibility(View.GONE);
            }
        }
    }

    private void initGoodsMsg(final OrderDetailsBean result) {
        llProductList.removeAllViews();
        for (int i = 0; i < result.goodsList.size(); i++) {
            View view = View.inflate(this, R.layout.item_order_details_product, null);
            iv_my_order_image = (ImageView) view.findViewById(R.id.iv_my_order_image);
            tv_my_order_product_name = (TextView) view.findViewById(R.id.tv_my_order_product_name);
            tv_my_order_product_price = (TextView) view.findViewById(R.id.tv_my_order_product_price);
            tv_my_order_product_count = (TextView) view.findViewById(R.id.tv_my_order_product_count);
            ll_btn_function = (LinearLayout) view.findViewById(R.id.ll_btn_function);
            btn_refund = (Button) view.findViewById(R.id.btn_refund);

            Tools.ImageLoaderShow(this, result.goodsList.get(i).icon, iv_my_order_image);
            tv_my_order_product_name.setText(result.goodsList.get(i).goodsName);
            tv_my_order_product_price.setText(result.goodsList.get(i).price);
            tv_my_order_product_count.setText(result.goodsList.get(i).count);

            doDifferentOperation(result);

            judgeComplaintStatus(result, i);
            llProductList.addView(view);
        }
    }

    private void doDifferentOperation(OrderDetailsBean result) {
        switch (result.orderStatus) {
            case "P":
                ll_btn_function.setVisibility(View.GONE);
                break;
            case "A":
                ll_btn_function.setVisibility(View.VISIBLE);
                break;
            case "S":
                ll_btn_function.setVisibility(View.VISIBLE);
                break;
            case "F":
                ll_btn_function.setVisibility(View.VISIBLE);
                break;
            case "C":
            case "E":
                ll_btn_function.setVisibility(View.GONE);
                break;
        }
    }

    private void initButtonStatusP(final OrderDetailsBean result) {
        btnLeft.setVisibility(View.VISIBLE);
        btnRight.setVisibility(View.VISIBLE);
        btnLeft.setBackgroundResource(R.mipmap.border_btn_gray);
        btnLeft.setTextColor(getResources().getColor(R.color.gray_light3));
        btnRight.setBackgroundResource(R.mipmap.border_btn_red);
        btnRight.setTextColor(getResources().getColor(R.color.text_red2));
        btnLeft.setText("取消订单");
        btnRight.setText("付款");
        btnLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDialog(OrderDetailsActivity.this, MyDialog.DIALOG_TWOOPTION, "温馨提示", "您确定要取消订单吗?", new MyDialogOnClick() {
                    @Override
                    public void sureOnClick(View v) {
                        MyActivityManager.getInstance().finishAllActivity();
                        cancelOrders(result);
                    }

                    @Override
                    public void cancelOnClick(View v) {}
                }).show();
            }
        });
        btnRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderDetailsActivity.this, OrderPayActivity.class);
                intent.putExtra(IntentExtraKeyConst.ORDER_NO, result.orderCode);
                intent.putExtra(IntentExtraKeyConst.ORDER_TOTAL, result.realPay);
                startActivity(intent);
            }
        });
    }

    private void initButtonStatusA(final OrderDetailsBean result) {
        btnLeft.setVisibility(View.GONE);
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setBackgroundResource(R.mipmap.border_btn_gray);
        btnRight.setTextColor(getResources().getColor(R.color.gray_light3));
        btnRight.setText("催单");
        btnRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("已为您催单,请亲不要着急");
            }
        });
    }
    private String pwsd="";
    private void initButtonStatusS(final OrderDetailsBean result) {

        btnLeft.setVisibility(View.VISIBLE);
        btnRight.setVisibility(View.VISIBLE);
        btnLeft.setBackgroundResource(R.mipmap.border_btn_gray);
        btnLeft.setTextColor(getResources().getColor(R.color.gray_light3));
        btnRight.setBackgroundResource(R.mipmap.border_btn_red);
        btnRight.setTextColor(getResources().getColor(R.color.text_red2));
        btnLeft.setText("查看物流");
        btnRight.setText("确认收货");
        btnLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogistics(result);
            }
        });
        btnRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyActivityManager.getInstance().finishAllActivity();
                confirmGoods(result,pwsd);
            }
        });
    }

    private void initButtonStatusF(final OrderDetailsBean result) {
        btnLeft.setVisibility(View.VISIBLE);
        btnRight.setVisibility(View.VISIBLE);
        btnLeft.setBackgroundResource(R.mipmap.border_btn_gray);
        btnLeft.setTextColor(getResources().getColor(R.color.gray_light3));
        btnRight.setBackgroundResource(R.mipmap.border_btn_red);
        btnRight.setTextColor(getResources().getColor(R.color.text_red2));
        btnLeft.setText("查看物流");
        btnRight.setText("去评价");
        btnLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogistics(result);
            }
        });
        btnRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                toEvaluate(result);
            }
        });
    }

    private void initButtonStatusCE(final OrderDetailsBean result) {
        btnLeft.setVisibility(View.GONE);
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setBackgroundResource(R.mipmap.border_btn_gray);
        btnRight.setTextColor(getResources().getColor(R.color.gray_light3));
        btnRight.setText("删除订单");
        btnRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MyActivityManager.getInstance().finishAllActivity();
                deleteOrders(result);
            }
        });
    }

    //删除订单service
    private void deleteOrders(OrderDetailsBean result) {
        service.deleteOrders(result.orderCode, new CommonResultListener<String>(OrderDetailsActivity.this) {
            @Override
            public void successHandle(String result) {
                finish();
                startActivity(MyOrderActivity.class);
                Toast.makeText(OrderDetailsActivity.this, "删除订单成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //确认收货service
    private void confirmGoods(OrderDetailsBean result,String password) {
        service.confirmGoods(result.orderCode, password,new CommonResultListener<String>(OrderDetailsActivity.this) {
            @Override
            public void successHandle(String result) {
                finish();
                startActivity(MyOrderActivity.class);
                Toast.makeText(OrderDetailsActivity.this, "确认收货成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //取消订单service
    private void cancelOrders(OrderDetailsBean result) {
        service.cancelOrders(result.orderCode, new CommonResultListener<String>(OrderDetailsActivity.this) {
            @Override
            public void successHandle(String result) {
                finish();
                startActivity(MyOrderActivity.class);
                Toast.makeText(OrderDetailsActivity.this, "取消订单成功", Toast.LENGTH_SHORT).show();
            }
        });
    }
    //去评价service
    private void toEvaluate(OrderDetailsBean result) {
        service.getEvaluateGoods(result.orderCode, new CommonResultListener<EvaluateBean>(OrderDetailsActivity.this) {
            @Override
            public void successHandle(EvaluateBean result) throws JSONException {
                evaluateGoodsList = result.goodsList;
                Intent intent = new Intent(OrderDetailsActivity.this, CommentAndShowActivity.class);
                intent.putExtra(ORDER_CODE, result.orderCode);
                intent.putExtra(EVALUATEGOODSLIST, (Serializable) evaluateGoodsList);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initSameMethod(OrderDetailsBean result) {
        initPackageMsg(result);
        initAddressMsg(result);
        initPriceMsg(result);
        initOrderMsg(result);
    }
    //初始化订单信息
    private void initOrderMsg(OrderDetailsBean result) {
        tvOrderNumber.setText(result.orderCode);

        String payWay = result.payWay;
        tvPayType.setText(payWay);
        switch (payWay) {
            case "A":
                tvPayType.setText("支付宝支付");
                break;
            case "W":
                tvPayType.setText("微信支付");
                break;
            case "P":
                tvPayType.setText("零钱支付");
                break;
        }

        tvCreateTime.setText(result.orderedTime);
    }
    //初始化快递信息
    private void initPackageMsg(OrderDetailsBean result) {
        tvPackageType.setText(result.latesDeliverInfo);
        tvPackageTime.setText(result.latestTime);
    }
    //初始化付款信息
    private void initPriceMsg(OrderDetailsBean result) {
        tvToBePayTotal.setText(result.realPay);
        tvTotalPrice.setText(result.total);
        tvCouponPrice.setText(result.discount);
    }
    //初始化地址信息
    private void initAddressMsg(OrderDetailsBean result) {
        tvUserName.setText(result.consigneeName);
        tvUserAddress.setText(result.consignDetailAddress);
        tvUserPhone.setText(result.consigneeMobile);
    }

    private void initHeadMsgP(OrderDetailsBean result) {
        tvGoodsState.setText("等待买家付款");
        tvStateTime.setText(result.timeLeft);
    }

    /**
     * 查看物流
     *
     * @param result
     */
    private void checkLogistics(OrderDetailsBean result) {
//        Intent intent = new Intent(OrderDetailsActivity.this, SeeLogistic.class);
//        intent.putExtra(ORDER_CODE, result.orderCode);
//        startActivity(intent);
        Intent mIntent = new Intent(OrderDetailsActivity.this, ExpressQueryActivity.class);
        startActivity(mIntent);
    }

    /**
     * 商品点击跳转到商品详情
     */
   /* private void jump2ProductDetails(OrderDetailsBean result, int finalI) {
        Intent intent = new Intent(OrderDetailsActivity.this, ProductDetailActivity.class);
        intent.putExtra(IntentExtraKeyConst.PRODUCT_CODE, result.goodsList.get(finalI).goodsCode);
        startActivity(intent);
    }*/

    /**
     * 判断维权状态
     *
     * @param result
     * @param i
     */
    private void judgeComplaintStatus(final OrderDetailsBean result, final int i) {
        complaintStatus = result.goodsList.get(i).complaintstatus;
        switch (complaintStatus) {
            case "P":
            case "W":
                btn_refund.setText("退款处理中");
                btn_refund.setOnClickListener(new dealRefundListener(i, result));
                break;
            case "B":
                btn_refund.setText("退货中");
                btn_refund.setOnClickListener(new returnGoodsListener(i, result));
                break;
            case "M":
                btn_refund.setText("退款中");
                btn_refund.setOnClickListener(new returnCashListener(i, result));
                break;
            case "F":
                btn_refund.setText("退款成功");
                btn_refund.setOnClickListener(new refundSuccessListener(i, result));
                break;
            default:
                btn_refund.setText("申请售后");
                btn_refund.setOnClickListener(new applyForSaleListener(i, result));
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_package:
                checkLogistics(orderDetailResult);
                break;
        }
    }

    private class dealRefundListener implements OnClickListener {
        private final int i;
        private final OrderDetailsBean result;

        public dealRefundListener(int i, OrderDetailsBean result) {
            this.i = i;
            this.result = result;
        }

        @Override
        public void onClick(View v) {
            final int index = i;

            Intent intent = new Intent(OrderDetailsActivity.this, RefundBeingProcessed.class);
            intent.putExtra(COMPLAINTID, result.goodsList.get(index).complaintId);
            startActivity(intent);
        }
    }

    private class returnGoodsListener implements OnClickListener {
        private final int i;
        private final OrderDetailsBean result;

        public returnGoodsListener(int i, OrderDetailsBean result) {
            this.i = i;
            this.result = result;
        }

        @Override
        public void onClick(View v) {
            final int index = i;

            Intent intent = new Intent(OrderDetailsActivity.this, ScaleReturnActivity.class);
            intent.putExtra(COMPLAINTID, result.goodsList.get(index).complaintId);
            startActivity(intent);
        }
    }

    private class returnCashListener implements OnClickListener {
        private final int i;
        private final OrderDetailsBean result;

        public returnCashListener(int i, OrderDetailsBean result) {
            this.i = i;
            this.result = result;
        }

        @Override
        public void onClick(View v) {
            final int index = i;

            Intent intent = new Intent(OrderDetailsActivity.this, RefundBeing.class);
            intent.putExtra(COMPLAINTID, result.goodsList.get(index).complaintId);
            startActivity(intent);
        }
    }

    private class refundSuccessListener implements OnClickListener {
        private final int i;
        private final OrderDetailsBean result;

        public refundSuccessListener(int i, OrderDetailsBean result) {
            this.i = i;
            this.result = result;
        }

        @Override
        public void onClick(View v) {
            final int index = i;
            Intent intent = new Intent(OrderDetailsActivity.this, RefundDetailsActivity.class);
            intent.putExtra(COMPLAINTID, result.goodsList.get(index).complaintId);
            startActivity(intent);
        }
    }

    private class applyForSaleListener implements OnClickListener {
        private final int i;
        private final OrderDetailsBean result;

        public applyForSaleListener(int i, OrderDetailsBean result) {
            this.i = i;
            this.result = result;
        }

        @Override
        public void onClick(View v) {
            final int index = i;
            Intent intent = new Intent(OrderDetailsActivity.this, ApplyForRefundActivity.class);
            intent.putExtra(ORDER_CODE, result.orderCode);
            intent.putExtra(GOODS_CODE, result.goodsList.get(index).goodsCode);
            intent.putExtra(SKUID, result.goodsList.get(index).skuId);
            intent.putExtra(REALPAY, result.goodsList.get(index).realPay);
            startActivity(intent);
        }
    }
}
