package com.base.basemodule.entity;

import com.zhouyou.http.model.ApiResult;

import java.io.Serializable;

/**
 * Created by 安卓 on 2017/11/3.
 */

public class BaseResponse<T> extends ApiResult<T> implements Serializable {


    /**
     * Success : true
     * Code : 1
     * Message : 操作成功
     * Data : 16:05:44
     * Count : 0
     */

    private boolean Success;
    private String Message;
    private T Data;
    private int Count;
    private String Code;

    @Override
    public boolean isOk() {
        return Success;
    }

    @Override
    public String getMsg() {
        return Message;
    }

    @Override
    public T getData() {
        return Data;
    }

    @Override
    public int getCode() {
        return Integer.parseInt(Code);
    }

    public int getCount() {
        return Count;
    }
}
