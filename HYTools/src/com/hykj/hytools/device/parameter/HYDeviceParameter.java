package com.hykj.hytools.device.parameter;

import android.content.Context;

public interface HYDeviceParameter {
	/**
	 * @param context
	 * @return
	 */
	public int getStatusBarHeight(Context context);
	/**
	 * @param context
	 * @return
	 */
	public int getScreenWidth(Context context);
	/**
	 * @param context
	 * @return
	 */
	public int getScreenHeight(Context context);
}
