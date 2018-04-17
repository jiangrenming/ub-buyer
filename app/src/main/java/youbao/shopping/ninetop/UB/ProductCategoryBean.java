package youbao.shopping.ninetop.UB;

import java.io.Serializable;

/**
 * Created by huangjinding on 2017/5/23.
 */
public class ProductCategoryBean implements Serializable {

    public int id;
    public String name;
    public String icon_url;
    public int order_num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public int getOrder_num() {
        return order_num;
    }

    public void setOrder_num(int order_num) {
        this.order_num = order_num;
    }
}
