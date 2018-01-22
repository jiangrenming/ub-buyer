package com.ninetop.adatper.product;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ninetop.base.BaseActivity;
import com.ninetop.bean.order.OrderItemBean;
import com.ninetop.common.AssembleHelper;
import com.ninetop.common.constant.TextConstant;
import com.ninetop.common.util.Tools;

import java.util.ArrayList;
import java.util.List;

import youbao.shopping.R;

/**
 * Created by jill on 2016/11/15.
 */

public class OrderConfirmAdapter extends BaseAdapter {

    List<OrderItemBean> dataList;
    BaseActivity activity;
    private List<EditText> noteList = new ArrayList<>();

    public OrderConfirmAdapter(BaseActivity activity, List<OrderItemBean> dataList) {
        this.dataList = dataList;
        this.activity = activity;
    }

    public void setData(List<OrderItemBean> dataList) {
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged(){
        super.notifyDataSetChanged();
        noteList.clear();
    }

    public List<String> getNoteList(){
        if(noteList.size() == 0)
            return null;

        List<String> list = new ArrayList<>();
        for(EditText noteText : noteList){
            list.add(noteText.getText().toString().trim());
        }

        return list;
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
        if (convertView == null) {
            holderView = new HolderView();
            convertView = LayoutInflater.from(activity).inflate(R.layout.item_product_with_note, null);
            holderView.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holderView.tv_prop = (TextView) convertView.findViewById(R.id.tv_prop);
            holderView.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holderView.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            holderView.tv_note = (EditText) convertView.findViewById(R.id.tv_note);
            holderView.iv_product_image = (ImageView) convertView.findViewById(R.id.iv_product_image);
            convertView.setTag(holderView);

            noteList.add(holderView.tv_note);
        } else {
            holderView = (HolderView) convertView.getTag();
        }

        OrderItemBean bean = dataList.get(position);
        holderView.tv_prop.setText(AssembleHelper.assembleSku3(bean.paramsList));
        holderView.tv_name.setText(bean.itemName);
        holderView.tv_price.setText(TextConstant.MONEY + Tools.saveNum(Double.valueOf(bean.price)));
        holderView.tv_number.setText(TextConstant.MULTIPLY + bean.amount);
        Tools.ImageLoaderShow(activity, bean.pic, holderView.iv_product_image);

        return convertView;
    }

    class HolderView {
        ImageView iv_product_image;
        TextView tv_name;
        TextView tv_prop;
        TextView tv_price;
        TextView tv_number;
        EditText tv_note;

    }
}
