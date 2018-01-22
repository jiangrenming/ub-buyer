package com.ninetop.UB;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ninetop.activity.ub.seller.SellerDetailActivity;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.util.Tools;

import java.util.List;

import youbao.shopping.R;

import static com.ninetop.config.AppConfig.BASE_IMAGE_URL;

/**
 * Created by huangjinding on 2017/6/4.
 */

public class SellerSearchAdapter extends BaseAdapter {
    Context context;
    List<UbSearchInfoBean> dataList;
    public SellerSearchAdapter(Context context, List<UbSearchInfoBean> dataList) {
        this.context = context;
        this.dataList = dataList;
}
    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.ub_item_seller_list, null);
            holderView.iv_seller = (ImageView) convertView.findViewById(R.id.iv_seller);
            holderView.tv_seller_name = (TextView) convertView.findViewById(R.id.tv_seller_name);
            holderView.tv_personal_price = (TextView) convertView.findViewById(R.id.tv_personal_price);
            holderView.tv_ub_ratio = (TextView) convertView.findViewById(R.id.tv_ub_ratio);
            holderView.ll_seller = (LinearLayout) convertView.findViewById(R.id.ll_seller);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        final UbSearchInfoBean seller1 =dataList.get(index);

        holderView.tv_seller_name.setText(seller1.getName());
        holderView.tv_personal_price.setText("人均消费 "+seller1.getPer_consume());
        holderView.tv_ub_ratio.setText("U币赠送比例 "+seller1.getRatio());

        Tools.ImageLoaderShow(context, BASE_IMAGE_URL+seller1.getIcon1(), holderView.iv_seller);

        holderView.ll_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SellerDetailActivity.class);
                intent.putExtra(IntentExtraKeyConst.SELLER_ID, seller1.getId());
                context.startActivity(intent);
            }
        });
        return convertView;
    }
class HolderView {
    LinearLayout ll_seller;
    ImageView iv_seller;
    TextView tv_seller_name;
    TextView tv_personal_price;
    TextView tv_ub_ratio;

}
}
