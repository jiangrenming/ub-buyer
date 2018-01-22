package com.ninetop.activity.ub.usercenter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;
import com.ninetop.UB.UbUserCenterService;
import com.ninetop.UB.UbUserDetail;
import com.ninetop.UB.UbUserInfo;
import com.ninetop.activity.ub.question.QuestionActivity;
import com.ninetop.activity.ub.usercenter.Camera.ClipImageActivity;
import com.ninetop.activity.ub.usercenter.Camera.view.CircleImageView;
import com.ninetop.activity.user.LoginActivity;
import com.ninetop.activity.user.PopWindowManager;
import com.ninetop.base.BaseActivity;
import com.ninetop.common.LoginAction;
import com.ninetop.common.util.DateUtils;
import com.ninetop.common.util.ToastUtils;
import com.ninetop.common.util.Tools;
import com.ninetop.common.util.image.MyImageLoader;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import youbao.shopping.BuildConfig;
import youbao.shopping.R;

import static com.ninetop.config.AppConfig.BASE_IMAGE_URL;

/**
 * Created by huangjinding on 2017/4/17.
 */
public class UbSystemSetActivity extends BaseActivity {
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    //请求打开相机
    private static final int CAMERA = 105;
    //调用照相机返回图片文件
    private File tempFile;
    // 1: qq, 2: weixin
    private int type;
    private final UbUserCenterService ubUserCenterService;
    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.rlm_user_icon)
    CircleImageView rlmUserIcon;

    @BindView(R.id.et_username_nick)
    EditText etUsernameNick;
    @BindView(R.id.user_gender)
    TextView userGender;

    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.ub_tv_shengri)
    ImageView ubTvShengRi;
    @BindView(R.id.ub_rl_bangdingshouji)
    RelativeLayout ubRlBangDingShouJi;
    @BindView(R.id.ub_rl_disanfang)
    RelativeLayout ubRlDiSanFang;
    @BindView(R.id.ub_rl_xiugaimima)
    RelativeLayout ubRlXiuGaiMiMa;
    @BindView(R.id.ub_rl_wentiyanzheng)
    RelativeLayout ubRlWentiYanZheng;

    private List<File> fileList = new ArrayList<>();
    private int maxLen = 8;
    private String mBirthday;
    private String mNickName;

    public UbSystemSetActivity() {
        ubUserCenterService = new UbUserCenterService(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_gerenshezhi;
    }

    protected void initView() {
        super.initView();
        hvHead.setTitle("设置");
    }

    protected void initData() {
        super.initData();
        initUserInfo();
    }

    private void initUserInfo() {
        ubUserCenterService.getUserInfo(new CommonResultListener<UbUserDetail>(this) {
            @Override
            public void successHandle(UbUserDetail result) throws JSONException {
                mNickName = result.nick_name;
                etUsernameNick.setText(mNickName);
                if ("1".equals(result.sex)) {
                    userGender.setText("男");
                } else {
                    userGender.setText("女");
                }
                mBirthday = result.birthday;
                tvBirthday.setText(mBirthday);
            }
        });
        ubUserCenterService.getUserCenter(new CommonResultListener<UbUserInfo>(this) {
            @Override
            public void successHandle(UbUserInfo result) {
                if (result != null) {
                    if (result.avatar != null) {
                        try {
                            Tools.ImageLoaderShow(UbSystemSetActivity.this, BASE_IMAGE_URL + result.avatar, rlmUserIcon);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @OnClick({R.id.rlm_user_icon, R.id.user_gender, R.id.ub_rl_genter, R.id.ub_tv_shengri,
            R.id.ub_rl_bangdingshouji, R.id.ub_rl_disanfang, R.id.btn_save, R.id.btn_cansel_login,
            R.id.ub_rl_xiugaimima, R.id.ub_rl_wentiyanzheng, R.id.rl_nickname, R.id.rl_birthday,
    })

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlm_user_icon:
                uploadHeadImage();
                break;
            case R.id.ub_rl_genter:
                modifyGender();
                break;
            case R.id.rl_birthday:
                showDate();
                break;
            case R.id.ub_rl_bangdingshouji:
                startActivity(BangDingShouJiActivity.class);
                break;
            case R.id.ub_rl_disanfang:
                startActivity(ThirdActivity.class);
                break;
            case R.id.ub_rl_xiugaimima:
                startActivity(ModifyPasswordActivity.class);
                break;
            case R.id.ub_rl_wentiyanzheng:
                startActivity(QuestionActivity.class);
                break;
            case R.id.btn_save:
                save();
                break;
            case R.id.btn_cansel_login:
                new MyDialog(this, MyDialog.DIALOG_TWOOPTION, "温馨提示", "您确定要退出登录吗?", new MyDialogOnClick() {
                    @Override
                    public void sureOnClick(View v) {
                        logOff();
                    }

                    @Override
                    public void cancelOnClick(View v) {
                    }
                }).show();
                break;
        }
    }

    //上传图片
    private void uploadHeadImage() {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        TextView btnCamera = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        @SuppressLint("InflateParams")
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    //权限判断
                    if (ContextCompat.checkSelfPermission(UbSystemSetActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(UbSystemSetActivity.this, Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        //申请WRITE_EXTERNAL_STORAGE权限
                        ActivityCompat.requestPermissions(UbSystemSetActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                                WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                    } else {
                        //跳转到调用系统相机
                        gotoCamera();
                    }
                } else {
                    //权限判断
                    gotoCamera();
                }
//
                popupWindow.dismiss();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(UbSystemSetActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ActivityCompat.requestPermissions(UbSystemSetActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                READ_EXTERNAL_STORAGE_REQUEST_CODE);
                    }
                } else {
                    //跳转到相册
                    gotoPhoto();
                }
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    /**
     * 外部存储权限申请返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoCamera();
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoPhoto();
            }
        }
    }


    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }


    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        //创建拍照存储的图片文件
        tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(UbSystemSetActivity.this, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }


    /**
     * 检查文件是否存在
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    Log.d("evan", "**********camera uri*******" + Uri.fromFile(tempFile).toString());
                    Log.d("evan", "**********camera path*******" + getRealFilePathFromUri(UbSystemSetActivity.this, Uri.fromFile(tempFile)));
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    Log.d("evan", "**********pick path*******" + getRealFilePathFromUri(UbSystemSetActivity.this, uri));
                    gotoClipActivity(uri);
                }

                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    postFile(new File(cropImagePath));
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    if (type == 1) {
                        rlmUserIcon.setImageBitmap(bitMap);
                    } else {
                        rlmUserIcon.setImageBitmap(bitMap);
                    }
                    //此处后面可以将bitMap转为二进制上传后台网络
                    //......

                }
                break;
        }
    }


    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.putExtra("type", type);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    private void postFile(File imgpath) {
        fileList.clear();
        fileList.add(imgpath);
        //   File file=new File("http://192.168.31.102:8080/youbao/"+imgpath);
        ubUserCenterService.fixHeadImage(fileList, new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) {
                showToast("上传成功");
                getUserAvatar();
            }
        });
    }

    private void getUserAvatar() {
        ubUserCenterService.getUserCenter(new CommonResultListener<UbUserInfo>(this) {
            @Override
            public void successHandle(UbUserInfo result) {
                if (result.avatar != null) {
                    try {
                        Tools.ImageLoaderShow(UbSystemSetActivity.this, BASE_IMAGE_URL + result.avatar, rlmUserIcon);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    protected void logOff() {
        LoginAction.logout(this);
        MyImageLoader.getMyHead();
        startActivity(LoginActivity.class);
        finish();
    }

    //修改性别
    private void modifyGender() {
        PopWindowManager
                .getInstance()
                .createPopupWindow(UbSystemSetActivity.this, "男", "女")
                .setOnPopOnClickListener(new PopWindowManager.OnPopOnClickListener() {
                    @Override
                    public void listOne() {
                        type = 1;
                        userGender.setText("男");
                    }

                    @Override
                    public void listTwo() {
                        userGender.setText("女");
                    }
                });
    }

    public void save() {
        ubUserCenterService.updateUserInfo(etUsernameNick.getText().toString(), type, tvBirthday.getText().toString(), new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) throws JSONException {
                showToast("修改成功");
            }
        });
    }

    private void showDate() {
        TimePickerView tpv = new TimePickerView(UbSystemSetActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);
        tpv.setCyclic(true);
        if (!tpv.isShowing()) {
            tpv.show();
        }
        tpv.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                @SuppressLint("SimpleDateFormat")
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String birthday = format.format(date);
                boolean isMaxTimeNow = DateUtils.ltCurrentDate(birthday);
                if (isMaxTimeNow) {
                    tvBirthday.setText(format.format(date));
                } else {
                    ToastUtils.showCenter(UbSystemSetActivity.this, "请重新选择出生日期");
                    TimePickerView tpv = new TimePickerView(UbSystemSetActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);
                    tpv.setCyclic(true);
                    if (!tpv.isShowing()) {
                        tpv.show();
                    }
                }
            }
        });
    }
}
