package com.ninetop.activity.ub.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninetop.UB.UbAddressBean;
import com.ninetop.UB.order.UbConfirmOrderAddressChangeBean;
import com.ninetop.UB.product.UbOrder.UbMainOrderBean;
import com.ninetop.UB.product.UbOrder.UbOrderBean;
import com.ninetop.UB.product.UbOrder.UbPreOrderBean;
import com.ninetop.UB.product.UbProductService;
import com.ninetop.activity.user.AddressManagerActivity;
import com.ninetop.base.BaseActivity;
import com.ninetop.base.GlobalInfo;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.LoginAction;
import com.ninetop.common.constant.TextConstant;
import com.ninetop.common.util.FormatUtil;
import com.ninetop.common.util.Tools;
import com.ninetop.common.view.HeadView;
import com.ninetop.common.view.ScrollViewWithListView;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

import static com.ninetop.config.AppConfig.BASE_IMAGE_URL;

/**
 * Created by huangjinding on 2017/6/15.
 */

public class UbConfirmOrderActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.rl_home)
    RelativeLayout rlHome;
    @BindView(R.id.tv_shopper)
    TextView tvShopper;
    @BindView(R.id.iv_shopper)
    ImageView ivShopper;
    @BindView(R.id.rl_shopper)
    RelativeLayout rlShopper;
    @BindView(R.id.tv_address_style)
    TextView tvAddressStyle;
    @BindView(R.id.tv_address_name)
    TextView tvAddressName;
    @BindView(R.id.tv_address_mobile)
    TextView tvAddressMobile;
    @BindView(R.id.tv_address_detail)
    TextView tvAddressDetail;
    @BindView(R.id.rl_change_address)
    RelativeLayout rlChangeAddress;
    @BindView(R.id.tv_ems)
    TextView tvEms;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_u_cut)
    TextView tvUCut;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.tv_need_pay)
    TextView tvNeedPay;
    @BindView(R.id.lv_product_list)
    ScrollViewWithListView lvProductList;
    @BindView(R.id.tv_chiness_name)
    TextView tvSellerName;
    @BindView(R.id.tv_address_add)
    TextView tvAddressAdd;
    @BindView(R.id.ll_user_info)
    LinearLayout llUserInfo;
    @BindView(R.id.pay_money)
    TextView pay_money;

    private static final int SELECT_ADDRESS = 1;
    private List<UbOrderBean> dataList;


    private UbPreOrderBean ubBean;
    private String addressId;
    private String orderFrom;
    private String amount;
    private String skuId;
    //    private String shopcartId;
    private String sellerName;
    private int takeType = 0; //0:快递;1:自提
    private UbProductService ubProductService;

    private List<Map> shopCartList;

    private List<Map> productList;
    private int frieeID;

    @Override
    protected int getLayoutId() {
        return R.layout.ub_shoppcar_querendingdan;
    }

    @Override
    protected void initView() {
        super.initView();
        ubProductService = new UbProductService(this);
        orderFrom = getIntentValue(IntentExtraKeyConst.ORDER_FROM);
        if (orderFrom == null) {
            return;
        }

        String productJsonData = getIntentValue(IntentExtraKeyConst.PRODUCT_LIST);
        Gson typeGSon = new Gson();
        TypeToken<List<Map>> productType = new TypeToken<List<Map>>() {
        };
        productList = typeGSon.fromJson(productJsonData, productType.getType());

        if ("cart".equals(orderFrom)) {
            String cartJsonData = getIntentValue(IntentExtraKeyConst.SHOPCART_LIST);
            Gson gson = new Gson();
            TypeToken<List<Map>> typeToken = new TypeToken<List<Map>>() {
            };
            shopCartList = gson.fromJson(cartJsonData, typeToken.getType());
        } else if ("buy".equals(orderFrom)) {
            amount = getIntentValue(IntentExtraKeyConst.ORDER_AMOUNT);
            skuId = getIntentValue(IntentExtraKeyConst.ORDER_SKUID);

        }

        String jsonData = getIntentValue(IntentExtraKeyConst.JSON_DATA);
        if (jsonData == null || jsonData.length() == 0) {
            return;
        }
        Gson gson = new Gson();
        TypeToken<UbPreOrderBean> typeToken = new TypeToken<UbPreOrderBean>() {
        };
        ubBean = gson.fromJson(jsonData, typeToken.getType());
        if (ubBean != null && ubBean.addressOwner != null && ubBean.addressOwner.length() > 0) {
            tvAddressAdd.setVisibility(View.GONE);
            llUserInfo.setVisibility(View.VISIBLE);
            tvAddressName.setText(ubBean.addressOwner);
            tvAddressMobile.setText(ubBean.mobile);
            tvAddressDetail.setText(ubBean.detailAddress);
            addressId = ubBean.addressId + "";
        } else {
            tvAddressAdd.setVisibility(View.VISIBLE);
            llUserInfo.setVisibility(View.GONE);
        }

        List<UbMainOrderBean> mainList = ubBean.franchiseeList;

        for (int i = 0; i < mainList.size(); i++) {
            dataList = mainList.get(i).attrList;
            sellerName = mainList.get(i).franchiseeName;
            frieeID = mainList.get(i).franchiseeId;
        }
        if (dataList != null) {
            MainAdapter adapter = new MainAdapter(this, dataList);
            lvProductList.setAdapter(adapter);

        }

        tvSellerName.setText(sellerName);
        setPriceTextValue(true);

        selectTag(0);
        hvHead.setTitle("确认下单");
    }

    @SuppressLint("SetTextI18n")
    private void setPriceTextValue(boolean home) {
        double logisticsCost = getDoubleValue(ubBean.logisticsCost);
        double totalProductPay = getDoubleValue(ubBean.totalProductPay);
        double ubPay = getDoubleValue(ubBean.ubPay);
        double balancePay = getDoubleValue(ubBean.balancePay);
        double moneyPay = getDoubleValue(ubBean.moneyPay);
        if (!home) {
            logisticsCost = 0;
            balancePay = getBalancePayForSelf(ubBean);
            moneyPay = getMoneyPayForSelf(ubBean);
        }

        tvEms.setText(FormatUtil.formatMoney(logisticsCost));
        tvTotalPrice.setText(String.valueOf(totalProductPay));
        tvUCut.setText(String.valueOf(ubPay).substring(0, String.valueOf(ubPay).indexOf(".")));
        tvBalance.setText(FormatUtil.formatMoney(balancePay));
        tvNeedPay.setText(String.valueOf(totalProductPay));
    }

    private void getOrderInfo() {
        int receiverId = getIntValue(addressId);
        ubProductService.postOrderConfirm(0, 0, receiverId, "", productList, new CommonResultListener<UbPreOrderBean>(this) {
            @Override
            public void successHandle(UbPreOrderBean result) throws JSONException {
                ubBean = result;
                setPriceTextValue(true);
            }
        });
    }

    @OnClick({R.id.rl_home, R.id.rl_shopper, R.id.rl_change_address, R.id.btn_confirm_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_home:
                selectTag(0);
                break;
            case R.id.rl_shopper:
                selectTag(1);
                break;
            case R.id.rl_change_address:
                Intent intent = new Intent(this, AddressManagerActivity.class);
                intent.putExtra(IntentExtraKeyConst.CAN_SELECT, "1");
                startActivityForResult(intent, SELECT_ADDRESS);
                break;
            case R.id.btn_confirm_pay:
                orderConfirm();
                break;
        }
    }

    private void selectTag(int index) {
        rlHome.setBackgroundResource(R.drawable.bg_border_gray);
        tvHome.setTextColor(getResources().getColor(R.color.text_gray));
        ivHome.setVisibility(View.INVISIBLE);
        rlShopper.setBackgroundResource(R.drawable.bg_border_gray);
        tvShopper.setTextColor(getResources().getColor(R.color.text_gray));
        ivShopper.setVisibility(View.INVISIBLE);

        if (index == 0) {
            rlHome.setBackgroundResource(R.drawable.bg_border_red);
            tvHome.setTextColor(getResources().getColor(R.color.text_red));
            ivHome.setVisibility(View.VISIBLE);
            tvAddressStyle.setText("收货地址:");
            selectAddressInfo();
            rlChangeAddress.setEnabled(true);
            takeType = 0;
            setPriceTextValue(true);
            pay_money.setVisibility(View.VISIBLE);
        } else if (index == 1) {
            rlShopper.setBackgroundResource(R.drawable.bg_border_red);
            tvShopper.setTextColor(getResources().getColor(R.color.text_red));
            ivShopper.setVisibility(View.VISIBLE);
            tvAddressStyle.setText("兑换地址");
            //选择失败
            setPriceTextValue(false);
            rlChangeAddress.setEnabled(false);
            changeAddressInfo();
            takeType = 1;
            pay_money.setVisibility(View.INVISIBLE);
        }
    }

    //到店自取需要第三方支付的金额
    private double getMoneyPayForSelf(UbPreOrderBean ubBean) {
        double moneyPay = getDoubleValue(ubBean.moneyPay);
        double logisticsCost = getDoubleValue(ubBean.logisticsCost);

        if (moneyPay > logisticsCost) {
            return moneyPay - logisticsCost;
        }

        return 0;
    }

    //到店自取需要余额支付的金额
    private double getBalancePayForSelf(UbPreOrderBean ubBean) {
        double moneyPay = getDoubleValue(ubBean.moneyPay);
        double balancePay = getDoubleValue(ubBean.balancePay);
        double logisticsCost = getDoubleValue(ubBean.logisticsCost);

        if (moneyPay > logisticsCost) {
            return balancePay;
        } else if (moneyPay + balancePay > logisticsCost) {
            return moneyPay + balancePay - logisticsCost;
        } else {
            return 0;
        }
    }

    private void orderConfirm() {
        if (takeType == 0 && (addressId == null || addressId.trim().length() == 0)) {
            showToast("请选择地址");
            return;
        }
        if (takeType != 1 && takeType != 0) {
            showToast("请选择取货方式");
            return;
        }
        //0表示立即兑换      1表示购物车
        if (takeType == 1) {
            addressId = "0";
        }
        int addressNewId = Integer.parseInt(addressId);
        if ("buy".equals(orderFrom)) {
            Map<String, String> map = new HashMap<>();
            map.put("amount", amount);
            map.put("skuId", skuId);
            String franchiseeId = GlobalInfo.franchiseeId;
            if (TextUtils.isEmpty(franchiseeId)) {
                franchiseeId = "1";
            }
            map.put("franchiseeId", franchiseeId);
            List<Map> productList = new ArrayList<>();
            productList.add(map);
            buyNowSubmit(addressNewId, productList);
        } else if ("cart".equals(orderFrom)) {
            cartOrderSubmit(addressNewId, shopCartList);
        }
    }

    //正常下单
    private void buyNowSubmit(int addressNewId, List<Map> productList) {
        //获取为空1.94，快递
        if (takeType == 0) {
            LoginAction.saveGetStyle("0", UbConfirmOrderActivity.this);
            ubProductService.postConfirmOrder(0, 0, addressNewId, "", productList, new CommonResultListener<JSONObject>(this) {
                @Override
                public void successHandle(JSONObject result) throws JSONException {
                    String dataString = result.getString("data");
                    Log.i("获取订单的数据=",dataString);
                    Intent intent = new Intent(UbConfirmOrderActivity.this, UbConfirmPayActivity.class);
                    intent.putExtra(IntentExtraKeyConst.JSON_ORDER, dataString);
                    intent.putExtra(IntentExtraKeyConst.GET_STYLE, "0");
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            //自取1
            LoginAction.saveGetStyle("1", UbConfirmOrderActivity.this);
            ubProductService.postConfirmOrder(0, 1, addressNewId, "", productList, new CommonResultListener<JSONObject>(this) {
                @Override
                public void successHandle(JSONObject result) throws JSONException {
                    String dataString = result.getString("data");
                    Log.i("获取订单的数据==",dataString);
                    Intent intent = new Intent(UbConfirmOrderActivity.this, UbConfirmPayActivity.class);
                    intent.putExtra(IntentExtraKeyConst.JSON_ORDER, dataString);
                    intent.putExtra(IntentExtraKeyConst.GET_STYLE, "1");
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    //购物车订单
    private void cartOrderSubmit(int addressNewId, List<Map> shopCartList) {
        //1为购物车，
        if (takeType == 0) {
            LoginAction.saveGetStyle("0", UbConfirmOrderActivity.this);
            ubProductService.postConfirmOrder(1, 0, addressNewId, "", shopCartList, new CommonResultListener<JSONObject>(this) {
                @Override
                public void successHandle(JSONObject result) throws JSONException {
                    String dataString = result.getString("data");
                    Log.i("获取订单的数据==",dataString);
                    Intent intent = new Intent(UbConfirmOrderActivity.this, UbConfirmPayActivity.class);
                    intent.putExtra(IntentExtraKeyConst.JSON_ORDER, dataString);
                    intent.putExtra(IntentExtraKeyConst.GET_STYLE, "0");
                    startActivity(intent);
                }
            });
        } else {
            LoginAction.saveGetStyle("1", UbConfirmOrderActivity.this);
            ubProductService.postConfirmOrder(1, 1, addressNewId, "", shopCartList, new CommonResultListener<JSONObject>(this) {
                @Override
                public void successHandle(JSONObject result) throws JSONException {
                    String dataString = result.getString("data");
                    Log.i("获取订单的数据=",dataString);
                    Intent intent = new Intent(UbConfirmOrderActivity.this, UbConfirmPayActivity.class);
                    intent.putExtra(IntentExtraKeyConst.JSON_ORDER, dataString);
                    intent.putExtra(IntentExtraKeyConst.GET_STYLE, "1");
                    startActivity(intent);
                }
            });
        }
    }

    //提货地址修改
    private void changeAddressInfo() {
        ubProductService.postFrnchiseeInfo(String.valueOf(frieeID),new CommonResultListener<UbConfirmOrderAddressChangeBean>(this) {
            @Override
            public void successHandle(UbConfirmOrderAddressChangeBean result) throws JSONException {
                Log.i("获取的自取地址=",result.toString());
                tvAddressAdd.setVisibility(View.GONE);
                llUserInfo.setVisibility(View.VISIBLE);
                tvAddressName.setText(result.name);
                tvAddressMobile.setText(result.mobile);
                tvAddressDetail.setText(result.detail_address);
            }
        });
    }

    //快递到家地址修改
    private void selectAddressInfo() {
        tvAddressName.setText(ubBean.addressOwner);
        tvAddressMobile.setText(ubBean.mobile);
        tvAddressDetail.setText(ubBean.detailAddress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_ADDRESS:
                    addressSelectChange(data);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addressSelectChange(Intent data) {
        tvAddressAdd.setVisibility(View.GONE);
        llUserInfo.setVisibility(View.VISIBLE);
        String result = data.getStringExtra(IntentExtraKeyConst.JSON_DATA);
        Gson gson = new Gson();
        Type typeToken = new TypeToken<UbAddressBean>() {
        }.getType();
        UbAddressBean ubAddressBean = gson.fromJson(result, typeToken);
        addressId = ubAddressBean.getId();
        tvAddressName.setText(ubAddressBean.name);
        tvAddressMobile.setText(ubAddressBean.mobile);
        tvAddressDetail.setText(ubAddressBean.addr_detail);

        getOrderInfo();
    }

    private class MainAdapter extends BaseAdapter {
        UbConfirmOrderActivity activity;
        List<UbOrderBean> dataList;
        public MainAdapter(UbConfirmOrderActivity activity, List<UbOrderBean> dataList) {
            this.activity = activity;
            this.dataList = dataList;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            HolderView holderView;
            if (convertView == null) {
                holderView = new HolderView();
                convertView = LayoutInflater.from(activity).inflate(R.layout.ub_item_order, parent,false);
                holderView.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                holderView.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
                holderView.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
                holderView.tv_prop = (TextView) convertView.findViewById(R.id.tv_prop);
                holderView.iv_image_product = (ImageView) convertView.findViewById(R.id.iv_product_image);
                convertView.setTag(holderView);
            } else {
                holderView = (HolderView) convertView.getTag();
            }
            final UbOrderBean bean = dataList.get(position);
            holderView.tv_name.setText(bean.productName);
            holderView.tv_number.setText(TextConstant.MULTIPLY + bean.amount);
            holderView.tv_prop.setText(bean.getProductSkuName() + "");
            holderView.tv_price.setText(Tools.saveNum(Double.valueOf(bean.getUnitPrice())));
            Tools.ImageLoaderShow(activity, BASE_IMAGE_URL + bean.getProductIcon(), holderView.iv_image_product);
            return convertView;
        }

        class HolderView {
            TextView tv_name;
            TextView tv_prop;
            TextView tv_price;
            TextView tv_number;
            ImageView iv_image_product;
        }
    }

}
