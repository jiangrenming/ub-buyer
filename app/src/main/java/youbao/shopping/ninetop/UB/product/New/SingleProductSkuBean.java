package youbao.shopping.ninetop.UB.product.New;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangjinding on 2017/6/9.
 */

public class SingleProductSkuBean implements Serializable {
      public Double originalPrice;
      public Double salePrice;
      public String stock;
      public List<SingleSkuBean> attrList;
      public int skuId;

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public List<SingleSkuBean> getAttrList() {
        return attrList;
    }

    public void setAttrList(List<SingleSkuBean> attrList) {
        this.attrList = attrList;
    }

    public int getSkuId() {
        return skuId;
    }

    public void setSkuId(int skuId) {
        this.skuId = skuId;
    }
}
