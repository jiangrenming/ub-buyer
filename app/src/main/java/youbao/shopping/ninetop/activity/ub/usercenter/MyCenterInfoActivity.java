package youbao.shopping.ninetop.activity.ub.usercenter;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import youbao.shopping.ninetop.activity.user.LoginActivity;
import youbao.shopping.ninetop.activity.user.PopWindowManager;
import youbao.shopping.ninetop.base.BaseActivity;
import youbao.shopping.ninetop.bean.user.UserInfo;
import youbao.shopping.ninetop.common.util.CameraUtils;
import youbao.shopping.ninetop.common.util.ImageSaveUtil;
import youbao.shopping.ninetop.common.util.Tools;
import youbao.shopping.ninetop.common.view.HeadView;
import youbao.shopping.ninetop.service.impl.UserCenterService;
import youbao.shopping.ninetop.service.listener.CommonResultListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import youbao.shopping.R;

/**
 * Created by huangjinding on 2017/5/2.
 */
public class MyCenterInfoActivity extends BaseActivity {

    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.user_touxiang)
    ImageView userPhoto;
    private File filePath;
    private String mImgPath;
    private List<File> fileList = new ArrayList<>();

    private static final int PHOTO_WITH_CAMERA = 100;//相机
    private static final int PHOTO_WITH_PHOTO = 101;//打开图库
    private static final int CROP=102;
    private final UserCenterService userCenterService;

    public MyCenterInfoActivity() {
        userCenterService = new UserCenterService(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ub_activity_gerenziliao;
    }

    protected void initView() {
        super.initView();
        hvHead.setTitle("个人资料");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.user_touxiang, R.id.iv_add_image, R.id.tv_username, R.id.radio0, R.id.radio1, R.id.sex, R.id.iv_add_birthday, R.id.tv_birthday, R.id.btn_already})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_touxiang:
                break;
            case R.id.iv_add_image:
                modifyRoundHead();
                break;
            case R.id.tv_username:
                break;
            case R.id.radio0:
                break;
            case R.id.radio1:
                break;
            case R.id.sex:
                break;
            case R.id.iv_add_birthday:
                break;
            case R.id.tv_birthday:
                break;
            case R.id.btn_already:
                startActivity(LoginActivity.class);
                break;
        }
    }

    protected void modifyRoundHead() {
        PopWindowManager.getInstance().createPopupWindow(MyCenterInfoActivity.this, "拍照", "我的相册").setOnPopOnClickListener(new PopWindowManager.OnPopOnClickListener() {
            @Override
            public void listOne() {
                filePath = CameraUtils.startCamera(MyCenterInfoActivity.this, PHOTO_WITH_CAMERA);
            }

            @Override
            public void listTwo() {
                CameraUtils.startCamera(MyCenterInfoActivity.this, PHOTO_WITH_PHOTO);
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
                    Cursor cursor = MyCenterInfoActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
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

    private void postFile(File imgPath) {
        fileList.clear();
        fileList.add(imgPath);

        userCenterService.fixHeadImage(imgPath, new CommonResultListener<UserInfo>(this) {
            @Override
            public void successHandle(UserInfo userInfo) {
                showToast("上传成功");
                setUserInfo(userInfo);
             //   Tools.ImageLoaderShow(this,userInfo.avatar,userTouxiang);
            }
        });
    }
    private void informMap() {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(filePath);
        intent.setData(uri);
        sendBroadcast(intent);
    }
    private void setUserInfo(UserInfo result) {
        if (result==null){
            return;
        }
        try {
            Tools.ImageLoaderShow(this,result.avatar,userPhoto);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}