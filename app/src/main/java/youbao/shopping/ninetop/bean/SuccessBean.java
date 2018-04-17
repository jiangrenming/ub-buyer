package youbao.shopping.ninetop.bean;

/**
 * Created by Administrator on 2018/3/28/028.
 */

public class SuccessBean {
    /**
     * msg : success
     * code : 200
     * data : {"code":"023400"}
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
         * code : 023400
         */

        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
//    {"msg":"success","code":200,"data":{"code":"023400"}}

}
