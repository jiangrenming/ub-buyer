package youbao.shopping.ninetop.UB.product;

import java.io.Serializable;

/**
 * Created by huangjinding on 2017/5/27.
 */
public class UbProductDetailBean implements Serializable {
    private int id;
    private String name;
  //  private int price;
    private int base_freight;
    private String[] icon;
    private String mobile;
    private int is_favor;
    private String price_range;
    public String html_content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBase_freight() {
        return base_freight;
    }

    public void setBase_freight(int base_freight) {
        this.base_freight = base_freight;
    }

    public String[] getIcon() {
        return icon;
    }

    public void setIcon(String[] icon) {
        this.icon = icon;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIs_favor() {
        return is_favor;
    }

    public void setIs_favor(int is_favor) {
        this.is_favor = is_favor;
    }

    public String getPrice_range() {
        return price_range;
    }

    public void setPrice_range(String price_range) {
        this.price_range = price_range;
    }

    public String getHtml_content() {
        return html_content;
    }

    public void setHtml_content(String html_content) {
        this.html_content = html_content;
    }
}
