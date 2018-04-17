package youbao.shopping.ninetop.UB.cartshop;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by huangjinding on 2017/6/14.
 */

public class ChildListView extends ListView {

    public ChildListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public ChildListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ChildListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
