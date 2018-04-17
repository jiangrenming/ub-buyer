package youbao.shopping.ninetop.bean.user;

import java.util.List;

/**
 * Created by Administrator on 2018/3/21/021.
 */

public class RewardBean {
    /**
     * data : [{"id":592,"accounts":"15012790742","registerTime":"2018-03-28 16:16:22","getMoney":6.8,"getPoint":10}]
     * status : 200
     */

    private String status;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 592
         * accounts : 15012790742
         * registerTime : 2018-03-28 16:16:22
         * getMoney : 6.8
         * getPoint : 10
         */

        private int id;
        private String accounts;
        private String registerTime;
        private double getMoney;
        private int getPoint;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAccounts() {
            return accounts;
        }

        public void setAccounts(String accounts) {
            this.accounts = accounts;
        }

        public String getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(String registerTime) {
            this.registerTime = registerTime;
        }

        public double getGetMoney() {
            return getMoney;
        }

        public void setGetMoney(double getMoney) {
            this.getMoney = getMoney;
        }

        public int getGetPoint() {
            return getPoint;
        }

        public void setGetPoint(int getPoint) {
            this.getPoint = getPoint;
        }
    }
//    {"data":[{"id":592,"accounts":"15012790742","registerTime":"2018-03-28 16:16:22","getMoney":6.8,"getPoint":10}],"status":"200"}

}
