package com.ninetop.common.view;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import youbao.shopping.R;

public class ClearAbleEditText extends LinearLayout{

	private EditText et_text;
	private ImageView iv_clear;
	private Context context;

	public ClearAbleEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.view_edit_clearable, this);

		iv_clear = (ImageView) view.findViewById(R.id.iv_clear);
		et_text = (EditText) view.findViewById(R.id.et_text);

		et_text.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			public void onTextChanged(CharSequence s, int start, int before, int count) {}

			@Override
			public void afterTextChanged(Editable s) {
				int visible = s.length() > 0 ? View.VISIBLE : View.GONE;
				iv_clear.setVisibility(visible);
			}
		});

		iv_clear.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				et_text.setText("");
			}
		});
	}

	public String getText(){
		return et_text.getText().toString().trim();
	}

	public void setHintText(String hintText){
		et_text.setHint(hintText);
	}

	public void setMaxLength(int length){
		et_text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
	}

}
