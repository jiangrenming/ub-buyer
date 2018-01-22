package com.ninetop.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.ninetop.activity.ub.util.StatusBarUtil;
import com.ninetop.common.util.ToastUtils;

import java.util.Map;

import butterknife.ButterKnife;
import youbao.shopping.R;

/**
 * Created by jill on 2016/11/8.
 */

public abstract class BaseActivity extends AppCompatActivity implements Viewable{

    private LoadingAction loadingAction;

    public BaseActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 沉浸状态栏
//            Window window = getWindow();
//            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        StatusBarCompat.init(this, getBgColor()); // 填充
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//            layoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | layoutParams.flags);
//        }
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      // StatusBarCompat.init(this, getBgColor()); // 填充
      //  setStatusBar();
        //getBgColor();
        loadingAction = new LoadingAction(this);

        initSoft();
        ViewUtils.inject(this);
        ButterKnife.bind(this);
        initView();
        initListener();
        initData();
    }
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.transparent));
    }
    protected  void initSoft(){};

    protected abstract int getLayoutId();

    /**
     * 设置浸润背景色
     * @return
     */
    protected int getBgColor(){
      //  return Color.parseColor("#CCCCCC");
        return Color.argb(200,222,38,40);
    }

    /**
     * 初始化视图
     */
    protected void initView(){}

    /**
     * 初始化数据
     */
    protected void initData(){}

    /**
     * 初始化监听
     */
    protected void initListener(){}

    /**
     * 显示提示
     * @param message
     */
    public void showToast(String message){
        ToastUtils.showCenter(this, message);
    }

    public void startActivity(Class clazz){
        Intent intent = new Intent(this, clazz);
        this.startActivity(intent);
    }

    public void startActivity(Class clazz, Map<String, String> map){
        Intent intent = new Intent(this, clazz);
        if(map != null) {
            for (String key : map.keySet()) {
                intent.putExtra(key, map.get(key));
            }
        }
        this.startActivity(intent);
    }

    /**
     * 等待提示框
     */
    public void addLoading() {
        loadingAction.add();
    }
    /**
     * 等待框消失
     */
    public void removeLoading() {
        loadingAction.remove();
    }

    public String getIntentValue(String key){
        return getIntent().getStringExtra(key);
    }

    public void refresh(){
        initView();
        initListener();
        initData();
    }

    protected double getDoubleTextValue(TextView textView){
        if(textView == null)
            return 0.0;
        try {
            return Double.parseDouble(textView.getText().toString());
        }catch (Exception e){
            return 0.0;
        }
    }

    protected long getLongTextValue(TextView textView){
        return (long)getDoubleTextValue(textView);
    }

    protected int getIntValue(String value){
        if(value == null)
            return 0;
        try {
            return Integer.parseInt(value);
        }catch (Exception e){
            return 0;
        }
    }

    protected double getDoubleValue(String value){
        if(value == null)
            return 0;
        try {
            return Double.parseDouble(value);
        }catch (Exception e){
            return 0;
        }
    }

    public BaseActivity getTargetActivity(){
        return this;
    }

    protected boolean isLogin(){
        String userToken = GlobalInfo.userToken;
        return userToken != null && userToken.length() > 0;
    }

    protected int getScreenWidth(){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        return wm.getDefaultDisplay().getWidth();
    }


}
