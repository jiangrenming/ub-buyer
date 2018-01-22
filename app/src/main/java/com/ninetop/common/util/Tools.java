package com.ninetop.common.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextPaint;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ninetop.common.constant.SharedKeyConstant;
import com.ninetop.common.util.image.MyImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
	/**
	 * 检查当前网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		// 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivityManager == null) {
			return false;
		} else {
			// 获取NetworkInfo对象
			NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

			if (networkInfo != null && networkInfo.length > 0) {
				for (int i = 0; i < networkInfo.length; i++) {
					//System.out.println(i + "===状态===" + networkInfo[i].getState());
					//System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
					// 判断当前网络状态是否为连接状态
					if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取屏幕的宽度
	 */

	@SuppressWarnings("deprecation")
	public static int getScreenWidth(Context context) {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getWidth();
	}

	/**
	 * 获取屏幕的高度
	 * 
	 */
	@SuppressWarnings("deprecation")
	public static int getScreenHeight(Context context) {
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = manager.getDefaultDisplay();
		return display.getHeight();
	}

	/**
	 * 获得状态栏的高度
	 * 
	 */
	public static int getStatusHeight(Context context) {
		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}

	/**
	 * 获取imageloader default
	 */
	public static ImageLoader ImageLoaderShow(Context activity, String logo, ImageView image) {
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(logo, image, MyImageLoader.getOption());
		return imageLoader;
	}
	
	/**
	 * 获取imageloader default
	 */
	public static ImageLoader ImageLoaderShow(Context activity, String logo, ImageView image, ImageLoadingListener listener) {
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(logo, image, MyImageLoader.getOption(), listener);
		return imageLoader;
	}
	

	public static ImageLoader MyImageLoaderShow(Context activity, String logo, ImageView image,DisplayImageOptions option) {
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(logo, image, option);
		return imageLoader;
	}
	
	public static ImageLoader HomeImageLoaderShow(Context activity, String logo, ImageView image) {
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(logo, image);
		return imageLoader;
	}
	
	
	/**
	 * 强制隐藏输入法键盘
	 */
	public static void hideInput(Context context, View view) {
		InputMethodManager inputMethodManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 打电话
	 * 
	 * @param activity
	 * @param callPhone
	 */
	public static void callPhone(Activity activity, String callPhone) {
		if (!"".equals(callPhone) && callPhone != null) {
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + callPhone));
//			activity.startActivity(intent);
		} else {
			ToastUtils.showCenter(activity, "没有获取到电话，无法拨打");
		}
	}

	/**
	 * 发短信
	 * 
	 * @param activity
	 * @param callPhone
	 */
	public static void sendMessage(Activity activity, String callPhone) {
		if (!"".equals(callPhone) && callPhone != null) {
			Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + callPhone));
			activity.startActivity(intent);
		} else {
			ToastUtils.showCenter(activity, "没有获取到电话，无法发送短信");
		}
	}

	/**
	 * String转date
	 */
	@SuppressLint("SimpleDateFormat")
	public static Date stringToDate(String date) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date newDate = sdf.parse(date);
			return newDate;
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * date转String
	 */
	@SuppressLint("SimpleDateFormat")
	public static String dateToString(Date date) {
		String newDate = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
		return newDate;
	}

	// 屏蔽特殊字符
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * date 转int
	 */
	public static int dateToint(String date) {
		int time = 0;
		String[] strArray = date.split(":");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int mun = calendar.get(Calendar.MINUTE);
		int sec = calendar.get(Calendar.SECOND);
		if (strArray.length > 0) {
			time += (Integer.valueOf(strArray[0]) - hour) * 60 * 60;
		}
		if (strArray.length > 1) {
			time += (Integer.valueOf(strArray[1]) - mun) * 60;
		}
		if (strArray.length > 2) {
			time += (Integer.valueOf(strArray[2]) - sec);
		} else {
			time += (-sec);
		}
		return time;
	}

	/**
	 * 动态设置ListView的高度
	 * 
	 * @param listView
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	public static void setGridViewHeightBasedOnChildren(GridView gridView) {
		// 获取GridView对应的Adapter
		ListAdapter listAdapter = gridView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int rows;
		int columns = 0;
		int horizontalBorderHeight = 0;
		Class<?> clazz = gridView.getClass();
		try {
			// 利用反射，取得每行显示的个数
			Field column = clazz.getDeclaredField("mRequestedNumColumns");
			column.setAccessible(true);
			columns = (Integer) column.get(gridView);
			// 利用反射，取得横向分割线高度
			Field horizontalSpacing = clazz.getDeclaredField("mRequestedHorizontalSpacing");
			horizontalSpacing.setAccessible(true);
			horizontalBorderHeight = (Integer) horizontalSpacing.get(gridView);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// 判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
		if (listAdapter.getCount() % columns > 0) {
			rows = listAdapter.getCount() / columns + 1;
		} else {
			rows = listAdapter.getCount() / columns;
		}
		int totalHeight = 0;
		for (int i = 0; i < rows; i++) { // 只计算每项高度*行数
			View listItem = listAdapter.getView(i, null, gridView);
			listItem.measure(0, 0); // 计算子项View 的宽高
			totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
		}
		ViewGroup.LayoutParams params = gridView.getLayoutParams();
		params.height = totalHeight + horizontalBorderHeight * (rows - 1);// 最后加上分割线总高度
		gridView.setLayoutParams(params);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}

		}
		return inSampleSize;
	}

	/**
	 * 添加中划线
	 * 
	 * @param textView
	 */
	public static void addTextViewLine(TextView textView) {
		textView.getPaint().setAntiAlias(true);// 抗锯齿
		textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG); // 设置中划线并加清晰
	}

	/**
	 * 判断登录
	 */
	public static boolean isLogin(Context context) {
		String data = MySharedPreference.get(SharedKeyConstant.TOKEN, "", context);
		if (data != null && data.length() > 0) {
			return true;
		}
		return false;
	}

	@SuppressLint("NewApi")
	public static String bitmapToByte(Bitmap bitmap) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 80, byteArrayOutputStream);
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		String imageString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
		return imageString;
	}

	// 数字转中文
	public static String numberToWord(int number) {
		// 单位数组
		String[] units = new String[] { "十", "百", "千", "万", "十", "百", "千", "亿" };
		// 中文大写数字数组
		String[] numeric = new String[] { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		String temp = String.valueOf(number);
		String res = "";
		// 遍历一行中所有数字
		for (int k = -1; temp.length() > 0; k++) {
			// 解析最后一位
			int j = Integer.parseInt(temp.substring(temp.length() - 1, temp.length()));
			String rtemp = numeric[j];

			// 数值不是0且不是个位 或者是万位或者是亿位 则去取单位
			if (j != 0 && k != -1 || k % 8 == 3 || k % 8 == 7) {
				rtemp += units[k % 8];
			}
			// 拼在之前的前面
			res = rtemp + res;
			// 去除最后一位
			temp = temp.substring(0, temp.length() - 1);
		}
		// 去除后面连续的零零..
		while (res.endsWith(numeric[0])) {
			res = res.substring(0, res.lastIndexOf(numeric[0]));
		}
		// 将零零替换成零
		while (res.indexOf(numeric[0] + numeric[0]) != -1) {
			res = res.replaceAll(numeric[0] + numeric[0], numeric[0]);
		}
		// 将 零+某个单位 这样的窜替换成 该单位 去掉单位前面的零
		for (int m = 1; m < units.length; m++) {
			res = res.replaceAll(numeric[0] + units[m], units[m]);
		}
		// 去掉一十改为十
		if (res.contains("一十")) {
			res = res.replace("一十", "十");
		}
		return res;
	}
	
//	public static String uploadImage(String url, String imagepath,String key) {
//		File file = new File(imagepath);
//		String result = "";
//		if (!file.exists()) {
//			return null;
//		}
////		Bitmap bitmap = BitmapFactory.decodeFile(imagepath);
////		Bitmap newBitmap = Tools.comp(bitmap);
////		if (file.exists()) {
////			file.delete();
////		}
////		FileOutputStream fos = null;
////		try {
////			fos = new FileOutputStream(file);
////		} catch (FileNotFoundException e1) {
////			e1.printStackTrace();
////		}
////		if (null != fos) {
////			newBitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
////			try {
////				fos.flush();
////			} catch (IOException e) {
////				e.printStackTrace();
////			}
////			try {
////				fos.close();
////			} catch (IOException e) {
////				e.printStackTrace();
////			}
////		}
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpPost post = new HttpPost(url);
//		System.out.println(">>"+url);
//		FileBody fileBody = new FileBody(file, "image/jpeg");
//		MultipartEntity multipartEntity = new MultipartEntity();
//		multipartEntity.addPart(key, fileBody);
//		post.setEntity(multipartEntity);
//		try {
//			HttpResponse httpResponse = httpClient.execute(post);
//			int code = httpResponse.getStatusLine().getStatusCode();
//			result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
//			if (code == 201) {
//				// 成功操作
//			}
//			return result;
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return result;
//	}
	
	
	
	public static String replaceAll(String str){
		str = str.replaceAll("\r\n", "<br>");
		str = str.replaceAll("\r", "<br>");
		str = str.replaceAll("\n", "<br>");
		
		return  str;
	}
	
	public static String replaceAll2(String str){
		str = str.replaceAll( "<br>","\r\n");
		str = str.replaceAll( "<br>","\r");
		str = str.replaceAll( "<br>","\n");
		
		return  str;
	}
	
	/**
	 * 保留两位小数
	 * @param num
	 * @return
	 */
	public static String saveNum(Double num){
		String str = "0.00";
		DecimalFormat df = new DecimalFormat("0.00");
		str = df.format(num);
		return str;
	}
	
	@SuppressWarnings("deprecation")
	public static int getPhoneAndroidSDK() {
		int version = 0;
		try {
			version = Integer.valueOf(android.os.Build.VERSION.SDK);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return version;
	}

	@SuppressWarnings("deprecation")
	public static ColorStateList getColorByResId(Context context, int resId){
		Resources resource = (Resources) context.getResources();
		return (ColorStateList) resource.getColorStateList(resId);
	}

	@SuppressWarnings("deprecation")
	public static int getColorValueByResId(Context context, int resId){
		Resources resource = (Resources) context.getResources();
		return resource.getColor(resId);
	}

	public static float getTextViewLength(TextView textView,String text){
		TextPaint paint = textView.getPaint();
		// 得到使用该paint写上text的时候,像素为多少
		float textLength = paint.measureText(text);
		return textLength;
	}

	/**读取assets本地地址列表
	 */
	public static String getAddressJson(Context context){
		String trim="";
		try {
			InputStream open = context.getAssets().open("city.json");
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

			byte[] by = new byte[1024];
			byte[] bytes = new byte[0];
			while (true) {

				int code = open.read(by);
				if (code == -1) {
					break;
				}
				byteArrayOutputStream.write(by, 0, code);

				bytes = byteArrayOutputStream.toByteArray();
			}
			trim = new String(bytes).trim();
			open.close();
			byteArrayOutputStream.close();
			//   Log.i("i",trim+"=");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return  trim;
	}


}
