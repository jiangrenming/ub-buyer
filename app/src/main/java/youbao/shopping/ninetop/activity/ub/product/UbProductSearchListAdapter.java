package youbao.shopping.ninetop.activity.ub.product;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import youbao.shopping.ninetop.UB.product.ProductSearchBean;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.util.Tools;

import java.util.List;

import youbao.shopping.R;

import static youbao.shopping.ninetop.config.AppConfig.BASE_IMAGE_URL;

/**
 * Created by huangjinding on 2017/5/26.
 */
public class UbProductSearchListAdapter extends BaseAdapter{
    private Context context;
    private List<ProductSearchBean> dataList;

    private boolean showMore = false;
    private View.OnClickListener showMoreClickListener = null;

    public UbProductSearchListAdapter(Context context, List<ProductSearchBean> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        if (dataList.size() % 2 == 1) {
            return dataList.size() / 2 + 1;
        } else
            return dataList.size() / 2;
    }

//    public void setShowMore(boolean showMore){
//        this.showMore = showMore;
//    }
//
//    public void setShowMoreClickListener(View.OnClickListener showMoreClickListener){
//        this.showMoreClickListener = showMoreClickListener;
//    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({ "ViewHolder", "InflateParams" })
    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        HolderView holderView;
        final int position1 = index * 2;
        final int position2 = position1 + 1;
        if (convertView == null) {
            holderView = new HolderView();
            convertView = LayoutInflater.from(context).inflate(R.layout.ub_product_all, null);
            holderView.iv_logo1 = (ImageView) convertView.findViewById(R.id.iv_logo1);
            holderView.iv_logo2 = (ImageView) convertView.findViewById(R.id.iv_logo2);
            holderView.tv_detail1 = (TextView) convertView.findViewById(R.id.tv_detail1);
            holderView.tv_detail2 = (TextView) convertView.findViewById(R.id.tv_detail2);
            holderView.tv_price1 = (TextView) convertView.findViewById(R.id.tv_price1);
            holderView.tv_price2 = (TextView) convertView.findViewById(R.id.tv_price2);
            holderView.tv_score1 = (TextView) convertView.findViewById(R.id.tv_count1);
            holderView.tv_score2 = (TextView) convertView.findViewById(R.id.tv_count2);
            holderView.ll_product1 = (LinearLayout) convertView.findViewById(R.id.ll_product1);
            holderView.ll_product2 = (LinearLayout) convertView.findViewById(R.id.ll_product2);
            holderView.ll_more = (LinearLayout) convertView.findViewById(R.id.ll_more);
            convertView.setTag(holderView);

            //如果第二个商品的索引大于总数量减1，说明数量总数是奇数，那么隐藏第二个商品
            if(position2 > dataList.size() - 1){
                holderView.ll_product2.setVisibility(View.INVISIBLE);
            }

            boolean isLastIndex = position2 >= dataList.size() - 1;

            if(showMore && isLastIndex){
                setShowMoreVisible(holderView);
            }
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        // 设置图片大小
        ViewGroup.LayoutParams lp = holderView.iv_logo1.getLayoutParams();
        lp.width = (int) (Tools.getScreenWidth(context)/ 2.0f);
        lp.height = (int) lp.width;
        holderView.iv_logo1.setLayoutParams(lp);
        holderView.iv_logo2.setLayoutParams(lp);

        final ProductSearchBean product1 = dataList.get(position1);

        holderView.tv_detail1.setText(product1.product_name);
        holderView.tv_score1.setText(product1.count_month+"");
        holderView.tv_price1.setText(Math.round(Double.valueOf(Double.parseDouble(product1.price)))+"");
        Tools.ImageLoaderShow(context,BASE_IMAGE_URL+product1.icon, holderView.iv_logo1);

        if (position2 < dataList.size()) {
            final ProductSearchBean product2 = dataList.get(position2);
            			holderView.ll_product2.setVisibility(View.VISIBLE);
            Tools.ImageLoaderShow(context, BASE_IMAGE_URL+product2.icon, holderView.iv_logo2);
            holderView.tv_detail2.setText(product2.product_name);
            holderView.tv_score2.setText(product2.count_month+"");
            holderView.tv_price2.setText(Math.round(Double.valueOf(product2.price))+"");
            holderView.ll_product2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UbProductInfoActivity.class);
                    intent.putExtra(IntentExtraKeyConst.PRODUCT_ID, product2.product_id+"");
                    context.startActivity(intent);
                }
            });
        }

        holderView.ll_product1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UbProductInfoActivity.class);
                intent.putExtra(IntentExtraKeyConst.PRODUCT_ID, product1.product_id+"");
                context.startActivity(intent);
            }
        });


        return convertView;
    }

    private void setShowMoreVisible(HolderView holderView){
        holderView.ll_product2.setVisibility(View.GONE);
        holderView.ll_more.setVisibility(View.VISIBLE);
        holderView.ll_more.setOnClickListener(showMoreClickListener);
    }

    class HolderView {
        ImageView iv_logo1;
        ImageView iv_logo2;
        TextView tv_detail1;
        TextView tv_detail2;
        TextView tv_price1;
        TextView tv_price2;
        TextView tv_score1;
        TextView tv_score2;
        LinearLayout ll_product1;
        LinearLayout ll_product2;
        LinearLayout ll_more;
    }
}

