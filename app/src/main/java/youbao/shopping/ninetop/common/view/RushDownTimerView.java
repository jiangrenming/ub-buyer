package youbao.shopping.ninetop.common.view;

import java.util.Timer;
import java.util.TimerTask;

import youbao.shopping.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class RushDownTimerView extends LinearLayout {

	private TextView tv_hour_decade;
	private TextView tv_min_decade;
	private TextView tv_sec_decade;

	private Context context;

	private int hour_decade;
	private int min_decade;
	private int sec_decade;
	private Timer timer;
	
	private int fontSize = -1;

	private Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			countDown();
		};
	};

	public RushDownTimerView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.view_rushdowntimer, this);

		tv_hour_decade = (TextView) view.findViewById(R.id.tv_hour_decade);
		tv_min_decade = (TextView) view.findViewById(R.id.tv_min_decade);
		tv_sec_decade = (TextView) view.findViewById(R.id.tv_sec_decade);
		
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
		tv_min_decade.setTextSize(fontSize);
		tv_sec_decade.setTextSize(fontSize);
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
		hour_decade = hour;
		min_decade = min;
		sec_decade = sec;
		if (hour == 0 && min == 0 && sec == 0) {
			setTimeZore();
			stop();
		} else {
			tv_hour_decade.setText(formatTime(hour_decade));
			tv_min_decade.setText(formatTime(min_decade));
			tv_sec_decade.setText(formatTime(sec_decade));
		}
	}
	
	private void setTimeZore(){
		tv_hour_decade.setText("00");
		tv_min_decade.setText("00");
		tv_sec_decade.setText("00");
	}

	/**
	 * 
	 */
	private void countDown() {
		if (isCarry4Decade(tv_sec_decade, 59)) {
			if (isCarry4Decade(tv_min_decade, 59)) {
				if (isCarry4Decade(tv_hour_decade, 0)) {
					setTimeZore();
					stop();
				}
			}
		}
	}

	/**
	 * 
	 */
	private boolean isCarry4Decade(TextView tv, int startValue) {

		int time = Integer.parseInt(tv.getText().toString());
		time = time - 1;
		if (time < 0) {
			time = startValue;
			tv.setText(formatTime(time));
			return true;
		} else {
			tv.setText(formatTime(time));
			return false;
		}
	}
	
	private String formatTime(int time){
		if(time < 10){
			return "0" + time;
		}
		
		return time + "";
	}

}
