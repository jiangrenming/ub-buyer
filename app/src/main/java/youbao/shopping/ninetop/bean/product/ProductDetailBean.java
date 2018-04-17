package youbao.shopping.ninetop.bean.product;

import java.util.List;

/**
 * Created by jill on 2016/11/30.
 */

public class ProductDetailBean {

    public String code;
    public String name;
    public String advtPoint;
    public String price;
    public List<ProductServiceBean> serviceList;
    public String providerName;
    public String commentCount;
    public List<ProductCommentBean> commentList;
//    public String paramsValueStr;
    public String skuId;
    public List<ProductSkuValueBean> skuList;
    public String couponName;

}
