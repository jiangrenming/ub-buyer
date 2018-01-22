package com.hykj.hytools.device.pixel.impl;

import android.content.Context;

import com.hykj.hytools.device.pixel.HYPixelTools;

public class HYPixelToolsImpl implements HYPixelTools {

	@Override
	public int dip2pix(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	@Override
	public int pix2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

}
