package com.bigkoo.convenientbanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bigkoo.convenientbanner.adapter.CBPageAdapter;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.CBPageChangeListener;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bigkoo.convenientbanner.view.CBLoopViewPager;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import youbao.shopping.R;

//import android.annotation.TargetApi;
//import android.annotation.TargetApi;

/**
 * 页面翻转控件，极方便的广告栏 支持无限循环，自动翻页，翻页特效
 * 
 * @author Sai 支持自动翻页
 */
public class ConvenientBanner<T> extends LinearLayout {
	private List<T> mDataS;
	private int[] page_indicatorId;
	private ArrayList<ImageView> mPointViews = new ArrayList<ImageView>();
	private CBPageChangeListener pageChangeListener;
	private ViewPager.OnPageChangeListener onPageChangeListener;
	private CBPageAdapter pageAdapter;
	private CBLoopViewPager viewPager;
	private ViewPagerScroller scroller;
	private ViewGroup loPageTurningPoint;
	private long autoTurningTime;
	private boolean turning;
	private boolean canTurn = false;
	private boolean manualPageable = true;
	private boolean canLoop = true;
	private Context context;

	public enum PageIndicatorAlign {
		ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT, CENTER_HORIZONTAL
	}

	private AdSwitchTask adSwitchTask;

	public ConvenientBanner(Context context) {
		super(context);
		init(context);
	}

	public ConvenientBanner(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
		canLoop = a.getBoolean(R.styleable.ConvenientBanner_canLoop, true);
		a.recycle();
		init(context);
	}

//	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
//	public ConvenientBanner(Context context, AttributeSet attrs, int defStyleAttr) {
//		super(context, attrs, defStyleAttr);
//		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
//		canLoop = a.getBoolean(R.styleable.ConvenientBanner_canLoop, true);
//		a.recycle();
//		init(context);
//	}

//	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//	public ConvenientBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//		super(context, attrs, defStyleAttr);//有修改
//		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ConvenientBanner);
//		canLoop = a.getBoolean(R.styleable.ConvenientBanner_canLoop, true);
//		a.recycle();
//		init(context);
//	}

	private void init(Context context) {
		this.context = context;
		View hView = LayoutInflater.from(context).inflate(R.layout.include_viewpager, this, true);
		viewPager = (CBLoopViewPager) hView.findViewById(R.id.cbLoopViewPager);
		loPageTurningPoint = (ViewGroup) hView.findViewById(R.id.loPageTurningPoint);
		initViewPagerScroll();

		adSwitchTask = new AdSwitchTask(this);
	}

	static class AdSwitchTask implements Runnable {

		private final WeakReference<ConvenientBanner> reference;

		AdSwitchTask(ConvenientBanner convenientBanner) {
			this.reference = new WeakReference<ConvenientBanner>(convenientBanner);
		}

