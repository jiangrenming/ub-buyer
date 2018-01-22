/*
 * Copyright 2014 http://Bither.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.bither.util;

import com.hykj.tools.image.CompressFinishListener;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;

public class NativeUtil {
	private static int DEFAULT_QUALITY = 100;
	private static CompressFinishListener myCompressFinishListener;

	public static void compressBitmap(Bitmap bit, String fileName,
			boolean optimize, CompressFinishListener mCompressFinishListener) {
		compressBitmap(bit, DEFAULT_QUALITY, fileName, optimize,
				mCompressFinishListener);
		myCompressFinishListener = mCompressFinishListener;
	}

	public static void compressBitmap(Bitmap bit, int quality, String fileName,
			boolean optimize, CompressFinishListener mCompressFinishListener) {
		myCompressFinishListener = mCompressFinishListener;
		Bitmap result = null;
		int width = (int) (bit.getWidth() * (Float.valueOf(quality) / 100));
		int heigth = (int) (bit.getHeight() * (Float.valueOf(quality) / 100));
		System.out.println("compress>>" + width + "," + heigth);
		result = Bitmap.createBitmap(width, heigth, Config.ARGB_8888);// 缩小3倍
		Canvas canvas = new Canvas(result);
		Rect rect = new Rect(0, 0, bit.getWidth(), bit.getHeight());// original
		rect = new Rect(0, 0, width, heigth);// 缩小3倍
		canvas.drawBitmap(bit, null, rect, null);
		saveBitmap(result, quality, fileName, optimize);
		result.recycle();
	}

	private static void saveBitmap(Bitmap bit, int quality, String fileName,
			boolean optimize) {
		compressBitmap(bit, bit.getWidth(), bit.getHeight(), quality,
				fileName.getBytes(), optimize);
		myCompressFinishListener.compressFinish(fileName);
	}

	private static native String compressBitmap(Bitmap bit, int w, int h,
			int quality, byte[] fileNameBytes, boolean optimize);

	static {
		System.loadLibrary("jpegbither");
		System.loadLibrary("bitherjni");
	}
}
