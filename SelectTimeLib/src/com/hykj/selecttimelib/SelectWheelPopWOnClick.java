package com.hykj.selecttimelib;

/**
 * 监听两个按钮的点击事件
 * 
 * @author Administrator
 *
 */
public interface SelectWheelPopWOnClick {
	/**
	 * 点击确定事件
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 */
	public void sureOnClick(int index, String text);

	/**
	 * 点击取消事件
	 */
	public void cancelOnClick();

}
