package youbao.shopping.ninetop.bean.user;

/**
 * Created by Administrator on 2018/3/27/027.
 */

public class RegisterBean {


    /**
     * msg :
     * code : 2000
     * data : {"money":"7.2","token":"d9ed235d86d673f567ced67ea31afe3eda8ef95f"}
     */

    private String msg;
    private int code;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * money : 7.2
         * token : d9ed235d86d673f567ced67ea31afe3eda8ef95f
         */

        private String money;
        private String token;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
