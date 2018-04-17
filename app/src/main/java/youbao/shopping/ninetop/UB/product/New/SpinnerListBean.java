package youbao.shopping.ninetop.UB.product.New;

import java.io.Serializable;

/**
 * Created by huangjinding on 2017/6/16.
 */

public class SpinnerListBean implements Serializable {
    public int id;
    public String name;
    public String distance;

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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
