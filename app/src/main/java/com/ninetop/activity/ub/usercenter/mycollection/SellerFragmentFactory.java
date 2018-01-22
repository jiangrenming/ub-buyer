package com.ninetop.activity.ub.usercenter.mycollection;

import com.ninetop.fragment.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huangjinding on 2017/5/9.
 */
public class SellerFragmentFactory {
    private static Map<Integer,BaseFragment>fragments=new HashMap<>();
    public static void clear(){
        fragments.clear();
    }
    public static BaseFragment create(int position){
       BaseFragment fragment=fragments.get(position);
        if(fragment==null){
            switch (position){
                case 0:
                fragment=new ProductFragment();
                break;
                case 1:
                    fragment=new SellerFragment();
                    break;
                case 2:
                    fragment=new FranchiseeFragment();
                    break;
            }
            if(fragment!=null){
                fragments.put(position,fragment);
            }
        }
      return fragment;
    }

}
