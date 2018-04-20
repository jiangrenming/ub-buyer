package youbao.shopping.ninetop.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;
import youbao.shopping.ninetop.activity.ub.util.StatusBarUtil;
import youbao.shopping.ninetop.common.util.ToastUtils;
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
        setContentView(getLayoutId());
        loadingAction = new LoadingAction(this);
        initSoft();
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
    @Override
    public void showToast(String message){
        ToastUtils.showCenter(this, message);
    }

    @Override
    public void startActivity(Class clazz){
        Intent intent = new Intent(this, clazz);
        this.startActivity(intent);
    }

    @Override
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
    @Override
    public void addLoading() {
        loadingAction.add();
    }
    /**
     * 等待框消失
     */
    @Override
    public void removeLoading() {
        loadingAction.remove();
    }

    @Override
    public String getIntentValue(String key){
        return getIntent().getStringExtra(key);
    }

    @Override
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

    @Override
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
