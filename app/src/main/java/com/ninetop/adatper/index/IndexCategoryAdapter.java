package com.ninetop.adatper.index;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ninetop.activity.index.IndexActivity;
import com.ninetop.base.BaseActivity;
import com.ninetop.base.ProductListAdapter;
import com.ninetop.bean.index.category.CategoryBean;
import com.ninetop.bean.product.ProductBean;

import java.util.List;

import youbao.shopping.R;


/**
 * Created by jill on 2016/11/15.
 */

public class IndexCategoryAdapter extends BaseAdapter {

    List<CategoryBean> dataList;
    BaseActivity activity;
    private boolean showMore = false;

    public IndexCategoryAdapter(BaseActivity activity, List<CategoryBean> dataList) {
        this.dataList = dataList;
        this.activity = activity;
    }

    public void setData(List<CategoryBean> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void setShowMore(boolean showMore){
        this.showMore = showMore;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HolderView holderView;


        if (convertView == null) {
            holderView = new HolderView();
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_category_product, null);
            holderView.tv_title = (TextView) convertView.findViewById(R.id.tv_head_title);
            holderView.lv_category = (ListView) convertView.findViewById(R.id.lv_product_list);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }

        final CategoryBean bean = dataList.get(position);
        holderView.tv_title.setText(bean.getCatName());

        List<ProductBean> beanList = bean.getItemList();

        ProductListAdapter adapter = new ProductListAdapter(activity, beanList);
        adapter.setShowMore(showMore);
        if(showMore){
            adapter.setShowMoreClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((IndexActivity)activity).setCategorySelectedIndex(bean.getCatCode());
                }
            });
        }

        holderView.lv_category.setAdapter(adapter);

        return convertView;
    }

    class HolderView {

        TextView tv_title;
        ListView lv_category;

    }
}
