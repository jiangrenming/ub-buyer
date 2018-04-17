package youbao.shopping.ninetop.fragment.index;

import android.support.v4.app.Fragment;

import youbao.shopping.ninetop.bean.index.category.CategoryBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jill on 2016/11/26.
 */

public class IndexFragmentManager {

    private static Map<String, Fragment> fragmentMap = new HashMap<>();

    public static Fragment getInstance(CategoryBean bean){
        Fragment fragment = fragmentMap.get(bean.getCatCode());
        if(fragment == null){
            if("RECOMMEND".equals(bean.getCatCode())){
                fragment = new RecommendFragment();
            }else {
                CategoryProductFragment fragment2 = new CategoryProductFragment();
                fragment2.setCategory(bean);
                fragment = fragment2;
            }
            fragmentMap.put(bean.getCatCode(), fragment);
        }
        return fragment;
    }

    public static void clear(){
        fragmentMap.clear();
    }
}
