package youbao.shopping.ninetop.activity.ub.usercenter.myWallet;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import youbao.shopping.ninetop.activity.ub.bean.product.BalanceBean;
import youbao.shopping.ninetop.base.BaseActivity;

import java.util.List;

import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/6/26.
 */

public class MyWalletBBAdapter extends BaseAdapter {

    BaseActivity activity;
    List<BalanceBean> dataList;

    public MyWalletBBAdapter(BaseActivity activity, List<BalanceBean> dataList) {
        this.activity = activity;
        this.dataList = dataList;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final HolderView holderView;
        if (convertView == null) {
            holderView = new HolderView();
            convertView = LayoutInflater.from(activity).inflate(R.layout.ub_item_mywallet, null);
            holderView.iv_balance = (ImageView) convertView.findViewById(R.id.iv_balance);
            holderView.tv_name = (TextView) convertView.findViewById(R.id.tv_type);
            holderView.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holderView.tv_amount = (TextView) convertView.findViewById(R.id.tv_detail);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        final BalanceBean walletBean = dataList.get(position);
        if (walletBean.is_income==0){
            holderView.iv_balance.setImageResource(R.mipmap.expenditure);
        }
        if (walletBean.is_income==1){
            holderView.iv_balance.setImageResource(R.mipmap.income);
        }

        holderView.tv_name.setText(walletBean.type);
        holderView.tv_date.setText(walletBean.create_time);
        holderView.tv_amount.setText(walletBean.amount);
        return convertView;
    }

    class HolderView {
        ImageView iv_balance;
        TextView tv_name;
        TextView tv_date;
        TextView tv_amount;
    }
}
