package com.ninetop.adatper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ninetop.base.DefaultBaseAdapter;
import com.ninetop.bean.user.DataListBean;

import java.util.List;

import youbao.shopping.R;


public class WelCardDetailedAdapter extends DefaultBaseAdapter {
    public WelCardDetailedAdapter(List<DataListBean> data, Context ctx) {
        super(data, ctx);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       WelCardHolder holder ;
        if (convertView == null) {
            convertView = View.inflate(ctx, R.layout.item_welcard_detailed_listview, null);
            holder = new WelCardHolder();
            holder.tv_detailed_recharge_name = (TextView) convertView.findViewById(R.id.tv_detailed_recharge_name);
            holder.tv_detailed_recharge_time = (TextView) convertView.findViewById(R.id.tv_detailed_recharge_time);
            holder.tv_detailed_recharge_account = (TextView) convertView.findViewById(R.id.tv_detailed_recharge_account);
            convertView.setTag(holder);
        }else {
            holder = (WelCardHolder) convertView.getTag();
        }
        //设置模拟数据
        DataListBean welCardBean = (DataListBean) datas.get(position);
        if ("A".equals(welCardBean.freecasUseType)){
            holder.tv_detailed_recharge_name.setText("收入");
            holder.tv_detailed_recharge_account.setText("+"+welCardBean.userAmount);
        }else if ("B".equals(welCardBean.freecasUseType)){
            holder.tv_detailed_recharge_name.setText("支出");
            holder.tv_detailed_recharge_account.setText("-"+welCardBean.userAmount);
        }
        holder.tv_detailed_recharge_time.setText(welCardBean.useTime);

        return convertView;
    }
    public class WelCardHolder {
        public TextView tv_detailed_recharge_name;
        public TextView tv_detailed_recharge_time;
        public TextView tv_detailed_recharge_account;
    }
}
