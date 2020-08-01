package com.base.basemodule.http;

import org.json.JSONObject;

/**
 * Created by Yuki on 16/11/7.
 */

public interface JsonCallback extends CallBack{
    void onBefore(int what);
    void onAfter(int what);
    void onFailed(JSONObject response, int what);
//    void onResponse(String response, int what);
    void onResponse(JSONObject response, int what);
    void onError(Exception e, int what);
}
