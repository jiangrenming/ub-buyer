package youbao.shopping.ninetop.bean.user;

public class CouponBean {
        public String name;
        public String level;
        public int voucherId;
        public String faceValue;
        public int useStatus;
        public String limitType;
        public String limitValue;
        public String usageDesc;
        public String startTime;
        public String endTime;
        public String isAvailable;

        @Override
        public String toString() {
                return "CouponBean{" +
                        "voucherId=" + voucherId +
                        ", faceValue=" + faceValue +
                        ", useStatus=" + useStatus +
                        ", limitType='" + limitType + '\'' +
                        ", limitValue=" + limitValue +
                        ", usageDesc='" + usageDesc + '\'' +
                        ", startTime='" + startTime + '\'' +
                        ", endTime='" + endTime + '\'' +
                        '}';
        }
}
