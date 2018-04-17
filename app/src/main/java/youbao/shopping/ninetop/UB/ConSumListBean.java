package youbao.shopping.ninetop.UB;

import java.io.Serializable;

/**
 * Created by huangjinding on 2017/5/16.
 */
public class ConSumListBean implements Serializable {
    public int id;
    public String order_code;
    public String union_shop_name;
    public String total_price;
    public String gain_point;
    public String create_time;

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getGain_point() {
        return gain_point;
    }

    public void setGain_point(String gain_point) {
        this.gain_point = gain_point;
    }

    public Number getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_code() {
        return order_code;
    }

    public void setOrder_code(String order_code) {
        this.order_code = order_code;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getUnion_shop_name() {
        return union_shop_name;
    }

    public void setUnion_shop_name(String union_shop_name) {
        this.union_shop_name = union_shop_name;
    }

}
