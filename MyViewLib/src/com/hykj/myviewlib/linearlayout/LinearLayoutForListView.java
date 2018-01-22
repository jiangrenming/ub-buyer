package com.hykj.myviewlib.linearlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * 可以使用适配器的LinearLayout
 * 
 * @author gm
 *
 */
public class LinearLayoutForListView extends LinearLayout {

	public LinearLayoutForListView(Context context) {
		super(context);
	}

	public LinearLayoutForListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private BaseAdapter adapter;
	private OnClickListener onClickListener = null;

	/**
	 * 循环添加View
	 */
	public void setAdapter(BaseAdapter adapter) {
		this.setOrientation(VERTICAL); // 设置垂直布局
		this.adapter = adapter;
		int count = adapter.getCount();
		this.removeAllViews();
		for (int i = 0; i < count; i++) {
			View v = adapter.getView(i, null, null);
			v.setOnClickListener(this.onClickListener);
			addView(v, i);
		}
	}

	public void setOnItemListener(OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}
}
