package com.hykj.hytools.Bitmap.BitmapCompress.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.hykj.hytools.Bitmap.BitmapCompress.HYBitmapCompress;

public class HYImageCompressImpl implements HYBitmapCompress {

	@Override
	public Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { //
			baos.reset();//
			options -= 10;//
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//
		return bitmap;
	}


}
