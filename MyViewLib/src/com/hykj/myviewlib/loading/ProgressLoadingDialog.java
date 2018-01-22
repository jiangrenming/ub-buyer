package com.hykj.myviewlib.loading;

import com.hykj.myviewlib.R;
import com.hykj.myviewlib.progress.ProgressWheel;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

/**
 * 加载框，但是meuzu上显示有bug
 * 
 * @author gm
 *
 */
public class ProgressLoadingDialog extends Dialog {

	private ProgressWheel progress;
	private TextView text;

	public ProgressLoadingDialog(Context context) {
		super(context, R.style.LoadingDialog);
	}

	public ProgressLoadingDialog(Context context, int theme) {
		super(context, R.style.LoadingDialog);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.process_loading_dialog);
		setCanceledOnTouchOutside(false);
		initView();
	}

	private void initView() {
		progress = (ProgressWheel) findViewById(R.id.progress);
		text = (TextView) findViewById(R.id.text);
	}

	public void setColor(String color) {
		progress.setBarColor(Color.parseColor(color));
		text.setTextColor(Color.parseColor(color));
	}
}
