package com.ninetop.activity.ub.util.newPop;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.ninetop.UB.SellerCategoryBean;

import java.util.List;

import youbao.shopping.R;

public class SellerPopWindow extends PopupWindow implements OnItemClickListener {

    private Context mContext;
    private ListView mListView;
    private SellerPopAdapter mAdapter;
    private SellerPopAdapter.IOnItemSelectListener mItemSelectListener;
    private List<SellerCategoryBean> dataList;

    public SellerPopWindow(Context context, List<SellerCategoryBean> sdataList) {
        dataList = sdataList;
        mContext = context;
        init();
    }


    public void setItemListener(SellerPopAdapter.IOnItemSelectListener listener) {
        mItemSelectListener = listener;
    }

    private void init() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ub_spinner_pop_window, null);
        setContentView(view);
        setWidth(LayoutParams.WRAP_CONTENT);
        setHeight(LayoutParams.WRAP_CONTENT);

        setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x00);
        setBackgroundDrawable(dw);


        mListView = (ListView) view.findViewById(R.id.listview);
        mAdapter = new SellerPopAdapter(mContext, dataList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
    }

    public void refreshData(List<SellerCategoryBean> list, int selIndex) {
        if (list != null && selIndex != -1) {
            mAdapter.refreshData(list, selIndex);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int pos, long arg3) {
        dismiss();
        if (mItemSelectListener != null) {
            mItemSelectListener.onItemClick(pos);
        }
    }


}
