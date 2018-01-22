package com.ninetop.activity.ub.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by liwen on 2016/9/13.
 */
public class MyScrollView extends ScrollView {


    private onScrollChangedListener mListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mListener != null) {
            mListener.onScrollChanged(t);
        }

    }

    public void addOnScrollChangedListener(onScrollChangedListener listener) {
        mListener = listener;
    }

    public interface onScrollChangedListener {
        void onScrollChanged(int t);
    }
}
