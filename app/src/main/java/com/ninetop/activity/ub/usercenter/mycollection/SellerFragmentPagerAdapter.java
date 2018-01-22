package com.ninetop.activity.ub.usercenter.mycollection;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by huangjinding on 2017/5/9.
 */
public class SellerFragmentPagerAdapter extends FragmentPagerAdapter {
    public SellerFragmentPagerAdapter(FragmentManager fm){
        super(fm);
        SellerFragmentFactory.clear();
    }

    @Override
    public Fragment getItem(int position){
        return SellerFragmentFactory.create(position);
    }

    @Override
    public int getCount(){
        return 3;
    }
}
