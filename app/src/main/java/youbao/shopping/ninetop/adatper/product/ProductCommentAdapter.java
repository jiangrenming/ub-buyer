package youbao.shopping.ninetop.adatper.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.base.DefaultBaseAdapter;
import youbao.shopping.ninetop.bean.product.PictureBean;
import youbao.shopping.ninetop.bean.product.ProductCommentBean;
import youbao.shopping.ninetop.common.util.Tools;

import java.util.List;

import youbao.shopping.R;

/**
 * Created by jill on 2016/11/15.
 */

public class ProductCommentAdapter extends DefaultBaseAdapter {

    public ProductCommentAdapter(List<ProductCommentBean> dataList, BaseActivity activity) {
        super(dataList, activity);
    }

    public void setData(List<ProductCommentBean> dataList) {
        datas.clear();
        datas.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HolderView holderView;

        if (convertView == null) {
            holderView = new HolderView();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.item_product_review, null);
            holderView.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holderView.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holderView.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
            holderView.tv_prop = (TextView) convertView.findViewById(R.id.tv_prop);
            holderView.ll_image_list = (LinearLayout) convertView.findViewById(R.id.ll_image_list);
            holderView.riv_avatar = (ImageView) convertView.findViewById(R.id.riv_avatar);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }

        ProductCommentBean bean = (ProductCommentBean)datas.get(position);
        holderView.tv_name.setText(bean.userName);
        holderView.tv_date.setText(bean.time);
        holderView.tv_content.setText(bean.comment);
        holderView.tv_prop.setText(bean.skuDesc);
        Tools.ImageLoaderShow(ctx, bean.userAvatar, holderView.riv_avatar);

        holderView.ll_image_list.removeAllViews();
        List<PictureBean> picList = bean.picList;
        if(picList != null || picList.size() > 0){
            for(PictureBean picBean : picList){
                ImageView imageView = createImageView();
                holderView.ll_image_list.addView(imageView);
                Tools.ImageLoaderShow(ctx, picBean.getPicUrl(), imageView);
            }
        }

        return convertView;
    }

    private ImageView createImageView(){
        ImageView imageView = new ImageView(ctx);
        int width = Tools.getScreenWidth(ctx);
        int padding = Tools.dip2px(ctx, 15);

        int oneImageWidth = (width - 5 * padding) / 4;
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setPadding(padding, 0, 0, 0);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(oneImageWidth + padding, oneImageWidth);
        imageView.setLayoutParams(lp);

        return imageView;
    }

    class HolderView {

        ImageView riv_avatar;
        TextView tv_name;
        TextView tv_date;
        TextView tv_content;
        TextView tv_prop;
        LinearLayout ll_image_list;

    }
}
