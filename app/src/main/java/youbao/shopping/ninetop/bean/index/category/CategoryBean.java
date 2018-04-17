package youbao.shopping.ninetop.bean.index.category;

import youbao.shopping.ninetop.bean.product.ProductBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jill on 2016/11/25.
 */
public class CategoryBean implements Serializable{

    private String catCode;
    private String catName;
    private List<ProductBean> itemList;

    public String getCatCode() {
        return catCode;
    }

    public String getCatName() {
        return catName;
    }

    public List<ProductBean> getItemList() {
        return itemList;
    }

    public String toString(){
        return catName;
    }
}
