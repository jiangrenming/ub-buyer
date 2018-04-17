package youbao.shopping.ninetop.fragment.product.dialog;


import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;

public class WXAttentionDialog {

    private  Activity activity;

    public WXAttentionDialog(Activity activity){
        this.activity=activity;
    }

    public MyDialog creatDialog() {
        return new MyDialog(activity, MyDialog.DIALOG_TWOOPTION, "微信关注公众号\"优保控股\"", "我们竭诚为您服务", new MyDialogOnClick() {
            @Override
            public void sureOnClick(View v) {
                try{
                    Intent intent = new Intent();
                    ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setComponent(cmp);
                    activity.startActivityForResult(intent, 0);
                }catch (Exception e){
                    e.printStackTrace();
                    Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://weixin.qq.com/"));
                    activity.startActivity(intent);
                }
            }
            @Override
            public void cancelOnClick(View v) {
            }
        });
    }
}
