package com.ninetop.fragment.product;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by jill on 2017/1/6.
 */

public class ProductSecKillFragmentPagerAdapter extends ProductFragmentPagerAdapter {
    public ProductSecKillFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragmentMap.get(position);
        if(fragment == null){
            if(position == 0){
                fragment = new ProductSecKillInfoFragment();
            }else if(position == 1){
                fragment = new ProductDetailFragment();
            }else{
                fragment = new ProductCommentFragment();
            }
            fragmentMap.put(position, fragment);
        }

        return fragment;

    }
}
