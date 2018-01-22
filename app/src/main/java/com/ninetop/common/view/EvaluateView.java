package com.ninetop.common.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hykj.dialoglib.MyDialog;
import com.hykj.dialoglib.MyDialogOnClick;
import com.ninetop.activity.user.PopWindowManager;
import com.ninetop.common.util.CameraUtils;
import com.ninetop.common.view.listener.EvaluateAddClickListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import youbao.shopping.R;

import static com.ninetop.activity.product.CommentAndShowActivity.PHOTO_WITH_CAMERA;
import static com.ninetop.activity.product.CommentAndShowActivity.PHOTO_WITH_PHOTO;
import static youbao.shopping.R.id.iv_add_image;


/**
 * @date: 2016/12/28
 * @author: Shelton
 * @version: 1.1.3
 * @Description:
 */
public class EvaluateView extends LinearLayout {

    private List<File> files1 = new ArrayList<>();
    private List<File> files2 = new ArrayList<>();
    private List<File> files3 = new ArrayList<>();
    private List<File> files4 = new ArrayList<>();
    private List<File> files5 = new ArrayList<>();

    private Context context;
    @BindView(iv_add_image)
    ImageView ivAddImage;
    @BindView(R.id.iv_remove_image)
    ImageView ivRemoveImage;
    @BindView(R.id.rl_pic)
    RelativeLayout rlPic;

    private File imageFile;

    private EvaluateAddClickListener addImageListener = null;
    private OnClickListener removeImageListener = null;

    private int position = -1;

    public EvaluateView(Context context) {
        this(context, null);
        this.context = context;

    }

    public EvaluateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;

    }

    public EvaluateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_evaluate, this);
        ButterKnife.bind(this, view);

        ivAddImage.setOnClickListener(new AddImageListener());
        ivRemoveImage.setOnClickListener(new RemoveImageListener());
    }

    public EvaluateView(Activity context, List<File> files1, List<File> files2, List<File> files3, List<File> files4, List<File> files5,int position) {
        super(context);
        this.files1 = files1;
        this.files2 = files2;
        this.files3 = files3;
        this.files4 = files4;
        this.files5 = files5;
        this.position = position;
    }

    private class AddImageListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            addImage();
            if (addImageListener != null) {
                addImageListener.onClick(EvaluateView.this);
            }
        }
    }

    private class RemoveImageListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            removeImage();

        }
    }

    private void removeImage() {

        ivRemoveImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyDialog((Activity) context, MyDialog.DIALOG_TWOOPTION, "温馨提示", "您确定要移除照片吗?", new MyDialogOnClick() {
                    @Override
                    public void sureOnClick(View v) {
                        switch (position){
                            case 0:
                                System.out.println("before files1::"+files1);
                                files1.clear();
                                 break;
                            case 1:
                                System.out.println("after file2"+files2);
                                files2.clear();
                                break;
                            case 2:
                                System.out.println("before file3"+files3);
                                files3.clear();
                                break;
                            case 3:
                                files4.clear();
                                break;
                            case 4:
                                files5.clear();
                                break;
                        }

                        ivAddImage.setImageResource(R.mipmap.add_image);
                        ivAddImage.setClickable(true);
                        ivRemoveImage.setClickable(false);
                        ivRemoveImage.setVisibility(View.GONE);

                        System.out.println("EvaluateView removeImgfile1:::"+files1);
                        System.out.println("EvaluateView removeImgfile2:::"+files2);
                        System.out.println("EvaluateView removeImgfile3:::"+files3);

                    }

                    @Override
                    public void cancelOnClick(View v) {
                    }
                }).show();
            }
        });
    }

    public void addImage() {
        PopWindowManager
                .getInstance()
                .createPopupWindow((Activity)context,"拍照", "我的相册")
                .setOnPopOnClickListener(new PopWindowManager.OnPopOnClickListener() {
                    @Override
                    public void listOne() {
                        imageFile = CameraUtils.startCamera((Activity) context, PHOTO_WITH_CAMERA);
                        //files1.add(filePath);
                    }

                    @Override
                    public void listTwo() {
                        CameraUtils.openMapStorage((Activity) context, PHOTO_WITH_PHOTO);
                    }
                });
        System.out.println("addImage+filePath::" + imageFile);
    }


    public File getFilePath() {
        return imageFile;
    }

    public void setAddImageListener(EvaluateAddClickListener clickListener) {
        this.addImageListener = clickListener;
    }

    public void setRemoveListener(OnClickListener clickListener) {
        this.removeImageListener = clickListener;
    }

    public void setImageBitmap(Bitmap bitmap) {
        ivAddImage.setImageBitmap(bitmap);
    }

    public void setImageRes() {
        ivAddImage.setImageResource(R.mipmap.add_image);
    }

    public void setRemoveImageVisible(boolean flag) {
        if (flag) {
            ivRemoveImage.setVisibility(VISIBLE);
        } else {
            ivRemoveImage.setVisibility(GONE);
        }
    }

    public void setIvAddImageVisible(boolean flag) {
        if (flag) {
            ivAddImage.setVisibility(VISIBLE);
        } else {
            ivAddImage.setVisibility(GONE);
        }
    }

    public void setIvRemoveImageClickable(boolean flag) {
        if (flag) {
            ivRemoveImage.setClickable(true);
        } else {
            ivRemoveImage.setClickable(false);
        }
    }

    public void setIvAddImageClickable(boolean flag) {
        if (flag) {
            ivAddImage.setClickable(true);
        } else {
            ivAddImage.setClickable(false);
        }
    }


}