package youbao.shopping.ninetop.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.lzy.okgo.OkGo;
import youbao.shopping.ninetop.common.MyActivityManager;
import youbao.shopping.ninetop.common.constant.SharedKeyConstant;
import youbao.shopping.ninetop.common.util.MySharedPreference;
import youbao.shopping.ninetop.common.util.image.MyImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;

public class MyApplication extends Application {
    static MyApplication applicationContext;

    private static Context mContext;

    public synchronized static MyApplication getInstance() {
        return applicationContext;
    }

//    public LocationService locationService;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        init();
        registerActivityCallback();
        ImageLoader.getInstance().init(MyImageLoader.getConfig(this));
        //百度地图定位初始化
//        locationService = new LocationService(getApplicationContext());
//        SDKInitializer.initialize(mContext);

        //设置LOG开关，默认为false
        UMConfigure.setLogEnabled(true);
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口
//        UMConfigure.init(this, "5aa380a2f29d98737c00044d", null, UMConfigure.DEVICE_TYPE_PHONE, null);
        UMConfigure.init(this, "5ab9d46bf43e4878b2000224", null, UMConfigure.DEVICE_TYPE_PHONE, null);
//        //开启ShareSDK debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        Config.DEBUG = true;
        UMShareAPI.get(this);

        //友盟初始化
        QueuedWork.isUseThreadPool = false;
    }

    //各个平台的配置，建议放在全局Application或者程序入口
    {
//        PlatformConfig.setWeixin("wxa79d3ce07e71120c", "cc1d8f52481e190c6a77b989d0f23c2f");
        PlatformConfig.setWeixin("wxf6b408256021748a", "6ff42e24d098b1850533c14a838f59db");
//        PlatformConfig.setQQZone("1106151272", "Bt2g0iG1fQHqZ3qD");
    }

    public static Context getContext() {
        return mContext;
    }

    private void init() {
        String versionName = null;
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String token = MySharedPreference.get(SharedKeyConstant.TOKEN, null, this);

        GlobalInfo.appVersionName = versionName;
        GlobalInfo.userToken = token;
        //-----------------------------------------------------------------------------------//
        //必须调用初始化
        OkGo.init(this);
    }

    private void registerActivityCallback() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                MyActivityManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });

    }
}
