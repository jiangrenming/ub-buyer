package youbao.shopping.ninetop.bean.seller;

import java.io.Serializable;

/**
 * Created by huangjinding on 2017/5/8.
 */
public class SellerBannerBean implements Serializable {

    //shop_id 商家id
    //      type
    //              string
    //    1:商品 2:外部链接 3:无链接
    //            pic_url
    //    string
    //            图片url
    //    pic_value
    //            string
    //    当type1为商品ID，2是外部URL
    //            sort_index
    //    string
    //            排序值


    //    "data":[{"shop_id":1,"pic_value":"1",
    // "name":"tyj","id":1,"sort_index":1,
    //            "pic_url":"upload/banner/1493866975442.png","type":1},
    //    {"shop_id":1,"pic_value":"1","name":"222","id":3,
    //            "sort_index":1,"pic_url":"upload/unionshopbanner/1494078530091.png",
    //            "type":11},{"shop_id":1,"pic_value":"1","name":"PIC2","id":4,"sort_index":1,
    //            "pic_url":"upload/unionshopbanner/1494138727553.png","type":1}]
    public String id;
    public String name;
    public Number shop_id;
    public String type;
    public String pic_url;
    public String pic_value;
    public String sort_index;

    @Override
    public String toString() {
        return "SellerBannerBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", shop_id=" + shop_id +
                ", type='" + type + '\'' +
                ", pic_url='" + pic_url + '\'' +
                ", pic_value='" + pic_value + '\'' +
                ", sort_index='" + sort_index + '\'' +
                '}';
    }
}
