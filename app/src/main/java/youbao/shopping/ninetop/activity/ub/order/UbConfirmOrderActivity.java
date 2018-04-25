package youbao.shopping.ninetop.activity.ub.order;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import youbao.shopping.ninetop.UB.UbAddressBean;
import youbao.shopping.ninetop.UB.order.UbConfirmOrderAddressChangeBean;
import youbao.shopping.ninetop.UB.product.UbOrder.UbMainOrderBean;
import youbao.shopping.ninetop.UB.product.UbOrder.UbOrderBean;
import youbao.shopping.ninetop.UB.product.UbOrder.UbPreOrderBean;
import youbao.shopping.ninetop.UB.product.UbProductService;
import youbao.shopping.ninetop.activity.ub.product.UbProductActivity;
import youbao.shopping.ninetop.activity.user.AddressManagerActivity;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.base.GlobalInfo;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.LoginAction;
import youbao.shopping.ninetop.common.constant.TextConstant;
import youbao.shopping.ninetop.common.util.FormatUtil;
import youbao.shopping.ninetop.common.util.Tools;
import youbao.shopping.ninetop.common.util.UbComfirmDialog;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.common.view.ScrollViewWithListView;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

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

import static youbao.shopping.ninetop.config.AppConfig.BASE_IMAGE_URL;

/**
 * Created by huangjinding on 2017/6/15.
 */

public class UbConfirmOrderActivity extends BaseActivity implements UbComfirmDialog.skipAddress{
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
  /*  @BindView(R.id.tv_chiness_name)
    TextView tvSellerName;*/
    @BindView(R.id.tv_address_add)
    TextView tvAddressAdd;
    @BindView(R.id.ll_user_info)
    LinearLayout llUserInfo;
    @BindView(R.id.pay_money)
    TextView pay_money;

    private static final int SELECT_ADDRESS = 1;
    private UbPreOrderBean ubBean;
    private String addressId;
    private String orderFrom;
    private String amount;
    private String skuId;
    private int takeType = 0; //0:快递;1:自提
    private UbProductService ubProductService;

