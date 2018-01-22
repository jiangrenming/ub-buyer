package com.ninetop.common.util.image;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import youbao.shopping.R;

public class MyImageLoader {
	/**
	 * 调用方法 // 异步加载图片 ImageLoader.getInstance().init(
	 * MyImageLoader.getConfig(getApplicationContext())); ImageLoader
	 * imageLoader = ImageLoader.getInstance(); imageLoader.displayImage(logo,
	 * head, MyImageLoader.getOption(), new ImageLoadingListener() {
	 * 
	 * @Override public void onLoadingStarted(String arg0, View arg1) { }
	 * @Override public void onLoadingFailed(String arg0, View arg1, FailReason
	 *           arg2) { }
	 * @Override public void onLoadingComplete(String arg0, View arg1, Bitmap
	 *           arg2) { // 保存下载的图片 MySharedPreference
	 *           .saveBitmapToSharedPreferences("head", arg2,
	 *           getApplicationContext()); }
	 * @Override public void onLoadingCancelled(String arg0, View arg1) { } } );
	 * @param context
	 * @return
	 */
	public static ImageLoaderConfiguration getConfig(Context context) {

		@SuppressWarnings("deprecation")
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(1024, 1024)
				.threadPoolSize(5)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(4 * 1024 * 1024)
				.discCacheSize(100 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(150)
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(
						new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout
				.writeDebugLogs() // Remove for release app
				.build();//
		return config;
	}

	@SuppressWarnings("deprecation")
	public static DisplayImageOptions getOption() {
		DisplayImageOptions options;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.mipmap.ub_image_product) //
				.showImageForEmptyUri(R.mipmap.user_photo_default)//
				.showImageOnFail(R.mipmap.user_photo_default) //
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.decodingOptions(new android.graphics.BitmapFactory.Options())
				.resetViewBeforeLoading(true)
				.displayer(new RoundedBitmapDisplayer(20))
				.displayer(new FadeInBitmapDisplayer(100))
				.build();
		return options;
	}
	
	@SuppressWarnings("deprecation")
	public static DisplayImageOptions getMyHead() {
		DisplayImageOptions options;
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.mipmap.icon_head)
				.showImageForEmptyUri(R.mipmap.icon_head)
				.showImageOnFail(R.mipmap.icon_head)
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.decodingOptions(new android.graphics.BitmapFactory.Options())
				.resetViewBeforeLoading(true)
				.displayer(new RoundedBitmapDisplayer(20))
				.displayer(new FadeInBitmapDisplayer(100))
				.build();
		return options;
	}
}
