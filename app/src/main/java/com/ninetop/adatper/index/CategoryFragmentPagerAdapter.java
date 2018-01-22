//package com.ninetop.adatper.index;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//
//import com.ninetop.bean.index.category.CategoryBean;
//
//import java.util.List;
//
///**
// * Created by jill on 2016/11/25.
// */
//
//public class CategoryFragmentPagerAdapter extends FragmentPagerAdapter {
//
//    private List<CategoryBean> dataList = null;
//
//    public CategoryFragmentPagerAdapter(FragmentManager fm, List<CategoryBean> dataList) {
//        super(fm);
//
//        this.dataList = dataList;
//    }
//
//    @Override
//    public CharSequence getPageTitle(int position) {
//        return dataList.get(position).toString();
//    }
//
//    @Override
//    public int getCount() {
//        return dataList.size();
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        return CategoryFragmentManager.getInstance(dataList.get(position));
//    }
//}
