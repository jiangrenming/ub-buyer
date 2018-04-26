package youbao.shopping.ninetop.activity.ub.order;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;
import com.hykj.myviewlib.gridview.GridPasswordView;

import youbao.shopping.ninetop.UB.ChangeCodeBean;
import youbao.shopping.ninetop.UB.cartshop.ChildListView;
import youbao.shopping.ninetop.UB.order.MyOrderDetailBean;
import youbao.shopping.ninetop.UB.order.MyOrderListBean;
import youbao.shopping.ninetop.UB.product.UbOrder.PayBean;
import youbao.shopping.ninetop.UB.product.UbProductService;
import youbao.shopping.ninetop.activity.order.ExpressQueryActivity;
import youbao.shopping.ninetop.activity.product.MyOrderActivity;
import youbao.shopping.ninetop.activity.ub.product.SellerServiceActivity;
import youbao.shopping.ninetop.activity.ub.product.UbProductActivity;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.base.GlobalInfo;
import youbao.shopping.ninetop.base.Viewable;
import youbao.shopping.ninetop.common.ActivityActionHelper;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.MyActivityManager;
import youbao.shopping.ninetop.common.constant.TextConstant;
import youbao.shopping.ninetop.common.util.Tools;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

import static youbao.shopping.ninetop.common.IntentExtraKeyConst.ORDER_TYPE;
import static youbao.shopping.ninetop.config.AppConfig.BASE_IMAGE_URL;

/**
 * Created by huangjinding on 2017/6/30.
 */

