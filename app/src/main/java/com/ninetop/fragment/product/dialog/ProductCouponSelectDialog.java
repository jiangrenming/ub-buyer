package com.ninetop.fragment.product.dialog;

import android.app.AlertDialog;
import android.view.View;
import android.widget.ListView;

import com.ninetop.activity.product.ProductDetailActivity;
import com.ninetop.adatper.product.ProductCouponAdapter;
import com.ninetop.bean.product.ProductDetailBean;
import com.ninetop.bean.user.CouponBean;
import com.ninetop.common.util.DialogUtil;
import com.ninetop.fragment.product.ProductInfoFragment;
import com.ninetop.service.impl.ProductService;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.List;

import youbao.shopping.R;

/**
 * Created by jill on 2016/12/2.
 */

public class ProductCouponSelectDialog {

    private ProductDetailActivity activity;
    private ProductInfoFragment productInfoFragment;
    private ProductDetailBean productDetailBean;
    private ProductService productService;

    private AlertDialog targetDialog;

    public ProductCouponSelectDialog(ProductDetailActivity activity, ProductInfoFragment productInfoFragment,
                                     ProductDetailBean productDetailBean, ProductService productService) {
        this.activity = activity;
        this.productDetailBean = productDetailBean;
        this.productInfoFragment = productInfoFragment;
        this.productService = productService;
    }

    public void showDialog() {
        View view = View.inflate(activity.getApplicationContext(),
                R.layout.activity_product_coupon_selector, null);
        targetDialog = DialogUtil.createDialogBottom(activity, view);

        final ListView lv_coupon_list = (ListView)view.findViewById(R.id.lv_coupon_list);

        productService.getProductCouponList(productDetailBean.code, new CommonResultListener<List<CouponBean>>(activity) {
            @Override
            public void successHandle(List<CouponBean> result) throws JSONException {
                if(result == null || result.size() == 0){
                    return;
                }

                ProductCouponAdapter productCouponAdapter = new ProductCouponAdapter(activity, result);
                lv_coupon_list.setAdapter(productCouponAdapter);

            }
        });


        View submitView = view.findViewById(R.id.btn_submit);
        submitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetDialog.dismiss();
            }
        });
    }
}
