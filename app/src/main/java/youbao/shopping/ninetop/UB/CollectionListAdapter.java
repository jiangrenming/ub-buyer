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

import java.util.ArrayList;
import java.util.List;

import youbao.shopping.R;

import static youbao.shopping.ninetop.config.AppConfig.BASE_IMAGE_URL;

/**
 * Created by huangjinding on 2017/5/13.
 */
public class CollectionListAdapter extends BaseAdapter {
    Context context;
    List<UbSellerCollectBean> dataList;
    private List<UbSellerCollectBean> selectList;
    boolean isEditStatus = false;
    public CollectionListAdapter(Context context, List<UbSellerCollectBean> dataList) {
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
    public void setIsEditStatus(boolean isEditStatus) {
        this.isEditStatus = isEditStatus;
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
            holderView.iv_select = (ImageView) convertView.findViewById(R.id.iv_select);
            holderView.iv_seller = (ImageView) convertView.findViewById(R.id.iv_seller);
            holderView.tv_seller_name = (TextView) convertView.findViewById(R.id.tv_seller_name);
            holderView.tv_personal_price = (TextView) convertView.findViewById(R.id.tv_personal_price);
            holderView.tv_ub_ratio = (TextView) convertView.findViewById(R.id.tv_ub_ratio);
            holderView.ll_seller = (LinearLayout) convertView.findViewById(R.id.ll_seller);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        final UbSellerCollectBean seller1 = dataList.get(index);
        holderView.tv_seller_name.setText(seller1.getName());
        holderView.tv_personal_price.setText("人均消费 ：" + Math.round(Double.valueOf(seller1.getPer_consume())));
        holderView.tv_ub_ratio.setText("U币赠送比例 " + seller1.getRatio());

        Tools.ImageLoaderShow(context, BASE_IMAGE_URL + seller1.getIcon(), holderView.iv_seller);
        if (isEditStatus) {
            holderView.iv_select.setVisibility(View.VISIBLE);
            holderView.ll_seller.setEnabled(false);
        } else {
            holderView.iv_select.setVisibility(View.GONE);
        }
        holderView.ll_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SellerDetailActivity.class);
                intent.putExtra(IntentExtraKeyConst.SELLER_ID, seller1.getId());
                context.startActivity(intent);
            }
        });


        holderView.iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v;
                if (isSelected((ImageView) v)) {
                    for (UbSellerCollectBean bean : dataList) {
                        addSelerctItem(dataList.get(index));
                    }
                    imageView.setImageResource(R.mipmap.edit_select);
                } else {
                    for (UbSellerCollectBean bean : dataList) {
                        removeSelectItem(dataList.get(index));
                    }
                    imageView.setImageResource(R.mipmap.edit_unselect);
                }

            }
        });
        return convertView;
    }

    public List<UbSellerCollectBean> getSelectList(){
        return selectList;
    }
        private boolean isSelected(ImageView imageView) {
            if (imageView.getDrawable().getCurrent().getConstantState().
                    equals(context.getResources().getDrawable(R.mipmap.edit_unselect).getConstantState())) {
                return true;
            }
            return false;
        }
        protected void addSelerctItem(UbSellerCollectBean bean){
            if(selectList==null){
                selectList=new ArrayList<>();
            }
            if(!selectList.contains(bean)){
                selectList.add(bean);
            }
        }
        protected void removeSelectItem(UbSellerCollectBean bean){
            if(selectList==null){
                selectList=new ArrayList<>();
            }
            if(selectList.contains(bean)){
                selectList.remove(bean);
            }

    }
    class HolderView {
        LinearLayout ll_seller;
        ImageView iv_seller;
        ImageView iv_select;
        TextView tv_seller_name;
        TextView tv_personal_price;
        TextView tv_ub_ratio;

    }
}
