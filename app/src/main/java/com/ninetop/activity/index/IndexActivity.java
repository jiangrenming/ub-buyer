package com.ninetop.activity.index;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ninetop.activity.TabBaseActivity;
import com.ninetop.activity.ub.product.ProductSearchActivity;
import com.ninetop.activity.ub.usercenter.SelectedCityActivity;
import com.ninetop.bean.index.category.CategoryBean;
import com.ninetop.bean.index.category.CategoryListBean;
import com.ninetop.fragment.index.IndexFragmentPagerAdapter;
import com.ninetop.service.impl.IndexService;
import com.ninetop.service.listener.CommonResultListener;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.R;

@SuppressLint("InflateParams")
public class IndexActivity extends TabBaseActivity {
    @BindView(R.id.vp_index)
    ViewPager vpIndex;
    private IndexService indexService = null;
    private FragmentPagerAdapter pagerAdapter;
    private List<CategoryBean> categoryList = null;
    public IndexActivity() {
        indexService = new IndexService(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index;
    }

    @Override
    protected void initData() {
        super.initData();

        indexService.getIndexMenu(new CommonResultListener<CategoryListBean>(this) {
            @Override
            public void successHandle(CategoryListBean result) {
                categoryList = result.getMenuList();
                pagerAdapter = new IndexFragmentPagerAdapter(getSupportFragmentManager(), categoryList);
                vpIndex.setAdapter(pagerAdapter);
            }
        });
    }

    @OnClick({R.id.iv_search, R.id.iv_notice,R.id.tv_select_city})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_search:
                startActivity(ProductSearchActivity.class);
                break;
            case R.id.iv_notice:
                startActivity(MessageActivity.class);
                break;
            case R.id.tv_select_city:
                startActivity(SelectedCityActivity.class);
                break;
        }
    }

    public void setCategorySelectedIndex(String categoryCode){
        if(categoryList == null || categoryList.size() == 0)
            return;

        int selectIndex = -1;
        for(int i=0;i<categoryList.size();i++){
            CategoryBean bean = categoryList.get(i);
            if(bean.getCatCode().equals(categoryCode)){
                selectIndex = i;
            }
        }

        if(selectIndex >= 0){
            vpIndex.setCurrentItem(selectIndex);
        }
    }
}
