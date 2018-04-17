package youbao.shopping.ninetop.base;

import android.Manifest;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.unistrong.yang.zb_permission.ZbPermission;

import youbao.shopping.ninetop.activity.MainActivity;
import youbao.shopping.ninetop.service.impl.UpdateService;

import youbao.shopping.R;

public class StartActivity extends BaseActivity {
    private static final String TAG = "StartActivity";

    private static final int BAI_DU_READ_PHONE_STATE = 100;
//    private UpdateService updateService;


    public StartActivity() {
//        updateService = new UpdateService(this);
    }

//    private final BroadcastReceiver apkInstallListener = new BroadcastReceiver() {
//
//        @Override
//        public void onReceive(Context context, Intent intent) {
//
//            if (Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
//                Log.i("TAG", "有应用被安装");
//            } else if (Intent.ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
//                Log.i("TAG", "有应用被删除");
//            } else if (Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction())) {
//                Log.i("TAG", "有应用被替换");
//            }
//
//        }
//    };

    @Override
    protected void onStart() {
        super.onStart();

    }



    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 沉浸状态栏
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 无标题
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
//        updateService.update(new CommonResultListener<UpdateBean>(this) {
//            @Override
//            public void successHandle(UpdateBean updateBean) throws JSONException {
//                int isUpdate = updateBean.getForceUpdate();
//                String versionRemark = updateBean.getVersionRemark();
////                String downloadUrl = updateBean.getDownloadUrl();
//                String downloadUrl = "http://app-global.pgyer.com/f84234c7929e0f50a966966bc55c7d0a.apk?e=1508399301&attname=app-release.apk&token=6fYeQ7_TVB5L0QSzosNFfw2HU8eJhAirMF5VxV9G:0CelXON5yk4jpYHH4bfkSCIzDGA=&sign=c7eb915b0417a5e31b30dd6d5b26d538&t=59e858c5";
//                String versionCode = updateBean.getVersionCode();
//                int versionInt = updateBean.getVersionInt();
//                int versionCodeNow = AppUtils.getVersionCode(StartActivity.this);
//                if (versionInt > versionCodeNow) {
//                    if (isUpdate == 0) {
//                        UpdateDialog.show(StartActivity.this, versionRemark, downloadUrl);
//                        ToastUtils.showCenter(StartActivity.this, "不强制更新");
//                    } else if (isUpdate == 1) {
//                        ToastUtils.showCenter(StartActivity.this, "强制更新");
//                        UpdateDialog.showForceUpdate(StartActivity.this, versionRemark, downloadUrl);
//                    } else {
//                        ToastUtils.showCenter(StartActivity.this, "暂时无法获取更新状态");
//                    }
//                } else {
//                    ToastUtils.showCenter(StartActivity.this, "没有版本信息");
//                }
//
//            }
//        });
//        registerSDCardListener();
        if (Build.VERSION.SDK_INT >= 23) {
//            //检查权限
//            int permissionCheck = ContextCompat.checkSelfPermission(StartActivity.this,Manifest.permission.WRITE_CALENDAR);
//            //请求权限
////            ActivityCompat.requestPermissions(StartActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
            getPermission();
        } else {
            startAnimation();
        }

//        ZbPermission.with(StartActivity.this)
//                .addRequestCode(REQUEST_CONTACT)

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start;
    }

//    //注册监听
//    private void registerSDCardListener() {
//        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_MEDIA_MOUNTED);
//        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
//        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
//        intentFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
//        intentFilter.addDataScheme("package");
//        registerReceiver(apkInstallListener, intentFilter);
//    }

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED
                    ) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
                ActivityCompat.requestPermissions(StartActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE
                        , Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, BAI_DU_READ_PHONE_STATE);
            } else {
                startAnimation();
            }
        } else {
            startAnimation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case BAI_DU_READ_PHONE_STATE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    Toast.makeText(getApplicationContext(), "获取位置权限成功", Toast.LENGTH_SHORT).show();
                    startAnimation();
                } else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                    startAnimation();
                }
                break;
            default:
                break;
        }
    }

    private void startAnimation() {
        final View splashIv = findViewById(R.id.splash_iv);
        ValueAnimator animator = ValueAnimator.ofObject(new FloatEvaluator(), 1.0f, 1.2f);
        animator.setDuration(3000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float value = (Float) animation.getAnimatedValue();
                if (value != 1.2f) {
                    splashIv.setScaleX(value);
                    splashIv.setScaleY(value);
                } else {
//                    if (isUpdate) {
//                        ToastUtils.showCenter(StartActivity.this, "更新", 2000);
//                        if (isInstall) {
//                            ToastUtils.showCenter(StartActivity.this, "安装", 2000);
//                        } else {
//                            goToActivity();
//                            ToastUtils.showCenter(StartActivity.this, "安装失败", 2000);
//                        }
//                    } else {
//                        ToastUtils.showCenter(StartActivity.this, "取消更新", 2000);
//                        goToActivity();
//                    }
                    goToActivity();
                }
            }

            private void goToActivity() {
                final Intent it = new Intent(StartActivity.this, MainActivity.class);
                startActivity(it);
                overridePendingTransition(0, android.R.anim.fade_out);
                finish();
            }
        });
        animator.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(apkInstallListener);
    }
}
