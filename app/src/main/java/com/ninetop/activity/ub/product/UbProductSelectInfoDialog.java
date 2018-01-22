package com.ninetop.activity.ub.product;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ninetop.UB.product.UbProductParameterBean;
import com.ninetop.UB.product.UbProductService;
import com.ninetop.base.DefaultBaseAdapter;
import com.ninetop.common.util.DialogUtil;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/6/4.
 */

public class UbProductSelectInfoDialog {
    private UbProductInfoActivity activity;
    private UbProductService ubProductService;
    private int id;

    private AlertDialog targetDialog;

    public UbProductSelectInfoDialog(UbProductInfoActivity activity, UbProductService ubProductService
            , int id) {
        this.activity = activity;
        this.id = id;
        this.ubProductService = ubProductService;
    }

    public void showDialog() {
        View view = View.inflate(activity.getApplicationContext(), R.layout.ub_dialog_product_info, null);
        targetDialog = DialogUtil.createDialogBottom(activity, view);
        final ListView listView = (ListView) view.findViewById(R.id.listview);
        ubProductService.getProductParameter(id, new CommonResultListener<List<UbProductParameterBean>>(activity) {
            @Override
            public void successHandle(List<UbProductParameterBean> result) throws JSONException {
                ProductParamAdapter productParamAdapter = new ProductParamAdapter(result, activity);
                listView.setAdapter(productParamAdapter);
            }
        });

        View submitView = view.findViewById(R.id.btn_cansel);
        submitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetDialog.dismiss();
            }
        });
    }

    public class ProductParamAdapter extends DefaultBaseAdapter {
        public ProductParamAdapter(List<UbProductParameterBean> datas, Context context) {
            super(datas, context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = View.inflate(ctx, R.layout.ub_product_paremater_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            UbProductParameterBean couponBean = (UbProductParameterBean) datas.get(position);
            viewHolder.tvKey.setText(couponBean.getParam_name());
            viewHolder.tvValue.setText(couponBean.param_value);
            return convertView;
        }

        public class ViewHolder {
            @BindView(R.id.tv_key)
            TextView tvKey;
            @BindView(R.id.tv_value)
            TextView tvValue;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }


    }


}
