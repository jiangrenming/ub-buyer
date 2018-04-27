package youbao.shopping.ninetop.bean;

import java.io.Serializable;

/**
 * Created by jiangrenming on 2018/4/27.
 */

public class BaseBean implements Serializable{
    private  String message;
    private  int code;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
