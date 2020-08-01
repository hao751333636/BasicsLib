package com.base.basemodule.entity;

public class VideoEventEntity extends BaseEventEntity{

    public VideoEventEntity(String filePath) {
        this.filePath = filePath;
    }

    String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
