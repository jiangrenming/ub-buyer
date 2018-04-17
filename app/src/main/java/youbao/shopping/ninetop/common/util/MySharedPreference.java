package youbao.shopping.ninetop.common.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MySharedPreference {

	public static void save(String name, String value, Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences("text", Activity.MODE_PRIVATE);
		Editor editor = mySharedPreferences.edit();
		editor.putString(name, value);
		editor.commit();
	}
	public static void saveBoolean(String name, boolean value, Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences("text", Activity.MODE_PRIVATE);
		Editor editor = mySharedPreferences.edit();
		editor.putBoolean(name, value);
		editor.commit();
	}
	public static boolean getBoolean(String name, boolean defvalue, Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences("text", Activity.MODE_PRIVATE);
		return mySharedPreferences.getBoolean(name, defvalue);
	}

	@SuppressLint("NewApi")
	public static void saveBitmap(String key, Bitmap bitmap, Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences("text", Activity.MODE_PRIVATE);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 80, byteArrayOutputStream);
		byte[] byteArray = byteArrayOutputStream.toByteArray();
		String imageString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));

		Editor editor = mySharedPreferences.edit();
		editor.putString(key, imageString);
		editor.commit();
	}

	@SuppressLint("NewApi")
	public static Bitmap getBitmap(String key, String def, Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences("text", Activity.MODE_PRIVATE);
		String imageString = mySharedPreferences.getString(key, def);
		byte[] byteArray = Base64.decode(imageString, Base64.DEFAULT);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);
		Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);
		return bitmap;
	}

	public static String get(String name, String defvalue, Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences("text", Activity.MODE_PRIVATE);
		return mySharedPreferences.getString(name, defvalue);
	}
	
	/**
	 * 获取List对象
	 * @param key
	 * @param defvalue
	 * @param context
	 * @return
	 */
	public static List<String> getList(String key, List<String> defvalue, Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences("text", Activity.MODE_PRIVATE);
		String values = mySharedPreferences.getString(key, "");
		if(values == null || values.length() == 0){
			return defvalue;
		}
		
		String[] valueArray = values.split("`;");
		List<String> list = new ArrayList<>();
		for(String one : valueArray){
			if(one.length() != 0){
				list.add(one);
			}
		}
		return list;
	}
	
	/**
	 * 保存List,
	 * 保存时,将List中的字符串用"`;"拼接起来，保存为字符串
	 * 获取时，将LIst中的字符串用"`;"拆分
	 * 用"`;"
	 * @param key
	 * @param values
	 * @param context
	 */
	public static void saveList(String key, List<String> values, Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences("text", Activity.MODE_PRIVATE);
		String valueString = "";
		if(values != null && values.size() > 0){
			for(String one : values){
				valueString += one + "`;";
			}
			valueString = valueString.substring(0, valueString.length() - 2);
		}

		Editor editor = mySharedPreferences.edit();
		editor.putString(key, valueString);
		editor.commit();
	}

	public static void saveListAddOneValue(String key, String text, Context context){
		List<String> list = MySharedPreference.getList(key, null, context);
		if(list == null){
			list = new ArrayList<>();
			list.add(text);
		}else{
			for(String value : list){
				if(value.equals(text)){
					list.remove(value);
					break;
				}
			}
			list.add(0, text);

			while(list.size() > 5){
				list.remove(5);
			}
		}
		MySharedPreference.saveList(key, list, context);
	}

	public static void clear(Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences("text", Activity.MODE_PRIVATE);
		Editor editor = mySharedPreferences.edit();
		editor.remove("userId");
		editor.clear();
		editor.commit();
	}
	
	public static void remove(String key, Context context) {
		SharedPreferences mySharedPreferences = context.getSharedPreferences("text", Activity.MODE_PRIVATE);
		Editor editor = mySharedPreferences.edit();
		editor.remove(key);
		editor.commit();
	}
}
