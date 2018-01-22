/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package com.hykj.myviewlib.listview.part;

import com.hykj.myviewlib.R;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class WaterDropListViewHeader extends FrameLayout {
	private LinearLayout mContainer;
	private ProgressBar mProgressBar;
	private WaterDropView mWaterDropView;
	private STATE mState = STATE.normal;
	private IStateChangedListener mStateChangedListener;

	private int stretchHeight;
	private int readyHeight;
	private static final int DISTANCE_BETWEEN_STRETCH_READY = 100;

	public enum STATE {
		normal, // 姝ｅ父
		stretch, // 鍑嗗杩涜鎷変几
		ready, // 鎷変几鍒版渶澶т綅缃�
		refreshing, // 鍒锋柊
		end// 鍒锋柊缁撴潫锛屽洖婊�
	}

	public WaterDropListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public WaterDropListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		LayoutInflater lf = LayoutInflater.from(context);
		mContainer = (LinearLayout) lf.inflate(
				R.layout.waterdroplistview_header, null);
		mProgressBar = (ProgressBar) mContainer
				.findViewById(R.id.waterdroplist_header_progressbar);
		mWaterDropView = (WaterDropView) mContainer
				.findViewById(R.id.waterdroplist_waterdrop);
		// 鍒濆鎯呭喌锛岃缃笅鎷夊埛鏂皏iew楂樺害涓�0
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
		addView(mContainer, lp);
		initHeight();
	}

	private void initHeight() {

		mContainer.getViewTreeObserver().addOnGlobalLayoutListener(
				new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						stretchHeight = mWaterDropView.getHeight();
						readyHeight = stretchHeight
								+ DISTANCE_BETWEEN_STRETCH_READY;
						getViewTreeObserver()
								.removeGlobalOnLayoutListener(this);
					}
				});

	}

	/**
	 * 淇敼鐘舵�併�傛敞锛氱姸鎬佺殑鏀瑰彉涓庡墠涓�涓姸鎬佷互鍙婁笅鎷夊ご楂樺害鏈夊叧
	 *
	 * @param state
	 */
	public void updateState(STATE state) {
		if (state == mState)
			return;
		STATE oldState = mState;
		mState = state;
		if (mStateChangedListener != null) {
			mStateChangedListener.notifyStateChanged(oldState, mState);
		}

		switch (mState) {
		case normal:
			handleStateNormal();
			break;
		case stretch:
			handleStateStretch();
			break;
		case ready:
			handleStateReady();
			break;
		case refreshing:
			handleStateRefreshing();
			break;
		case end:
			handleStateEnd();
			break;
		default:
		}
	}

	/**
	 * 澶勭悊澶勪簬normal鐘舵�佺殑鍊�
	 */
	private void handleStateNormal() {
		mWaterDropView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
		mContainer.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
	}

	/**
	 * 澶勭悊姘存淮鎷変几鐘舵��
	 */
	private void handleStateStretch() {
		mWaterDropView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
		mContainer.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
	}

	/**
	 * 澶勭悊姘存淮ready鐘舵�侊紝鍥炲脊鏁堟灉
	 */
	@SuppressLint("NewApi")
	private void handleStateReady() {
		mWaterDropView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
		Animator shrinkAnimator = mWaterDropView.createAnimator();
		shrinkAnimator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				// 鍥炲脊缁撴潫鍚庡嵆杩涘叆refreshing鐘舵��
				updateState(STATE.refreshing);
			}
		});
		shrinkAnimator.start();// 寮�濮嬪洖寮�
	}

	/**
	 * 澶勭悊姝ｅ湪杩涜鍒锋柊鐘舵��
	 */
	private void handleStateRefreshing() {
		mWaterDropView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.VISIBLE);
	}

	/**
	 * 澶勭悊鍒锋柊瀹屾瘯鐘舵��
	 */
	private void handleStateEnd() {
		mWaterDropView.setVisibility(View.GONE);
		mProgressBar.setVisibility(View.GONE);
	}

	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) mContainer.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
		// 閫氱煡姘存淮杩涜鏇存柊
		if (mState == STATE.stretch) {
			float pullOffset = (float) Utils.mapValueFromRangeToRange(height,
					stretchHeight, readyHeight, 0, 1);
			if (pullOffset < 0 || pullOffset > 1) {
				// throw new IllegalArgumentException(
				// "pullOffset should between 0 and 1!" + mState + " "
				// + height);
				return;
			}
			//Log.e("pullOffset", "pullOffset:" + pullOffset);
			mWaterDropView.updateComleteState(pullOffset);
		}

	}

	public int getVisiableHeight() {
		return mContainer.getHeight();
	}

	public STATE getCurrentState() {
		return mState;
	}

	public int getStretchHeight() {
		return stretchHeight;
	}

	public int getReadyHeight() {
		return readyHeight;
	}

	public void setStateChangedListener(IStateChangedListener l) {
		mStateChangedListener = l;
	}

	public interface IStateChangedListener {
		public void notifyStateChanged(STATE oldState, STATE newState);
	}
}
