package youbao.shopping.ninetop.bean.product.category;

import java.io.Serializable;

/**
 * @date: 2016/11/28
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class SecondCategoryBean implements Serializable{
    public String code;
    public String picUrl;
    public String name;
    public int id;

    @Override
    public String toString() {
        return "SecondCategoryBean{" +
                "code='" + code + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
