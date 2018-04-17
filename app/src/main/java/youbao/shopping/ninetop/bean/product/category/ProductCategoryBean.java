package youbao.shopping.ninetop.bean.product.category;

/**
 * @date: 2016/11/28
 * @author: Shelton
 * @version: 1.1.3
 * @Description:一级分类bean
 */
public class ProductCategoryBean {

    //
//    public List<DataBean> data;
//
//    public class DataBean {
    public int id;
    public String code;
    public String name;
    public String picUrl;

    @Override
    public String toString() {
        return "ProductCategoryBean{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", picUrl='" + picUrl + '\'' +
                '}';
    }

    /*  public String catCode;
    public String catName;
    public int id;*/
}

