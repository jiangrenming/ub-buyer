package youbao.shopping.ninetop.adatper.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.bean.user.CouponBean;
import youbao.shopping.ninetop.service.impl.ProductService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;


/**
 * Created by jill on 2016/11/15.
 */

public class ProductCouponAdapter extends BaseAdapter {

    List<CouponBean> dataList;
    BaseActivity activity;

    ProductService productService = null;

    public ProductCouponAdapter(BaseActivity activity, List<CouponBean> dataList) {
        this.dataList = dataList;
        this.activity = activity;
        productService = new ProductService(activity);
    }

    public void setData(List<CouponBean> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HolderView holderView;
        final CouponBean bean = dataList.get(position);
        if (convertView == null) {

            holderView = new HolderView(bean);
            convertView = holderView.targetView;
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }


        holderView.tvMoney.setText(bean.faceValue + "");
        holderView.tvCondition.setText(getLimitText(bean));
        holderView.tvProductLimit.setText(bean.usageDesc);
        holderView.tvTime.setText(bean.startTime + "至" + bean.endTime);

        holderView.tvReceiveNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productService.receiveCoupon(bean.voucherId + "", new CommonResultListener<String>(activity) {
                    @Override
                    public void successHandle(String result) throws JSONException {
                        activity.showToast("领取成功");
                    }
                });
            }
        });

        return convertView;
    }

    private String getLimitText(CouponBean bean){
        if("N".equals(bean.limitType)){
            return "无条件";
        }

        return "满" + bean.limitValue + "元可用";
    }

    class HolderView {

        @BindView(R.id.tv_money)
        TextView tvMoney;
        @BindView(R.id.tv_condition)
        TextView tvCondition;
        @BindView(R.id.tv_product_limit)
        TextView tvProductLimit;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_receive_now)
        View tvReceiveNow;

        View targetView;

        HolderView(CouponBean bean) {
            targetView = LayoutInflater.from(activity).inflate(R.layout.item_product_coupon, null);

            ButterKnife.bind(this, targetView);


        }
    }
}
