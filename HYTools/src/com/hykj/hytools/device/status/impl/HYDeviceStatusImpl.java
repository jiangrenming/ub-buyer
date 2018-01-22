package com.hykj.hytools.device.status.impl;


import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.PowerManager;

import com.hykj.hytools.device.status.HYDeviceStatus;

import java.util.List;

public class HYDeviceStatusImpl implements HYDeviceStatus {

	@Override
	public boolean isLockScreen(Context context) {
		PowerManager pm = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		boolean isScreenOn = false;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ECLAIR_MR1) {
			isScreenOn = pm.isScreenOn();
		}
		return isScreenOn;
	}

	@Override
	public boolean isAppOnForeground(Context context) {
		// Returns a list of application processes that are running on the
		// device

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = context.getPackageName();

		List<RunningAppProcessInfo> appProcesses = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.CUPCAKE) {
			appProcesses = activityManager
                    .getRunningAppProcesses();
		}
		if (appProcesses == null)
			return false;

		for (RunningAppProcessInfo appProcess : appProcesses) {
			// The name of the process that this object is associated with.
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}
		return false;
	}

}
