package com.base.basemodule.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.base.basemodule.R;
import com.base.basemodule.base.BaseViewModel;
import com.base.basemodule.base.IBaseViewModel;
import com.base.basemodule.entity.BaseEventEntity;
import com.base.basemodule.entity.VideoEventEntity;
import com.base.basemodule.http.HttpUtil;
import com.base.basemodule.presenter.AbstractMvpPersenter;
import com.base.basemodule.presenter.interfaces.IMvpBaseView;
import com.blankj.utilcode.util.LogUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


public abstract class BaseActivity<T extends BaseViewModel> extends RxAppCompatActivity implements IBaseViewModel {

    protected InputMethodManager inputMethodManager;

    public Toolbar toolbar;
    protected TextView titleView;
    protected RelativeLayout rightLayout;
    protected ImageView rightImage;
    protected TextView rightTxt;
    protected ImageView ivTitle;

    private boolean hasTitle = true;
    private boolean hasStatusBar = true;

    protected T mViewMode;

    protected Context mContext;
    private ViewModelProvider mActivityProvider;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView();
        initViewModel();
        ARouter.getInstance().inject(this);
        initToolBar();
        EventBus.getDefault().register(this);
        initStatusBar();
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getViewModel().injectLifecycleProvider(this);
    }

    protected void initStatusBar() {
        if (hasStatusBar) {
            ImmersionBar.with(this).fitsSystemWindows(true)  //使用该属性,必须指定状态栏颜色
                    .statusBarColor(R.color.colorPrimary)
                    .init();
        }
    }

    protected void setHasStatusBar(boolean b) {
        hasStatusBar = b;
    }

    private void initToolBar() {
        if (hasTitle) {
            toolbar = findViewById(R.id.toolbar);
            titleView = findViewById(R.id.title);
            rightLayout = findViewById(R.id.right_layout);
            rightImage = findViewById(R.id.right_image);
            rightTxt = findViewById(R.id.right_txt);
            toolbar = findViewById(R.id.toolbar);
            ivTitle = findViewById(R.id.iv_title);
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSoftKeyboard();
                    supportFinishAfterTransition();
                }
            });
        }
    }

    public void setContentView(int layoutId) {
        setContentView(layoutId, true);
    }

//    public void setContentView(View layoutId) {
//        setContentView(layoutId, true);
//    }

    /**
     * 容器模版
     *
     * @param layoutId         内容视图
     * @param isContainerTitle true 带有toolbar的布局容器 false无toolbar 你可以自定义标题实现复杂的title
     */
    protected void setContentView(int layoutId, boolean isContainerTitle) {
        if (isContainerTitle) {
            LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_toolbar, null);
            LayoutInflater.from(this).inflate(layoutId, root);
            super.setContentView(root);
        } else {
            setNoTitle();
            super.setContentView(layoutId);
        }
    }

    protected void setNoTitle() {
        hasTitle = false;
    }

    protected void setTitle(String title) {
        titleView.setText(title);
    }

    public void setTitle(CharSequence title) {
        titleView.setText(title);
    }

    public void setRightImageResource(int resId) {
        rightImage.setVisibility(View.VISIBLE);
        rightTxt.setVisibility(View.GONE);
        rightImage.setImageResource(resId);
    }

    public void setRightText(String text) {
        rightImage.setVisibility(View.GONE);
        rightTxt.setVisibility(View.VISIBLE);
        rightTxt.setText(text);
    }

    protected void showQuestion(View.OnClickListener clickListener) {
        ivTitle.setVisibility(View.VISIBLE);
        ivTitle.setOnClickListener(clickListener);
    }

    public void setRightLayoutClickListener(View.OnClickListener listener) {
        rightLayout.setOnClickListener(listener);
    }

    public RelativeLayout getRightLayout() {
        return rightLayout;
    }

    public TextView getRightTxt() {
        return rightTxt;
    }

    protected void showSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }

    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        try {
            super.onDestroy();
            ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void registerRxBus() {

    }

    @Override
    public void removeRxBus() {

    }


    /**
     * 1. 设置布局
     */
    protected abstract void setContentView();

    @SuppressLint("ResourceAsColor")
    protected void setBarColor(@ColorRes int color) {
        toolbar.setBackgroundColor(color);
    }

    @Subscribe
    public <T extends BaseEventEntity> void onEventMainThread(T t) {
    }

    @Subscribe
    public void onEventMainThread(VideoEventEntity t) {
    }


    protected abstract T createViewModel();


    private void initViewModel() {
        if (mViewMode == null) {
            mViewMode = createViewModel();
        }
    }

    protected <T extends ViewModel> T getActivityViewModel(@NonNull Class<T> modelClass) {
        if (mActivityProvider == null) {
            mActivityProvider = new ViewModelProvider(this);
        }
        return mActivityProvider.get(modelClass);
    }

    public T getViewModel() {
        return mViewMode;
    }
}
