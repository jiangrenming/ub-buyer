package youbao.shopping.ninetop.UB.product;

/**
 * Created by huangjinding on 2017/5/23.
 */
public class ProductBannerBean {
    public int id;
    public String name;
    public int type;
    public String pic_url;
    public String pic_value;
    public int show;
    public int sort_index;

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

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public int getShow() {
        return show;
    }

    public void setShow(int show) {
        this.show = show;
    }

    public String getPic_value() {
        return pic_value;
    }

    public void setPic_value(String pic_value) {
        this.pic_value = pic_value;
    }

    public int getSort_index() {
        return sort_index;
    }

    public void setSort_index(int sort_index) {
        this.sort_index = sort_index;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
