package youbao.shopping.ninetop.common.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageSaveUtil {
	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE};

	/**
	 * * Checks if the app has permission to write to device storage
	 * *
	 * * If the app does not has permission then the user will be prompted to
	 * * grant permissions
	 * *
	 * * @param activity
	 */
	public static void verifyStoragePermissions(Activity activity) {
		// Check if we have write permission
		int permission = ActivityCompat.checkSelfPermission(activity,
				Manifest.permission.WRITE_EXTERNAL_STORAGE);

		if (permission != PackageManager.PERMISSION_GRANTED) {
			// We don't have permission so prompt the user
			ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
					REQUEST_EXTERNAL_STORAGE);
		}
	}
	
	public static String saveImageToGallery(Context context, Bitmap bmp, boolean showTip) {
		verifyStoragePermissions((Activity) context);

		String state = Environment.getExternalStorageState();
		if (!state.equals(Environment.MEDIA_MOUNTED)) {
			ToastUtils.showLongCenter(context, "SD卡不存在");
			return null;
		}
		
	    if (bmp == null){
	        ToastUtils.showLongCenter(context, "文件为空无法保存");
	        return null;
	    }
	    // 首先保存图片
	    File appDir = new File(Environment.getExternalStorageDirectory(), "jianjiao");
	    if (!appDir.exists()) {
	        appDir.mkdir();
	    }
	    String fileName = System.currentTimeMillis() + ".jpg";
	    File file = new File(appDir, fileName);
	    try {
	        FileOutputStream fos = new FileOutputStream(file);
	        bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
	        fos.flush();
	        fos.close();
	    } catch (FileNotFoundException e) {
	        ToastUtils.showLongCenter(context, "文件未发现");
	        e.printStackTrace();
	    } catch (IOException e) {
	        ToastUtils.showLongCenter(context, "读写出现异常");
	        e.printStackTrace();
	    }catch (Exception e){
	        ToastUtils.showLongCenter(context, "保存出错了");
	        e.printStackTrace();
	    }

	    // 最后通知图库更新
	    try {
	        MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
	    } catch (FileNotFoundException e) {
	    	ToastUtils.showLongCenter(context, "系统图库无法读取");
	    }
	    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//	    Uri uri = Uri.fromFile(file);
		Uri uri = getUriForFile(context, file);
	    intent.setData(uri);
	    context.sendBroadcast(intent);
	    if(showTip){
	    	ToastUtils.showLongCenter(context, "图片已保存到" + file.getAbsolutePath());
	    }
	    
	    return file.getAbsolutePath();
	}

	public static Uri getUriForFile(Context context, File file){
		return Uri.fromFile(file);

//		return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
	}

	public static Uri getUriForFile(Context context, String fileName){
		return Uri.fromFile(new File(fileName));
//		return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", new File(fileName));
	}

	public static String saveImageToGallery(Context context, Bitmap bmp) {
		return saveImageToGallery(context, bmp, false);
	}
	
	public static String saveImageToGallery(Context context, ImageView imageView, boolean showTip) {
		BitmapDrawable bmpDrawable = (BitmapDrawable)imageView.getDrawable();  
		Bitmap bmp = bmpDrawable.getBitmap();
		return saveImageToGallery(context, bmp, showTip);
	}
	
	public static String saveImageToGallery(Context context, ImageView imageView) {
		return saveImageToGallery(context, imageView, true);
	}
	
	public static Bitmap createBitmap(final Activity activity, View view) {
		Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		view.draw(canvas);
		
		return bitmap;
		
//		Bitmap newBitmap = Bitmap.createBitmap(bitmap, 200, 300, view.getWidth() - 400, view.getHeight() - 600);
//		
//		saveImageToGallery(activity, newBitmap);
	}

	//图片的压缩
	public static Bitmap ratio(String filePath, float pixelW, float pixelH){
		//只是获取图片参数并没有改变大小 及配置
		BitmapFactory.Options  options=new BitmapFactory.Options();
		//打开如果设置为true，解码器将返回null（无位图），
		// 但字段仍将被设置，允许调用者查询位图而不必为其像素分配内存
		options.inJustDecodeBounds=true;
		// 如果这是非空的，解码器将尝试解码成这个内部配置
		options.inPreferredConfig= Bitmap.Config.RGB_565;
		//把本地的文件路径转位bitmap对象
		Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
		options.inJustDecodeBounds=false;
		//获取图片宽高
		int outWidth = options.outWidth;
		int outHeight = options.outHeight;
		//压缩比例
		int size = 1;
		if (outWidth <= pixelW && outHeight <= pixelH) {
			size = 1;
		} else {
			double scale = outWidth >= outHeight ? outWidth / pixelW : outHeight / pixelH;
			double log = Math.log(scale) / Math.log(2);
			double logCeil = Math.ceil(log);
			size = (int) Math.pow(2, logCeil);
		}
		//设置压缩比例
		options.inSampleSize = size;
		// 重新读取
		bitmap = BitmapFactory.decodeFile(filePath, options);
		return bitmap;
	}
}
