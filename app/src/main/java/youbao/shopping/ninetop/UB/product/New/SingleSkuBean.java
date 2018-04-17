package youbao.shopping.ninetop.UB.product.New;

import java.io.Serializable;

/**
 * Created by huangjinding on 2017/6/9.
 */

public class SingleSkuBean implements Serializable{
    public int attrId;
    public int attrValueId;
    public String attrName;
    public String attrValue;

    public int getAttrId() {
        return attrId;
    }

    public void setAttrId(int attrId) {
        this.attrId = attrId;
    }

    public int getAttrValueId() {
        return attrValueId;
    }

    public void setAttrValueId(int attrValueId) {
        this.attrValueId = attrValueId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    public void setAttrValue(String attrValue) {
        this.attrValue = attrValue;
    }
}
