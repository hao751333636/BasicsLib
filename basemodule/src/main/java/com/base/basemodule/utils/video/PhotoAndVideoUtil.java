package com.base.basemodule.utils.video;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.SDCardUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;

public class PhotoAndVideoUtil {

    private static PhotoAndVideoUtil instance;
    protected File cameraFile;

    private PhotoAndVideoUtil() {
    }

    public static PhotoAndVideoUtil getInstance() {
        if (instance == null) {
            synchronized (PhotoAndVideoUtil.class) {
                if (instance == null) {
                    instance = new PhotoAndVideoUtil();
                }
            }
        }
        return instance;
    }

    public void camera(Activity activity) {
        if (!SDCardUtils.isSDCardEnable()) {
            ToastUtils.showShort("sd卡不存在");
            return;
        }
        cameraFile = new File(PathUtils.getExternalPicturesPath(),
                +System.currentTimeMillis() + ".jpg");
        //noinspection ResultOfMethodCallIgnored
        cameraFile.getParentFile().mkdirs();
//        activity.startActivityForResult(
//                new Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile)),
//                REQUEST_CODE_CAMERA);
    }


}
