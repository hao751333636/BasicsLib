package com.base.basemodule.http;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

import static com.zhouyou.http.utils.HttpUtil.UTF8;

public class BaseExpiredInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(UTF8);
        }
        String bodyString = buffer.clone().readString(charset);
//        LogUtils.e("EasyHttp:" + bodyString);
        try {
            JSONObject jsonObject = new JSONObject(bodyString);
            String data = jsonObject.getString("Data");
            String newBodyString = StringEscapeUtils.unescapeJava(data);
            if (newBodyString.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(newBodyString);
                jsonObject.put("Data", jsonArray);
            } else if(newBodyString.startsWith("{")){
                JSONObject jsonObject1 = new JSONObject(newBodyString);
                jsonObject.put("Data", jsonObject1);
            }else{
                jsonObject.put("Data", newBodyString);
            }
            LogUtils.e("EasyHttp:" + jsonObject);
            ResponseBody newResponseBody = ResponseBody.create(contentType, jsonObject.toString());
            response = response.newBuilder().body(newResponseBody).build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return response;
    }

}
