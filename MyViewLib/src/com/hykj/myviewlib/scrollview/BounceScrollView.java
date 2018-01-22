package com.hykj.myviewlib.scrollview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * 具有回弹效果的scrollview
 * 
 * @author gm
 *
 */

public class BounceScrollView extends ScrollView {

	private View inner;// 瀛╁瓙View

	private float y;// 鐐瑰嚮鏃秠鍧愭爣

	private Rect normal = new Rect();// 鐭╁舰(杩欓噷鍙槸涓舰寮忥紝鍙槸鐢ㄤ簬鍒ゆ柇鏄惁锟�??锟斤拷鍔ㄧ敾.)

	private boolean isCount = false;// 鏄惁锟�??锟斤拷璁＄畻

	public BounceScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/***
	 * 鏍规嵁 XML 鐢熸垚瑙嗗浘宸ヤ綔瀹屾垚.璇ュ嚱鏁板湪鐢熸垚瑙嗗浘鐨勬渶鍚庤皟鐢紝鍦ㄦ墍鏈夊瓙瑙嗗浘娣诲姞瀹屼箣锟�??
	 * 鍗充娇瀛愮被瑕嗙洊锟�??onFinishInflate 鏂规硶锛屼篃搴旇璋冪敤鐖剁被鐨勬柟娉曪紝浣胯鏂规硶寰椾互鎵ц.
	 */
	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			inner = getChildAt(0);
		}
	}

	/***
	 * 鐩戝惉touch
	 */
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (inner != null) {
			commOnTouchEvent(ev);
		}

		return super.onTouchEvent(ev);
	}

	/***
	 * 瑙︽懜浜嬩欢
	 * 
	 * @param ev
	 */
	public void commOnTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:
			// 鎵嬫寚鏉惧紑.
			if (isNeedAnimation()) {
				animation();
				isCount = false;
			}
			break;
		/***
		 * 鎺掗櫎鍑虹锟�??锟斤拷绉诲姩璁＄畻锛屽洜涓虹锟�??锟斤拷鏃犳硶寰楃煡y鍧愭爣锟�??鍦∕otionEvent.
		 * ACTION_DOWN涓幏鍙栦笉鍒帮紝
		 * 鍥犱负姝ゆ椂鏄疢yScrollView鐨則ouch浜嬩欢浼狅拷?鍒板埌浜哃IstView鐨勫瀛恑tem涓婇潰
		 * .锟�??锟斤拷浠庣浜屾璁＄畻锟�??锟斤拷.
		 * 鐒讹拷?鎴戜滑涔熻杩涜鍒濆鍖栵紝灏辨槸绗竴娆＄Щ鍔ㄧ殑鏃讹拷?璁╂粦鍔ㄨ窛绂诲綊0. 涔嬪悗璁板綍鍑嗙‘浜嗗氨姝ｅ父鎵ц.
		 */
		case MotionEvent.ACTION_MOVE:
			final float preY = y;// 鎸変笅鏃剁殑y鍧愭爣
			float nowY = ev.getY();// 鏃舵椂y鍧愭爣
			int deltaY = (int) (preY - nowY);// 婊戝姩璺濈
			if (!isCount) {
				deltaY = 0; // 鍦ㄨ繖閲岃锟�??.
			}

			y = nowY;
			// 褰撴粴鍔ㄥ埌锟�??锟斤拷鎴栵拷?锟�??锟斤拷鏃跺氨涓嶄細鍐嶆粴鍔紝杩欐椂绉诲姩甯冨眬
			if (isNeedMove()) {
				// 鍒濆鍖栧ご閮ㄧ煩锟�??
				if (normal.isEmpty()) {
					// 淇濆瓨姝ｅ父鐨勫竷锟�??锟斤拷锟�??
					normal.set(inner.getLeft(), inner.getTop(),
							inner.getRight(), inner.getBottom());
				}
				// 绉诲姩甯冨眬
				inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2,
						inner.getRight(), inner.getBottom() - deltaY / 2);
			}
			isCount = true;
			break;

		default:
			break;
		}
	}

	/***
	 * 鍥炵缉鍔ㄧ敾
	 */
	public void animation() {
		// 锟�??锟斤拷绉诲姩鍔ㄧ敾
		TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
				normal.top);
		ta.setDuration(200);
		inner.startAnimation(ta);
		// 璁剧疆鍥炲埌姝ｅ父鐨勫竷锟�??锟斤拷锟�??
		inner.layout(normal.left, normal.top, normal.right, normal.bottom);

		normal.setEmpty();
	}

	// 鏄惁锟�??锟斤拷锟�??锟斤拷鍔ㄧ敾
	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}

	/***
	 * 鏄惁锟�??锟斤拷绉诲姩甯冨眬 inner.getMeasuredHeight():鑾峰彇鐨勬槸鎺т欢鐨勶拷?楂樺害
	 * 
	 * getHeight()锛氳幏鍙栫殑鏄睆骞曠殑楂樺害
	 * 
	 * @return
	 */
	public boolean isNeedMove() {
		int offset = inner.getMeasuredHeight() - getHeight();
		int scrollY = getScrollY();
		// 0鏄《閮紝鍚庨潰閭ｄ釜鏄簳锟�??
		if (scrollY == 0 || scrollY == offset) {
			return true;
		}
		return false;
	}
}
