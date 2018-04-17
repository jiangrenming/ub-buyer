package youbao.shopping.ninetop.UB.order;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangjinding on 2017/6/15.
 */

public class UbOrderBean implements Serializable{
    public int orderFrom;
    public int takeType;
    public int receiverId;
    public String remark;
    public List<OrderProductListBean> productList;

    public int getOrderFrom() {
        return orderFrom;
    }

    public void setOrderFrom(int orderFrom) {
        this.orderFrom = orderFrom;
    }

    public int getTakeType() {
        return takeType;
    }

    public void setTakeType(int takeType) {
        this.takeType = takeType;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<OrderProductListBean> getProductList() {
        return productList;
    }

    public void setProductList(List<OrderProductListBean> productList) {
        this.productList = productList;
    }
}
