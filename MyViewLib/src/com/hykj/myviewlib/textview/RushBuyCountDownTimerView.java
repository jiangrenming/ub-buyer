package com.hykj.myviewlib.textview;

import java.util.Timer;
import java.util.TimerTask;

import com.hykj.myviewlib.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class RushBuyCountDownTimerView extends LinearLayout {

	private TextView tv_hour_decade;
	private TextView tv_hour_unit;
	private TextView tv_min_decade;
	private TextView tv_min_unit;
	private TextView tv_sec_decade;
	private TextView tv_sec_unit;

	private Context context;

	private int hour_decade;
	private int hour_unit;
	private int min_decade;
	private int min_unit;
	private int sec_decade;
	private int sec_unit;
	private Timer timer;
	
	private int fontSize = -1;

	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			countDown();
		};
	};

	public RushBuyCountDownTimerView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.view_countdowntimer, this);

		tv_hour_decade = (TextView) view.findViewById(R.id.tv_hour_decade);
		tv_hour_unit = (TextView) view.findViewById(R.id.tv_hour_unit);
		tv_min_decade = (TextView) view.findViewById(R.id.tv_min_decade);
		tv_min_unit = (TextView) view.findViewById(R.id.tv_min_unit);
		tv_sec_decade = (TextView) view.findViewById(R.id.tv_sec_decade);
		tv_sec_unit = (TextView) view.findViewById(R.id.tv_sec_unit);
		
		setTextFontSize();
	}
	
	public void setFontSize(int fontSize){
		this.fontSize = fontSize;
		
		setTextFontSize();
	}
	
	private void setTextFontSize(){
		if(tv_hour_decade == null || fontSize < 0)
			return;
		
		tv_hour_decade.setTextSize(fontSize);
		tv_hour_unit.setTextSize(fontSize);
		tv_min_decade.setTextSize(fontSize);
		tv_min_unit.setTextSize(fontSize);
		tv_sec_decade.setTextSize(fontSize);
		tv_sec_unit.setTextSize(fontSize);
	}

	/**
	 * 
	 */
	public void start() {

		if (timer == null) {
			timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					handler.sendEmptyMessage(0);
				}
			}, 0, 1000);
		}
	}

	/**
	 * 
	 */
	public void stop() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}

	/**
	 * @throws Exception
	 * 
	 */
	public void setTime(int hour, int min, int sec) {

		if (hour >= 60 || min >= 60 || sec >= 60 || hour < 0 || min < 0 || sec < 0) {
			throw new RuntimeException("Time format is error,please check out your code");
		}
		hour_decade = hour / 10;
		hour_unit = hour - hour_decade * 10;
		min_decade = min / 10;
		min_unit = min - min_decade * 10;
		sec_decade = sec / 10;
		sec_unit = sec - sec_decade * 10;
		if (hour == 0 && min == 0 && sec == 0) {
			tv_hour_decade.setText("0");
			tv_hour_unit.setText("0");
			tv_min_decade.setText("0");
			tv_min_unit.setText("0");
			tv_sec_decade.setText("0");
			tv_sec_unit.setText("0");
			stop();
		} else {
			tv_hour_decade.setText(hour_decade + "");
			tv_hour_unit.setText(hour_unit + "");
			tv_min_decade.setText(min_decade + "");
			tv_min_unit.setText(min_unit + "");
			tv_sec_decade.setText(sec_decade + "");
			tv_sec_unit.setText(sec_unit + "");
		}
	}

	/**
	 * 
	 */
	private void countDown() {
		if (isCarry4Unit(tv_sec_unit)) {
			if (isCarry4Decade(tv_sec_decade)) {
				if (isCarry4Unit(tv_min_unit)) {
					if (isCarry4Decade(tv_min_decade)) {
						if (isCarry4Unit(tv_hour_unit)) {
							if (isCarry4Decade(tv_hour_decade)) {
								// Toast.makeText(context, "倒计时结束",
								// Toast.LENGTH_SHORT).show();
								stop();
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 */
	private boolean isCarry4Decade(TextView tv) {

		int time = Integer.valueOf(tv.getText().toString());
		time = time - 1;
		if (time < 0) {
			time = 5;
			tv.setText(time + "");
			return true;
		} else {
			tv.setText(time + "");
			return false;
		}
	}

	/**
	 * 
	 */
	private boolean isCarry4Unit(TextView tv) {

		int time = Integer.valueOf(tv.getText().toString());
		time = time - 1;
		if (time < 0) {
			time = 9;
			tv.setText(time + "");
			return true;
		} else {
			tv.setText(time + "");
			return false;
		}
	}
}
