package com.ninetop.activity.user;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import com.ninetop.base.BaseActivity;
import com.ninetop.bean.user.UserInfo;
import com.ninetop.common.util.CameraUtils;
import com.ninetop.common.util.ImageSaveUtil;
import com.ninetop.common.util.RoundImageView;
import com.ninetop.common.util.Tools;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.impl.UserCenterService;
import com.ninetop.service.listener.CommonResultListener;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * @date: 2016/11/11
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class PersonalDataActivity extends BaseActivity {

    private static final int PHOTO_WITH_CAMERA = 100;//相机
    private static final int PHOTO_WITH_PHOTO = 101;//打开图库
    private static final int CROP=102;
    private final UserCenterService userCenterService;
    @BindView(R.id.hv_title)
    HeadView hvTitle;
    @BindView(R.id.riv_round_head)
    RoundImageView rivRoundHead;
    @BindView(R.id.tv_personal_name)
    TextView tvPersonalName;
    @BindView(R.id.tv_personal_gender)
    TextView tvPersonalGender;
    private File filePath;
    private List<File> fileList=new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_data;
    }
    public PersonalDataActivity(){
        userCenterService = new UserCenterService(this);
    }
    @Override
    protected void initView() {
        super.initView();
        ButterKnife.bind(this);
        hvTitle.setTitle("个人资料");
    }

    @Override
    protected void initListener() {
        super.initListener();
    }

    @OnClick({R.id.ll_round_head, R.id.rl_nickname, R.id.rl_gender})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_round_head:
                modifyRoundHead();
                break;
            case R.id.rl_nickname:
                startActivity(ModifyNicknameActivity.class);
                break;
            case R.id.rl_gender:
                modifyGender();
                break;
        }
    }

    private void modifyRoundHead() {
        PopWindowManager
                .getInstance()
                .createPopupWindow(PersonalDataActivity.this,"拍照","我的相册")
                .setOnPopOnClickListener(new PopWindowManager.OnPopOnClickListener() {
            @Override
            public void listOne() {
                filePath=CameraUtils.startCamera(PersonalDataActivity.this,PHOTO_WITH_CAMERA);
            }

            @Override
            public void listTwo() {
                CameraUtils.openMapStorage(PersonalDataActivity.this,PHOTO_WITH_PHOTO);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_WITH_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (data == null) {
                        informMap();
                        //上传并且修改头像
                        if (filePath != null) {
                            CameraUtils.crop(Uri.fromFile(filePath),this,CROP);
                        }
                    }
                }
                break;
            case PHOTO_WITH_PHOTO:
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage==null){
                        return;
                    }
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = PersonalDataActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    String mImgPath;
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        mImgPath = picturePath;
                        cursor.close();
                    } else {
                        mImgPath = selectedImage.getPath();
                    }
                    //上传并且修改头像
                    if (mImgPath != null) {
                        CameraUtils.crop(Uri.fromFile(new File(mImgPath)),this,CROP);
                    }
                }

            case CROP:
                if (resultCode == RESULT_OK) {
                    if (data!=null){
                        Bitmap bitmap = data.getParcelableExtra("data");
                        if(bitmap!=null){
                            String filepath = ImageSaveUtil.saveImageToGallery(this, bitmap);
                            postFile(new File(filepath));
                        }
                    }
                }
                break;
        }
    }

    private void postFile(File imgpath) {
        fileList.clear();
        fileList.add(imgpath);
        userCenterService.fixUserInfo(null,null,fileList, new CommonResultListener<UserInfo>(this) {
            @Override
            public void successHandle(UserInfo userInfo) {
                showToast("上传成功");
                setUserInfo(userInfo);
            }
        });
    }
    private void informMap() {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(filePath);
        intent.setData(uri);
        sendBroadcast(intent);
    }
    //修改性别
    private void modifyGender() {
                PopWindowManager
                .getInstance()
                .createPopupWindow(PersonalDataActivity.this,"男", "女")
                .setOnPopOnClickListener(new PopWindowManager.OnPopOnClickListener() {
            @Override
            public void listOne() {
                fixSex("M");
            }

            @Override
            public void listTwo() {
                fixSex("F");
            }
        });
    }
    public void fixSex(String sex){
        userCenterService.fixUserInfo(null, sex, fileList, new CommonResultListener<UserInfo>(this) {
            @Override
            public void successHandle(UserInfo userInfo) throws JSONException {
                showToast("修改成功");
                setUserInfo(userInfo);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }
    private void getData() {
        userCenterService.getUserInfo(new CommonResultListener<UserInfo>(this) {
            @Override
            public void successHandle(UserInfo result) {
                setUserInfo(result);
            }
        });
    }
    private void setUserInfo(UserInfo result) {
        if (result==null){
            return;
        }
        try {
            Tools.ImageLoaderShow(this,result.avatar,rivRoundHead);
        }catch (Exception e){
            e.printStackTrace();
        }
        tvPersonalName.setText(result.nickName);
        String sex = result.sex;
        if ("M".equals(sex)){
            tvPersonalGender.setText("男");
        }else if ("F".equals(sex)){
            tvPersonalGender.setText("女");
        }else {
            tvPersonalGender.setText(sex);
        }
    }

}