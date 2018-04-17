package youbao.shopping.ninetop.bean.order;

import java.io.Serializable;

/**
 * Created by jill on 2017/1/11.
 */

public class WeChatPayInfoBean implements Serializable{

    public WeChatPayBean payinfo;

    public WeChatPayBean getPayinfo() {
        return payinfo;
    }

    public void setPayinfo(WeChatPayBean payinfo) {
        this.payinfo = payinfo;
    }
}
