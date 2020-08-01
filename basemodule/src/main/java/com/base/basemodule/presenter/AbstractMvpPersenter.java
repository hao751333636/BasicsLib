package com.base.basemodule.presenter;

import com.base.basemodule.presenter.interfaces.IMvpBaseView;

/**
 * <pre>
 *      author : shihaoyu
 *      e-mail : hao751333636@qq.com
 *      time   : 2018/01/24
 *      desc   :
 *      version: 1.0
 *  </pre>
 */

public abstract class AbstractMvpPersenter<V extends IMvpBaseView> {
    private V mMvpView;

    /**
     * 绑定V层
     *
     * @param view
     */
    public void attachMvpView(V view) {
        this.mMvpView = view;
    }

    /**
     * 解除绑定V层
     */
    public void detachMvpView() {
        mMvpView = null;
        System.gc();
    }

    /**
     * 获取V层
     *
     * @return
     */
    public V getmMvpView() {
        return mMvpView;
    }
}
