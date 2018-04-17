package youbao.shopping.ninetop.bean.user;



public class OrderCountBean {

    public String waitPayCount;
    public String waitSendCount;
    public String waitReceiveCount;
    public String waitEvaluateCount;
    public String returnOrChangeCount;

    @Override
    public String toString() {
        return "OrderCountBean{" +
                "waitPayCount='" + waitPayCount + '\'' +
                ", waitSendCount='" + waitSendCount + '\'' +
                ", waitReceiveCount='" + waitReceiveCount + '\'' +
                ", waitEvaluateCount='" + waitEvaluateCount + '\'' +
                ", returnOrChangeCount='" + returnOrChangeCount + '\'' +
                '}';
    }
}
