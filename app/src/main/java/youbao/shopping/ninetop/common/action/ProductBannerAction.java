//package com.ninetop.common.action;
//
//import android.content.Context;
//import android.content.Intent;
//
//import com.ninetop.activity.product.ProductDetailActivity;
//import com.ninetop.bean.index.BannerBean;
//import com.ninetop.common.IntentExtraKeyConst;
//
///**
// * Created by jill on 2016/12/29.
// */
//
//public class ProductBannerAction implements BannerAction{
//
//    @Override
//    public void action(Context context, BannerBean content) {
//        Intent intent  = new Intent(context, ProductDetailActivity.class);
//        intent.putExtra(IntentExtraKeyConst.PRODUCT_CODE, content.getTitle());
//        context.startActivity(intent);
//    }
//}
