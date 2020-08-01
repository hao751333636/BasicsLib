package com.base.basemodule.activity.video;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.basemodule.R;
import com.base.basemodule.activity.BaseActivity;
import com.base.basemodule.base.BaseViewModel;
import com.base.basemodule.entity.VideoEventEntity;
import com.base.basemodule.presenter.AbstractMvpPersenter;
import com.base.basemodule.utils.ActivityPath;

import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.permission.PermissionManager;
import org.devio.takephoto.uitl.TConstant;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

@Route(path = ActivityPath.VideoActivity)
public class VideoActivity extends BaseActivity {

    private final int REQUEST_CODE_VIDEO = 0x03;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TContextWrap contextWrap = TContextWrap.of(this);
        boolean cameraGranted =
                ContextCompat.checkSelfPermission(contextWrap.getActivity(), PermissionManager.TPermission.CAMERA.stringValue())
                        == PackageManager.PERMISSION_GRANTED ? true : false;
        if (!cameraGranted) {
            ArrayList<String> permissions = new ArrayList<>();
            permissions.add(PermissionManager.TPermission.CAMERA.stringValue());
            requestPermission(contextWrap, permissions.toArray(new String[permissions.size()]));
        } else {
            video();
        }

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_video);
    }

    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }

    public void video(){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        // 录制视频最大时长10s
        intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
        intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT,1);
        startActivityForResult(intent, REQUEST_CODE_VIDEO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_VIDEO:
                    Uri uri = data.getData();
                    Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                    if (cursor != null && cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.VideoColumns._ID));
                        // 视频路径
                        String filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DATA));
                        EventBus.getDefault().post(new VideoEventEntity(filePath));
                        // ThumbnailUtils类2.2以上可用  Todo 获取视频缩略图
//                        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(filePath, MediaStore.Images.Thumbnails.MICRO_KIND);
//                        // 图片Bitmap转file
//                        File file = CommonUtils.compressImage(bitmap);
//                        // 保存成功后插入到图库，其中的file是保存成功后的图片path。这里只是插入单张图片
//                        // 通过发送广播将视频和图片插入相册
//                        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));

                        cursor.close();
                        finish();
                    }
                    break;
            }
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }

    public static void requestPermission(@NonNull TContextWrap contextWrap, @NonNull String[] permissions) {
        if (contextWrap.getFragment() != null) {
            contextWrap.getFragment().requestPermissions(permissions, TConstant.PERMISSION_REQUEST_TAKE_PHOTO);
        } else {
            ActivityCompat.requestPermissions(contextWrap.getActivity(), permissions, TConstant.PERMISSION_REQUEST_TAKE_PHOTO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        video();
    }
}
