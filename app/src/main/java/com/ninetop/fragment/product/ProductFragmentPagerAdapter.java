package com.ninetop.fragment.product;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Map;

import static android.media.CamcorderProfile.get;

/**
 * Created by jill on 2016/11/25.
 */

public class ProductFragmentPagerAdapter extends FragmentPagerAdapter {

    protected Map<Integer, Fragment> fragmentMap = new HashMap<>();

    public ProductFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragmentMap.get(position);
        if(fragment == null){
            if(position == 0){
                fragment = new ProductInfoFragment();
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
