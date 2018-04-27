package youbao.shopping.ninetop.bean;

/**
 * Created by jiangrenming on 2018/4/27.
 */

public class MessageEvent {

    public   int nCount;
    public  int  type ;

    public MessageEvent(int count,int type) {
        this.nCount = count;
        this.type = type;
    }
}
