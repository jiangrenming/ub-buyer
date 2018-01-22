package com.ninetop.activity.order;

import android.content.Intent;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ninetop.adatper.product.OrderCouponAdapter;
import com.ninetop.adatper.product.OrderCouponInvalidAdapter;
import com.ninetop.base.BaseActivity;
import com.ninetop.bean.user.CouponBean;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.view.HeadView;
import com.ninetop.common.view.SelectChangedListener;
import com.ninetop.service.impl.ProductService;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import youbao.shopping.R;

/**
 * Created by jill on 2016/11/16.
 */

public class OrderCouponSelectorActivity extends BaseActivity {
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.lv_coupon_valid)
    ListView lvCouponValid;

    @BindView(R.id.lv_coupon_invalid)
    ListView lvCouponInvalid;

    private OrderCouponAdapter couponValidAdapter;

    private OrderCouponInvalidAdapter couponInvalidAdapter;

    private ProductService productService;

    private String selectCoupon = "";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_coupon_selector;
    }

    @Override
    protected void initData() {
        super.initData();

        hvHead.setTitle("选择优惠券");

        String skuInfo = getIntentValue(IntentExtraKeyConst.ORDER_SKU_INFO);
        selectCoupon = getIntentValue(IntentExtraKeyConst.ORDER_COUPON);
        if(skuInfo == null || skuInfo.length() == 0)
            return;

        productService = new ProductService(this);

        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> skuIdMap = gson.fromJson(skuInfo, type);

        productService.getOrderCouponList(skuIdMap, new CommonResultListener<List<CouponBean>>(this) {
            @Override
            public void successHandle(List<CouponBean> result) throws JSONException {
                if(result == null || result.size() == 0)
                    return;

                List<CouponBean> validList = new ArrayList<>();
                List<CouponBean> invalidList = new ArrayList<>();
                for(CouponBean bean : result){
                    if("0".equals(bean.isAvailable)){
                        invalidList.add(bean);
                    }else{
                        validList.add(bean);
                    }
                }

                if(validList.size() > 0) {
                    couponValidAdapter = new OrderCouponAdapter(OrderCouponSelectorActivity.this, validList);

                    if(selectCoupon != null && selectCoupon.length() > 0){
                        for(CouponBean bean : validList){
                            if(selectCoupon.equals(bean.voucherId + "")){
                                couponValidAdapter.setSelectCoupon(bean);
                                break;
                            }
                        }
                    }

                    lvCouponValid.setAdapter(couponValidAdapter);
                    couponValidAdapter.setSelectChangeListener(new SelectChangedListener<CouponBean>() {
                        @Override
                        public void changeHandle(CouponBean value) {
                            Intent intent = new Intent();
                            Gson gson = new Gson();
                            intent.putExtra(IntentExtraKeyConst.JSON_DATA, gson.toJson(value));
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                }

                if(invalidList.size() > 0) {
                    couponInvalidAdapter = new OrderCouponInvalidAdapter(OrderCouponSelectorActivity.this, invalidList);
                    lvCouponInvalid.setAdapter(couponInvalidAdapter);
                }
            }
        });
    }
}
