package com.ninetop.adatper.index;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import youbao.shopping.R;
import com.ninetop.activity.product.ProductDetailActivity;
import com.ninetop.base.BaseActivity;
import com.ninetop.bean.product.ProductBean;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.util.Tools;
import java.util.List;


/**
 * Created by jill on 2016/11/15.
 */

public class IndexRecommendAdapter extends BaseAdapter {

    List<ProductBean> dataList;
    BaseActivity activity;


    public IndexRecommendAdapter(BaseActivity activity, List<ProductBean> dataList) {
        this.dataList = dataList;
        this.activity = activity;
    }

    public void setData(List<ProductBean> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (dataList.size() % 2 == 1) {
            return dataList.size() / 2 + 1;
        } else
            return dataList.size() / 2;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({ "ViewHolder", "InflateParams" })
    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        HolderView holderView;
        final int position1 = index * 2;
        final int position2 = position1 + 1;
        if (convertView == null) {
            holderView = new HolderView();
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_recommend_product, null);
            holderView.iv_logo1 = (ImageView) convertView.findViewById(R.id.iv_logo1);
            holderView.iv_logo2 = (ImageView) convertView.findViewById(R.id.iv_logo2);
            holderView.iv_more1 = (ImageView) convertView.findViewById(R.id.iv_more1);
            holderView.iv_more2 = (ImageView) convertView.findViewById(R.id.iv_more2);
            holderView.tv_name1 = (TextView) convertView.findViewById(R.id.tv_name1);
            holderView.tv_name2 = (TextView) convertView.findViewById(R.id.tv_name2);
            holderView.tv_price1 = (TextView) convertView.findViewById(R.id.tv_price1);
            holderView.tv_price2 = (TextView) convertView.findViewById(R.id.tv_price2);
            holderView.ll_product1 = (LinearLayout) convertView.findViewById(R.id.ll_product1);
            holderView.ll_product2 = (LinearLayout) convertView.findViewById(R.id.ll_product2);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }

        final ProductBean product1 = dataList.get(position1);
        holderView.tv_name1.setText(product1.getName());
        holderView.tv_price1.setText(product1.getPrice() + "元");

        Tools.ImageLoaderShow(activity, product1.getPicUrl(), holderView.iv_logo1);

        holderView.ll_product1.setBackgroundResource(getProductBgColorResId(position1));
        holderView.ll_product2.setBackgroundResource(getProductBgColorResId(position2));

        if (position2 < dataList.size()) {
            final ProductBean product2 = dataList.get(position2);
            holderView.ll_product2.setVisibility(View.VISIBLE);
            Tools.ImageLoaderShow(activity, product2.getPicUrl(), holderView.iv_logo2);
            holderView.tv_name2.setText(product2.getName());
            holderView.tv_price2.setText(product2.getPrice() + "元");
            holderView.ll_product2.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ProductDetailActivity.class);
                    intent.putExtra(IntentExtraKeyConst.PRODUCT_CODE, product2.getCode());
                    activity.startActivity(intent);
                }
            });
        }
        else{
            holderView.ll_product2.setVisibility(View.INVISIBLE);
        }

        holderView.ll_product1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ProductDetailActivity.class);
                intent.putExtra(IntentExtraKeyConst.PRODUCT_CODE, product1.getCode());
                activity.startActivity(intent);
            }
        });



        return convertView;
    }

    private int getProductBgColorResId(int index){
        int value = index % 4;
        if(value == 1 || value == 2){
            return R.color.gray_light2;
        }

        return R.color.white;
    }

    class HolderView {
        ImageView iv_logo1;
        ImageView iv_logo2;
        TextView tv_name1;
        TextView tv_name2;
        TextView tv_price1;
        TextView tv_price2;
        LinearLayout ll_product1;
        LinearLayout ll_product2;
        ImageView iv_more1;
        ImageView iv_more2;
    }
}
