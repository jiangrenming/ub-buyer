package youbao.shopping.ninetop.common.util;
import android.app.Activity;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.os.Looper;
import android.os.RemoteException;
import android.text.format.Formatter;

import youbao.shopping.ninetop.config.Power;

import java.lang.reflect.Method;


/**
 * @date: 2016/11/23
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class cleanCatchUtils {

    public  void cleanCatch(final Activity ac, final ClearCache clearCache) {
        PackageManager  pm = ac.getPackageManager();
        // 参数为用户需要的空间，如果手机空间不够，会自动删除缓存来满足要求，如果参数为一个足够大的数字，那么就会删除所有的缓存
//		public abstract void freeStorageAndNotify(long freeStorageSize, IPackageDataObserver observer);
        // 用反射的方式，调用上面这个方法
        try {
            Class<? extends PackageManager> clazz = pm.getClass();
            Method method = clazz.getMethod("freeStorageAndNotify", long.class, IPackageDataObserver.class);
            Power.jionPower(ac);
            method.invoke(pm, Long.MAX_VALUE, new IPackageDataObserver.Stub() {
                @Override
                public void onRemoveCompleted(String packageName, boolean succeeded)
                        throws RemoteException {
                    Power.jionPower(ac);
                    if (clearCache!=null){
                        clearCache.cleanFinish();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            if (clearCache!=null){
                clearCache.cleanFinish();
            }
        }
    }

    public void getAppCatch(Activity activity,OnGetCleanSizeCallBack onGetCleanSizeCallBack) {
        PackageManager pm = activity.getPackageManager();
        String packageName = activity.getPackageName();
        Class<? extends PackageManager> clazz = pm.getClass();
        // 参数一方法名 参数二该方法对象的参数的字节码
        MyPackageSizeObserver observer = null;
        try {
            Method method = clazz.getMethod("getPackageSizeInfo", String.class,
                    IPackageStatsObserver.class);
            // 参数一代表执行所得方法的对象 ,
            // 参数二代表的是该方法的真实参数
            observer = new MyPackageSizeObserver(activity,onGetCleanSizeCallBack);
            method.invoke(pm, packageName,
                    observer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  class MyPackageSizeObserver extends IPackageStatsObserver.Stub {

        private OnGetCleanSizeCallBack onGetCleanSizeCallBack;
        private Activity activity;
        public String size;
        public MyPackageSizeObserver(Activity activity, OnGetCleanSizeCallBack onGetCleanSizeCallBack) {
            this.activity = activity;
            this.onGetCleanSizeCallBack = onGetCleanSizeCallBack;
        }
        @Override
        public void onGetStatsCompleted(final PackageStats pStats, boolean succeeded)
                throws RemoteException {
            size = Formatter.formatFileSize(activity, pStats.cacheSize);
            Looper.prepare();
            if (onGetCleanSizeCallBack!=null){
                onGetCleanSizeCallBack.size(size);
            }
            Looper.loop();
        }
    }
    public interface ClearCache{
        void cleanFinish();
    }
    public interface OnGetCleanSizeCallBack{
        void size(String size);
    }
}
