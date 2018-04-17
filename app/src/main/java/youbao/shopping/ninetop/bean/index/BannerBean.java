package youbao.shopping.ninetop.bean.index;



/**
 * Created by jill on 2016/11/24.
 */

public class BannerBean {

    private String picUrl;
    private String link;
    private String title;
    private String type; //1:商品 2:新手礼包 3:秒杀 4:外部链接 5:无链接

    public String getPicUrl() {
        return picUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
