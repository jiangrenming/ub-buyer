package youbao.shopping.ninetop.adatper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import youbao.shopping.ninetop.activity.user.ChangingOrRefundingState;
import youbao.shopping.ninetop.base.DefaultBaseAdapter;
import youbao.shopping.ninetop.bean.user.ChangingOrRefundBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;


public class ReturnMoneyAdapter extends DefaultBaseAdapter {

    private OnCanCelOnClickLis onCanCenlOnClickLis;
    public ReturnMoneyAdapter(List<ChangingOrRefundBean.DataListBean> datas, Context ctx) {
        super(datas, ctx);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(ctx, R.layout.item_scale_return, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ChangingOrRefundBean.DataListBean orderBean = (ChangingOrRefundBean.DataListBean) datas.get(position);
        if (orderBean == null) {
            return null;
        }
        String complaintstatus = orderBean.complaintstatus;
        String scale_return = ChangingOrRefundingState.SCALE_RETURN.getState();
        String close_state = ChangingOrRefundingState.CLOSE_STATE.getState();
        String refunding_dispose = ChangingOrRefundingState.REFUNDING_DISPOSE.getState();
        String refunding_state = ChangingOrRefundingState.REFUNDING_STATE.getState();
        String close_cancel = ChangingOrRefundingState.CLOSE_CANCEL.getState();
        String wait_scale_return = ChangingOrRefundingState.WAIT_SCALE_RETURN.getState();
        String successful_state = ChangingOrRefundingState.SUCCESSFUL_STATE.getState();
        if (scale_return.equals(complaintstatus)||wait_scale_return.equals(complaintstatus)){
            ChangingOrRefundingState.SCALE_RETURN.update(ctx,viewHolder,orderBean,position,this);
        }else if (close_state.equals(complaintstatus)||close_cancel.equals(complaintstatus)){
            ChangingOrRefundingState.CLOSE_STATE.update(ctx,viewHolder,orderBean,position,this);
        }else if (refunding_dispose.equals(complaintstatus)){
            ChangingOrRefundingState.REFUNDING_DISPOSE.update(ctx,viewHolder,orderBean,position,this);
        }else if (refunding_state.equals(complaintstatus)){
            ChangingOrRefundingState.REFUNDING_STATE.update(ctx,viewHolder,orderBean,position,this);
        }else if (successful_state.equals(complaintstatus)){
            ChangingOrRefundingState.SUCCESSFUL_STATE.update(ctx,viewHolder,orderBean,position,this);
        }else {
            viewHolder.llScaleReturn.setVisibility(View.GONE);
        }
        return convertView;
    }
    public interface OnCanCelOnClickLis {
        void cancel(int position);
    }
    public void setOnCanCelOnClickLis(OnCanCelOnClickLis onCanCelOnClickLis) {
        this.onCanCenlOnClickLis = onCanCelOnClickLis;
    }
    public void onRightClickLis(final int position, ReturnMoneyAdapter.ViewHolder viewHolder) {
        viewHolder.btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消退款
                if (onCanCenlOnClickLis != null) {
                    onCanCenlOnClickLis.cancel(position);
                }
            }
        });
    }
    public static class ViewHolder {
        @BindView(R.id.tv_my_order_time)
        public
        TextView tvMyOrderTime;
        @BindView(R.id.ll_scale_return)
        public
        LinearLayout llScaleReturn;
        @BindView(R.id.tv_my_order_type)
        public
        TextView tvMyOrderType;
        @BindView(R.id.iv_my_order_image)
        public
        ImageView ivMyOrderImage;
        @BindView(R.id.tv_my_order_product_name)
        public
        TextView tvMyOrderProductName;
        @BindView(R.id.tv_my_order_product_price)
        public
        TextView tvMyOrderProductPrice;
        @BindView(R.id.tv_my_order_product_count)
        public
        TextView tvMyOrderProductCount;
        @BindView(R.id.tv_deal)
        public
        TextView tvDeal;
        @BindView(R.id.tv_refund)
        public
        TextView tvRefund;
        @BindView(R.id.btn_right)
        public
        Button btnRight;
        @BindView(R.id.ll_cancel)
        public
        LinearLayout llCancel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
