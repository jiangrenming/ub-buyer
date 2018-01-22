package com.hykj.hytools.device.pixel;

import android.content.Context;
/**
 * @author abc
 *
 */
public interface HYPixelTools {
	/**
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public int dip2pix(Context context, float dpValue);
	/**
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public int pix2dip(Context context, float pxValue);
}