public class UbMyOrderDetailActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.tv_wait_get)
    TextView tvWaitGet;
    @BindView(R.id.tv_alert)
    TextView tvAlert;
    @BindView(R.id.tv_franchiseeName)
    TextView tvFranchiseeName;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;
    @BindView(R.id.tv_franchiseename)
    TextView tvFranShopName;
    @BindView(R.id.tv_order_style)
    TextView tvOrderStyle;
    @BindView(R.id.tv_EMS)
    TextView tvEMS;
    @BindView(R.id.tv_product_pri)
    TextView tvProductPri;
    @BindView(R.id.tv_u_cut)
    TextView tvUCut;
    @BindView(R.id.tv_balance_cut)
    TextView tvBalanceCut;
    @BindView(R.id.tv_order_code)
    TextView tvOrderCode;
    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.tv_order_pay_time)
    TextView tvOrderPayTime;
    @BindView(R.id.tv_order_send_time)
    TextView tvOrderSendTime;
    @BindView(R.id.tv_order_ems_code)
    TextView tvOrderEmsCode;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.listview)
    ChildListView listView;
    private UbProductService ubProductService;
    private String status;
    private String orderId;
    private String changeCode;
    private int orderIdNew;
    private int amount;
    private int skuId;
    private MyOrderListBean ubOrderDetailBean;
    private int franchiseeId;

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_myorder_detail;
    }

    @Override
    protected void initView() {
        super.initView();
        hvHead.setTitle("订单详情");
        ubProductService = new UbProductService(this);
        orderId = getIntentValue(IntentExtraKeyConst.ORDER_ID);
        status = getIntent().getStringExtra(ORDER_TYPE);
        RequestOrderDetails(orderId);
        handlerDetail();
    }

    private void RequestOrderDetails(String orderId) {

        ubProductService.getOrderAllDetail(orderId, new CommonResultListener<MyOrderListBean>(this) {
            @Override
            public void successHandle(MyOrderListBean result) throws JSONException {
                ubOrderDetailBean = result;
                orderIdNew = ubOrderDetailBean.orderId;
                franchiseeId = ubOrderDetailBean.franchiseeId;
                initOrderDetails(result);

                if (ubOrderDetailBean.takeType == 0) {
                    return;
                }


            }
        });

    }

    public void getChangeCode() {
        ubProductService.getChangeCode(orderId, new CommonResultListener<ChangeCodeBean>(UbMyOrderDetailActivity.this) {
            @Override
            public void successHandle(ChangeCodeBean result) throws JSONException {
                if (result == null) {
                    return;
                }
                changeCode = result.authCode;
                tvAlert.setText("请您到取货地址出示兑换码  ：" + changeCode);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //    RequestOrderDetails(orderId);
    }

    private String password = "";
    private void handlerDetail() {
        if (status.equals("P")) {
            tvWaitGet.setText("待兑换");
            tvAlert.setText("等待买家付款，买家将在24小时内兑换，超时订单自动关闭");
            tvOrderStyle.setText("待兑换");
            tvLeft.setText("取消订单");
            tvRight.setText("去兑换");
            tvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCancelOrderDialog(orderIdNew);
                }
            });
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goChange(orderIdNew);
                }
            });
        } else if (status.equals("A")) {
            tvWaitGet.setText("待发货");
            tvOrderStyle.setText("待发货");
            tvAlert.setText("等待卖家发货，卖家将尽快为您发货");
            tvLeft.setVisibility(View.GONE);
            tvRight.setText("取消订单");
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelOrder(orderIdNew);
                }
            });
        } else if (status.equals("S")) {
            tvWaitGet.setText("待收货");
            tvAlert.setText("卖家已发货，7天之后自动确认收货");
            tvOrderStyle.setText("待收货");
            tvLeft.setText("查看物流");
            tvRight.setText("确认收货");
            tvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkMES();
                }
            });
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmGet(orderIdNew);
                }
            });
        } else if (status.equals("G")) {
            getChangeCode();
            tvWaitGet.setText("待自取");
            tvOrderStyle.setText("待自取");
            tvLeft.setVisibility(View.GONE);
            tvRight.setVisibility(View.GONE);
            tvOrderSendTime.setVisibility(View.GONE);
            tvOrderEmsCode.setVisibility(View.GONE);
            setGetDetail();
        } else {
            tvWaitGet.setText("交易成功");
            tvOrderStyle.setText("已完成");
            tvAlert.setText("订单已完成");
            tvLeft.setText("删除订单");
            tvRight.setText("申请售后");
            tvLeft.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteOrder(orderIdNew);
                }
            });
            tvRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle mBundle = new Bundle();
                    mBundle.putInt(IntentExtraKeyConst.SERVICE_FRANCHISEE_ID, franchiseeId);
                    Intent mIntent = new Intent(UbMyOrderDetailActivity.this, SellerServiceActivity.class);
                    mIntent.putExtras(mBundle);
                    startActivity(mIntent);
                }
            });
        }
    }

    @OnClick({R.id.rl_order_head,R.id.tv_left, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_order_head:
                break;
            case R.id.tv_left:
                break;
            case R.id.tv_right:
                break;
        }
    }

    private void initOrderDetails(MyOrderListBean result) {
        List<MyOrderDetailBean> attrList = new ArrayList<>();

        attrList.addAll(result.attrList);
        ItemOrderAdapter adapter = new ItemOrderAdapter(attrList, UbMyOrderDetailActivity.this);
        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        for (MyOrderDetailBean bean : attrList) {
            skuId = bean.productSkuId;
            amount = bean.amount;
        }

        tvMobile.setText(result.receiverMobile);
        tvFranchiseeName.setText(result.receiverName);
        tvAddressDetail.setText(result.detailAdress);
        tvEMS.setText(result.logisticsCost + "");

        tvFranShopName.setText(result.franchiseeName);

        tvProductPri.setText(Math.round(Double.valueOf(result.totalPay))+"");
        tvUCut.setText(result.ubPay + "");
        tvBalanceCut.setText(result.balancePay + "");

        tvOrderCode.setText("订单编号 ：" + result.orderCode + "");
        if ("".equals(result.createTime)) {
            return;
        } else {
            tvOrderTime.setText("提交时间 ：" + result.createTime.substring(0, result.createTime.indexOf(".")));
        }
        if ("".equals(result.payTime)) {
            return;
        } else {
            tvOrderPayTime.setText("付款时间 ：" + result.payTime.substring(0, result.payTime.indexOf(".")));
        }
        if ("".equals(result.sendTime)) {
            return;
        } else {
            tvOrderSendTime.setText("发货时间 ：" + result.sendTime.substring(0, result.sendTime.indexOf(".")));
        }
        if ("".equals(result.logisticsNum)) {
            return;
        } else {
            tvOrderEmsCode.setText("物流单号 ：" + result.logisticsNum);
        }
        if ("".equals(result.totalPay)) {
            return;
        } else {
            tvTotalPrice.setText("合计  " + TextConstant.MONEY + Math.round(Double.valueOf(result.totalPay)) + "");
        }


    }

    //去兑换
    private void goChange(int orderId) {
        final List<Map> productList = new ArrayList<>();
        final Map<String, Integer> map = new HashMap<>();
        if (TextUtils.isEmpty(String.valueOf(franchiseeId))) {
            franchiseeId = 1;
        }
        map.put("franchiseeId", franchiseeId);
        map.put("skuId", skuId);
        map.put("amount", amount);
        productList.add(map);
        final Gson gson = new Gson();
        ubProductService.getOrderPay(orderId, new CommonResultListener<JSONObject>(this) {
            @Override
            public void successHandle(JSONObject result) throws JSONException {
                String dataString = result.getString("data");
                Intent intent = new Intent(UbMyOrderDetailActivity.this, UbConfirmPayActivity.class);
                TypeToken<PayBean> typeToken = new TypeToken<PayBean>() {};
                intent.putExtra(IntentExtraKeyConst.JSON_ORDER, dataString);
                PayBean  bean = gson.fromJson(dataString, typeToken.getType());
                if (bean != null){
                    intent.putExtra(IntentExtraKeyConst.PAY_MONEY, String.valueOf(bean.getTotalPay()));
                }
                startActivity(intent);
            }
        });
    }

    private void setGetDetail() {}

    //查看物流
    private void checkMES() {
        Intent mIntent = new Intent(UbMyOrderDetailActivity.this, ExpressQueryActivity.class);
        startActivity(mIntent);
    }


    private Dialog dialog;
    private void showReceiptDialog(final int code) {
        dialog = new Dialog(this);
        dialog.show();
        View view = View.inflate(getApplicationContext(), R.layout.activity_pay_enter_password, null);
        final GridPasswordView psdView = (GridPasswordView) view.findViewById(R.id.gps_view);
        View closeView = view.findViewById(R.id.iv_close);
        View button = view.findViewById(R.id.btn_submit);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setContentView(view);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth());
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(R.color.transparent);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = psdView.getPassWord();
                if (password.length() < 6) {
                    showToast("请输入6位支付密码");
                    return;
                }
                confirmationOfReceipt(code,password);
                dialog.dismiss();
                Log.i("收货结果=======",code+"");
            }
        });
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void confirmationOfReceipt(final int code,String password){
        ubProductService.postConfirmReceive(code,password,new CommonResultListener(this) {
            @Override
            public void successHandle(Object result) throws JSONException {
//                startActivity(UbProductActivity.class);
//                Intent intent=new Intent();
//                intent.setClass(UbMyOrderDetailActivity.this, UbProductActivity.class);
//                setResult(1,intent);
                ActivityActionHelper.goToMain(UbMyOrderDetailActivity.this);
//                finish();
                showToast("确认收货成功");
            }
        });
    }

    //确认收货
    private void confirmGet(int orderId) {
        showReceiptDialog(orderId);
//        UbPayOrderUnite ubPayOrderUnite = new UbPayOrderUnite(this);
//        ubPayOrderUnite.toReceipt(orderId);
//        ubProductService.postConfirmReceive(orderId,password,new CommonResultListener(this) {
//            @Override
//            public void successHandle(Object result) throws JSONException {
//                Log.i("收货结果=======",result.toString());
//                showToast("OK");
//            }
//        });
    }

    //取消订单
    private void cancelOrder(int orderId) {
        ubProductService.getCanselOrder(orderId, new CommonResultListener(this) {
            @Override
            public void successHandle(Object result) throws JSONException {
                showToast("取消订单成功");
                ActivityActionHelper.goToMain(UbMyOrderDetailActivity.this);
//                finish();
            }
        });
    }

    //删除订单
    private void deleteOrder(int orderId) {
        ubProductService.getDeleteOrder(orderId, new CommonResultListener(this) {
            @Override
            public void successHandle(Object result) throws JSONException {
                showToast("删除订单成功");
                ActivityActionHelper.goToMain(UbMyOrderDetailActivity.this);
//                finish();
            }
        });
    }

    private void showCancelOrderDialog(final int orderId) {
        new MyDialog(UbMyOrderDetailActivity.this, MyDialog.DIALOG_TWOOPTION, "温馨提示", "您确定要取消订单吗?", new MyDialogOnClick() {
            @Override
            public void sureOnClick(View v) {
                cancelOrder(orderId);
            }

            @Override
            public void cancelOnClick(View v) {
            }
        }).show();
    }

    public class ItemOrderAdapter extends BaseAdapter {
        private Context context;
        private List<MyOrderDetailBean> itemList;

        public ItemOrderAdapter(List<MyOrderDetailBean> itemList, Context context) {
            this.context = context;
            this.itemList = itemList;
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ItemHolder itemHolder;
            if (convertView == null) {
                itemHolder = new ItemHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.ub_item_myorder_product, parent,false);
                itemHolder.iv_image = (ImageView) convertView.findViewById(R.id.iv_product_image);
                itemHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_product_name);
                itemHolder.tv_num = (TextView) convertView.findViewById(R.id.tv_product_num);
                itemHolder.tv_prop = (TextView) convertView.findViewById(R.id.tv_product_prop);
                itemHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_product_price);
                convertView.setTag(itemHolder);
            } else {
                itemHolder = (ItemHolder) convertView.getTag();
            }
            final MyOrderDetailBean detailBean = itemList.get(position);
            itemHolder.tv_name.setText(detailBean.productName);
            Tools.ImageLoaderShow(UbMyOrderDetailActivity.this, BASE_IMAGE_URL + detailBean.productIcon, itemHolder.iv_image);
            itemHolder.tv_price.setText(Math.round(detailBean.unitPrice)+"");
            itemHolder.tv_prop.setText(detailBean.productSkuName);
            itemHolder.tv_num.setText(TextConstant.MULTIPLY + detailBean.amount + "");
            return convertView;
        }

        class ItemHolder {
            ImageView iv_image;
            TextView tv_name;
            TextView tv_prop;
            TextView tv_num;
            TextView tv_price;
        }
    }
}
