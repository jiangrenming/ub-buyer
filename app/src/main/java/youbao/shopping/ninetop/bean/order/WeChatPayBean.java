package youbao.shopping.ninetop.bean.order;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jill on 2017/1/10.
 */

public class WeChatPayBean implements Serializable{

    public String appid;
    public String noncestr;

    @SerializedName("package")
    public String packageinfo;
    public String partnerid;
    public String prepayid;
    public String sign;
    public String timestamp;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageinfo() {
        return packageinfo;
    }

    public void setPackageinfo(String packageinfo) {
        this.packageinfo = packageinfo;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
