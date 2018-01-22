package com.ninetop.adatper.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ninetop.base.BaseActivity;
import com.ninetop.bean.user.CouponBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;

/**
 * Created by jill on 2016/11/15.
 */

public class OrderCouponInvalidAdapter extends BaseAdapter {

    List<CouponBean> dataList;
    BaseActivity activity;

    public OrderCouponInvalidAdapter(BaseActivity activity, List<CouponBean> dataList) {
        this.dataList = dataList;
        this.activity = activity;
    }

    public void setData(List<CouponBean> dataList) {
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
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HolderView holderView;
        CouponBean bean = dataList.get(position);
        if (convertView == null) {

            holderView = new HolderView(bean);
            convertView = holderView.targetView;
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }

        holderView.tvMoney.setText(bean.faceValue + "");
        holderView.tvCondition.setText(getLimitText(bean));
        holderView.tvProductLimit.setText(bean.usageDesc);
        holderView.tvTime.setText(bean.startTime + "至" + bean.endTime);

        return convertView;
    }

    private String getLimitText(CouponBean bean) {
        if ("N".equals(bean.limitType)) {
            return "无条件";
        }

        return "满" + bean.limitValue + "元可用";
    }

    class HolderView {

        View targetView;
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_condition)
        TextView tvCondition;
        @BindView(R.id.tv_product_limit)
        TextView tvProductLimit;
        @BindView(R.id.tv_time)
        TextView tvTime;

        CouponBean couponBean;

        HolderView(CouponBean couponBean) {
            targetView = LayoutInflater.from(activity).inflate(R.layout.item_order_coupon_disable, null);

            this.couponBean = couponBean;
            ButterKnife.bind(this, targetView);
        }

    }
}
