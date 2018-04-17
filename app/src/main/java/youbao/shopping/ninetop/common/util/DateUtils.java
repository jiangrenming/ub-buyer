package youbao.shopping.ninetop.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	private static SimpleDateFormat sf = null;

	/* 获取系统时间 格式为："yyyy/MM/dd " */
	public static  String getCurrentDate() {
		Date d = new Date();
		sf = new SimpleDateFormat("yyyy/MM/ddHH:mm:ss");
		return sf.format(d);
	}

	/* 时间戳转换成字符窜 */
	public static String getDateToString(long time) {
		Date d = new Date(time);
		sf = new SimpleDateFormat("yyyy/MM/ddHH:mm:ss");
		return sf.format(d);
	}

	/* 将字符串转为时间戳 */
	public static  long getStringToDate(String time) {
		sf = new SimpleDateFormat("yyyy/MM/ddHH:mm:ss");
		Date date = new Date();
		try {
			date = sf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date.getTime();
	}
	
	/* 将字符串转为时间戳 */
	public static  long getString2Date(String time) {
		sf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try {
			date = sf.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date.getTime();
	}
	
	/* 将字符串转为时间戳 */
	public static long getStringToDate(String time, String formatString) {
		sf = new SimpleDateFormat(formatString);
		Date date = new Date();
		try {
			date = sf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}
	
	/**
	 * 判断传入的日期，是否小于当前时间
	 * @param date
	 * @return
	 */
	public static boolean ltCurrentDate(String date){
		long mills = getStringToDate(date, "yyyy-MM-dd");
		if(mills < System.currentTimeMillis()){
			return true;
		}
		
		return false;
	}
	
	public static TimeItem second2Time(int seconds){
		int  day = seconds / (  60 * 60 * 24);
		int hour = (seconds-day*(  60 * 60 * 24))/(60 * 60);
		int second = seconds % 60;
		int minute = (seconds-day*(  60 * 60 * 24)-hour * ( 60 * 60))/( 60);
		
		return new youbao.shopping.ninetop.common.util.TimeItem(day, hour, minute, second);
	}

}
