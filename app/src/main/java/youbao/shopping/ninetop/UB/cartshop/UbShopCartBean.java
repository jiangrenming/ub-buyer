package youbao.shopping.ninetop.UB.cartshop;

import youbao.shopping.ninetop.UB.product.New.ShopCartItemListBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangjinding on 2017/6/9.
 */

public class UbShopCartBean implements Serializable {
    public String franchiseeName;
    public int franchiseeId;
    public  boolean isShoperSelect;
    public List<ShopCartItemListBean> shopCartItemList;

    public String getFranchiseeName() {
        return franchiseeName;
    }

    public void setFranchiseeName(String franchiseeName) {
        this.franchiseeName = franchiseeName;
    }

    public int getFranchiseeId() {
        return franchiseeId;
    }

    public void setFranchiseeId(int franchiseeId) {
        this.franchiseeId = franchiseeId;
    }

    public boolean isShoperSelect() {
        return isShoperSelect;
    }

    public void setShoperSelect(boolean shoperSelect) {
        isShoperSelect = shoperSelect;
    }

    public List<ShopCartItemListBean> getShopCartItemList() {
        return shopCartItemList;
    }

    public void setShopCartItemList(List<ShopCartItemListBean> shopCartItemList) {
        this.shopCartItemList = shopCartItemList;
    }
}
