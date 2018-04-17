package youbao.shopping.ninetop.activity.product;

import youbao.shopping.ninetop.fragment.category.SecondCategoryFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * @date: 2016/12/13
 * @author: Shelton
 * @version: 1.1.3
 * @Description:CategorySecondFragmentManager 复用fragment
 */
public class CategorySecondFragmentManager {
    private static Map<String, SecondCategoryFragment> fragmentMap = new HashMap<>();

    public static SecondCategoryFragment getInstance(String code) {
        SecondCategoryFragment fragment = fragmentMap.get(code);
        if (fragment == null) {
            fragment = new SecondCategoryFragment(code);
        }
        if (fragment != null) {
            //缓存下来
            fragmentMap.put(code, fragment);
        }
        return fragment;
    }
}
