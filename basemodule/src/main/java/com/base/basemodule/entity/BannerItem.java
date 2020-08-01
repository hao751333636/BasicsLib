package com.base.basemodule.entity;

/**
 * Created by hao75 on 2017/7/12.
 */

public class BannerItem {
    public BannerItem(String image) {
        this.image = image;
    }


    public BannerItem(int imageId) {
        this.imageId = imageId;
    }

    private int imageId;

    public int getImageId() {
        return imageId;
    }

    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
