package com.base.basemodule.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ImageView;

import com.base.basemodule.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class ImageUtil {

    public static void loadImage(Context context, Object path, ImageView imageView) {
        if (imageView == null) {
            return;
        } else {
            Glide.with(context).asBitmap().load(path)
                    .apply(new RequestOptions()
                            .dontAnimate()
                            .error(R.drawable.ic_image_failed)
                            .placeholder(R.mipmap.ic_default_head)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .fitCenter())

                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imageView);
        }
    }
    public static void loadImageFile(Context context, Object path, ImageView imageView) {
        if (imageView == null) {
            return;
        } else {
            Glide.with(context).asFile().load(path)
                    .apply(new RequestOptions()
                            .dontAnimate()
                            .error(R.drawable.ic_image_failed)
                            .placeholder(R.mipmap.ic_default_head)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .fitCenter())
                    .into(imageView);
        }
    }

    //加载圆形图片
    public static void loadCircleImage(Context context, Object path, ImageView imageView) {
        try {
            Glide.with(context).asBitmap().load(path)
                    .apply(new RequestOptions()
                            .placeholder(R.mipmap.ic_default_head)
                            .centerCrop()
                            .dontAnimate()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .transform(new GlideCircleTransform(context)))
                    .into(new BitmapImageViewTarget(imageView) {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource,
                                                    @Nullable Transition<? super Bitmap> transition) {
                            imageView.setImageBitmap(resource);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //加载高斯模糊图片
    public static void loadBlurImage(Context context, Object path, ImageView imageView, String nickname) {
        Drawable fromPath = Drawable.createFromPath(
                path.toString());
        Glide.with(context).load(path)
                .apply(new RequestOptions()
                        .placeholder(fromPath)
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .bitmapTransform(new BlurTransformation(context, 10)))
                .into(imageView);


    }


}
