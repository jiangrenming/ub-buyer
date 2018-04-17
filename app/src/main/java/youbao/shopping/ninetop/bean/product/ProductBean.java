package youbao.shopping.ninetop.bean.product;

import java.io.Serializable;

/**
 * Created by jill on 2016/11/11.
 */

public class ProductBean implements Serializable {

    private String picUrl;
    private String price;
    private String name;
    private String code;
    private String qualityScore;

    public String getPicUrl() {
        return picUrl;
    }

//    public void setPicUrl(String picUrl) {
//        this.picUrl = picUrl;
//    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getQualityScore() {
        return qualityScore;
    }

    public void setQualityScore(String qualityScore) {
        this.qualityScore = qualityScore;
    }
}
