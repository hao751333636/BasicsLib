package com.base.basemodule.wedget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.base.basemodule.R;
import com.base.basemodule.entity.BannerItem;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;


/**
 * Created by Yuki 轮播图配置器
 * <p>
 * 图片轮播控件ConvenientBanner
 */
public class BannerHolderView implements Holder<BannerItem> {
    private View view;

    @Override
    public View createView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.item_banner, null, false);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, BannerItem data) {
        ImageView iv = (ImageView) view.findViewById(R.id.img_main_banner);
//        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();

//        ImageUtil.loadImage(context, StringUtils.isEmpty(data.getImage()) ? data.getImageId() : data.getImage(), iv);
        Glide.with(context)
                .load(TextUtils.isEmpty(data.getImage()) ? data.getImageId() : data.getImage())
//                .placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                .into(iv);


    }
}