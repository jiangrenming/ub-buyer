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
    public List<ShopCartItemListBean> shopCartItemList;
}
