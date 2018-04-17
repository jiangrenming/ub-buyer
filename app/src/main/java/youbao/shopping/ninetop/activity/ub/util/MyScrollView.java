package youbao.shopping.ninetop.activity.ub.util;

import android.content.Context;
import android.graphics.Rect;
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

    /**
     * 解决 由于子控件的大小导致ScrollView滚动到底部的问题
     *
     * @param rect
     * @return
     */
    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }


    public void addOnScrollChangedListener(onScrollChangedListener listener) {
        mListener = listener;
    }

    public interface onScrollChangedListener {
        void onScrollChanged(int t);
    }
}
