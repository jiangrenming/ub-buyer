package com.ninetop.adatper.index;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninetop.activity.product.ProductSaleDetailActivity;
import com.ninetop.base.BaseActivity;
import com.ninetop.bean.product.ProductSaleBean;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.constant.TextConstant;
import com.ninetop.common.util.Tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import youbao.shopping.R;

/**
 * Created by jill on 2016/11/15.
 */

public class IndexSaleAdapter extends BaseAdapter {

    List<ProductSaleBean> dataList;
    BaseActivity activity;

    public IndexSaleAdapter(BaseActivity activity, List<ProductSaleBean> dataList) {
        this.dataList = dataList;
        this.activity = activity;
    }

    public void setData(List<ProductSaleBean> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
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
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HolderView holderView;

        if(dataList.size() <= position)
            return null;

        final ProductSaleBean bean = dataList.get(position);
        if (convertView == null) {
            holderView = new HolderView();
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_index_seckilling, null);
            holderView.iv_product_image = (ImageView)convertView.findViewById(R.id.iv_product_image);
            holderView.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            holderView.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
            holderView.tv_old_price = (TextView)convertView.findViewById(R.id.tv_old_price);
            convertView.setTag(holderView);

            holderView.iv_product_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(IntentExtraKeyConst.PRODUCT_CODE, bean.getCode());
                    map.put(IntentExtraKeyConst.PRODUCT_SECKILL_ID, bean.getActivityItemId());
                    activity.startActivity(ProductSaleDetailActivity.class, map);
                }
            });
        } else {
            holderView = (HolderView) convertView.getTag();
        }

        holderView.tv_name.setText(bean.getName());
        holderView.tv_old_price.setText(TextConstant.MONEY + bean.getPrice());
        holderView.tv_price.setText(TextConstant.MONEY + bean.getKillPrice());
        Tools.addTextViewLine(holderView.tv_old_price);
        Tools.ImageLoaderShow(activity, bean.getPicUrl(), holderView.iv_product_image);
        return convertView;
    }

    class HolderView {

        ImageView iv_product_image;
        TextView tv_name;
        TextView tv_price;
        TextView tv_old_price;

    }
}
