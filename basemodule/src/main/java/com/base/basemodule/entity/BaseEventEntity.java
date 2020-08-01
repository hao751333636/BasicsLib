package com.base.basemodule.entity;

public class BaseEventEntity {

    String state;
    Object object;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BaseEventEntity() {
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public BaseEventEntity(String state) {
        this.state = state;
    }
}
