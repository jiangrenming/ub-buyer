package youbao.shopping.ninetop.UB.product.UbOrder;

import java.io.Serializable;

/**
 * Created by huangjinding on 2017/6/19.
 */

public class UbOrderBean implements Serializable {
    public int id;
    public String productName;
    public String productIcon;
    public int productSkuId;
    public int shopCartId;
    public String productSkuName;
    public String amount;
    public String unitPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public int getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(int productSkuId) {
        this.productSkuId = productSkuId;
    }

    public int getShopCartId() {
        return shopCartId;
    }

    public void setShopCartId(int shopCartId) {
        this.shopCartId = shopCartId;
    }

    public String getProductSkuName() {
        return productSkuName;
    }

    public void setProductSkuName(String productSkuName) {
        this.productSkuName = productSkuName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
}
