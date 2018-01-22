package com.ninetop.activity.user;
/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
 * Created by Administrator on 2016/11/14.
 */


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.ninetop.base.BaseActivity;
import com.ninetop.common.IntentExtraKeyConst;
import com.ninetop.common.util.CameraUtils;
import com.ninetop.common.util.ImageSaveUtil;
import com.ninetop.common.util.Tools;
import com.ninetop.common.util.ValidateUtil;
import com.ninetop.common.view.HeadView;
import com.ninetop.service.impl.UserCenterService;
import com.ninetop.service.listener.CommonResultListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import youbao.shopping.R;

public class ApplyForRefundActivity extends BaseActivity {

    @BindView(R.id.hv_head)
    HeadView hvHead;
    @BindView(R.id.tv_no_pro)
    TextView tvNoPro;
    @BindView(R.id.tv_exit_money_reason)
    TextView tvExitMoneyReason;
    @BindView(R.id.et_refund_money)
    EditText etRefundMoney;
    @BindView(R.id.ll_add_img)
    LinearLayout llAddImg;
    @BindView(R.id.rl_check_up_image)
    RelativeLayout rlCheckUpImage;
    @BindView(R.id.et_explan)
    EditText etExplain;
    @BindView(R.id.tv_explan)
    TextView tvExplain;
    @BindView(R.id.rl_message)
    RelativeLayout rlMessage;
    @BindView(R.id.tv_count)
    TextView tvCount;
    private float measuredHeight;
    private float measuredWidth;
    private ArrayList<Bitmap> bitmaps;
    private List<File> fileList;
    private File filePath;
    private static final int PHOTO_WITH_CAMERA = 103;//相机
    private static final int PHOTO_WITH_PHOTO = 102;//打开图库
    private String TYPE="";
    private String REASON="";
    private String ORDER_CODE="";
    private String SKU_ID="";
    private String GOODS_CODE="";
    private String PRICE="";
    private final UserCenterService userCenterService;
    private  int  deleterPosition=-1;
    private String MESSAGE="";
    private boolean isCheck=false;
    private ArrayList<IPickerViewKey> refundStyle;
    private ArrayList<IPickerViewKey> refundReason;

