package youbao.shopping.ninetop.common;

import youbao.shopping.ninetop.UB.product.New.SingleSkuBean;
import youbao.shopping.ninetop.bean.product.ProductParamBean;
import youbao.shopping.ninetop.bean.product.ProductSkuValueBean;

import java.util.List;

/**
 * Created by jill on 2017/1/6.
 */

public class AssembleHelper {

    public static String assembleSku(List<ProductSkuValueBean> valueList){
        if(valueList == null || valueList.size() == 0) {
            return "";
        }
        String rs = "";
        for(ProductSkuValueBean value : valueList){
            rs += "“" + value.value+ "” ";
        }
        return rs;
    }

    public static String assembleSkuUb(List<SingleSkuBean> valueList){
        if(valueList == null || valueList.size() == 0) {
            return "";
        }
        String rs = "";
        for(SingleSkuBean value : valueList){
            rs += value.attrName + ":" + value.attrValue + ",";
        }
        rs = rs.substring(0, rs.length() - 1);
        return rs;
    }

    public static String assembleSku2(List<ProductParamBean> beanList){
        if(beanList == null || beanList.size() == 0){
            return "";
        }

        String rs = "";
        for(ProductParamBean bean : beanList){
            rs += bean.paramName + ":" + bean.paramValue + ";";

        }
        rs = rs.substring(0, rs.length() - 1);
        return rs;
    }

    public static String assembleSku3(List<ProductSkuValueBean> valueList){
        if(valueList == null || valueList.size() == 0){
            return "";
        }

        String rs = "";
        for(ProductSkuValueBean bean : valueList){
            rs += bean.name + ":" + bean.value + ";";

        }
        rs = rs.substring(0, rs.length() - 1);
        return rs;
    }
}
