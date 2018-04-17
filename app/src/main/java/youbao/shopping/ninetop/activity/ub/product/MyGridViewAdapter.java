//package com.ninetop.activity.ub.product;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.ninetop.UB.SellerCategoryBean;
//import com.ninetop.activity.ub.seller.SellerSearchInfoActivity;
//import com.ninetop.common.IntentExtraKeyConst;
//import com.ninetop.common.util.Tools;
//
//import java.util.List;
//
//import youbao.shopping.R;
//
//import static com.ninetop.config.AppConfig.BASE_IMAGE_URL;
//
///**
// * GridView加载数据adapter
// */
//public class MyGridViewAdapter extends BaseAdapter {
//
//    private List<SellerCategoryBean> listData;
//    private LayoutInflater inflater;
//    private Context context;
//    private int mIndex;//页数下标，表示第几页，从0开始
//    private int mPagerSize;//每页显示的最大数量
//    private SellerCategoryBean bean;
//    public MyGridViewAdapter(Context context,List<SellerCategoryBean> listData,int mIndex,int mPagerSize) {
//        this.context = context;
//        this.listData = listData;
//        this.mIndex = mIndex;
//        this.mPagerSize = mPagerSize;
//        inflater = LayoutInflater.from(context);
//    }
//
//    /**
//     * 先判断数据集的大小是否足够显示满本页？listData.size() > (mIndex + 1)*mPagerSize
//     * 如果满足，则此页就显示最大数量mPagerSize的个数
//     * 如果不够显示每页的最大数量，那么剩下几个就显示几个 (listData.size() - mIndex*mPagerSize)
//     */
//    @Override
//    public int getCount() {
//        return listData.size() > (mIndex + 1)*mPagerSize ? mPagerSize : (listData.size() - mIndex*mPagerSize);
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return listData.get(position + mIndex * mPagerSize);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position + mIndex * mPagerSize;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder = null;
//        if(convertView == null){
//            convertView = inflater.inflate(R.layout.ub_item_category_gridview,parent,false);
//            holder = new ViewHolder();
//            holder.proName = (TextView) convertView.findViewById(R.id.tv_category_gridview_name);
//            holder.imgUrl = (ImageView) convertView.findViewById(R.id.iv_category_gridview_image);
//            holder.ll_category_gridview = (LinearLayout) convertView.findViewById(R.id.ll_category_gridview);
//            convertView.setTag(holder);
//        }else{
//            holder = (ViewHolder) convertView.getTag();
//        }
//        //重新确定position（因为拿到的是总的数据源，数据源是分页加载到每页的GridView上的，为了确保能正确的点对不同页上的item）
//        final int pos = position + mIndex*mPagerSize;//假设mPagerSize=8，假如点击的是第二页（即mIndex=1）上的第二个位置item(position=1),那么这个item的实际位置就是pos=9
//         bean = listData.get(pos);
//        holder.proName.setText(bean.getName());
//       // holder.imgUrl.setImageResource(bean.getIcon_url()+"");
//        Tools.ImageLoaderShow(context, BASE_IMAGE_URL+ bean.icon_url, holder.imgUrl);
//        //添加item监听
//        holder.ll_category_gridview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, SellerSearchInfoActivity.class);
//                intent.putExtra(IntentExtraKeyConst.SELLER_CATEGORY_ID, bean.getId());
//                intent.putExtra(IntentExtraKeyConst.SELLER_CATEGORY_NAME, bean.getName());
//                context.startActivity(intent);
//            }
//        });
//        return convertView;
//    }
//
//    class ViewHolder{
//        private TextView proName;
//        private ImageView imgUrl;
//        public LinearLayout ll_category_gridview;
//    }
//}
