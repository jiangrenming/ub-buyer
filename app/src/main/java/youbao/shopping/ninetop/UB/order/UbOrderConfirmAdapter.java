package youbao.shopping.ninetop.UB.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.bean.order.OrderItemBean;
import youbao.shopping.ninetop.common.AssembleHelper;
import youbao.shopping.ninetop.common.constant.TextConstant;
import youbao.shopping.ninetop.common.util.Tools;

import java.util.ArrayList;
import java.util.List;

import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/6/16.
 */

public class UbOrderConfirmAdapter extends BaseAdapter {

    BaseActivity activity;
    List<OrderItemBean> dataList;
    private List<EditText> noteList = new ArrayList<>();

    public UbOrderConfirmAdapter(BaseActivity activity, List<OrderItemBean> dataList) {
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
        HolderView holderView;
        if (convertView == null) {
            holderView = new HolderView();
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_product_with_note, null);
            holderView.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holderView.tv_prop = (TextView) convertView.findViewById(R.id.tv_prop);
            holderView.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            holderView.iv_product_image = (ImageView) convertView.findViewById(R.id.iv_product_image);
            noteList.add(holderView.tv_note);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        final OrderItemBean bean = dataList.get(position);
        holderView.tv_name.setText(bean.itemName);
        holderView.tv_number.setText(TextConstant.MULTIPLY + bean.amount);
        holderView.tv_price.setText(TextConstant.MONEY +Double.valueOf(bean.price));
        holderView.tv_prop.setText(AssembleHelper.assembleSku3(bean.paramsList));
        Tools.ImageLoaderShow(activity, bean.pic, holderView.iv_product_image);

        return convertView;
    }


    class HolderView {
        TextView tv_name;
        TextView tv_number;
        TextView tv_prop;
        TextView tv_price;
        EditText tv_note;
        ImageView iv_product_image;
    }
}
