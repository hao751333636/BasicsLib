package com.base.basemodule.activity.video;

import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.basemodule.R;
import com.base.basemodule.activity.BaseActivity;
import com.base.basemodule.presenter.AbstractMvpPersenter;

import java.io.File;

public abstract class BasePlayActivity extends BaseActivity {

    protected String filePath;
    protected MediaPlayer mMediaPlayer;

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public void doPlay(String url) {
        //配置播放器 MediaPlayer
        mMediaPlayer = new MediaPlayer();
        try {
            //设置声音文件
            mMediaPlayer.setDataSource(url);

            //设置监听回调
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlay();
                }
            });
            //设置出错的监听器
            mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    playFail();
                    //提示用户
                    stopPlay();
                    //释放播放器
                    return true;
                }
            });
            //配置音量，是否循环
//            mMediaPlayer.setVolume(1,1);
            mMediaPlayer.setLooping(false);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
            playFail();
            stopPlay();
        }
    }

    public void stopPlay() {
        try {
            if (mMediaPlayer != null) {
                mMediaPlayer.reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopPlay();
    }

    protected abstract void playFail();

}
