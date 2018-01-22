package com.ninetop.common.action;

import android.content.Context;
import android.content.Intent;

import com.ninetop.activity.index.SaleActivity;
import com.ninetop.bean.index.BannerBean;

/**
 * Created by jill on 2016/12/29.
 */

public class ProductSaleAction implements BannerAction{

    @Override
    public void action(Context context, BannerBean content) {
        Intent intent  = new Intent(context, SaleActivity.class);
        context.startActivity(intent);
    }
}
