package youbao.shopping.ninetop.bean.user.order;

import java.io.Serializable;

/**
 * @date: 2016/12/12
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class EvaluateGoodsBean implements Serializable{
    public String goodsCode;
    public String icon;
    public String skuDesc;

    @Override
    public String toString() {
        return "EvaluateGoodsBean{" +
                "goodsCode='" + goodsCode + '\'' +
                ", icon='" + icon + '\'' +
                ", skuDesc='" + skuDesc + '\'' +
                '}';
    }
}
