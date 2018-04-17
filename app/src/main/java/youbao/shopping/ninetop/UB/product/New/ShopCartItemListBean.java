package youbao.shopping.ninetop.UB.product.New;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangjinding on 2017/6/9.
 */

public class ShopCartItemListBean implements Serializable {
    public int amount;
    public int createUserId;
    public int productId;
    public String createTime;
    public String icon;
    public int shopCartId;
    public int stock;
    public String productName;
    public double productPrice;
    public int skuId;
    public List<SingleSkuBean> attrList;
    public  int franchiseeId;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(int createUserId) {
        this.createUserId = createUserId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getShopCartId() {
        return shopCartId;
    }

    public void setShopCartId(int shopCartId) {
        this.shopCartId = shopCartId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }

    public List<SingleSkuBean> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<SingleSkuBean> attrList) {
        this.attrList = attrList;
    }

    public int getFranchiseeId() {
        return franchiseeId;
    }

    public void setFranchiseeId(int franchiseeId) {
        this.franchiseeId = franchiseeId;
    }

    @Override
    public String toString() {
        return "ShopCartItemListBean{" +
                "amount=" + amount +
                ", createUserId=" + createUserId +
                ", productId=" + productId +
                ", createTime='" + createTime + '\'' +
                ", icon='" + icon + '\'' +
                ", shopCartId=" + shopCartId +
                ", stock=" + stock +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", skuId=" + skuId +
                ", attrList=" + attrList +
                ", franchiseeId=" + franchiseeId +
                '}';
    }
}