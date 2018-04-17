package youbao.shopping.ninetop.common.util;

import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import youbao.shopping.ninetop.base.MyApplication;

public class ImageSizeUtils {
	
	public static void setImageHeight(ImageView image, int height){
		LayoutParams lp = image.getLayoutParams();
		lp.height  = height;
		image.setLayoutParams(lp);
	}

	public static void setImageSize(ImageView image, int width, int height){
		LayoutParams lp = image.getLayoutParams();
		lp.width = width;
		lp.height  = height;
		image.setLayoutParams(lp);
	}

}
