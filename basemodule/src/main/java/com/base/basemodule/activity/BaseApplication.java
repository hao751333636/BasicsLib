//package com.base.basemodule.activity;
//
//import android.content.Context;
//import android.support.multidex.MultiDex;
//import android.support.multidex.MultiDexApplication;
//
//import com.alibaba.android.arouter.launcher.ARouter;
//import com.baidu.mapapi.CoordType;
//import com.baidu.mapapi.SDKInitializer;
//import com.tencent.bugly.crashreport.CrashReport;
//import com.uuzuche.lib_zxing.activity.ZXingLibrary;
//import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;
//import com.yolanda.nohttp.NoHttp;
//import com.yolanda.nohttp.cache.DBCacheStore;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.util.concurrent.TimeUnit;
//
//import okhttp3.OkHttpClient;
//
//public class BaseApplication extends MultiDexApplication {
//
//    private boolean isDebugARouter = true;
//
//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        CrashReport.initCrashReport(getApplicationContext(), "3c9b9d0589", false);
//        initNOHttp();
//        if (isDebugARouter) {
//            // 下面两行必须写在init之前，否则这些配置在init过程中将无效
//            ARouter.openLog();     // 打印日志
//            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！
//            // 线上版本需要关闭,否则有安全风险)
//            ARouter.openDebug();
//        }
//        //初始化ARouter
//        ARouter.init(this);
//        //初始化二维码
//        ZXingLibrary.initDisplayOpinion(this);
//        initBaidu();
//    }
//
//    private void initBaidu() {
//        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
//        SDKInitializer.initialize(this);
//        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
//        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
//        SDKInitializer.setCoordType(CoordType.BD09LL);
//    }
//
//    @Override
//    public void onTerminate() {
//        super.onTerminate();
//        ARouter.getInstance().destroy();
//    }
//
//    private void initNOHttp() {
//        new OkHttpClient.Builder()
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .readTimeout(60, TimeUnit.SECONDS)
//                .writeTimeout(30, TimeUnit.SECONDS)
//                .build();
//        NoHttp.initialize(this, new NoHttp.Config()
//                // 设置全局连接超时时间，单位毫秒
//                .setConnectTimeout(30 * 1000)
//                // 设置全局服务器响应超时时间，单位毫秒
//                .setReadTimeout(30 * 1000)
//                .setNetworkExecutor(new OkHttpNetworkExecutor())
//                .setCacheStore(
//                        // 如果不使用缓存，设置false禁用。
//                        new DBCacheStore(this).setEnable(true))
//        );
//        com.yolanda.nohttp.Logger.setDebug(true);// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
//        com.yolanda.nohttp.Logger.setTag("NoHttpSample");// 设置NoHttp打印Log的tag。
//    }
//
//
//
//}
