package com.base.basemodule.utils.video;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;


import androidx.fragment.app.Fragment;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.LubanOptions;
import org.devio.takephoto.model.TImage;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.model.TakePhotoOptions;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by 胡彦明 on 2017/5/29.
 * <p>
 * Description 相册图片选择工具
 * <p>
 * otherTips
 */

public class PicturesActivity extends TakePhotoActivity {
    public static final int TYPE_CAMERA = 0x01;
    public static final int TYPE_PICTURE = 0x02;
    int limit = 9;

    private boolean isCompressIcon;//是否压缩
    private boolean isTailor;//是否裁剪

    public static void Start(Activity from, int type, int requestCode) {
        Intent intent = new Intent(from, PicturesActivity.class);
        intent.putExtra("type", type);
        from.startActivityForResult(intent, requestCode);

    }

    public static void Start(Activity from, int type, int requestCode, int limit, boolean isCompressIcon,
                             boolean isTailor) {
        Intent intent = new Intent(from, PicturesActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("isCompressIcon", isCompressIcon);
        intent.putExtra("limit", limit);
        intent.putExtra("isTailor", isTailor);
        from.startActivityForResult(intent, requestCode);
    }

    public static void Start(Fragment from, int type, int requestCode, int limit, boolean isCompressIcon,
                             boolean isTailor) {
        Intent intent = new Intent(from.getActivity(), PicturesActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("isCompressIcon", isCompressIcon);
        intent.putExtra("limit", limit);
        intent.putExtra("isTailor", isTailor);
        from.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int type = getIntent().getIntExtra("type", -1);
        limit = getIntent().getIntExtra("limit", 9);
        isCompressIcon = getIntent().getBooleanExtra("isCompressIcon", true);
        isTailor = getIntent().getBooleanExtra("isTailor", true);
        takePhote(type, getTakePhoto());

    }

    private void takePhote(int type, TakePhoto takePhoto) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        if (!isCompressIcon) {
            takePhoto.onEnableCompress(null, false);
//            takePhoto.onPickMultipleWithCrop(limit, getCropOptions());
        } else {
            configCompress(takePhoto, true);
        }
        configTakePhotoOption(takePhoto);
        switch (type) {
            case TYPE_CAMERA:
                //拍照
                if (!isCompressIcon) {
                    takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                } else {
                    if (isTailor) {
                        takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                    } else {
                        takePhoto.onPickFromCapture(imageUri);
                    }
                }

                break;
            case TYPE_PICTURE:
                if (!isCompressIcon) {
                    takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                } else {
                    //相册
                    if (isTailor) {
                        takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                    } else {
                        takePhoto.onPickMultiple(limit);//默认9张图
                    }
                }
                break;
        }

    }

    @Override
    public void takeCancel() {
        super.takeCancel();
        finish();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        finish();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        Intent intent = new Intent();
        ArrayList<TImage> images = result.getImages();
        intent.putExtra("images", images);
        setResult(RESULT_OK, intent);
        finish();
    }

    //配置 图片相关属性
    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);//使用自带相册
        builder.setCorrectImage(true);//纠正图片拍照角度
        takePhoto.setTakePhotoOptions(builder.create());

    }

    //压缩相关配置
    private void configCompress(TakePhoto takePhoto, boolean luban) {
        int maxSize = 5242880;//图片最大不超过5m
        int width = 1080; //图片比例1080*1920
        int height = 1920;
        boolean showProgressBar = false;//压缩时显示进度条
        boolean enableRawFile = true;//压缩后保存原图

        CompressConfig config;
        if (!luban) {
            //采用自己的压缩方式
            config = new CompressConfig.Builder()
                    .setMaxSize(maxSize)
                    .setMaxPixel(width >= height ? width : height)
                    .enableReserveRaw(enableRawFile)
                    .create();
        } else {
            //采用鲁班的压缩方式
            LubanOptions option = new LubanOptions.Builder()
                    .setMaxHeight(height)
                    .setMaxWidth(width)
                    .setMaxSize(maxSize)
                    .create();
            config = CompressConfig.ofLuban(option);
            config.enableReserveRaw(enableRawFile);
        }
        takePhoto.onEnableCompress(config, showProgressBar);

    }

    //裁剪图片
    private CropOptions getCropOptions() {
        int height = 290;//裁剪宽高比
        int width = 250;

        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);//宽x高
//        builder.setOutputX(25).setOutputY(29);//宽/高
        builder.setWithOwnCrop(false);//是否使用自带裁剪工具，true 是false 三方
        return builder.create();

    }
}
