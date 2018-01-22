package com.ninetop.activity.product;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.ninetop.base.BaseActivity;
import com.ninetop.common.MyActivityManager;
import com.ninetop.common.util.Tools;
import com.ninetop.common.view.HeadView;
import com.ninetop.common.view.IndicatorView;
import com.ninetop.fragment.product.orderFragmentPagerAdapter;

import youbao.shopping.R;


/**
 * @date: 2016/11/12
 * @author: Shelton
 * @version: 1.1.3
 * @Description:我的订单
 */
public class MyOrderActivity extends BaseActivity implements View.OnClickListener {

    private HeadView headView;

    private ViewPager viewPager;

    private IndicatorView mIndicator;

    private TextView mTabOne;
    private TextView mTabTwo;
    private TextView mTabThree;
    private TextView mTabFour;
    private TextView mTabFive;

    private TextView[] TabArray = null;

    //接收UserCenter传过来的订单类型
    private int count;


    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_wodedingdan;
    }

    @Override
    protected void initView() {
        super.initView();
        MyActivityManager.getInstance().addActivity(this);
        headView = (HeadView) findViewById(R.id.hv_title);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        mIndicator = (IndicatorView) findViewById(R.id.indicator);
        mTabOne = (TextView) findViewById(R.id.tab_one);
        mTabTwo = (TextView) findViewById(R.id.tab_two);
        mTabThree = (TextView) findViewById(R.id.tab_three);
        mTabFour = (TextView) findViewById(R.id.tab_four);
        mTabFive = (TextView) findViewById(R.id.tab_five);
        headView.setTitle("我的订单");
        mTabOne.setOnClickListener(this);
        mTabTwo.setOnClickListener(this);
        mTabThree.setOnClickListener(this);
        mTabFour.setOnClickListener(this);
        mTabFive.setOnClickListener(this);
        //viewpager设置缓存页面
        viewPager.setOffscreenPageLimit(5);
    }
    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        if (intent != null) {
            count = intent.getIntExtra("order_fg", -1);
            switch (count) {
                case 0:
                    initTabColor(0);
                    break;
                case 1:
                    initTabColor(1);
                    break;
                case 2:
                    initTabColor(2);
                    break;
                case 3:
                    initTabColor(3);
                    break;
                case 4:
                    initTabColor(4);
                    break;

            }
        }
        initViewPager();
    }

    private void initViewPager() {
        viewPager.setAdapter(new orderFragmentPagerAdapter(getSupportFragmentManager()));
        //切换viewpager
        viewPager.setCurrentItem(count);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mIndicator.scroll(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                setOnPageSelectedWithPosition(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private void setOnPageSelectedWithPosition(int position) {
        switch (position) {
            case 0:
                initTabColor(0);
                break;
            case 1:
                initTabColor(1);
                break;
            case 2:
                initTabColor(2);
                break;
            case 3:
                initTabColor(3);
                break;
            case 4:
                initTabColor(4);
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tab_one:
                initTabColor(0);
                viewPager.setCurrentItem(0);
                break;
            case R.id.tab_two:
                initTabColor(1);
                viewPager.setCurrentItem(1);
                break;
            case R.id.tab_three:
                initTabColor(2);
                viewPager.setCurrentItem(2);
                break;
            case R.id.tab_four:
                initTabColor(3);
                viewPager.setCurrentItem(3);
                break;
            case R.id.tab_five:
                initTabColor(4);
                viewPager.setCurrentItem(4);
                break;

        }
    }

    private void initTabColor(int index) {
        if (TabArray == null) {
            TabArray = new TextView[]{mTabOne, mTabTwo, mTabThree, mTabFour, mTabFive};
        }

        for (int i = 0; i < TabArray.length; i++) {
            if (i == index) {
                TabArray[i].setTextColor(Tools.getColorValueByResId(this, R.color.text_red2));
            } else {
                TabArray[i].setTextColor(Tools.getColorValueByResId(this,R.color.text_black));
            }
        }
    }

}