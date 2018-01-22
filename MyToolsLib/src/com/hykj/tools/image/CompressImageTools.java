package com.hykj.tools.image;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import net.bither.util.NativeUtil;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

/**
 * 图片压缩
 * 
 * @author gm
 *
 */
public class CompressImageTools {

	/**
	 * 初始化
	 * 
	 */
	public static CompressImageTools getInstance() {
		CompressImageTools mCompressImageTools = new CompressImageTools();
		return mCompressImageTools;
	}

	/**
	 * 压缩图片
	 * 
	 * 大概可以将图片压缩到 100K以下
	 * 
	 */
	public void compressImage(Activity activity, String filePath,
			final String compressedPath,
			final CompressFinishListener mCompressFinishListener) {
		File dirFile = new File(filePath); // 原始图片
		if (!dirFile.exists()) { // 不存在
			return;
		}
		ImageLoader.getInstance().init(
				getConfig(activity.getApplicationContext()));
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.loadImage("file:///" + filePath,
				new ImageLoadingListener() {
					@Override
					public void onLoadingStarted(String arg0, View arg1) {
					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap bitmap) {
						System.out.println(">>" + bitmap.getWidth() + ","
								+ bitmap.getHeight());
						File compressFile = new File(compressedPath); // 原始图片

						if (bitmap.getWidth() <= 2000) {
							NativeUtil.compressBitmap(bitmap,
									compressFile.getAbsolutePath(), true,
									mCompressFinishListener); // 执行压缩
						} else {
							NativeUtil.compressBitmap(bitmap, 50,
									compressFile.getAbsolutePath(), true,
									mCompressFinishListener); // 执行三倍压缩
						}
					}

					@Override
					public void onLoadingCancelled(String arg0, View arg1) {
					}

					@Override
					public void onLoadingFailed(String arg0, View arg1,
							FailReason arg2) {
					}
				});
	}

	/**
	 * 图片压缩配置
	 * 
	 */
	private static ImageLoaderConfiguration getConfig(Context context) {
		@SuppressWarnings("deprecation")
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(2000, 2000)
				.threadPoolSize(5)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(4 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100)
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(
						new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout
				.writeDebugLogs() // Remove for release app
				.build();// 锟斤拷始锟斤拷锟斤拷
		return config;
	}
}
