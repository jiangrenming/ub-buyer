package com.ninetop.adatper;
/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
 * Created by Administrator on 2016/11/14.
 */


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninetop.base.DefaultBaseAdapter;
import com.ninetop.bean.user.CouponBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;

public class CouponUsedAdapter extends DefaultBaseAdapter {
    public CouponUsedAdapter(List<CouponBean> dataS, Context ctx) {
        super(dataS, ctx);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(ctx, R.layout.item_noused_page, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CouponBean couponbean = (CouponBean) datas.get(position);

        viewHolder.tvMoney.setText((couponbean.faceValue) + "");
        viewHolder.tvlimeType.setText(couponbean.name);
        viewHolder.tvUseMoney.setText((couponbean.limitType.equals("M") ? "满" + couponbean.limitValue + "可以使用"
                : ((!TextUtils.isEmpty(couponbean.limitType)) ? "无限制"
                : "") + ""));
        viewHolder.tvTime.setText(couponbean.startTime + "至" + couponbean.endTime);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_limeType)
        TextView tvlimeType;
        @BindView(R.id.tv_use_money)
        TextView tvUseMoney;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_select)
        ImageView ivSelect;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
