package com.base.basemodule.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

public class AnimUtils {
    private static AnimUtils utils;
    private static final Object mLock = new Object();

    public static final AnimUtils get() {
        synchronized (mLock) {
            if (utils == null) {
                utils = new AnimUtils();
            }
        }
        return utils;
    }


    public void startRotateAnimation(View view,float fromDegrees, float toDegrees){
        view.clearAnimation();

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view,"rotation",fromDegrees,toDegrees);
        objectAnimator.setDuration(300);
        objectAnimator.start();
    }

    public void startMoveAnimation(View view, float startX, float endX, float startY, float endY, Animator.AnimatorListener animatorListener){
        ObjectAnimator translationX = new ObjectAnimator().ofFloat(view,"translationX",startX,endX);
        ObjectAnimator translationY = new ObjectAnimator().ofFloat(view,"translationY",startY,endY);

        AnimatorSet animatorSet = new AnimatorSet();  //组合动画
        animatorSet.playTogether(translationX,translationY); //设置动画
        animatorSet.setDuration(500);  //设置动画时间
        animatorSet.addListener(animatorListener);
        animatorSet.start(); //启动
    }

}