		@Override
		public void run() {
			ConvenientBanner convenientBanner = reference.get();

			if (convenientBanner != null) {
				if (convenientBanner.viewPager != null && convenientBanner.turning) {
					int page = convenientBanner.viewPager.getCurrentItem() + 1;
					convenientBanner.viewPager.setCurrentItem(page);
					convenientBanner.postDelayed(convenientBanner.adSwitchTask, convenientBanner.autoTurningTime);
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ConvenientBanner<T> setPages(CBViewHolderCreator<T> holderCreator, List<T> dataS) {
		this.mDataS = dataS;
		pageAdapter = new CBPageAdapter(holderCreator, mDataS);
		viewPager.setAdapter(pageAdapter, canLoop);

		if (page_indicatorId != null)
			setPageIndicator(page_indicatorId);
		return this;
	}

	/**
	 * 通知数据变化 如果只是增加数据建议使用 notifyDataSetAdd()
	 */
	public void notifyDataSetChanged() {
		viewPager.getAdapter().notifyDataSetChanged();
		if (page_indicatorId != null)
			setPageIndicator(page_indicatorId);
	}

	/**
	 * 设置底部指示器是否可见
	 *
	 * @param visible
	 */
	public ConvenientBanner setPointViewVisible(boolean visible) {
		loPageTurningPoint.setVisibility(visible ? View.VISIBLE : View.GONE);
		return this;
	}

	/**
	 * 底部指示器资源图片
	 *
	 * @param page_indicatorId
	 */
	private ConvenientBanner<T> setPageIndicator(int[] page_indicatorId) {
		loPageTurningPoint.removeAllViews();
		mPointViews.clear();
		this.page_indicatorId = page_indicatorId;
		if (mDataS == null)
			return this;
		for (int count = 0; count < mDataS.size(); count++) {
			// 翻页指示的点
			ImageView pointView = new ImageView(getContext());
			pointView.setPadding(5, 0, 5, 0);
			if (mPointViews.isEmpty())
				pointView.setImageResource(page_indicatorId[1]);
			else
				pointView.setImageResource(page_indicatorId[0]);
			mPointViews.add(pointView);
			loPageTurningPoint.addView(pointView);
		}
		pageChangeListener = new CBPageChangeListener(mPointViews, page_indicatorId);
		viewPager.setOnPageChangeListener(pageChangeListener);
		pageChangeListener.onPageSelected(viewPager.getRealItem());
		if (onPageChangeListener != null)
			pageChangeListener.setOnPageChangeListener(onPageChangeListener);

		return this;
	}

	/**
	 * 指示器的方向
	 * 
	 * @param align
	 *            三个方向：居左 （RelativeLayout.ALIGN_PARENT_LEFT），居中
	 *            （RelativeLayout.CENTER_HORIZONTAL），居右
	 *            （RelativeLayout.ALIGN_PARENT_RIGHT）
	 * @return
	 */
	public ConvenientBanner setPageIndicatorAlign(PageIndicatorAlign align) {
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) loPageTurningPoint.getLayoutParams();
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
				align == PageIndicatorAlign.ALIGN_PARENT_LEFT ? RelativeLayout.TRUE : 0);
		layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,
				align == PageIndicatorAlign.ALIGN_PARENT_RIGHT ? RelativeLayout.TRUE : 0);
		layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,
				align == PageIndicatorAlign.CENTER_HORIZONTAL ? RelativeLayout.TRUE : 0);
		loPageTurningPoint.setLayoutParams(layoutParams);
		return this;
	}

	/***
	 * 是否开启了翻页
	 * 
	 * @return
	 */
	public boolean isTurning() {
		return turning;
	}

	/***
	 * 开始翻页
	 * 
	 * @param autoTurningTime
	 *            自动翻页时间
	 * @return
	 */
	public ConvenientBanner startTurning(long autoTurningTime) {
		// 如果是正在翻页的话先停掉
		if (turning) {
			stopTurning();
		}
		// 设置可以翻页并开启翻页
		canTurn = true;
		this.autoTurningTime = autoTurningTime;
		turning = true;
		postDelayed(adSwitchTask, autoTurningTime);
		return this;
	}

	public void stopTurning() {
		turning = false;
		removeCallbacks(adSwitchTask);
	}

	public void setAutoPlay(boolean auto){
		canTurn = auto;
	}

	/**
	 * 自定义翻页动画效果
	 *
	 * @param transformer
	 * @return
	 */
	public ConvenientBanner setPageTransformer(PageTransformer transformer) {
		viewPager.setPageTransformer(true, transformer);
		return this;
	}

	/**
	 * 设置ViewPager的滑动速度
	 */
	private void initViewPagerScroll() {
		try {
			Field mScroller = null;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			scroller = new ViewPagerScroller(viewPager.getContext());
			mScroller.set(viewPager, scroller);

		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public boolean isManualPageable() {
		return viewPager.isCanScroll();
	}

	public void setManualPageable(boolean manualPageable) {
		viewPager.setCanScroll(manualPageable);
	}

	// 触碰控件的时候，翻页应该停止，离开的时候如果之前是开启了翻页的话则重新启动翻页
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		int action = ev.getAction();
		if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
				|| action == MotionEvent.ACTION_OUTSIDE) {
			// 开始翻页
			if (canTurn)
				startTurning(autoTurningTime);
		} else if (action == MotionEvent.ACTION_DOWN) {
			// 停止翻页
			if (canTurn)
				stopTurning();
		}
		return super.dispatchTouchEvent(ev);
	}

