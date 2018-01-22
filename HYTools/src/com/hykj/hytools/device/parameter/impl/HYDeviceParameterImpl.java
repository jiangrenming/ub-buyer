package com.hykj.hytools.device.parameter.impl;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import com.hykj.hytools.device.parameter.HYDeviceParameter;

public class HYDeviceParameterImpl implements HYDeviceParameter {

	@Override
	public int getStatusBarHeight(Context context) {
		int result = 0;
		int resourceId = context.getResources().getIdentifier(
				"status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	}

	@SuppressWarnings("deprecation")
	@Override
	public int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();
	}

}
