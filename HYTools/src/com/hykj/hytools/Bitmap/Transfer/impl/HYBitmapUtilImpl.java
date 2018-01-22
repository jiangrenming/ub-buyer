package com.hykj.hytools.Bitmap.Transfer.impl;

import java.io.ByteArrayOutputStream;

import com.hykj.hytools.Bitmap.Transfer.HYBitmapUtil;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;


/**
 * 
 * @author linpp
 *
 */
public class HYBitmapUtilImpl implements HYBitmapUtil {
	@Override
	public Bitmap Uri2Bitmap(Uri uri, Activity activity) {
		{
			try {
				Bitmap bitmap = MediaStore.Images.Media.getBitmap(
						activity.getContentResolver(), uri);
				return bitmap;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@Override
	public Uri Bitmap2Uri(Bitmap bitmap, Activity activity) {
		Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(
				activity.getContentResolver(), bitmap, null, null));
		return uri;
	}

	@Override
	public Bitmap Drawable2Bitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Drawable Bitmap2Drawable(Bitmap Bitmap) {
		Drawable drawable = new BitmapDrawable(Bitmap);
		return drawable;
	}

	@Override
	public Bitmap Bytes2Bitmap(byte[] bts) {
		if (bts.length != 0) {
			return BitmapFactory.decodeByteArray(bts, 0, bts.length);
		} else {
			return null;
		}

	}

	@Override
	public byte[] Bitmap2Byte(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	@Override
	public byte[] Uri2Byte(Uri uri, Activity activity) {
		Bitmap bitmap = this.Uri2Bitmap(uri, activity);
		return this.Bitmap2Byte(bitmap);
	}

	@Override
	public Uri Byte2Uri(Uri uri, Activity activity) {
		Bitmap bitmap = this.Uri2Bitmap(uri, activity);
		return this.Bitmap2Uri(bitmap, activity);
	}
}
