package youbao.shopping.ninetop.bean.index.category;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jill on 2016/11/29.
 */
public class CategoryListBean implements Serializable{

    private List<CategoryBean> menuList;

    public List<CategoryBean> getMenuList() {
        return menuList;
    }

}
