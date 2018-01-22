package com.ninetop.adatper.index;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninetop.activity.product.ProductSaleDetailActivity;
import com.ninetop.base.BaseActivity;
import com.ninetop.bean.index.SaleDetailBean;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.constant.TextConstant;
import com.ninetop.common.util.Tools;
import com.ninetop.common.view.ProgressBarView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import youbao.shopping.R;

/**
 * Created by jill on 2016/11/15.
 */

public class SaleDetailAdapter extends BaseAdapter {

    List<SaleDetailBean> dataList;
    BaseActivity activity;

    public SaleDetailAdapter(BaseActivity activity, List<SaleDetailBean> dataList) {
        this.dataList = dataList;
        this.activity = activity;
    }

    public void setData(List<SaleDetailBean> dataList) {
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

        final SaleDetailBean bean = dataList.get(position);
        if (convertView == null) {
            holderView = new HolderView();
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_seckilling_detail, null);
            holderView.iv_product_image = (ImageView)convertView.findViewById(R.id.iv_product_image);
            holderView.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
            holderView.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
            holderView.tv_old_price = (TextView)convertView.findViewById(R.id.tv_old_price);
            holderView.btn_buy = (TextView)convertView.findViewById(R.id.btn_buy);
            holderView.tv_left_count = (TextView)convertView.findViewById(R.id.tv_left_count);
            holderView.pbv_ratio = (ProgressBarView)convertView.findViewById(R.id.pbv_ratio);
            convertView.setTag(holderView);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put(IntentExtraKeyConst.PRODUCT_CODE, bean.code);
                    map.put(IntentExtraKeyConst.PRODUCT_SECKILL_ID, bean.activityItemId);
                    activity.startActivity(ProductSaleDetailActivity.class, map);
                }
            });
        } else {
            holderView = (HolderView) convertView.getTag();
        }

        holderView.tv_name.setText(bean.name);
        holderView.tv_old_price.setText(TextConstant.MONEY + bean.price);
        holderView.tv_price.setText(TextConstant.MONEY + bean.killPrice);

        int totalAmount = Integer.parseInt(bean.totalAmount);
        int leftAmount  = Integer.parseInt(bean.leftAmount);
        holderView.pbv_ratio.setMaxValue(totalAmount);
        holderView.pbv_ratio.setValue(leftAmount);
        holderView.tv_left_count.setText("剩余" + leftAmount + "件");
        Tools.addTextViewLine(holderView.tv_old_price);
        if("0".equals(bean.status)){
            holderView.btn_buy.setText("抢完了");
            holderView.btn_buy.setBackgroundColor(Tools.getColorValueByResId(activity, R.color.text_gray3));
            holderView.btn_buy.setEnabled(false);
        }else{
        }
        Tools.addTextViewLine(holderView.tv_old_price);
        Tools.ImageLoaderShow(activity, bean.picUrl, holderView.iv_product_image);
        return convertView;
    }

    class HolderView {

        ImageView iv_product_image;
        TextView tv_name;
        TextView tv_price;
        TextView tv_old_price;
        ProgressBarView pbv_ratio;
        TextView tv_left_count;
        TextView btn_buy;

    }
}
