package youbao.shopping.ninetop.common.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by jill on 2016/11/30.
 */

public class DialogUtil {

    public static AlertDialog createDialogBottom(Activity activity, View view){
        AlertDialog dialog = new AlertDialog.Builder(activity).create();
        dialog.show();

        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setContentView(view);
        WindowManager windowManager = (activity).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow()
                .getAttributes();
        lp.width = (int) (display.getWidth()); // 设置宽度
        window.setAttributes(lp);
        window.setBackgroundDrawableResource(android.R.color.transparent);

        return dialog;
    }
}
