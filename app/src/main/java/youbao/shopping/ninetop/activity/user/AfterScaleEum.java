package youbao.shopping.ninetop.activity.user;
/**
 * Created by li on 2016/12/13.
 */

public class AfterScaleEum {

    public  enum  RefundStyle implements IPickerViewKey{
        ONLY_REFUND("M","我要退款，但不退货"),REFUND_SCALERETURN("B","我要退款并退货");
        private String style;
        private String explain;

        private RefundStyle(String style, String mExplain){
            this.style = style;
            this.explain=mExplain;
        }
        public String getStyle(){
            return style;
        }
        public String getExplian() {
            return explain;
        }
        @Override
        public String getPickerViewText() {
            return explain;
        }

        @Override
        public String getPickerViewKey() {
            return style;
        }
    }

    public enum  RefundReason implements IPickerViewKey{
        UN_RECEIVED_GOODS("A","未收到货"),
        DESCRIBE_GOODS_UNLIKE("B","描述与商品不一致"),
        QUALITY_PROBLEM("C","质量问题"),
        OTHER("D","其他");
        private String reason;
        private String exPlain;
        private RefundReason(String reason,String mExplain){
            this.reason = reason;
            this.exPlain=mExplain;
        }

        public String getReason() {
            return reason;
        }

        public String getExplian() {
            return exPlain;
        }
        @Override
        public String getPickerViewText() {
            return exPlain;
        }

        @Override
        public String getPickerViewKey() {
            return reason;
        }
    }
}
