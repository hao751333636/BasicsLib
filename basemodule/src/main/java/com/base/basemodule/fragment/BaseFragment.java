package com.base.basemodule.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.base.basemodule.base.BaseViewModel;
import com.base.basemodule.http.HttpUtil;
import com.gyf.barlibrary.ImmersionFragment;

import java.util.Objects;

public abstract class BaseFragment<T extends BaseViewModel> extends ImmersionFragment {

    protected T mViewMode;
    private ViewModelProvider mActivityProvider;

    protected InputMethodManager inputMethodManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        initViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        initViewModel();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void initImmersionBar() {

    }

    protected void hideSoftKeyboard() {
        if (getActivity().getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getActivity().getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }



    protected abstract T createViewModel();

    private void initViewModel() {
        if (mViewMode == null) {
            mViewMode = createViewModel();
        }
    }


    protected <T extends ViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(Objects.requireNonNull(this));
        }
        return mActivityProvider.get(modelClass);
    }

    public T getViewModel() {
        return mViewMode;
    }

}
