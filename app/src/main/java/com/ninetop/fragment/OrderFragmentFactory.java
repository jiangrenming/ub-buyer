package com.ninetop.fragment;

import java.util.HashMap;
import java.util.Map;

/**
 * @date: 2016/11/16
 * @author: Shelton
 * @version: 1.1.3
 * @Description: 生产订单Fragment的工厂
 */
public class OrderFragmentFactory {
    private static Map<Integer, BaseOrderFragment> fragents = new HashMap<>();

    public static void clear(){
        fragents.clear();
    }

    public static BaseOrderFragment create(int position) {
        BaseOrderFragment fragment = fragents.get(position);//从缓存中获取
        if (fragment == null) { //如果缓存中没有 创建新的对象 添加到缓存中
            switch (position) {
//                case 0:
//                    fragment = new OrderAllFragment();
//                    break;
                case 0:
                    fragment = new OrderWaitingPayFragments();
                    break;
                case 1:
                    fragment = new OrderWillSendFragment();
                    break;
                case 2:
                    fragment = new OrderWillReceiveFragment();
                    break;
                case 3:
                    fragment = new OrderWillGetFragment();
                    break;
                case 4:
                    fragment = new OrderFinishFragment();
                    break;
            }
            if (fragment != null) {
                //缓存下来
                fragents.put(position, fragment);
            }
        }
        return fragment;
    }
}
