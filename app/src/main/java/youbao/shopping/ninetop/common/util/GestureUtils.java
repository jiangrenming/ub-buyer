package youbao.shopping.ninetop.common.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 手势工具类
 * 
 * @author yanshengli
 * @since 2015-2-10
 */
public class GestureUtils 
{

	/**
	 * 获取屏幕的大小
	 * @param context
	 * @return
	 */
	public static Screen getScreenPix(Context context) 
	{
		DisplayMetrics dm = new DisplayMetrics();
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		windowManager.getDefaultDisplay().getMetrics(dm);
		return new Screen(dm.widthPixels, dm.heightPixels);
	}

	/**
	 * 屏幕
	 * @author yanshengli
	 * @since 2015-2-10
	 */
	public static class Screen 
	{
		public int widthPixels;
		public int heightPixels;
		
		public Screen() {

		}

		public Screen(int widthPixels, int heightPixels)
		{
			this.widthPixels = widthPixels;
			this.heightPixels = heightPixels;
		}

		@Override
		public String toString() {
			return "(" + widthPixels + "," + heightPixels + ")";
		}
	}
}
