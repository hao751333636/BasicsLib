package com.base.basemodule.entity;

import java.util.List;

public class BaseListEntity<T> {
    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
