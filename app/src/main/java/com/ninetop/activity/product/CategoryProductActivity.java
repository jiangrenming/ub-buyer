package com.ninetop.activity.product;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ninetop.activity.TabBaseActivity;
import com.ninetop.activity.ub.product.ProductSearchActivity;
import com.ninetop.base.DefaultBaseAdapter;
import com.ninetop.bean.product.category.ProductCategoryBean;
import com.ninetop.bean.product.category.SecondCategoryBean;
import com.ninetop.common.util.Tools;
import com.ninetop.service.impl.CategoryService;
import com.ninetop.service.listener.CommonResultListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import youbao.shopping.R;

import static com.ninetop.common.IntentExtraKeyConst.CATEGORY_NAME;
import static com.ninetop.common.IntentExtraKeyConst.SECONDCATEGORIES;
import static com.ninetop.common.IntentExtraKeyConst.SECONDCATEGORIES_CATCODE;


/**
 * @date: 2016/11/9
 * @author: Shelton
 * @version: 1.1.3
 * @Description:分类
 */
public  class CategoryProductActivity extends TabBaseActivity implements View.OnClickListener {
    List<ProductCategoryBean> categories = null;

    private EditText et_category_search;
    private ListView lv_category;
    private ImageView iv_category;
    private GridView gv_category;
    //记录点击的位置
    private int clickCategoryPosition = 0;
    private int clickSubCategoryPosition2 = 0;

    private MyGridViewAdapter gvAdapter;
    private CategoryService categoryService = null;

    private List<SecondCategoryBean> secondCategories = null;

    private int times = 0;

