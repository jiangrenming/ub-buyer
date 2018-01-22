package com.hykj.selecttimelib;

/**
 * 监听两个按钮的点击事件
 * 
 * @author Administrator
 *
 */
public interface SelectTimeWheelPopWOnClick {
	/**
	 * 点击确定事件
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 */
	public void sureOnClick(int year, int month, int day, int hour, int minute);

	/**
	 * 点击取消事件
	 */
	public void cancelOnClick();

}
