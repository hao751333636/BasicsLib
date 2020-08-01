package com.base.basemodule.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.view.WindowManager;
import android.widget.EditText;

import com.base.basemodule.R;
import com.base.basemodule.wedget.CustomDialog;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zhouyou.http.subsciber.IProgressDialog;

import org.devio.takephoto.model.TContextWrap;
import org.devio.takephoto.uitl.TConstant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static Utils utils;
    private static final Object mLock = new Object();

    public static final Utils get() {
        synchronized (mLock) {
            if (utils == null) {
                utils = new Utils();
            }
        }
        return utils;
    }

    private CustomDialog progressDialog;


    public static void main(String[] args) {
        List<ListBean.Result.Items> data = new ArrayList<>();
        ListBean list = new ListBean();
        data.clear();
        data.addAll((list == null ? new ArrayList<>() : (list.result == null ? new ArrayList<>() : (list.result.items == null ? new ArrayList<>() : list.result.items))));
    }

    static class ListBean {
        Result result;

        static class Result {
            List<Items> items;

            static class Items {

            }
        }

    }

    /**
     * 显示加载框
     *
     * @param context
     */
    public void showProgress(Context context) {
        createProgress(context, "");
    }

    /**
     * 显示加载框
     *
     * @param context
     * @param content
     */
    public void showProgress(Context context, String content) {
        createProgress(context, content);

    }

    public void dismissProgress() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建加载框
     *
     * @param context
     * @param content
     */
    private void createProgress(Context context, String content) {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
                progressDialog = null;
            }
            progressDialog = new CustomDialog(context, R.style.CustomDialog);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized AlertDialog showAlertDialog(Context context, String msg,
                                                    DialogInterface.OnClickListener onClickListener) {
        return showAlertDialog(context, msg, onClickListener, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    public synchronized AlertDialog showAlertDialog(Context context, String msg,
                                                    DialogInterface.OnClickListener onClickListener,
                                                    DialogInterface.OnClickListener cancelClickListener) {
        return showAlertDialog(context, msg, "确定", "取消", onClickListener, cancelClickListener);
    }

    public synchronized AlertDialog showAlertDialog(Context context, String msg, String btnText, String cancelText,
                                                    DialogInterface.OnClickListener onClickListener,
                                                    DialogInterface.OnClickListener cancelClickListener) {
        AlertDialog alertDialog = null;
        try {
            alertDialog = new AlertDialog.Builder(context)
                    .setMessage(msg)
                    .setCancelable(true)
                    .setPositiveButton(btnText, onClickListener)
                    .setNegativeButton(cancelText, cancelClickListener)
                    .create();
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alertDialog;
    }

    public synchronized AlertDialog showSureAlertDialog(Context context, String msg,
                                                        DialogInterface.OnClickListener onClickListener) {
        AlertDialog alertDialog = null;
        try {
            alertDialog = new AlertDialog.Builder(context)
                    .setMessage(msg)
                    .setCancelable(true)
                    .setPositiveButton("确定", onClickListener)
                    .create();
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alertDialog;
    }

    public synchronized AlertDialog showAlertDialogWithEdt(Context context, String msg, EditText view,
                                                           DialogInterface.OnClickListener onClickListener) {
        AlertDialog alertDialog = null;
        try {
            alertDialog = new AlertDialog.Builder(context)
                    .setMessage(msg)
                    .setCancelable(true)
                    .setView(view)
                    .setPositiveButton("确定", onClickListener)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alertDialog;
    }

    /**
     * 字符串转换成十六进制字符串
     *
     * @param str 待转换的ASCII字符串
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    public String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;

        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 设置亮度
     *
     * @param context
     * @param brightness
     */
    public void setLight(Activity context, int brightness) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        context.getWindow().setAttributes(lp);
    }

    public int getLight(Activity activity) {
        try {
            return Settings.System.getInt(activity.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 调用第三方浏览器打开
     *
     * @param context
     * @param url     要浏览的资源地址
     */
    public void openBrowser(Context context, String url) {
        LogUtils.e("url:" + url);
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名
        // 官方解释 : Name of the component implementing an activity that can display the intent
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            final ComponentName componentName = intent.resolveActivity(context.getPackageManager());
            // 打印Log   ComponentName到底是什么
            LogUtils.d("componentName = " + componentName.getClassName());
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            ToastUtils.showShort("请下载浏览器");
        }
    }

    public void requestPermission(@NonNull TContextWrap contextWrap, @NonNull String[] permissions) {
        if (contextWrap.getFragment() != null) {
            contextWrap.getFragment().requestPermissions(permissions, TConstant.PERMISSION_REQUEST_TAKE_PHOTO);
        } else {
            ActivityCompat.requestPermissions(contextWrap.getActivity(), permissions, TConstant.PERMISSION_REQUEST_TAKE_PHOTO);
        }
    }

    /**
     * 将assets下的文件放到sd指定目录下
     *
     * @param context    上下文
     * @param assetsPath assets下的路径
     * @param sdCardPath sd卡的路径
     */
    public void putAssetsToSDCard(Context context, String assetsPath,
                                  String sdCardPath) {
        try {
            String mString[] = context.getAssets().list(assetsPath);
            if (mString.length == 0) { // 说明assetsPath为空,或者assetsPath是一个文件
                InputStream mIs = context.getAssets().open(assetsPath); // 读取流
                byte[] mByte = new byte[1024];
                int bt = 0;
                File file = new File(sdCardPath + File.separator
                        + assetsPath);
                if (!file.exists()) {
                    file.createNewFile(); // 创建文件
                } else {

                }
                FileOutputStream fos = new FileOutputStream(file); // 写入流
                while ((bt = mIs.read(mByte)) != -1) { // assets为文件,从文件中读取流
                    fos.write(mByte, 0, bt);// 写入流到文件中
                }
                fos.flush();// 刷新缓冲区
                mIs.close();// 关闭读取流
                fos.close();// 关闭写入流

            } else { // 当mString长度大于0,说明其为文件夹
                sdCardPath = sdCardPath + File.separator + assetsPath;
                File file = new File(sdCardPath);
                if (!file.exists())
                    file.mkdirs(); // 在sd下创建目录
                for (String stringFile : mString) { // 进行递归
                    putAssetsToSDCard(context, assetsPath + File.separator
                            + stringFile, sdCardPath);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IProgressDialog getProgressDialog(Context context) {
        return new IProgressDialog() {
            @Override
            public Dialog getDialog() {
                return new CustomDialog(context, R.style.CustomDialog);
            }
        };

    }

}
