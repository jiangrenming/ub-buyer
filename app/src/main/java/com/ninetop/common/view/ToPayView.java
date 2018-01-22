package com.ninetop.common.view;

import youbao.shopping.R;

import android.content.Context;
import android.util.AttributeSet;

public class ToPayView extends PayView{
	

	public ToPayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.view_to_pay;
	}

}
