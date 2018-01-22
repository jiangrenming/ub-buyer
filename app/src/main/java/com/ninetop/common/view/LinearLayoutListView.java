package com.ninetop.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * Created by jill on 2016/12/16.
 */

public class LinearLayoutListView extends LinearLayout {
    private BaseAdapter adapter;
    private OnClickListener onClickListener = null;

    /**
     * 绑定布局
     */

    public void bindLinearLayout() {
        removeAllViews();

        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            View v = adapter.getView(i, null, null);
            addView(v, i);
        }
    }

    public LinearLayoutListView(Context context) {
        super(context);

    }

    public LinearLayoutListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 获取Adapter
     *
     * @return adapter
     */
    public BaseAdapter getAdpater() {
        return adapter;
    }

    /**
     * 设置数据
     *
     * @param adpater
     */
    public void setAdapter(BaseAdapter adpater) {
        this.adapter = adpater;
        bindLinearLayout();
    }

    /**
     * 获取点击事件
     *
     * @return
     */
    public OnClickListener getOnclickListner() {
        return onClickListener;
    }

    /**
     * 设置点击事件
     *
     * @param onClickListener
     */
    public void setOnclickLinstener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
