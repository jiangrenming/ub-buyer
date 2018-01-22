package com.hykj.hytools.Bitmap.Transfer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;

/**
 * 
 * @author linpp
 *
 */
public interface HYBitmapUtil {
	/**
	 *
	 * @param uri
	 * @param activity
	 * @return
	 */
	public Bitmap Uri2Bitmap(Uri uri, Activity activity);

	/**
	 *
	 * @param bitmap
	 * @param activity
	 * @return
	 */
	public Uri Bitmap2Uri(Bitmap bitmap, Activity activity);

	/**
	 *
	 * @param drawable
	 * @return
	 */
	public Bitmap Drawable2Bitmap(Drawable drawable);

	/**
	 *
	 * @param Bitmap
	 * @return
	 */
	public Drawable Bitmap2Drawable(Bitmap Bitmap);

	/**
	 *
	 * @param bts
	 * @return
	 */
	public Bitmap Bytes2Bitmap(byte[] bts);

	/**
	 * bitmapתbyte[]
	 * 
	 * @param bitmap
	 * @return
	 */
	public byte[] Bitmap2Byte(Bitmap bitmap);

	/**
	 * Uriתbyte[]
	 * 
	 * @param uri
	 * @return
	 */
	public byte[] Uri2Byte(Uri uri, Activity activity);

	/**
	 * byte[]תUri
	 * 
	 * @param uri
	 * @param activity
	 * @return
	 */
	public Uri Byte2Uri(Uri uri, Activity activity);
}
