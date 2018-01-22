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

public class CouponDisableAdapter extends DefaultBaseAdapter {
    private final int TYPE_1=0;
    private final int TYPE_2=1;
    public CouponDisableAdapter(List<CouponBean> datas, Context ctx) {
        super(datas, ctx);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        ViewHolder viewHolder1=null;
        int itemViewType = getItemViewType(position);

        if (convertView==null){
            switch (itemViewType){
                case TYPE_1:
                    convertView = View.inflate(ctx, R.layout.item_disable_page, null);
                    viewHolder=new ViewHolder(convertView);
                    convertView.setTag(viewHolder);
                    break;
                case TYPE_2:
                    convertView = View.inflate(ctx, R.layout.item_coupon_past, null);
                    viewHolder1=new ViewHolder(convertView);
                    convertView.setTag(viewHolder1);
                    break;
            }

        }else {
            switch (itemViewType){
                case TYPE_1:
                    viewHolder= (ViewHolder) convertView.getTag();
                    break;
                case TYPE_2:
                    viewHolder1= (ViewHolder) convertView.getTag();
                    break;
            }

        }
        CouponBean couponbean = (CouponBean) datas.get(position);
        switch (itemViewType){
            case TYPE_1:
                viewHoldSet(viewHolder, couponbean);
                break;
            case TYPE_2:
                viewHoldSet(viewHolder1, couponbean);
                break;
        }
        return convertView;
    }

    private void viewHoldSet(ViewHolder viewHolder1, CouponBean couponbean) {
        viewHolder1.tvMoney.setText((couponbean.faceValue)+"");
        viewHolder1.tvUseMoney.setText((couponbean.limitType.equals("M")?"满"+couponbean.limitValue+"可以使用"
                :((!TextUtils.isEmpty(couponbean.limitType))?"无限制"
                :"")+""));
        viewHolder1.tvLimeType.setText(couponbean.name);
        viewHolder1.tvTime.setText(couponbean.startTime+"至"+couponbean.endTime);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        CouponBean couponbean = (CouponBean) datas.get(position);
        int useStatus = couponbean.useStatus;
        if (useStatus==1){
            //已使用
            return TYPE_1;
        }else if (useStatus==2){
            //已过期
            return TYPE_2;
        }
        return super.getItemViewType(position);
    }

     class ViewHolder {
         @BindView(R.id.tv_limeType)
         TextView tvLimeType;
        @BindView(R.id.tv_money)
        TextView tvMoney;
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
