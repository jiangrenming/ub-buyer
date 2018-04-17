package youbao.shopping.ninetop.bean.index.recommend;

import youbao.shopping.ninetop.bean.product.ProductBean;
import youbao.shopping.ninetop.bean.product.ProductSaleBean;

import java.util.List;

/**
 * Created by jill on 2016/11/25.
 */

public class RecommendBean {

    private List<ProductBean> recommendList;

    private List<ProductSaleBean> secKillList;

    public List<ProductBean> getRecommendList() {
        return recommendList;
    }

    public List<ProductSaleBean> getSecKillList() {
        return secKillList;
    }

}
