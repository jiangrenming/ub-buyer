package com.hykj.tools.uploadimage;

import java.io.File;

import java.io.IOException;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.hykj.tools.image.CompressFinishListener;
import com.hykj.tools.image.CompressImageTools;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

/**
 * 上传多张图片
 * 
 * @author Administrator
 *
 */
public class UploadImage {

	Activity activity;
	String uploadUrl;
	UploadImageListener mUploadImageListener;

	@SuppressLint("HandlerLeak")
	Handler handle = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == -999) {
				// 将压缩后的图片上传
				upload(String.valueOf(msg.obj.toString()));
			}
		};
	};

	/**
	 * 初始化
	 */
	public static UploadImage getInstance() {
		UploadImage mUploadImage = new UploadImage();
		return mUploadImage;
	}

	/**
	 * 上传单张图片
	 * 
	 * @param url
	 *            上传的URL
	 * @param imagePath
	 *            图片地址
	 * @param mUploadImageListener
	 *            上传成功回调
	 */
	public void uploadImage(Activity activity, final String url,
			final String imagePath, UploadImageListener mUploadImageListener) {
		this.activity = activity;
		this.uploadUrl = url;
		this.mUploadImageListener = mUploadImageListener;

		File dirFile = activity.getExternalCacheDir(); // 获取缓存的路径
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
		// 压缩图片后的路径
		File compressFile = new File(dirFile, new Date().getTime() + ".jpg");
		CompressImageTools.getInstance().compressImage(activity, imagePath,
				compressFile.getAbsolutePath(), new CompressFinishListener() {
					@Override
					public void compressFinish(String compressedPath) {
						Message msg = new Message();
						msg.what = -999;
						msg.obj = compressedPath;
						handle.sendMessage(msg);
					}
				});
	}

	private void upload(final String imagePath) {
		ImageLoader.getInstance().init(
				getConfig(activity.getApplicationContext()));
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.loadImage("file:///" + imagePath,
				new ImageLoadingListener() {
					@Override
					public void onLoadingStarted(String arg0, View arg1) {
					}

					@Override
					public void onLoadingComplete(String arg0, View arg1,
							Bitmap bitmap) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								File file = new File(imagePath); // 缓存图片
								HttpClient httpClient = new DefaultHttpClient();
								HttpPost post = new HttpPost(uploadUrl);
								FileBody fileBody = new FileBody(file,
										"image/jpeg");
								MultipartEntity multipartEntity = new MultipartEntity();
								multipartEntity.addPart("img", fileBody);
								post.setEntity(multipartEntity);
								try {
									HttpResponse httpResponse = httpClient
											.execute(post);
									int code = httpResponse.getStatusLine()
											.getStatusCode();
									String result = EntityUtils.toString(
											httpResponse.getEntity(), "utf-8");
									Log.e("UploadImage", String.valueOf(code));
									// 返回上传之后的参数
									mUploadImageListener
											.uploadImageListener(result);
									File iamgeFile = new File(imagePath);
									if (file.exists()) {
										iamgeFile.delete();
									}
								} catch (ClientProtocolException e) {
									e.printStackTrace();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}).start();
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
				.memoryCache(new UsingFreqLimitedMemoryCache(4 * 1024 * 1024))
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
