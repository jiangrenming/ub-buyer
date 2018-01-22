package com.ninetop.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import youbao.shopping.R;
import com.ninetop.common.ActivityActionHelper;

/**
 * @date: 2016/12/7
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public abstract class OrderPageFrameLayout extends FrameLayout {
    private View emptyView;
    private View successView;

    public static final int STATUS_EMPTY = 0;
    public static final int STATUS_SUCCESS = 1;
    private int status = STATUS_SUCCESS;

    public OrderPageFrameLayout(Context context) {
        super(context);
        initLayout();
    }

    public OrderPageFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    public OrderPageFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout();
    }


    private void initLayout() {
        if (emptyView == null) {
            emptyView = createEmptyView();
            this.addView(emptyView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, -1));
        }

        showLayout();

    }

    private View createEmptyView() {
        View view = View.inflate(getContext(), R.layout.page_empty, null);
        view.findViewById(R.id.button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //show();
                ActivityActionHelper.goToMain(getContext());
            }
        });
        return view;
    }


    private void showLayout() {
        if (emptyView != null) {
            emptyView.setVisibility(status == STATUS_EMPTY ? View.VISIBLE : View.GONE);
        }
        if (status == STATUS_SUCCESS) {
            if (successView == null) {
                successView = createSuccessView();
                this.addView(successView, FrameLayout.LayoutParams.MATCH_PARENT, -1);
            } else {
                successView.setVisibility(View.VISIBLE);
            }
        } else {
            if (successView != null) {
                successView.setVisibility(View.GONE);
            }
        }
    }

    public void show() {
     /*   if (status == STATUS_EMPTY){
            status = STATUS_SUCCESS;
        }
        showLayout();*/
        
        status = RequestOrderList();
        showLayout();
    }

    protected abstract View createSuccessView();

    protected abstract int RequestOrderList();

}
