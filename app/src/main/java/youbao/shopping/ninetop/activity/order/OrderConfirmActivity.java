package youbao.shopping.ninetop.activity.order;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import youbao.shopping.ninetop.activity.order.pay.OrderPayActivity;
import youbao.shopping.ninetop.activity.user.AddressManagerActivity;
import youbao.shopping.ninetop.adatper.product.OrderConfirmAdapter;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.base.Viewable;
import youbao.shopping.ninetop.bean.order.OrderConfirmResponseBean;
import youbao.shopping.ninetop.bean.order.OrderItemBean;
import youbao.shopping.ninetop.bean.order.PreOrderBean;
import youbao.shopping.ninetop.bean.user.AddressBean;
import youbao.shopping.ninetop.bean.user.CouponBean;
import youbao.shopping.ninetop.bean.user.UserInfoBean;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.util.FormatUtil;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.impl.ProductService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;


/**
 * Created by jill on 2016/11/15.
 */

public class OrderConfirmActivity extends BaseActivity {


    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.tv_address_add)
    TextView tvAddressAdd;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_mobile)
    TextView tvUserMobile;
    @BindView(R.id.tv_user_address)
    TextView tvUserAddress;
    @BindView(R.id.rv_user_info)
    RelativeLayout rvUserInfo;
    @BindView(R.id.lv_product_list)
    ListView lvProductList;
    @BindView(R.id.rl_select_coupon)
    View rlSelectCoupon;
    @BindView(R.id.tv_product_number)
    TextView tvProductNumber;
    @BindView(R.id.tv_coupon)
    TextView tvCoupon;
    @BindView(R.id.tv_sum_price)
    TextView tvSumPrice;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;

    protected PreOrderBean preOrderBean;

    protected String addressId;

    protected String voucherId = "";

    protected ProductService productService;

    private String orderFrom;

    private static final int SELECT_ADDRESS  = 1;
    private static final int SELECT_COUPON  = 2;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_confirm;
    }

    @Override
    protected void initData() {
        super.initData();
        hvHead.setTitle("确认订单");

        productService = new ProductService(this);

        orderFrom = getIntentValue(IntentExtraKeyConst.ORDER_FROM);
        if(orderFrom == null)
            return;

        String jsonData = getIntentValue(IntentExtraKeyConst.JSON_DATA);
        if(jsonData == null || jsonData.length() == 0) {
            return;
        }

        Gson gson = new Gson();
        TypeToken<PreOrderBean> typeToken = new TypeToken<PreOrderBean>(){};
        //gson.fromJAson 提供两个参数 json字符串和需转换对象的类型
        preOrderBean = gson.fromJson(jsonData, typeToken.getType());

        UserInfoBean receiver = preOrderBean.receiver;
        if(receiver != null && receiver.name != null && receiver.name.length() > 0) {
            tvAddressAdd.setVisibility(View.GONE);
            rvUserInfo.setVisibility(View.VISIBLE);
            tvUserName.setText(receiver.name);
            tvUserAddress.setText(receiver.province + " " + receiver.city + " " + receiver.district + " " + receiver.detail);
            tvUserMobile.setText(receiver.mobile);
            addressId = receiver.index;
        }else{
            tvAddressAdd.setVisibility(View.VISIBLE);
            rvUserInfo.setVisibility(View.GONE);
        }

        List<OrderItemBean> itemList = preOrderBean.itemList;
        if(itemList != null){
            tvProductNumber.setText("共" + itemList.size() + "件商品");
            String price =Math.round(Double.parseDouble(preOrderBean.total))+"";
            tvSumPrice.setText(price);
            tvTotalPrice.setText(price);

            OrderConfirmAdapter orderConfirmAdapter = new OrderConfirmAdapter(this, itemList);
            lvProductList.setAdapter(orderConfirmAdapter);
        }
    }

    @OnClick({R.id.rv_address, R.id.rl_select_coupon, R.id.tv_submit_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rv_address:
                Intent intent = new Intent(this, AddressManagerActivity.class);
                intent.putExtra(IntentExtraKeyConst.CAN_SELECT, "1");
                startActivityForResult(intent, SELECT_ADDRESS);
                break;
            case R.id.rl_select_coupon:
                Intent countIntent = new Intent(this, OrderCouponSelectorActivity.class);
                countIntent.putExtra(IntentExtraKeyConst.ORDER_SKU_INFO, assembleSkuInfo());
                countIntent.putExtra(IntentExtraKeyConst.ORDER_COUPON, voucherId);
                startActivityForResult(countIntent, SELECT_COUPON);
                break;
            case R.id.tv_submit_order:
                orderPay();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_ADDRESS:
                    addressSelectChange(data);
                    break;
                case SELECT_COUPON:
                    couponSelectChange(data);
                default:
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void orderPay(){
        if(addressId == null || addressId.trim().length() == 0){
            showToast("请选择地址");
            return;
        }

        if(preOrderBean.itemList == null || preOrderBean.itemList.size() == 0)
            return;

        if("buy".equals(orderFrom)){
            buyNowSubmit();
        }else{

            cartOrderSubmit();
        }

    }

    protected void buyNowSubmit(){
        OrderItemBean bean = preOrderBean.itemList.get(0);
        String amount = bean.amount;

        productService.buyNowOrderSubmit(voucherId, bean.skuId, amount, addressId, new OrderConfirmResultListener(this));
    }
    //购物车订单提交
    private void cartOrderSubmit(){
//        List<String> noteList = orderConfirmAdapter.getNoteList();
        String skuIds = "";
        for(int i=0;i<preOrderBean.itemList.size();i++){
//            Map<String, String> map = new HashMap<>();
            OrderItemBean bean = preOrderBean.itemList.get(i);
//            map.put("skuId", bean.skuId);
////            map.put("remark", noteList.get(i));
//
//            itemList.add(map);
            skuIds += bean.skuId + ",";
        }
        skuIds = skuIds.substring(0, skuIds.length() - 1);

        productService.cartOrderSubmit(voucherId, skuIds, addressId, new OrderConfirmResultListener(this));
    }

    private String assembleSkuInfo(){
        if(preOrderBean == null || preOrderBean.itemList == null || preOrderBean.itemList.size() == 0)
            return "";
        Map<String, String> map  = new HashMap<>();
        for(OrderItemBean bean : preOrderBean.itemList){
            map.put(bean.skuId, bean.amount);
        }

        Gson gson = new Gson();
        return gson.toJson(map);
    }

    private void addressSelectChange(Intent data){
        tvAddressAdd.setVisibility(View.GONE);
        rvUserInfo.setVisibility(View.VISIBLE);
        String result = data.getStringExtra(IntentExtraKeyConst.JSON_DATA);
        Gson gson = new Gson();
        Type type = new TypeToken<AddressBean>() {}.getType();
        AddressBean addressBean = gson.fromJson(result, type);
        addressId = addressBean.index + "";
        tvUserName.setText(addressBean.receiver);
        tvUserMobile.setText(addressBean.tel);
        tvUserAddress.setText(addressBean.province + " " + addressBean.city + " " + addressBean.county + " " + addressBean.detail);
    }

    private void couponSelectChange(Intent data){
        String result = data.getStringExtra(IntentExtraKeyConst.JSON_DATA);
        Gson gson = new Gson();
        Type type = new TypeToken<CouponBean>() {}.getType();
        CouponBean couponBean = gson.fromJson(result, type);
        voucherId = couponBean.voucherId + "";
        String faceValue = couponBean.faceValue;
        float face = faceValue != null && faceValue.length() > 0 ? Float.parseFloat(faceValue) : 0;
        tvCoupon.setText("-" + FormatUtil.formatMoney(face));

        changePrice(face);
    }

    private void changePrice(float couponValue){
        if(preOrderBean == null)
            return;

        float price = Float.parseFloat(preOrderBean.total) - couponValue;
        if(price < 0)
            price = 0;

        String priceString = Math.round(price)+"";
        tvTotalPrice.setText(priceString);
        tvSumPrice.setText(priceString);
    }

    class OrderConfirmResultListener extends CommonResultListener<OrderConfirmResponseBean>{

        public OrderConfirmResultListener(Viewable context){
            super(context);
        }

        public void successHandle(OrderConfirmResponseBean result) throws JSONException {
            if(result == null)
                return;

            Map<String, String> map = new HashMap<String, String>();
            map.put(IntentExtraKeyConst.ORDER_NO, result.orderCode);
            map.put(IntentExtraKeyConst.ORDER_TOTAL, result.amount);
            map.put(IntentExtraKeyConst.ORDER_FROM, "buy");
            startActivity(OrderPayActivity.class, map);
            finish();
        }

    }

}
