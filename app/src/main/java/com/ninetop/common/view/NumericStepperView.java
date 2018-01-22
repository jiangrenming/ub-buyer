package com.ninetop.common.view;

import youbao.shopping.R;
import com.ninetop.common.util.Tools;
import com.ninetop.common.view.listener.DataChangeListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.R.attr.data;
import static android.R.attr.radius;

public class NumericStepperView extends LinearLayout{
	
	private int maxValue = Integer.MAX_VALUE;
	
	private int minValue = 1; //最小值，默认1
	
	private TextView tv_div;
	private TextView tv_number;
	private TextView tv_add;

	private Context viewContext;

	private DataChangeListener dataChangeListener = null;
	
	public NumericStepperView(Context context, AttributeSet attrs) {
		super(context, attrs);

		viewContext = context;
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(getLayoutResId(), this);
		
		tv_div = (TextView) view.findViewById(R.id.tv_div);
		tv_number = (TextView) view.findViewById(R.id.tv_number);
		tv_add = (TextView) view.findViewById(R.id.tv_add);
		
		tv_div.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int number = getNumberValue();
				if (number > minValue) {
					tv_number.setText(String.valueOf(number - 1));

					int currentNumber = number - 1;
					initDiv();

					if(dataChangeListener != null){
						dataChangeListener.handle(currentNumber);
					}
				}

			}
		});
		
		tv_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int number = getNumberValue();
				if(maxValue > 0 && number < maxValue){
					tv_number.setText(String.valueOf(number + 1));

					int currentNumber = number + 1;
					initDiv();

					if(dataChangeListener != null){
						dataChangeListener.handle(currentNumber);
					}
				}

			}
		});
	}

	public void setDataChangeListener(DataChangeListener dataChangeListener){
		this.dataChangeListener = dataChangeListener;
	}

	private int getNumberValue(){
		return Integer.parseInt(tv_number.getText().toString());
	}
	
	protected int getLayoutResId(){
		return R.layout.view_numeric_stepper;
	}
	
	public int getValue(){
		return Integer.parseInt(tv_number.getText().toString());
	}

	public void setValue(int value){
		tv_number.setText(value + "");

		initDiv();
	}

	private void initDiv(){
		if(tv_div == null)
			return;

		int currentNumber = getNumberValue();
		if(currentNumber > minValue){
			tv_div.setTextColor(Tools.getColorValueByResId(viewContext, R.color.text_black));
			tv_div.setEnabled(true);
		}else{
			tv_div.setTextColor(Tools.getColorValueByResId(viewContext, R.color.text_disable));
			tv_div.setEnabled(false);
		}
	}
	
	public void setMaxValue(int maxValue){
		this.maxValue = maxValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}
	
}
