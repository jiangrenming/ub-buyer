package youbao.shopping.ninetop.UB.order;

import java.io.Serializable;

/**
 * Created by huangjinding on 2017/6/15.
 */

public class HotCityBean implements Serializable{
    public String id;
    public String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