    {
        if (refundStyle==null){
            refundStyle = new ArrayList<>();
        }
        if (refundReason==null){
            refundReason = new ArrayList<>();
        }
        for (int i = 0; i < AfterScaleEum.RefundStyle.values().length; i++) {
            refundStyle.add(AfterScaleEum.RefundStyle.values()[i]);
        }
        for (int i = 0; i < AfterScaleEum.RefundReason.values().length; i++) {
            refundReason.add(AfterScaleEum.RefundReason.values()[i]);
        }
    }
    public ApplyForRefundActivity(){
        userCenterService = new UserCenterService(this);
    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_request_exit_money;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent!=null){
            ORDER_CODE=intent.getStringExtra(IntentExtraKeyConst.ORDER_CODE);
            GOODS_CODE=intent.getStringExtra(IntentExtraKeyConst.GOODS_CODE);
            SKU_ID=intent.getStringExtra(IntentExtraKeyConst.SKUID);
            PRICE=intent.getStringExtra(IntentExtraKeyConst.REALPAY);
            PRICE=Tools.saveNum(Double.valueOf((TextUtils.isEmpty(PRICE)?0:PRICE)+""));
        }
        initTitle();
    }

    @Override
    protected void initSoft() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
    }
    private void initTitle() {
        hvHead.setTitle("申请退款");
        hvHead.setSearchImageVisible(View.GONE);
        etRefundMoney.setHint("最多不能超过"+PRICE+"元");
        hvHead.setBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void initListener() {
        etExplain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tvCount.setText(s.length()+"/200");
                MESSAGE=s.toString().trim();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        rlCheckUpImage.measure(0,0);
        measuredHeight = rlCheckUpImage.getMeasuredHeight();
        measuredWidth = rlCheckUpImage.getMeasuredWidth();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @OnClick({R.id.ll_no_pro,R.id.ll_exit_money_reason,R.id.rl_check_up_image,R.id.bt_commit,R.id.tv_explan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_no_pro:
            //    退款类型
                reasonAndType(refundStyle,tvNoPro,0);
                break;
            case R.id.ll_exit_money_reason:
                //退款原因
                reasonAndType(refundReason,tvExitMoneyReason,1);
                break;
            case R.id.rl_check_up_image:
                //上传图片
                if (fileList==null){
                    fileList=new ArrayList<>();
                }
                if (bitmaps==null){
                    bitmaps=new ArrayList<>();
                }
                //打开相机
                if (bitmaps.size()>=3){
                    showToast("最多只能上传三张");
                    return;
                }
                startCamera();
                break;
            case R.id.bt_commit:
            //提交申请
            returnMoney();
                break;
            case R.id.tv_explan:
                //提交申请
                if (isCheck){
                    if (etExplain.isCursorVisible()&&!(TextUtils.isEmpty(etExplain.getText().toString().trim())))
                        return;
                }
                isCheck=!isCheck;
                if (isCheck){
                    tvExplain.setHint("");
                    rlMessage.setVisibility(View.VISIBLE);
                }else{
                    tvExplain.setHint("最多200字");
                    rlMessage.setVisibility(View.GONE);
                    MESSAGE="";
                }
                break;
        }
    }

    private void reasonAndType(ArrayList<IPickerViewKey> refundReason, TextView tvExitMoneyReason, int style) {
        Tools.hideInput(this,getWindow().peekDecorView());
        setOption(refundReason, tvExitMoneyReason, style);
    }


    public void setOption(final ArrayList<IPickerViewKey> arrayList, final TextView textView, final int style){
        OptionsPickerView optionsPickerView = new OptionsPickerView(this);
        optionsPickerView.setPicker(arrayList);
        optionsPickerView.setCyclic(false);
        optionsPickerView.setSelectOptions(1);
        optionsPickerView.show();
        optionsPickerView.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                settingStyleAndReason(style,arrayList.get(options1).getPickerViewKey());
                textView.setText(arrayList.get(options1).getPickerViewText());
            }
        });
    }

    private void settingStyleAndReason(int style, String pickerViewText) {
        if (style==0){
            //退款类型
            TYPE=pickerViewText;
        }else if (style==1){
            //退款原因
            REASON=pickerViewText;
        }
    }
    private void startCamera() {
        PopWindowManager
                .getInstance()
                .createPopupWindow(ApplyForRefundActivity.this,"拍照","打开相机")
                .setOnPopOnClickListener(new PopWindowManager.OnPopOnClickListener() {
            @Override
            public void listOne() {
                filePath=CameraUtils.startCamera(ApplyForRefundActivity.this, PHOTO_WITH_CAMERA);
            }

            @Override
            public void listTwo() {
                CameraUtils.openMapStorage(ApplyForRefundActivity.this,PHOTO_WITH_PHOTO);
            }
        });
    }

    private void returnMoney() {
        String tvStyle = tvNoPro.getText().toString().trim();
        String tvexitMoneyReason = tvExitMoneyReason.getText().toString().trim();
        String tvrefundMoney = etRefundMoney.getText().toString().trim();
        if (TextUtils.isEmpty(tvStyle)){
            showToast("请选择退款类型");
            return;
        }
        if (TextUtils.isEmpty(tvexitMoneyReason)){
            showToast("请选择退款原因");
            return;
        }
        if (!ValidateUtil.isMoney(tvrefundMoney)){
            showToast("请输入正确格式的金额");
            return;
        }
        if (Double.valueOf(PRICE)<Double.valueOf(tvrefundMoney)){
            showToast("输入金额超过了最大金额");
            return;
        }
        requestReturnMoney(tvrefundMoney);
    }

    private void requestReturnMoney(String tvRefundMoney) {
        if (fileList==null){
            fileList=new ArrayList<>();
        }
        userCenterService.applyForReturnMoney(ORDER_CODE,GOODS_CODE,SKU_ID,TYPE, REASON, tvRefundMoney, MESSAGE, fileList, new CommonResultListener<String>(this) {
            @Override
            public void successHandle(String result) {
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PHOTO_WITH_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (data==null){
                        //通知图库更新
                        informMap();
                        addBitmapToList(filePath);
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
                    Cursor cursor = ApplyForRefundActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                    String mImgPath;
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        mImgPath = cursor.getString(columnIndex);
                        cursor.close();
                    } else {
                        mImgPath = selectedImage.getPath();
                    }
                    //上传并且修改头像
                    addBitmapToList(new File(mImgPath));
                }
        }
    }

    private void addBitmapToList(File file) {
        Bitmap bitmap = getBitmap(file);
        bitmaps.add(bitmap);
        addImage();
        String fileAbs = ImageSaveUtil.saveImageToGallery(ApplyForRefundActivity.this, bitmap);
        fileList.add(new File(fileAbs));
    }

    private Bitmap getBitmap(File file) {
        Bitmap bitmap = ImageSaveUtil.ratio(file.toString(), measuredWidth, measuredHeight);
        bitmap=Bitmap.createScaledBitmap(bitmap,(int)measuredWidth,(int)measuredHeight,true);
        return bitmap;
    }
    private void informMap() {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(filePath);
        intent.setData(uri);
        sendBroadcast(intent);
    }
    private void addImage() {
        llAddImg.removeAllViews();
        for (int i=0;i<bitmaps.size();i++){
            View view =  View.inflate(this, R.layout.imageview, null);
            ImageView img = (ImageView) view.findViewById(R.id.iv);
            ImageView iv_substract = (ImageView) view.findViewById(R.id.tv_substract);
            final int finalI = i;
            iv_substract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleterPosition= finalI;
                    if (deleterPosition>-1){
                        bitmaps.remove(deleterPosition);
                        fileList.remove(deleterPosition);
                        deleterPosition=-1;
                        addImage();
                    }
                }
            });
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) img.getLayoutParams();
            layoutParams.width= (int) measuredWidth;
            layoutParams.height= (int) measuredHeight;
            layoutParams.leftMargin=Tools.dip2px(this,9);
            img.setImageBitmap(bitmaps.get(i));
            llAddImg.addView(view);
        }
    }
}
