package com.ninetop.activity.product;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hykj.myviewlib.tab.PagerSlidingTabStrip;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.ninetop.activity.ub.product.ProductSearchActivity;
import com.ninetop.base.BaseActivity;
import com.ninetop.bean.product.category.SecondCategoryBean;
import com.ninetop.common.util.Tools;

import java.util.List;

import youbao.shopping.R;

import static com.ninetop.common.IntentExtraKeyConst.CATEGORY_NAME;
import static com.ninetop.common.IntentExtraKeyConst.SECONDCATEGORIES;


/**
 * @date: 2016/11/10
 * @author: Shelton
 * @version: 1.1.3
 * @Description:二级分类界面--商品列表
 */
public class CategorySecondActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.vp_index)
    private ViewPager vp_index;

    @ViewInject(R.id.iv_icon_back)
    private ImageView iv_icon_back;

    @ViewInject(R.id.iv_common_icon)
    private ImageView iv_common_icon;

    @ViewInject(R.id.tv_title)
    private TextView tv_title;

    @ViewInject(R.id.pst_tab)
    private PagerSlidingTabStrip pstTab;

    private int id;

    private List<SecondCategoryBean> secondCategories;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_category_second;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        secondCategories = (List<SecondCategoryBean>) intent.getSerializableExtra(SECONDCATEGORIES);
        id = intent.getIntExtra("ID", 0);

        initHeadView();
        initPagerSlidingTabStrip();
    }

    private void initPagerSlidingTabStrip() {
        pstTab.setShouldExpand(false);// 自适应宽度
        pstTab.setIndicatorColorResource(R.color.text_red);
        pstTab.setUnderlineHeight(Tools.dip2px(this, 1));
        pstTab.setIndicatorHeight(Tools.dip2px(this, 2));
        pstTab.setDividerColorResource(R.color.white);
        pstTab.setUnderlineColorResource(R.color.gray);
        pstTab.setTextSize(Tools.dip2px(this, 14));
        pstTab.setTypeface(null, Typeface.NORMAL);
        pstTab.setTabPaddingLeftRight(Tools.dip2px(this, 15));
    }

    //初始化标题view
    private void initHeadView() {
        tv_title.setText(getIntent().getStringExtra(CATEGORY_NAME));
        iv_common_icon.setOnClickListener(this);
        iv_icon_back.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        initFragment();
    }

    private void initFragment() {
        FragmentPagerAdapter pagerAdapter = new CategorySecondFragmentPagerAdapter(getSupportFragmentManager());
        vp_index.setAdapter(pagerAdapter);
        vp_index.setCurrentItem(id);
        pstTab.setViewPager(vp_index);
    }

    private class CategorySecondFragmentPagerAdapter extends FragmentPagerAdapter {

        public CategorySecondFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //复用fragment
            return CategorySecondFragmentManager.getInstance(secondCategories.get(position).code);
        }

        @Override
        public int getCount() {
            return secondCategories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return secondCategories.get(position).name.toString();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_common_icon:
                startActivity(ProductSearchActivity.class);
                break;
            case R.id.iv_icon_back:
                finish();
                break;
        }
    }
}