    public CategoryProductActivity() {
        //初始化CategoryService
        categoryService = new CategoryService(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_product_category;
    }

    @Override
    protected void initView() {
        et_category_search = (EditText) findViewById(R.id.et_category_search);
        lv_category = (ListView) findViewById(R.id.lv_category);
        iv_category = (ImageView) findViewById(R.id.iv_category);
        gv_category = (GridView) findViewById(R.id.gv_category);
        et_category_search.setFocusable(false);

    }

    @Override
    protected void initListener() {
        super.initListener();
        iv_category.setOnClickListener(this);
        et_category_search.setOnClickListener(this);

    }

    protected void initData() {

        initCategory();

    }

    private void initCategory() {
        categoryService.getProductCategoryList(new CommonResultListener<List<ProductCategoryBean>>(this) {

            @Override
            public void successHandle(List<ProductCategoryBean> result) {
                if (result == null || result.size() == 0) {
                    return;
                }
                categories = new ArrayList<ProductCategoryBean>();

                for (int i = 0; i < result.size(); i++) {
                    ProductCategoryBean productCategoryBean = result.get(i);
                    categories.add(productCategoryBean);
                }
                Tools.ImageLoaderShow(CategoryProductActivity.this, categories.get(clickCategoryPosition).picUrl, iv_category);
                showCategoryData();
                if (times == 0) {
                    initSecondCategory(categories.get(0));
                }
            }
        });
    }

    private void showCategoryData() {
        final MyListViewAdapter lvAdapter = new MyListViewAdapter(categories, CategoryProductActivity.this);
        lv_category.setAdapter(lvAdapter);
        lv_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickCategoryPosition = position;

                Tools.ImageLoaderShow(CategoryProductActivity.this, categories.get(clickCategoryPosition).picUrl, iv_category);
                lvAdapter.notifyDataSetChanged();

                initSecondCategory(categories.get(position));

                times++;

            }
        });
    }


    private void initSecondCategory(ProductCategoryBean productCategoryBean) {
        categoryService.getSecondCategoryList(productCategoryBean.code,new CommonResultListener<List<SecondCategoryBean>>(this) {
            @Override
            public void successHandle(List<SecondCategoryBean> result) {
                if (result == null || result.size() == 0) {
                    return;
                }
                secondCategories = new ArrayList<SecondCategoryBean>();
                for (int i = 0; i < result.size(); i++) {
                    SecondCategoryBean secondCategoryBean = result.get(i);
                    secondCategories.add(secondCategoryBean);
                }
                showSubCategoryData();
            }
        });
    }

    private void showSubCategoryData() {
        gvAdapter = new MyGridViewAdapter(secondCategories, CategoryProductActivity.this);
        gv_category.setAdapter(gvAdapter);
        gv_category.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickSubCategoryPosition2 = position;
                ArrayList<String> codes = new ArrayList<String>();
                for (int i = 0; i < secondCategories.size(); i++) {
                    codes.add(secondCategories.get(i).code);
                }

                Intent intent = new Intent(CategoryProductActivity.this, CategorySecondActivity.class);
                intent.putExtra(CATEGORY_NAME, categories.get(clickCategoryPosition).name);
                intent.putExtra("ID", position);
                intent.putExtra(SECONDCATEGORIES, (Serializable) secondCategories);
                intent.putExtra(SECONDCATEGORIES_CATCODE, secondCategories.get(position).code);
                startActivity(intent);
            }
        });
    }

    class MyGridViewAdapter extends DefaultBaseAdapter {

        public MyGridViewAdapter(List datas, Context ctx) {
            super(datas, ctx);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GridViewViewHolder gv_holder = null;
            if (convertView == null) {
                gv_holder = new GridViewViewHolder();
                convertView = View.inflate(CategoryProductActivity.this, R.layout.item_category_gridview, null);
                gv_holder.iv_category_gridview = (ImageView) convertView.findViewById(R.id.iv_category_gridview);
                gv_holder.tv_category_gridview_item = (TextView) convertView.findViewById(R.id.tv_category_gridview_item);
                gv_holder.ll_category_gridview = (LinearLayout) convertView.findViewById(R.id.ll_category_gridview);
                convertView.setTag(gv_holder);
            } else {
                gv_holder = (GridViewViewHolder) convertView.getTag();
            }

            SecondCategoryBean secondCategoryBean = secondCategories.get(position);

            gv_holder.tv_category_gridview_item.setText(secondCategoryBean.name);
            Tools.ImageLoaderShow(CategoryProductActivity.this, secondCategoryBean.picUrl, gv_holder.iv_category_gridview);

            return convertView;
        }

        class GridViewViewHolder {
            public ImageView iv_category_gridview;
            public TextView tv_category_gridview_item;
            public LinearLayout ll_category_gridview;
        }
    }

    class MyListViewAdapter extends DefaultBaseAdapter {

        public MyListViewAdapter(List datas, Context ctx) {
            super(datas, ctx);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(CategoryProductActivity.this, R.layout.item_category_listview, null);
                holder.tv_category_listview_item = (TextView) convertView.findViewById(R.id.tv_category_listview_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            ProductCategoryBean productCategoryBean = categories.get(position);

            holder.tv_category_listview_item.setText(productCategoryBean.name);

            setClickTextChange(position, convertView, holder);

            return convertView;
        }

        public class ViewHolder {
            public TextView tv_category_listview_item;
        }
    }

    private void setClickTextChange(int position, View convertView, MyListViewAdapter.ViewHolder holder) {
        if (position == clickCategoryPosition) {
            holder.tv_category_listview_item.setTextSize(16);
            holder.tv_category_listview_item.setTextColor(getResources().getColor(R.color.category_select));
            convertView.setBackgroundResource(R.drawable.item_category_select_bg);
        } else {
            holder.tv_category_listview_item.setTextColor(getResources().getColor(R.color.category_normal));
            holder.tv_category_listview_item.setTextSize(14);
            convertView.setBackgroundColor(Color.WHITE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_category_search:
                startActivity(ProductSearchActivity.class);
                break;
            case R.id.iv_category:
                Intent intent = new Intent(this, CategorySecondActivity.class);
                intent.putExtra(CATEGORY_NAME, categories.get(clickCategoryPosition).name);
                intent.putExtra(SECONDCATEGORIES, (Serializable) secondCategories);
                intent.putExtra(SECONDCATEGORIES_CATCODE, secondCategories.get(clickSubCategoryPosition2).code);
                startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCategory();
    }
}
