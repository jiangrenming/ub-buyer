package com.ninetop.UB;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/5/16.
 */
public class ConsumeListAdapter extends BaseAdapter {
    Context context;
    List<ConSumListBean> dataList;

    public ConsumeListAdapter(Context context, List<ConSumListBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
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

    @SuppressWarnings({"ViewHolder", "InflateParams"})
    @Override
    public View getView(final int index, View convertView, ViewGroup parent) {
        HolderView holderView;
        if (convertView == null) {
            holderView = new HolderView();
            convertView = LayoutInflater.from(context).inflate(R.layout.ub_item_order_listview, null);
            holderView.consume_list = (LinearLayout) convertView.findViewById(R.id.ll_consume_list);
            holderView.order_seller_name = (TextView) convertView.findViewById(R.id.tv_order_seller_name);
            holderView.time = (TextView) convertView.findViewById(R.id.tv_time);
            holderView.order_price = (TextView) convertView.findViewById(R.id.tv_order_price);
            holderView.credit = (TextView) convertView.findViewById(R.id.tv_credit);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        final ConSumListBean seller1 = dataList.get(index);
        holderView.order_seller_name.setText(seller1.getUnion_shop_name());
        holderView.time.setText(seller1.getCreate_time());
        holderView.order_price.setText("￥"+seller1.getTotal_price());
        holderView.credit.setText("增加积分: +"+seller1.getGain_point());

        // Tools.ImageLoaderShow(context, seller1.getIcon1(), holderView.ll_consume_list);
//        holderView.consume_list.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, SellerDetailActivity.class);
//                intent.putExtra(IntentExtraKeyConst.SELLER_ID, seller1.getId()+"");
//                context.startActivity(intent);
//            }
//        });
        return convertView;
    }
    class HolderView {
        LinearLayout consume_list;
        TextView order_seller_name;
        TextView time;
        TextView order_price;
        TextView credit;
    }
}
