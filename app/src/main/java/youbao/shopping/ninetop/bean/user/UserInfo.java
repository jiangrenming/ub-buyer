package youbao.shopping.ninetop.bean.user;


public class UserInfo {
        public String avatar;
        public String mobile;
        public String nickName;
        public String  sex;
        public boolean havePayPwd;
        public float u_balance;
        public float account_balance;

        @Override
        public String toString() {
                return "UserInfo{" +
                        "avatar='" + avatar + '\'' +
                        ", mobile='" + mobile + '\'' +
                        ", nickName='" + nickName + '\'' +
                        '}';
        }
}
