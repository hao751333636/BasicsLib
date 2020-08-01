package com.base.basemodule.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.blankj.utilcode.util.ScreenUtils;

public abstract class BaseDislogFragment extends DialogFragment {
    private boolean isShow = false;

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    /**
     * 显示弹窗信息
     * @param savedInstanceState
     * @param builder
     */
    protected abstract void displayDialog(Bundle savedInstanceState, AlertDialog.Builder builder);

    /**
     * 设置布局文件
     * @return
     */
    protected abstract int getLayoutId();

    protected abstract boolean isCanceledOnTouchOutside();

    protected abstract boolean isTransparent();
    protected abstract boolean setView(View view);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), null);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getDialog().setCancelable(false);//这个会屏蔽掉返回键
        getDialog().setCanceledOnTouchOutside(isCanceledOnTouchOutside());
        setView(rootView);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (isTransparent()) {
            //设置背景半透明
//            DisplayMetrics dm = new DisplayMetrics();
//            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            getDialog().getWindow().setLayout(ScreenUtils.getScreenWidth(), ScreenUtils.getScreenHeight());
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//        final View view = inflater.inflate(getLayoutId(), null);
//        builder.setView(view);
//        displayDialog(savedInstanceState,builder);
//        setView(view);
//        return builder.create();
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public void dismissDialog() {
        if (isShow) {
            this.dismiss();
        }
    }
}
