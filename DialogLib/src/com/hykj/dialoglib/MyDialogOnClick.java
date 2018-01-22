package com.hykj.dialoglib;

import android.view.View;

public interface MyDialogOnClick {
	/**
	 * 确认
	 * 
	 * @param v
	 */
	public void sureOnClick(View v);

	/**
	 * 取消
	 * 
	 * @param v
	 */
	public void cancelOnClick(View v);

}
