package youbao.shopping.ninetop.common.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import youbao.shopping.R;

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 *
 * @author jiangrenming
 * @date 2018/4/23
 */

public class UbComfirmDialog extends Dialog implements View.OnClickListener,DialogInterface.OnKeyListener{

    private  skipAddress mAddress;
    private  Context mContext;
    public UbComfirmDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_item);
        Window window = getWindow();
        WindowManager windowManager = ((Activity)mContext).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp =window.getAttributes();
        lp.width = display.getWidth()*4/5; // 设置dialog宽度为屏幕的4/5
        setCanceledOnTouchOutside(false);// 点击Dialog外部消失
        window.setAttributes(lp);
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        initView();
    }

    private void initView() {
        ImageView delete = (ImageView) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        this.setOnKeyListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.delete:
                try {
                    if (this != null && this.isShowing()){
                        this.dismiss();
                        mAddress.changeAddress();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            break;
            default:
                break;
        }
    }

    public  void setSkipAddress(skipAddress mAddress){
        this.mAddress = mAddress;
    }

    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0){
            this.dismiss();
            mAddress.changeAddress();
        }
        return false;
    }

    public  interface  skipAddress{
        void changeAddress();
    }
}