	// 获取当前的页面index
	public int getCurrentItem() {
		if (viewPager != null) {
			return viewPager.getRealItem();
		}
		return -1;
	}

	// 设置当前的页面index
	public void setcurrentitem(int index) {
		if (viewPager != null) {
			viewPager.setCurrentItem(index);
		}
	}

	public ViewPager.OnPageChangeListener getOnPageChangeListener() {
		return onPageChangeListener;
	}

	/**
	 * 设置翻页监听器
	 * 
	 * @param onPageChangeListener
	 * @return
	 */
	public ConvenientBanner setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
		this.onPageChangeListener = onPageChangeListener;
		// 如果有默认的监听器（即是使用了默认的翻页指示器）则把用户设置的依附到默认的上面，否则就直接设置
		if (pageChangeListener != null)
			pageChangeListener.setOnPageChangeListener(onPageChangeListener);
		else
			viewPager.setOnPageChangeListener(onPageChangeListener);
		return this;
	}

	public boolean isCanLoop() {
		return viewPager.isCanLoop();
	}

	/**
	 * 监听item点击
	 * 
	 * @param onItemClickListener
	 */
	public ConvenientBanner setOnItemClickListener(OnItemClickListener onItemClickListener) {
		if (onItemClickListener == null) {
			viewPager.setOnItemClickListener(null);
			return this;
		}
		viewPager.setOnItemClickListener(onItemClickListener);
		return this;
	}

	/**
	 * 设置ViewPager的滚动速度
	 * 
	 * @param scrollDuration
	 */
	public void setScrollDuration(int scrollDuration) {
		scroller.setScrollDuration(scrollDuration);
	}

	public int getScrollDuration() {
		return scroller.getScrollDuration();
	}

	public CBLoopViewPager getViewPager() {
		return viewPager;
	}

	public void setCanLoop(boolean canLoop) {
		this.canLoop = canLoop;
		viewPager.setCanLoop(canLoop);
	}

	////// 自己添加的方法
	/**
	 * 
	 * @param netWorkImages
	 * @param localImages
	 */
	@SuppressWarnings("unchecked")
	public ConvenientBanner<T> setData(ConvenientBanner banner, List<String> netWorkImages, List<Integer> localImages,
			Integer[] pageIndicator) {
//		initImageLoader(); // 初始化加载
		if (netWorkImages != null && netWorkImages.size() > 0) {
			banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
				@Override
				public NetworkImageHolderView createHolder() {
					return new NetworkImageHolderView();
				}
			}, netWorkImages);
		} else {
			banner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
				@Override
				public LocalImageHolderView createHolder() {
					return new LocalImageHolderView();
				}
			}, localImages);
		}
		// 设置指示标点
		if (pageIndicator != null && pageIndicator.length > 1) {
			this.setPageIndicator(new int[] { pageIndicator[0], pageIndicator[1] });
		} else {
			this.setPageIndicator(new int[] { R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused });
		}
		// 设置时间
		banner.setScrollDuration(300);
		banner.startTurning(3000);
		return banner;

		// 设置指示器的方向
		// .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
		// .setOnPageChangeListener(this)//监听翻页事件
		// .setOnItemClickListener(this);
		// convenientBanner.setManualPageable(false);//设置不能手动影响
		// 设置切换动画
		// convenientBanner.getViewPager().setPageTransformer(true,transforemer);
	}

//	// 初始化网络图片缓存库
//	private void initImageLoader() {
//		// 网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
//		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//				.showImageForEmptyUri(R.mipmap.ic_default_adimage).cacheInMemory(true).cacheOnDisk(true).build();
//
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.context)
//				.defaultDisplayImageOptions(defaultOptions).threadPriority(Thread.NORM_PRIORITY - 2)
//				.denyCacheImageMultipleSizesInMemory().diskCacheFileNameGenerator(new Md5FileNameGenerator())
//				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
//		ImageLoader.getInstance().init(config);
//	}
}
