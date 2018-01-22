package com.hykj.hytools.device.status;


import android.content.Context;

/**
 * @author abc
 *
 */
public interface HYDeviceStatus {
	/**
	 * @param context
	 * @return
	 */
	public boolean isLockScreen(Context context);
	
	/**
	 *
	 * @return
	 */
	public boolean isAppOnForeground(Context context);
}
