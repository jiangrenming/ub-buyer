package youbao.shopping.ninetop.UB;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import youbao.shopping.ninetop.activity.ub.seller.SellerDetailActivity;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.util.Tools;

import java.util.List;

import youbao.shopping.R;

import static youbao.shopping.ninetop.config.AppConfig.BASE_IMAGE_URL;

/**
 * Created by huangjinding on 2017/5/11.
 */
public class SellerSearchListAdapter extends BaseAdapter {
    Context context;
    List<SellerBean> dataList;
    public SellerSearchListAdapter(Context context, List<SellerBean> dataList) {
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
            holderView.tv_location=(TextView)convertView.findViewById(R.id.tv_location_distance);
            holderView.tv_personal_price = (TextView) convertView.findViewById(R.id.tv_personal_price);
            holderView.tv_ub_ratio = (TextView) convertView.findViewById(R.id.tv_ub_ratio);
            holderView.ll_seller = (LinearLayout) convertView.findViewById(R.id.ll_seller);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        // 设置图片大小
        ViewGroup.LayoutParams lp = holderView.iv_seller.getLayoutParams();
        lp.height = (int) lp.width;
        holderView.iv_seller.setLayoutParams(lp);
        final SellerBean seller1 =dataList.get(index);

        holderView.tv_seller_name.setText(seller1.getName());
        holderView.tv_personal_price.setText("人均消费 :￥"+Math.round(Double.valueOf(seller1.getPer_consume())));
        holderView.tv_ub_ratio.setText("U币赠送比例 "+seller1.getRatio());
          holderView.tv_location.setText(seller1.getDistance()+"KM");
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
        TextView tv_location;
    }
}
