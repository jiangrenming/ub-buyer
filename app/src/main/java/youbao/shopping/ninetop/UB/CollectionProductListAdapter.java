package youbao.shopping.ninetop.UB;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import youbao.shopping.ninetop.activity.ub.bean.product.ProductFavorBean;
import youbao.shopping.ninetop.activity.ub.product.UbProductInfoActivity;
import youbao.shopping.ninetop.common.IntentExtraKeyConst;
import youbao.shopping.ninetop.common.util.Tools;

import java.util.ArrayList;
import java.util.List;

import youbao.shopping.R;

import static youbao.shopping.ninetop.config.AppConfig.BASE_IMAGE_URL;

/**
 *
 * @author huangjinding
 * @date 2017/5/13
 */
public class CollectionProductListAdapter extends BaseAdapter {

    Context context;
    List<ProductFavorBean> dataList;
    UbUserService ubUserService;
    private List<ProductFavorBean> selectList = new ArrayList<>();

    boolean isEditStatus = false;
    public CollectionProductListAdapter(UbUserService ubUserService,Context context, List<ProductFavorBean> dataList) {
        this.context = context;
        this.ubUserService=ubUserService;
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
    public List<ProductFavorBean> getSelectList(){
        return selectList;
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
            holderView.tv_ub_franchisee = (TextView) convertView.findViewById(R.id.tv_franchisee_name);
            holderView.ll_seller = (LinearLayout) convertView.findViewById(R.id.ll_seller);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        final ProductFavorBean product1 = dataList.get(index);
        Log.e("商品的信息:",product1.franchisee_name+"/id="+product1.product_id+"/加盟商id="+product1.franchisee_id);
        holderView.tv_ub_franchisee.setVisibility(View.VISIBLE);
        holderView.tv_seller_name.setText(product1.product_name);
        holderView.tv_personal_price.setText("人均消费 "+product1.price);
        holderView.tv_ub_franchisee.setText(product1.franchisee_name);
        Tools.ImageLoaderShow(context, BASE_IMAGE_URL+product1.icon, holderView.iv_seller);
        if (isEditStatus) {
            holderView.iv_select.setVisibility(View.VISIBLE);
            holderView.ll_seller.setEnabled(false);
        } else {
            holderView.iv_select.setVisibility(View.GONE);
            holderView.ll_seller.setEnabled(true);
        }

        holderView.ll_seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,UbProductInfoActivity.class);
                intent.putExtra(IntentExtraKeyConst.PRODUCT_ID, product1.product_id+"");
                intent.putExtra(IntentExtraKeyConst.FRANCHISEEID, product1.franchisee_id+"");
                context.startActivity(intent);
            }
        });

        holderView.iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView=(ImageView) v;
                if(isSelected(product1)){
                    removeSelectItem(product1);
                    imageView.setImageResource(R.mipmap.edit_unselect);
                }else {
                    addSelerctItem(product1);
                    imageView.setImageResource(R.mipmap.edit_select);
                }

            }
        });
        return convertView;
    }
    private boolean isSelected( ProductFavorBean favorBean) {
        boolean isSelect = false;
        if (selectList !=null && selectList.size() >0){
            for (int i = 0; i < selectList.size(); i++) {
                ProductFavorBean productFavorBean = selectList.get(i);
                if (productFavorBean.franchisee_id == favorBean.franchisee_id){
                    isSelect = true;
                }
            }
        }
        return isSelect;
    }
    protected void addSelerctItem(ProductFavorBean bean){
        if(selectList==null){
            selectList=new ArrayList<>();
        }
        if(!selectList.contains(bean)){
            selectList.add(bean);
        }
    }

    protected void removeSelectItem(ProductFavorBean bean){
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
        TextView tv_ub_franchisee;
    }
}
