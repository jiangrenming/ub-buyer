package com.ninetop.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import youbao.shopping.R;

public class ProgressBarView extends LinearLayout{

	private float maxValue = 100;

	private float minValue = 0; //最小值，默认0

	private float currentValue = 0;

	private View v_progress;

	private View v_progress_blank;

	private Context viewContext;

	public ProgressBarView(Context context, AttributeSet attrs) {
		super(context, attrs);

		viewContext = context;
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(getLayoutResId(), this);

		v_progress = view.findViewById(R.id.v_progress);
		v_progress_blank = view.findViewById(R.id.v_progress_blank);
	}

	protected int getLayoutResId(){
		return R.layout.view_progress_bar;
	}
	
	public void setValue(float value){
		currentValue = value;

//		int bgRes = R.drawable.bg_radius_gray;
		float ratio = currentValue / maxValue;
		if(ratio < 0){
			ratio = 0;
		}
		if(ratio > 1){
			ratio = 1;
		}

//		if(ratio >= 1){
//			bgRes = R.drawable.bg_radius_gray;
//		}else if(ratio > 0.7){
//			bgRes = R.drawable.bg_radius_red;
//		}else if(ratio > 0.5){
//			bgRes = R.drawable.bg_radius_yellow;
//		}else if(ratio > 0){
//			bgRes = R.drawable.bg_radius_green;
//		}
//		v_progress.setBackgroundResource(bgRes);

		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, ratio);
		v_progress_blank.setLayoutParams(lp);

		LayoutParams lp2 = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1 - ratio);
		v_progress.setLayoutParams(lp2);
	}
	
	public void setMaxValue(float maxValue){
		this.maxValue = maxValue;
	}

	public void setMinValue(float minValue) {
		this.minValue = minValue;
	}
	
}
