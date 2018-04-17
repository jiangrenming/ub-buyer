//package com.ninetop.activity.ub.usercenter;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.provider.MediaStore;
//import android.text.Editable;
//import android.text.Selection;
//import android.text.TextWatcher;
//import android.view.Gravity;
//import android.view.View;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.bigkoo.pickerview.TimePickerView;
//import com.hykj.dialoglib.MyDialog;
//import com.hykj.dialoglib.MyDialogOnClick;
//import com.ninetop.UB.UbUserCenterService;
//import com.ninetop.UB.UbUserDetail;
//import com.ninetop.UB.UbUserInfo;
//import com.ninetop.activity.ub.question.QuestionActivity;
//import com.ninetop.activity.user.LoginActivity;
//import com.ninetop.activity.user.PopWindowManager;
//import com.ninetop.base.BaseActivity;
//import com.ninetop.common.LoginAction;
//import com.ninetop.common.util.CameraUtils;
//import com.ninetop.common.util.DateUtils;
//import com.ninetop.common.util.ImageSaveUtil;
//import com.ninetop.common.util.RoundImageView;
//import com.ninetop.common.util.ToastUtils;
//import com.ninetop.common.util.Tools;
//import com.ninetop.common.view.HeadView;
//import com.ninetop.service.listener.CommonResultListener;
//
//import org.json.JSONException;
//
//import java.io.File;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//import youbao.shopping.R;
//
//import static com.ninetop.config.AppConfig.BASE_IMAGE_URL;
//
///**
// * Created by huangjinding on 2017/4/17.
// */
//public class SystemSetActivity extends BaseActivity {
//    private static final int PHOTO_WITH_CAMERA = 100;//相机
//    private static final int PHOTO_WITH_PHOTO = 101;//打开图库
//    private static final int CROP = 102;
//    private final UbUserCenterService ubUserCenterService;
//    @BindView(R.id.hv_head)
//    HeadView hvHead;
//    @BindView(R.id.rlm_user_icon)
//    RoundImageView rlmUserIcon;
//    @BindView(R.id.et_username_nick)
//    EditText etUsernameNick;
//    @BindView(R.id.user_gender)
//    TextView userGender;
//    @BindView(R.id.tv_birthday)
//    TextView tvBirthday;
//    @BindView(R.id.ub_rl_genter)
//    RelativeLayout ubRlGenter;
//    @BindView(R.id.ub_tv_shengri)
//    ImageView ubTvShengri;
//    @BindView(R.id.ub_rl_bangdingshouji)
//    RelativeLayout ubRlBangdingshouji;
//    @BindView(R.id.ub_rl_disanfang)
//    RelativeLayout ubRlDisanfang;
//    @BindView(R.id.ub_rl_xiugaimima)
//    RelativeLayout ubRlXiugaimima;
//    @BindView(R.id.ub_rl_wentiyanzheng)
//    RelativeLayout ubRlWentiyanzheng;
//
//    private File filePath;
//    private List<File> fileList = new ArrayList<>();
//    private int type;
//    private int maxLen = 8;
//
//    public SystemSetActivity() {
//        ubUserCenterService = new UbUserCenterService(this);
//
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.ub_activity_gerenshezhi;
//
//    }
//
//    protected void initView() {
//        super.initView();
//        hvHead.setTitle("设置");
//        etUsernameNick.setGravity(Gravity.END);
//
//        etUsernameNick.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Editable editable = etUsernameNick.getText();
//                int len = editable.length();
//
//                if (len > maxLen) {
//                    ToastUtils.showCenter("输入昵称长度超过限制");
//                    int selEndIndex = Selection.getSelectionEnd(editable);
//                    String str = editable.toString();
//                    //截取新字符串
//                    String newStr = str.substring(0, maxLen);
//                    etUsernameNick.setText(newStr);
//                    editable = etUsernameNick.getText();
//                    //新字符串的长度
//                    int newLen = editable.length();
//                    //旧光标位置超过字符串长度
//                    if (selEndIndex > newLen) {
//                        selEndIndex = editable.length();
//                    }
//                    //设置新光标所在的位置
//                    Selection.setSelection(editable, selEndIndex);
//
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//    }
//
//    protected void initData() {
//        super.initData();
//        initUserInfo();
//    }
//
//    private void initUserInfo() {
//        ubUserCenterService.getUserInfo(new CommonResultListener<UbUserDetail>(this) {
//            @Override
//            public void successHandle(UbUserDetail result) throws JSONException {
//                etUsernameNick.setText(result.nick_name);
//                etUsernameNick.setSelection(result.nick_name.length());//将光标移至文字末尾
//                if ("1".equals(result.sex)) {
//                    userGender.setText("男");
//                } else {
//                    userGender.setText("女");
//                }
//                tvBirthday.setText(result.birthday);
//            }
//        });
//        ubUserCenterService.getUserCenter(new CommonResultListener<UbUserInfo>(this) {
//            @Override
//            public void successHandle(UbUserInfo result) {
//                if (result != null) {
//                    if (result.avatar != null) {
//                        try {
//                            Tools.ImageLoaderShow(SystemSetActivity.this, BASE_IMAGE_URL + result.avatar, rlmUserIcon);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        });
//    }
//
//    @OnClick({R.id.rlm_user_icon, R.id.user_gender, R.id.ub_rl_genter, R.id.ub_tv_shengri,
//            R.id.ub_rl_bangdingshouji, R.id.ub_rl_disanfang, R.id.btn_save, R.id.btn_cansel_login,
//            R.id.ub_rl_xiugaimima, R.id.ub_rl_wentiyanzheng, R.id.rl_nickname, R.id.rl_birthday,
//    })
//
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.rlm_user_icon:
//                modifyRoundHead();
//                break;
//            case R.id.ub_rl_genter:
//                modifyGender();
//                break;
//            case R.id.rl_birthday:
//                showDate();
//                break;
//            case R.id.ub_rl_bangdingshouji:
//                startActivity(BangDingShouJiActivity.class);
//                break;
//            case R.id.ub_rl_disanfang:
//                startActivity(ThirdActivity.class);
//                break;
//            case R.id.ub_rl_xiugaimima:
//                startActivity(ModifyPasswordActivity.class);
//                break;
//            case R.id.ub_rl_wentiyanzheng:
//                startActivity(QuestionActivity.class);
//                break;
//            case R.id.btn_save:
//                save();
//                break;
//            case R.id.btn_cansel_login:
//                new MyDialog(this, MyDialog.DIALOG_TWOOPTION, "温馨提示", "您确定要退出登录吗?", new MyDialogOnClick() {
//                    @Override
//                    public void sureOnClick(View v) {
//                        logOff();
//                    }
//
//                    @Override
//                    public void cancelOnClick(View v) {
//                    }
//                }).show();
//                break;
//        }
//    }
//
//    private void modifyRoundHead() {
//        PopWindowManager
//                .getInstance()
//                .createPopupWindow(SystemSetActivity.this, "拍照", "我的相册")
//                .setOnPopOnClickLisener(new PopWindowManager.OnPopOnClickLisener() {
//                    @Override
//                    public void listOne() {
//                        filePath = CameraUtils.startCamera(SystemSetActivity.this, PHOTO_WITH_CAMERA);
//                    }
//
//                    @Override
//                    public void listTwo() {
//                        CameraUtils.openMapStorage(SystemSetActivity.this, PHOTO_WITH_PHOTO);
//                    }
//                });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        switch (requestCode) {
//            case PHOTO_WITH_CAMERA:
//                if (resultCode == RESULT_OK) {
//                    if (data == null) {
//                        informMap();
//                        //上传并且修改头像
//                        if (filePath != null) {
//                            CameraUtils.crop(ImageSaveUtil.getUriForFile(this, filePath), this, CROP);
//                        }
//                    }
//                }
//                break;
//            case PHOTO_WITH_PHOTO:
//                if (data != null) {
//                    Uri selectedImage = data.getData();
//                    if (selectedImage == null) {
//                        return;
//                    }
//                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                    Cursor cursor = SystemSetActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                    String mImgPath;
//                    if (cursor != null) {
//                        cursor.moveToFirst();
//                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                        mImgPath = cursor.getString(columnIndex);
//                        cursor.close();
//                    } else {
//                        mImgPath = selectedImage.getPath();
//                    }
//                    //上传并且修改头像
//                    if (mImgPath != null) {
//                        //crop 剪切图片
//                        CameraUtils.crop(ImageSaveUtil.getUriForFile(this, mImgPath), this, CROP);
//                    }
//                }
//
//            case CROP:
//                if (resultCode == RESULT_OK) {
//                    if (data != null) {
//                        Bitmap bitmap = data.getParcelableExtra("data");
//                        if (bitmap != null) {
//                            String filepath = ImageSaveUtil.saveImageToGallery(this, bitmap);
//                            postFile(new File(filepath));
////                            ubUserCenterService.fixHeadImage(new File(BASE_IMAGE_URL+filepath), new CommonResultListener<String>(this) {
////                                @Override
////                                public void successHandle(String result) {
////                                    showToast("上传成功");
////
////                                }
////                            });
//                        }
//                    }
//                }
//                break;
//        }
//    }
//
//    private void postFile(File imgpath) {
//        fileList.clear();
//        fileList.add(imgpath);
//        //   File file=new File("http://192.168.31.102:8080/youbao/"+imgpath);
//        ubUserCenterService.fixHeadImage(fileList, new CommonResultListener<String>(this) {
//            @Override
//            public void successHandle(String result) {
//                showToast("上传成功");
//                getUserAvatar();
//            }
//        });
//    }
//
//    private void getUserAvatar() {
//        ubUserCenterService.getUserCenter(new CommonResultListener<UbUserInfo>(this) {
//            @Override
//            public void successHandle(UbUserInfo result) {
//                if (result.avatar != null) {
//                    try {
//                        Tools.ImageLoaderShow(SystemSetActivity.this, BASE_IMAGE_URL + result.avatar, rlmUserIcon);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//            }
//        });
//
//    }
//
//    private void informMap() {
//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = ImageSaveUtil.getUriForFile(this, filePath);
//        intent.setData(uri);
//        sendBroadcast(intent);
//    }
//
//
//    protected void logOff() {
//        LoginAction.logout(this);
//        startActivity(LoginActivity.class);
//        finish();
//    }
//
//    //修改性别
//    private void modifyGender() {
//        PopWindowManager
//                .getInstance()
//                .createPopupWindow(SystemSetActivity.this, "男", "女")
//                .setOnPopOnClickLisener(new PopWindowManager.OnPopOnClickLisener() {
//                    @Override
//                    public void listOne() {
//                        type = 1;
//                        userGender.setText("男");
//                    }
//
//                    @Override
//                    public void listTwo() {
//                        userGender.setText("女");
//                    }
//                });
//    }
//
//    public void save() {
//        ubUserCenterService.updateUserInfo(etUsernameNick.getText().toString(), type, tvBirthday.getText().toString(), new CommonResultListener<String>(this) {
//            @Override
//            public void successHandle(String result) throws JSONException {
//                showToast("修改成功");
//
//            }
//        });
//
//    }
//
//    private void showDate() {
//        TimePickerView tpv = new TimePickerView(SystemSetActivity.this, TimePickerView.Type.YEAR_MONTH_DAY);
//        tpv.setCyclic(true);
//        if (!tpv.isShowing()) {
//            tpv.show();
//        }
//        tpv.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(Date date) {
//                @SuppressLint("SimpleDateFormat")
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//                String birthday = format.format(date);
//                boolean isMaxTimeNow = DateUtils.ltCurrentDate(birthday);
//                if (isMaxTimeNow) {
//                    tvBirthday.setText(format.format(date));
//                } else {
//                    ToastUtils.showCenter("选择的出生日期不能大于当前时间");
//                    tvBirthday.setText("");
//                }
//            }
//        });
//    }
//}
