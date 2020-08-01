package com.base.basemodule.http;

import com.zhouyou.http.exception.ApiException;

public interface CallBack<T> {

    void onSuccess(T t);

    void onError(ApiException e);
}
