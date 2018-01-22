//package com.ninetop.UB;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.ninetop.activity.ub.seller.SellerDetailActivity;
//import com.ninetop.common.IntentExtraKeyConst;
//import com.ninetop.common.util.Tools;
//
//import java.util.List;
//
//import youbao.shopping.R;
//
///**
// * Created by huangjinding on 2017/5/11.
// */
//public class SellerCategoryAdapter extends BaseAdapter {
//    Context context;
//    List<SellerCategoryBean> dataList;
//    private View.OnClickListener showMoreClickListener = null;
//
//    public SellerCategoryAdapter(Context context, List<SellerCategoryBean> dataList) {
//        this.context = context;
//        this.dataList = dataList;
//    }
//    /**
//     * How many items are in the data set represented by this Adapter.
//     *
//     * @return Count of items.
//     */
//    @Override
//    public int getCount() {
//        return dataList.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
//
//    @SuppressWarnings({"ViewHolder", "InflateParams"})
//    @Override
//    public View getView(final int index, View convertView, ViewGroup parent) {
//        HolderView holderView;
//        if (convertView == null) {
//            holderView = new HolderView();
//            convertView = LayoutInflater.from(context).inflate(R.layout.ub_baneer_item, null);
//            holderView.banner_seller_category = (ImageView) convertView.findViewById(R.id.iv_banner_seller_category);
//            holderView.banner = (LinearLayout) convertView.findViewById(R.id.ll_banner);
//            convertView.setTag(holderView);
//        } else {
//            holderView = (HolderView) convertView.getTag();
//        }
//        //获取实例，
//        final SellerCategoryBean seller1 = dataList.get(index);
//
//        Tools.ImageLoaderShow(context, "http://192.168.31.102:8080/youbao/"+seller1.getIcon_url(), holderView.banner_seller_category);
//
//        holderView.banner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, SellerDetailActivity.class);
//                intent.putExtra(IntentExtraKeyConst.SELLER_ID, seller1.getId());
//                context.startActivity(intent);
//            }
//        });
//        return convertView;
//    }
//    class HolderView {
//        LinearLayout banner;
//        ImageView banner_seller_category;
//
//    }
//}
