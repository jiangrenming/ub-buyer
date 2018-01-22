package com.ninetop.activity.product;

import com.ninetop.fragment.product.ProductFragmentPagerAdapter;
import com.ninetop.fragment.product.ProductSecKillFragmentPagerAdapter;

/**
 * Created by jill on 2017/1/6.
 */

public class ProductSaleDetailActivity extends ProductDetailActivity {

    @Override
    protected void initView() {
        super.initView();
//        llAddShopcart.setVisibility(View.GONE);
    }

    protected ProductFragmentPagerAdapter createPagerAdapter() {
        return new ProductSecKillFragmentPagerAdapter(getSupportFragmentManager());
    }
}
