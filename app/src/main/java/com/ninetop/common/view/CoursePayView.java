package com.ninetop.common.view;

import youbao.shopping.R;

import android.content.Context;
import android.util.AttributeSet;

public class CoursePayView extends PayView{
	

	public CoursePayView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.view_pay_course;
	}
	
	@Override
	protected String formatWalletText(String balance) {
		return "(余额:" + balance + ")";
	}

}
