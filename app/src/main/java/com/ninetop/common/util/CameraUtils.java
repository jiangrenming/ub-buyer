package com.ninetop.common.util;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraUtils {
    public static final String PATH= Environment.getExternalStorageDirectory()+"/Images";
    private static File filePath;

    public static File startCamera(Activity ac, int requestCode){
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(ac, "SD卡不存在", Toast.LENGTH_LONG)
                    .show();
            return null;
        }
        creatPath();
        //兼容6.0拍照
        ActivityCompat.requestPermissions( ac,
                new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri=Uri.fromFile(filePath);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        ac.startActivityForResult(intent,requestCode);
        return filePath;
    }

    public static void creatPath() {
        creatFile(PATH);
        String  fileName = creatFilname();
        filePath = new File(PATH, fileName);
    }

    private static String creatFilname() {
        Date date=new Date(System.currentTimeMillis());
        SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-ddHH-mm-ss");
        return  sim.format(date)+".jpg";
    }

    private static void creatFile(String path) {
        File file=new File(path);
        if (!file.exists()){
            file.mkdir();
        }
    }
    //打开图库
    public static void  openMapStorage(Activity ac, int requestCode){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT){
            Intent album = new Intent(Intent.ACTION_GET_CONTENT);
            album.setAction(Intent.ACTION_PICK);
            album.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            ac.startActivityForResult(album, requestCode);
        }else {
            Intent album = new Intent(Intent.ACTION_GET_CONTENT);
            album.setType("image/*");
            album.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            ac.startActivityForResult(album, requestCode);
        }
    }
    //剪切
    public static void crop(Uri uri,Activity ac, int requestCode){
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为cut
        ac.startActivityForResult(intent, requestCode);
    }
}
