package com.hykj.dialoglib;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyDialog extends Dialog {

	public static final int DIALOG_ONEOPTION = 1;
	public static final int DIALOG_TWOOPTION = -1;

	private Button yes;
	private Button no;
	private TextView textText;
	private TextView dialogMsg;
	private String title;
	private String text;
	private String suretext;
	private String cancletext;
	private LinearLayout des;
	private View v;
	private int type;
	MyDialogOnClick onclick;
	MyDialog dialog;

	public MyDialog(Context context) {
		super(context);
	}

	public MyDialog(Context context, int theme) {
		super(context, R.style.myPopupWindow);
	}

	/**
	 * 
	 * @param activity
	 * @param type
	 *            DIALOG_ONEOPTION 表示一个按钮 DIALOG_TWOOPTION 表示两个按钮
	 * @param title
	 *            没有就传null
	 * @param text
	 *            没有就传null
	 * @param onclick
	 *            一个按钮时 监听写在sure回调中 cancle不进行回调
	 */
	public MyDialog(Activity activity, int type, String title, String text,
			MyDialogOnClick onclick) {
		super(activity, R.style.myPopupWindow);
		this.title = title;
		this.text = text;
		this.v = null;
		this.type = type;
		this.onclick = onclick;
		dialog = this;
	}

	/**
	 * 
	 * @param activity
	 * @param type
	 *            DIALOG_ONEOPTION 表示一个按钮 DIALOG_TWOOPTION 表示两个按钮
	 * @param title
	 *            没有就传null
	 * @param text
	 *            没有就传null
	 * @param suretext
	 *            确定按钮文字
	 * @param cancletext
	 *            取消按钮文字
	 * @param view
	 *            若显示信息无法用text显示 则传入view显示 提示信息
	 * @param onclick
	 *            一个按钮时 监听写在sure回调中 cancle不进行回调
	 */
	public MyDialog(Activity activity, int type, String title, String text,
			String suretext, String cancletext, View view,
			MyDialogOnClick onclick) {
		super(activity, R.style.myPopupWindow);
		this.title = title;
		this.text = text;
		this.type = type;
		this.suretext = suretext;
		this.cancletext = cancletext;
		this.v = view;
		this.onclick = onclick;
		dialog = this;
	}

	/**
	 * 
	 * @param activity
	 * @param type
	 *            DIALOG_ONEOPTION 表示一个按钮 DIALOG_TWOOPTION 表示两个按钮
	 * @param title
	 *            没有就传null
	 * @param text
	 *            没有就传null
	 * @param view
	 *            若显示信息无法用text显示 则传入view显示 提示信息
	 * @param onclick
	 *            一个按钮时 监听写在sure回调中 cancle不进行回调
	 */
	public MyDialog(Activity activity, int type, String title, String text,
			View view, MyDialogOnClick onclick) {
		super(activity, R.style.myPopupWindow);
		this.title = title;
		this.text = text;
		this.type = type;
		this.v = view;
		this.onclick = onclick;
		dialog = this;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (type == DIALOG_ONEOPTION) {
			this.setContentView(R.layout.popupwindow_one_dialog);
		} else {
			this.setContentView(R.layout.popupwindow_sure_dialog);
		}
		getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		initView();
		initAction();
	}

	private void initView() {
		if (type != DIALOG_ONEOPTION) {
			no = (Button) findViewById(R.id.dialog_no);
			if (cancletext == null || cancletext.equals("")) {
			} else {
				no.setText(cancletext);
			}
		}
		textText = (TextView) findViewById(R.id.text);
		if (title == null || title.equals("")) {
			textText.setVisibility(View.GONE);
		} else {
			textText.setText(title);
		}
		yes = (Button) findViewById(R.id.dialog_yes);
		if (suretext == null || suretext.equals("")) {
		} else {
			yes.setText(suretext);
		}
		dialogMsg = (TextView) findViewById(R.id.dialogMsg);
		if (title == null || text.equals("")) {
			dialogMsg.setVisibility(View.GONE);
		} else {
			dialogMsg.setText(text);
		}
		des = (LinearLayout) findViewById(R.id.des);
		if (v != null) {
			des.addView(v);
		}
	}

	private void initAction() {
		if (type != DIALOG_ONEOPTION) {
			no.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.dismiss();
					onclick.cancelOnClick(v);
				}
			});
		}
		yes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				onclick.sureOnClick(v);
			}
		});
	}
}
