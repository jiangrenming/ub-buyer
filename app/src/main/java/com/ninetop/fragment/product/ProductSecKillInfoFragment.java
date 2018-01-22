package com.ninetop.fragment.product;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.ninetop.activity.order.OrderSecKillConfirmActivity;
import com.ninetop.base.BaseActivity;
import com.ninetop.base.Viewable;
import com.ninetop.bean.product.ProductSaleDetailBean;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import youbao.shopping.R;

/**
 * Created by jill on 2017/1/6.
 */

public class ProductSecKillInfoFragment extends ProductInfoFragment {

    @BindView(R.id.iv_seckill_price)
    ImageView ivSalePrice;

    private String productSaleID;

    @Override
    protected void initView() {
        productSaleID = ((BaseActivity)getContext()).getIntentValue(IntentExtraKeyConst.PRODUCT_SECKILL_ID);

        super.initView();

        rlSelectProp.setVisibility(View.GONE);
        rlSelectCoupon.setVisibility(View.GONE);
        ivSalePrice.setVisibility(View.VISIBLE);
    }

    protected void getProductDetail(String productCode){
        if(productSaleID == null || productSaleID.length() == 0)
            return;

        productService.getProductSeckillDetail(productSaleID, new CommonResultListener<ProductSaleDetailBean>((Viewable) getContext()) {
            @Override
            public void successHandle(ProductSaleDetailBean result) throws JSONException {
                if(result == null)
                    return;

                onProductDetailHandle(result);
            }
        });
    }

    public void buyNow(){
        final int amount = 1;
        productService.buyNowProductSeckill(productSaleID, amount, new CommonResultListener<JSONObject>((Viewable) getContext()) {
            @Override
            public void successHandle(JSONObject result) throws JSONException {
                String dataString = result.getString("data");

                Intent intent = new Intent(getContext(), OrderSecKillConfirmActivity.class);
                intent.putExtra(IntentExtraKeyConst.JSON_DATA, dataString);
                intent.putExtra(IntentExtraKeyConst.ORDER_FROM, "buy");
                intent.putExtra(IntentExtraKeyConst.PRODUCT_AMOUNT, amount + "");
                intent.putExtra(IntentExtraKeyConst.PRODUCT_SECKILL_ID, productSaleID);
                startActivity(intent);
            }
        });
    }
}
