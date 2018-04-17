package youbao.shopping.ninetop.activity.user;


import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import youbao.shopping.ninetop.adatper.ReturnMoneyAdapter;
import youbao.shopping.ninetop.bean.user.ChangingOrRefundBean;
import youbao.shopping.ninetop.common.util.Tools;

public enum ChangingOrRefundingState implements IChangingOrRefunding {
    SUCCESSFUL_STATE("F","退款成功"){
        @Override
        public void update(Context ctx, ReturnMoneyAdapter.ViewHolder viewHolder, ChangingOrRefundBean.DataListBean orderBean, int position, ReturnMoneyAdapter returnAdapter) {
            viewHolder.llScaleReturn.setVisibility(View.VISIBLE);
            viewholderSetData(ctx,viewHolder, orderBean,getDescrible());
            viewHolder.llCancel.setVisibility(View.GONE);
        }
    },
    CLOSE_STATE("R","退款关闭") {
        @Override
        public void update(Context ctx, ReturnMoneyAdapter.ViewHolder viewHolder, ChangingOrRefundBean.DataListBean orderBean, int positon, ReturnMoneyAdapter returnAdapter) {
            viewHolder.llScaleReturn.setVisibility(View.VISIBLE);
            viewholderSetData(ctx,viewHolder, orderBean,getDescrible());
            viewHolder.llCancel.setVisibility(View.GONE);
        }
    },
    CLOSE_CANCEL("C","退款关闭") {
        @Override
        public void update(Context ctx, ReturnMoneyAdapter.ViewHolder viewHolder, ChangingOrRefundBean.DataListBean orderBean, int positon, ReturnMoneyAdapter returnAdapter) {
            viewHolder.llScaleReturn.setVisibility(View.VISIBLE);
            viewholderSetData(ctx,viewHolder, orderBean,getDescrible());
            viewHolder.llCancel.setVisibility(View.GONE);
        }
    },
    REFUNDING_STATE("M","退款中") {
        @Override
        public void update(Context ctx, ReturnMoneyAdapter.ViewHolder viewHolder, ChangingOrRefundBean.DataListBean orderBean, int positon, ReturnMoneyAdapter returnAdapter) {
            viewHolder.llScaleReturn.setVisibility(View.VISIBLE);
            viewholderSetData(ctx,viewHolder, orderBean,getDescrible());
            viewHolder.llCancel.setVisibility(View.GONE);
        }
    },
    REFUNDING_DISPOSE("P","退款处理中") {
        @Override
        public void update(Context ctx, ReturnMoneyAdapter.ViewHolder viewHolder, ChangingOrRefundBean.DataListBean orderBean, int positon, ReturnMoneyAdapter returnAdapter) {
            viewHolder.llScaleReturn.setVisibility(View.VISIBLE);
            viewholderSetData(ctx,viewHolder, orderBean,getDescrible());
            viewHolder.llCancel.setVisibility(View.VISIBLE);
            viewHolder.btnRight.setVisibility(View.VISIBLE);
            returnAdapter.onRightClickLis(positon,viewHolder);
        }
    },
    SCALE_RETURN("B","退货中") {
        @Override
        public void update(Context ctx, ReturnMoneyAdapter.ViewHolder viewHolder, ChangingOrRefundBean.DataListBean orderBean, int positon, ReturnMoneyAdapter returnAdapter) {
            viewHolder.llScaleReturn.setVisibility(View.VISIBLE);
            viewholderSetData(ctx,viewHolder, orderBean,getDescrible());
            viewHolder.llCancel.setVisibility(View.VISIBLE);
            viewHolder.btnRight.setVisibility(View.VISIBLE);
            returnAdapter.onRightClickLis(positon,viewHolder);

        }
    },
    WAIT_SCALE_RETURN("W","退货中") {
        @Override
        public void update(Context ctx, ReturnMoneyAdapter.ViewHolder viewHolder, ChangingOrRefundBean.DataListBean dataListBean, int position, ReturnMoneyAdapter returnMoneyAdapter) {

        }
    };
    private  String describle;
    private  String state;
    ChangingOrRefundingState(String state, String describle){
        this.state=state;
        this.describle=describle;
    }

    public String getState() {
        return state;
    }
    public String getDescrible() {
        return describle;
    }
    public void viewholderSetData(Context ctx, ReturnMoneyAdapter.ViewHolder viewHolder, ChangingOrRefundBean.DataListBean orderBean, String message) {
        viewHolder.tvMyOrderType.setText(message);
        viewHolder.tvMyOrderTime.setText(orderBean.createdTime);
        viewHolder.tvMyOrderProductName.setText(orderBean.goodsName);
        Double aaDouble = Double.valueOf(TextUtils.isEmpty(orderBean.price)?0+"":orderBean.price);
        String price = Tools.saveNum(aaDouble);
        Tools.ImageLoaderShow(ctx, orderBean.icon, viewHolder.ivMyOrderImage);
        viewHolder.tvMyOrderProductPrice.setText(price);
        viewHolder.tvMyOrderProductCount.setText(orderBean.amount);
        Double aDouble = Double.valueOf(TextUtils.isEmpty(orderBean.realPay)?0+"":orderBean.realPay);
        String realPay = Tools.saveNum(aDouble);
        viewHolder.tvDeal.setText(realPay);
        Double dreturnMoney = Double.valueOf(orderBean.returnMoney);
        String sreturnMoney = Tools.saveNum(dreturnMoney);
        viewHolder.tvRefund.setText(sreturnMoney);
    }

}
