package com.base.basemodule.activity.video;

import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.base.basemodule.R;
import com.base.basemodule.activity.BaseActivity;
import com.base.basemodule.base.BaseViewModel;
import com.base.basemodule.presenter.AbstractMvpPersenter;
import com.base.basemodule.utils.ActivityPath;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;

@Route(path = ActivityPath.PlayVideoActivity)
public class PlayVideoActivity extends BaseActivity  implements TextureView.SurfaceTextureListener {

    protected TextureView textureview;
    private MediaPlayer mMediaPlayer;
    private Surface surface;

    @Autowired
    String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(TextUtils.isEmpty(url)){
            ToastUtils.showShort("路径为空");
            finish();
        }
        setTitle("播放视频");
        textureview = findViewById(R.id.textureview);
        textureview.setSurfaceTextureListener(this);
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_play_video);
    }

    @Override
    protected BaseViewModel createViewModel() {
        return null;
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        surface=new Surface(surfaceTexture);
        new PlayerVideo().start();//开启一个线程去播放视频
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        surfaceTexture=null;
        surface=null;
        mMediaPlayer.stop();
        mMediaPlayer.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    private class PlayerVideo extends Thread{
        @Override
        public void run(){
            try {
//                File file=new File(url);
//                if(!file.exists()){//文件不存在
//                    ToastUtils.showShort("文件不存在");
//                }

                mMediaPlayer= new MediaPlayer();
                mMediaPlayer.setDataSource(url);
                mMediaPlayer.setSurface(surface);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp){
                        mMediaPlayer.start();
                    }
                });
                mMediaPlayer.prepare();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
