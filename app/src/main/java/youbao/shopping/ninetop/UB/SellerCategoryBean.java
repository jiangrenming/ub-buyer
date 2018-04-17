package youbao.shopping.ninetop.UB;

import java.io.Serializable;

/**
 * Created by huangjinding on 2017/5/17.
 */
public class SellerCategoryBean implements Serializable {
    public int id;
    public String name;
    public String icon_url;

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SellerCategoryBean(int id,String name,String icon_url){
        this.id=id;
        this.name=name;
        this.icon_url=icon_url;

    }
}
