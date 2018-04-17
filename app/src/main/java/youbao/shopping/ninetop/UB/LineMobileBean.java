package youbao.shopping.ninetop.UB;

import java.io.Serializable;

/**
 * Created by huangjinding on 2017/5/16.
 */
public class LineMobileBean implements Serializable {
    public Number id;
    public String mobile;

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


}
