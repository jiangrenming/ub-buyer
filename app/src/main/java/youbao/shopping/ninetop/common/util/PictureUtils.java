package youbao.shopping.ninetop.common.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;

import com.ta.utdid2.android.utils.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * 图片工具类
 * @author yanshengli
 * @since 2015-3-26
 */
public class PictureUtils {

    /**
     * getBitmapFromFile method
     * @param dst
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getBitmapFromFile(File dst,int width,int height){
        if (null != dst && dst.exists ()) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options ();
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile (dst.getPath (), opts);
                // 计算图片缩放比例
                final int minSideLength = Math.max (width, height);
                opts.inSampleSize = computeSampleSize (opts, minSideLength, width * height);
                opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try {
                return BitmapFactory.decodeFile (dst.getPath (), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace ();
            }
        }
        return null;
    }

    /**
     * getBitmapFromFile重载
     * 
     * @param filepath
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getBitmapFromFile(String filepath,int width,int height){
        return getBitmapFromFile (new File (filepath), width, height);
    }

    /**
     * computeSampleSize method
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    public static int computeSampleSize(BitmapFactory.Options options,int minSideLength,int maxNumOfPixels){
        int initialSize = computeInitialSampleSize (options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    /**
     * computeInitialSampleSize method
     * @param options
     * @param minSideLength
     * @param maxNumOfPixels
     * @return
     */
    private static int computeInitialSampleSize(BitmapFactory.Options options,int minSideLength,int maxNumOfPixels){
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil (Math.sqrt (w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min (Math.floor (w / minSideLength), Math.floor (h / minSideLength));
        if (upperBound < lowerBound) { return lowerBound; }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 将bitmap保存到文件中
     * @param filepath
     * @param bitmap
     */
    public static void saveBitmapToFile(String filepath,Bitmap bitmap){
        if (bitmap == null) { return; }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream (filepath);
            bitmap.compress (Bitmap.CompressFormat.JPEG, 70, fOut);
            fOut.flush ();
        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            if (fOut != null) {
                try {
                    fOut.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
            if (bitmap != null && !bitmap.isRecycled ()) {
                bitmap.recycle ();
            }
        }

    }

    /**
     * 将洗车拍照后的照片重新保存一下
     * @param filepath
     * @param context
     */
    public static void convertWashcarTakePicture(String filepath,Context context){

        // 获取屏幕的高度和宽度
        int height = youbao.shopping.ninetop.common.util.GestureUtils.getScreenPix (context).heightPixels;
        int width = youbao.shopping.ninetop.common.util.GestureUtils.getScreenPix (context).widthPixels;

        // 先按照方向保存一下
        // Bitmap bm = zoomBitmap (autoFixOrientationByWH (filepath), width, height);

        // 获取图片角度再重新旋转   根据路径未读取到图片
        Bitmap bm = autoFixOrientationByWH (compressBySize (filepath, width, height));
        if(bm!=null){
        	// 重新保存图片
            saveBitmapToFile (filepath, bm);
        }
    }

    /**
     * 
     *@Description: 质量压缩
     *@Author: 李焱生
     *@Since: 2015年5月22日下午4:34:32
     *@param image
     *@return
     */
    public static final Bitmap compressImage(Bitmap image){
        ByteArrayOutputStream baos = new ByteArrayOutputStream ();
        image.compress (Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray ().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset ();// 重置baos即清空baos
            image.compress (Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream (baos.toByteArray ());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream (isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 
     *@Description: TODO(这里用一句话描述这个方法的作用) 
     *@Author: 李焱生
     *@Since: 2015年5月18日下午9:56:36
     *@param bitmap
     *@return
     */
    private static Bitmap autoFixOrientationByWH(Bitmap bitmap){
        if (bitmap == null) { return null; }
        // 得到图片的宽度、高度；
        float imgWidth = bitmap.getWidth ();
        float imgHeight = bitmap.getHeight ();
        int angle = 0;
        if (imgWidth > imgHeight) {
            angle = 90;
        }
        return rotatePic (bitmap, angle);
    }

    /**
     * 
     *@Description: 压缩图片尺寸
     *@Author: 李焱生
     *@Since: 2015年5月18日下午1:36:59
     *@param pathName
     *@param targetWidth
     *@param targetHeight
     *@return
     */
    public static Bitmap compressBySize(String pathName,int targetWidth,int targetHeight){
        BitmapFactory.Options opts = new BitmapFactory.Options ();
        opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
        Bitmap bitmap = BitmapFactory.decodeFile (pathName, opts);
        // 得到图片的宽度、高度；
        float imgWidth = opts.outWidth;
        float imgHeight = opts.outHeight;
        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
        int widthRatio = (int) Math.ceil (imgWidth / (float) targetWidth);
        int heightRatio = (int) Math.ceil (imgHeight / (float) targetHeight);
        opts.inSampleSize = 1;
        if (widthRatio > 1 || widthRatio > 1) {
            if (widthRatio > heightRatio) {
                opts.inSampleSize = widthRatio;
            } else {
                opts.inSampleSize = heightRatio;
            }
        }
        // 设置好缩放比例后，加载图片进内容；
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile (pathName, opts);
        return bitmap;
    }

    /**
     * 生成缩图
     * @param largeImagePath
     * @param thumbnailPath
     * @param width
     * @param height
     */
    public static void createImageThumbnail(String largeImagePath,String thumbnailPath,int width,int height){
        Bitmap bitmap = getBitmapByPath (largeImagePath, null);
        if (bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail (bitmap, width, height);
            saveBitmapToFile (thumbnailPath, bitmap);
        }
    }

    /**
     * 放大缩小图片
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bgimage,double newWidth,double newHeight){
        if (bgimage == null) { return null; }
        // 获取这个图片的宽和高
        float width = bgimage.getWidth ();
        float height = bgimage.getHeight ();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix ();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale (scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap (bgimage, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 
     *@Description: 按照宽度缩放图片 
     *@Author: 李焱生
     *@Since: 2015年4月24日下午5:06:11
     *@param bgimage
     *@param newWidth
     *@return
     */
    public static Bitmap zoomBitmapToWidth(Bitmap bgimage,double newWidth){
        if (bgimage == null) { return null; }
        // 获取这个图片的宽和高
        float width = bgimage.getWidth ();
        float height = bgimage.getHeight ();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix ();
        // 计算宽高缩放率
        float scale = ((float) newWidth) / width;
        // 缩放图片动作
        matrix.postScale (scale, scale);
        Bitmap bitmap = Bitmap.createBitmap (bgimage, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 
     *@Description: 缩放到对应的高度
     *@Author: 李焱生
     *@Since: 2015年4月24日下午5:07:40
     *@param bgimage
     *@param newHeight
     *@return
     */
    public static Bitmap zoomBitmapToHeight(Bitmap bgimage,double newHeight){
        if (bgimage == null) { return null; }
        // 获取这个图片的宽和高
        float width = bgimage.getWidth ();
        float height = bgimage.getHeight ();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix ();
        // 计算宽高缩放率
        float scale = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale (scale, scale);
        Bitmap bitmap = Bitmap.createBitmap (bgimage, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }

    /**
     * 等比例缩放
     * @param bitmap
     * @return
     */
    public static Bitmap scaleBitmap(Bitmap bitmap,int scale){
        // 获取这个图片的宽和高
        int width = bitmap.getWidth ();
        int height = bitmap.getHeight ();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix ();
        // 缩放图片动作
        matrix.postScale (scale, scale);
        // 创建新的图片
        return Bitmap.createBitmap (bitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * 根据路径获取图片的bitmap
     * @param filePath
     * @param opts
     * @return
     */
    public static Bitmap getBitmapByPath(String filePath,BitmapFactory.Options opts){
        Bitmap bitmap = null;
        try {
            if (opts == null) {
                opts = new BitmapFactory.Options ();
            }
            opts.inJustDecodeBounds = false;
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            opts.inPurgeable = true;// 允许在内存不足时回收图片
            opts.inInputShareable = true;
            bitmap = BitmapFactory.decodeFile (filePath, opts);
        } catch (OutOfMemoryError e) {
            e.printStackTrace ();
            System.gc ();
        } catch (Exception e) {
            e.printStackTrace ();
        }
        return bitmap;
    }

    /**
     * 获取bitmap
     * @param filePath
     * @return
     */
    public static Bitmap getBitmapByPath(String filePath){
        return getBitmapByPath (filePath, null);
    }

    /**
     * 
     *@Description: 旋转图片 
     *@Author: 李焱生
     *@Since: 2015年5月15日下午8:15:10
     *@param
     *@return
     */
    public static Bitmap singleLargePic(String filePath){
        return getBitmapByPath (filePath);
    }

    /**
     *@Description: 根据宽高来自动调整图片的方向
     *@Author: 李焱生
     *@Since: 2015年5月18日下午3:09:41
     *@param filePath
     *@return
     */
    public static Bitmap autoFixOrientationByWH(String filePath){
        BitmapFactory.Options opts = new BitmapFactory.Options ();
        opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
        BitmapFactory.decodeFile (filePath, opts);
        // 得到图片的宽度、高度；
        float imgWidth = opts.outWidth;
        float imgHeight = opts.outHeight;
        int angle = 0;
        if (imgWidth > imgHeight) {
            angle = 90;
        }
        Bitmap bm = getBitmapByPath (filePath);
        return rotatePic (bm, angle);
    }

    /**
     * 自动修正图片的方向
     *
     */
    public static Bitmap autoFixOrientation(String filePath){
        Bitmap bm = getBitmapByPath (filePath);
        if (bm == null) { return bm; }
        int deg = 0;
        try {
            ExifInterface exif = null;
            if (!StringUtils.isEmpty (filePath)) {
                exif = new ExifInterface (filePath);
            } else {
                return null;
            }
            String rotate = exif.getAttribute (ExifInterface.TAG_ORIENTATION);
            int rotateValue = Integer.parseInt (rotate);
            //MyLog.d (PictureUtils.class.getName (), "orientetion : " + rotateValue);
            switch (rotateValue) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    deg = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    deg = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    deg = 270;
                    break;
                default:
                    deg = 0;
                    break;
            }
        } catch (Exception ee) {
            return bm;
        }
        Matrix m = new Matrix ();
        m.preRotate (deg);
        bm = Bitmap.createBitmap (bm, 0, 0, bm.getWidth (), bm.getHeight (), m, true);
        return bm;
    }

    /**
     * 
     *@Description: 旋转图片
     *@Author: 李焱生
     *@Since: 2015年5月18日下午3:03:54
     *@param source
     *@param angle
     *@return
     */
    public static Bitmap rotatePic(Bitmap source,int angle){
        Matrix m = new Matrix ();
        m.preRotate (angle);
        Bitmap resultBitMap = Bitmap.createBitmap (source, 0, 0, source.getWidth (), source.getHeight (), m, true);
        return resultBitMap;
    }

}
