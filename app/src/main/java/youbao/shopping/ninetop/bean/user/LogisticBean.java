package youbao.shopping.ninetop.bean.user;


import java.util.List;

public class LogisticBean {
    /**
     * logisticname : 韵达快递
     * logisticcode : 3911731574580
     * trackstate : 3
     * trackList : [{"accepttime":"2016-11-26 21:25:08","acceptstation":"到达：浙江主城区公司杭州拱墅区新北站服务部 由 已签收 签收"},{"accepttime":"2016-11-26 20:34:58","acceptstation":"到达：浙江主城区公司杭州拱墅区新北站服务部 指定：齐国强(18268102399) 派送"}]
     */

    public String logisticname;
    public String logisticcode;
    public String trackstate;
    /**
     * accepttime : 2016-11-26 21:25:08
     * acceptstation : 到达：浙江主城区公司杭州拱墅区新北站服务部 由 已签收 签收
     */

    public List<TrackListBean> trackList;

    public  class TrackListBean {
        public String accepttime;
        public String acceptstation;
    }
}
