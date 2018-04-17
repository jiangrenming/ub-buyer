package youbao.shopping.ninetop.UB.product.UbOrder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by huangjinding on 2017/6/19.
 */

public class UbMainOrderBean implements Serializable {
    public int franchiseeId;
    public String franchiseeName;
    public List<UbOrderBean> attrList;

}
