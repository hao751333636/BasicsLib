package com.base.basemodule.http;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import android.text.TextUtils;

import com.base.basemodule.entity.BaseListEntity;
import com.base.basemodule.entity.BaseResponse;
import com.base.basemodule.entity.BaseResultEntity;
import com.base.basemodule.utils.Utils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.body.ProgressResponseCallBack;
import com.zhouyou.http.callback.CallBackProxy;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.callback.CallBack;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import retrofit2.converter.gson.GsonConverterFactory;

@SuppressWarnings("unchecked")
public class HttpUtil {

    public static <T> Disposable post(String url, Map<String, String> map, CallBack<T> callBack) {
        return EasyHttp.post(url)
                .params(map)
                .sign(true)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))//自定义的可以设置GsonConverterFactory
                .timeStamp(true)
                .execute(new CallBackProxy<BaseResponse<T>, T>(callBack) {
                });
    }
    public static <T> Disposable post(String url, String json, CallBack<T> callBack) {
        return EasyHttp.post(url)
                .upJson(json)
                .sign(true)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))//自定义的可以设置GsonConverterFactory
                .timeStamp(true)
                .execute(new CallBackProxy<BaseResponse<T>, T>(callBack) {
                });
    }
    public static <T> Disposable postFile(String url, File file, CallBack<T> callBack) {
        return EasyHttp.post(url)
                .sign(true)
                .params(file.getName(), file, file.getName(), new ProgressResponseCallBack() {
                    @Override
                    public void onResponseProgress(long bytesWritten, long contentLength, boolean done) {
                    }
                })
                .addConverterFactory(GsonConverterFactory.create(new Gson()))//自定义的可以设置GsonConverterFactory
                .timeStamp(true)
                .execute(new CallBackProxy<BaseResponse<T>, T>(callBack) {
                });
    }


    public static <T> Disposable get(String url, Map<String, String> map, CallBack<T> callBack) {
        return EasyHttp.get(url)
                .params(map)
                .sign(true)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))//自定义的可以设置GsonConverterFactory
                .timeStamp(true)
                .execute(new CallBackProxy<BaseResponse<T>, T>(callBack) {
                });
    }


}
