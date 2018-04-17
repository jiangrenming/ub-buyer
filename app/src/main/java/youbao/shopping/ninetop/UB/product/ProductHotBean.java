package youbao.shopping.ninetop.UB.product;

import java.io.Serializable;

/**
 * Created by huangjinding on 2017/5/30.
 */
public class ProductHotBean implements Serializable {
    public String name;
    public String remark;
    public String id;
    public String order_num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_num() {
        return order_num;
    }

    public void setOrder_num(String order_num) {
        this.order_num = order_num;
    }
}