    private List<Map> shopCartList;
    List<UbMainOrderBean> mainList ;
    private List<Map> productList;
    private String detailsFrieeID;

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
        Gson gson = new Gson();
        if ("cart".equals(orderFrom)) {
            String cartJsonData = getIntentValue(IntentExtraKeyConst.SHOPCART_LIST);
            TypeToken<List<Map>> typeToken = new TypeToken<List<Map>>() {};
            shopCartList = gson.fromJson(cartJsonData, typeToken.getType());
        } else if ("buy".equals(orderFrom)) {
            amount = getIntentValue(IntentExtraKeyConst.ORDER_AMOUNT);
            skuId = getIntentValue(IntentExtraKeyConst.ORDER_SKUID);
            detailsFrieeID = getIntentValue(IntentExtraKeyConst.FRANCHISEEID);
            String productJsonData = getIntentValue(IntentExtraKeyConst.PRODUCT_LIST);
            TypeToken<List<Map>> productType = new TypeToken<List<Map>>() {};
            productList = gson.fromJson(productJsonData, productType.getType());
        }
        String jsonData = getIntentValue(IntentExtraKeyConst.JSON_DATA);
        if (jsonData == null || jsonData.length() == 0) {
            return;
        }
        TypeToken<UbPreOrderBean> typeToken = new TypeToken<UbPreOrderBean>() {};
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
       mainList = ubBean.franchiseeList;
        if (mainList != null &&mainList.size() > 0){
            MainAdapter adapter = new MainAdapter(this, mainList);
            lvProductList.setAdapter(adapter);
        }
        setPriceTextValue(true);
        selectTag(0);
        hvHead.setTitle("确认下单");
    }

    @Override
    public void changeAddress() {
        selectTag(0);
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

        tvEms.setText(Math.round(Double.valueOf(logisticsCost))+"");
        tvTotalPrice.setText(Math.round(Double.valueOf(totalProductPay))+"");
        tvUCut.setText(Math.round(Double.valueOf(ubPay))+"");
        tvBalance.setText(Math.round(Double.valueOf(balancePay))+"");
        tvNeedPay.setText(Math.round(Double.valueOf(totalProductPay))+"");
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

    private UbComfirmDialog dialog ;
    @OnClick({R.id.rl_home, R.id.rl_shopper, R.id.rl_change_address, R.id.btn_confirm_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_home:
                selectTag(0);
                break;
            case R.id.rl_shopper:
                selectTag(1);
                if (mainList != null && mainList.size() > 1){
                    dialog = new UbComfirmDialog(UbConfirmOrderActivity.this);
                    dialog.setSkipAddress(this);
                    dialog.show();
                }
                break;
            case R.id.rl_change_address:
                Intent intent = new Intent(this, AddressManagerActivity.class);
                intent.putExtra(IntentExtraKeyConst.CAN_SELECT, "1");
                startActivityForResult(intent, SELECT_ADDRESS);
                break;
            case R.id.btn_confirm_pay:
                orderConfirm();
                break;
            default:
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
            if (TextUtils.isEmpty(detailsFrieeID)) {
                detailsFrieeID = "1";
            }
            map.put("franchiseeId", detailsFrieeID);
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
        List<UbMainOrderBean> franchiseeList = ubBean.franchiseeList;
        String frieeID = null;
        if (franchiseeList.size() >  0){
            for (int i = 0 ;i < franchiseeList.size() ;i++){
                 frieeID = String.valueOf(franchiseeList.get(i).franchiseeId);
            }
        }
        Log.i("自体的地址：",frieeID);
        ubProductService.postFrnchiseeInfo(frieeID,new CommonResultListener<UbConfirmOrderAddressChangeBean>(this) {
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
        List<UbMainOrderBean> mainList;
        public MainAdapter(UbConfirmOrderActivity activity, List<UbMainOrderBean> dataList) {
            this.activity = activity;
            this.mainList = dataList;
        }

        @Override
        public int getCount() {
            return mainList.size();
        }

        @Override
        public Object getItem(int position) {
            return mainList.get(position);
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
                holderView.tv_chiness_name = (TextView)convertView.findViewById(R.id.tv_chiness_name);
                holderView.items = (ScrollViewWithListView) convertView.findViewById(R.id.items_);
                convertView.setTag(holderView);
            } else {
                holderView = (HolderView) convertView.getTag();
            }
            UbMainOrderBean ubMainOrderBean = mainList.get(position);
            String franchiseeName = ubMainOrderBean.franchiseeName;
            List<UbOrderBean> attrList = ubMainOrderBean.attrList;
            if (attrList != null && attrList.size() >0 ){
                holderView.tv_chiness_name.setVisibility(View.VISIBLE);
                if (!TextUtils.isEmpty(franchiseeName)){
                    holderView.tv_chiness_name.setText(franchiseeName);
                }
                MainItemsAdapter adapter = new MainItemsAdapter(attrList,activity);
                holderView.items.setAdapter(adapter);
            }else {
                holderView.tv_chiness_name.setVisibility(View.GONE);
            }
            return convertView;
        }
        class HolderView {
            TextView tv_chiness_name;
            ListView items;
        }
    }

    private class MainItemsAdapter extends BaseAdapter{

        private  List<UbOrderBean> datas ;
        Context mContext;
        public MainItemsAdapter( List<UbOrderBean> datas,UbConfirmOrderActivity activity){
            this.datas = datas;
            this.mContext = activity;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyHolderView holderView;
            if (convertView == null) {
                holderView = new MyHolderView();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent,false);
                holderView.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                holderView.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
                holderView.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
                holderView.tv_prop = (TextView) convertView.findViewById(R.id.tv_prop);
                holderView.iv_image_product = (ImageView) convertView.findViewById(R.id.iv_product_image);
                convertView.setTag(holderView);
            } else {
                holderView = (MyHolderView) convertView.getTag();
            }
            UbOrderBean ubOrderBean = datas.get(position);
            holderView.tv_name.setText(ubOrderBean.productName);
            holderView.tv_number.setText(TextConstant.MULTIPLY + ubOrderBean.amount);
            holderView.tv_prop.setText(ubOrderBean.getProductSkuName() + "");
            holderView.tv_price.setText(Math.round((Double.valueOf(ubOrderBean.getUnitPrice())))+"");
            Tools.ImageLoaderShow(mContext, BASE_IMAGE_URL + ubOrderBean.getProductIcon(), holderView.iv_image_product);
            return convertView;
        }
        class MyHolderView {
            TextView tv_name;
            TextView tv_prop;
            TextView tv_price;
            TextView tv_number;
            ImageView iv_image_product;
        }
    }
}

