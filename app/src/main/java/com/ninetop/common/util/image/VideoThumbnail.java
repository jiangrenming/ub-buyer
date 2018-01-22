package com.ninetop.common.util.image;

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;

public class VideoThumbnail {

	Bitmap bitmap = null;
	private VideoThumbnailCallback mVideoThumbnailCallback;

	Handler handle = new Handler() {
		public void handleMessage(Message msg) {
			mVideoThumbnailCallback.loaderFinish(bitmap);
		};
	};

	@SuppressLint("NewApi")
	public void createVideoThumbnail(final String url, final int width, final int height,
			VideoThumbnailCallback callback) {
		mVideoThumbnailCallback = callback;
		new Thread(new Runnable() {
			@Override
			public void run() {
				bitmap = getBitmap(url, width, height);
				handle.sendMessage(new Message());
			}
		}).start();
	}

	@SuppressLint("NewApi")
	private Bitmap getBitmap(String url, int width, int height) {
		Bitmap bitmap = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		int kind = MediaStore.Video.Thumbnails.MINI_KIND;
		try {
			if (Build.VERSION.SDK_INT >= 14) {
				retriever.setDataSource(url, new HashMap<String, String>());
			} else {
				retriever.setDataSource(url);
			}
			bitmap = retriever.getFrameAtTime();
		} catch (IllegalArgumentException ex) {
		} catch (RuntimeException ex) {
		} finally {
			try {
				retriever.release();
			} catch (RuntimeException ex) {
			}
		}
		if (kind == Images.Thumbnails.MICRO_KIND && bitmap != null) {
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		}
		return bitmap;
	}
}
