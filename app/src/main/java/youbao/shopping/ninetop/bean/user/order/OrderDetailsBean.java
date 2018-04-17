package youbao.shopping.ninetop.bean.user.order;

import java.io.Serializable;
import java.util.List;

/**
 * @date: 2016/12/2
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class OrderDetailsBean implements Serializable{
   // public String code;
    //public Object errCode;
    /**
     * complaintstatus :
     * complaintId :
     * orderStatus : F
     * consigneeName : 收件人
     * consigneeMobile : 187671245894
     * remark : remark
     * realPay : 150.00
     * amount : 150.00
     * discount : 0.00
     * orderCode : 20161121194922
     * payWay :
     * orderedTime : 2016-11-21 19:49:54
     * isCommented : N
     * goodsList : [{"goodsCode":"123","icon":"http://7xuimw.com1.z0.glb.clouddn.com/9fc1f01b-4842-4a1b-ad59-afcdac76ea1b.jpg",
     * "goodsName":"SWAROVSKI 施华洛世奇 女士穿孔星星耳钉","price":"10.00","count":"1"},
     * {"goodsCode":"124","icon":"http://oh309wv8t.bkt.clouddn.com/image/commodity.jpg",
     * "goodsName":"商品1","price":"90.00","count":"1"}]
     */
    //public DataBean data;
   // public Object msg;
    //public Object action;
        public String complaintstatus;
        public String complaintId;
        public String orderStatus;
        public String consigneeName;
        public String consigneeMobile;
        public String remark;
        public String realPay;
        public String total;
        public String discount;
        public String orderCode;
        public String payWay;
        public String orderedTime;
        public String isCommented;
        public String timeLeft;
        public String consignDetailAddress;
        public String latestTime;
        public String latesDeliverInfo;

    /**
         * goodsCode : 123
         * icon : http://7xuimw.com1.z0.glb.clouddn.com/9fc1f01b-4842-4a1b-ad59-afcdac76ea1b.jpg
         * goodsName : SWAROVSKI 施华洛世奇 女士穿孔星星耳钉
         * price : 10.00
         * count : 1
         */

        public List<GoodsBean> goodsList;



}
