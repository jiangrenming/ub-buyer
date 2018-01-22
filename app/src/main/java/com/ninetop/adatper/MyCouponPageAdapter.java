package com.ninetop.adatper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.ninetop.base.DefaultPagerAdapter;
import com.ninetop.page.BasePager;

import java.util.List;

/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
 * Created by Administrator on 2016/11/16.
 */


public class MyCouponPageAdapter extends DefaultPagerAdapter {
    public MyCouponPageAdapter(List dataS, Context ctx) {
        super(dataS, ctx);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager currentNewsItemPager = (BasePager) datas.get(position);
        View currentView = currentNewsItemPager.initView();
        container.addView(currentView);
        //初始化数据  不要忘记
        currentNewsItemPager.initData();
        return currentView;
    }
}
