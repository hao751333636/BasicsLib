package com.base.basemodule.activity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.basemodule.R;
import com.base.basemodule.base.BaseViewModel;
import com.base.basemodule.presenter.AbstractMvpPersenter;
import com.base.basemodule.presenter.interfaces.IMvpBaseView;
import com.base.basemodule.utils.AnimUtils;
import com.base.basemodule.wedget.ClearEditText;
import com.base.basemodule.wedget.MyItemDecoration;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseListActivity<T extends BaseViewModel, D> extends BaseActivity<T> {

    protected RecyclerView recyclerView;
    protected SmartRefreshLayout refreshLayout;
    protected BaseListAdapter mAdapter;
    protected FrameLayout llMain;
    protected LinearLayout ll_float;
    protected ImageView iv_switch;

    private int mSkipCount = 0;
    private int mPageSize = 10;

    private boolean isOpenRefresh = false;

    private boolean isOpenLoadMore = false;
    protected int refresh = 0;
    protected List<D> mDatas;

    private boolean isSearch = false;
    private ClearEditText filter_edit;

    private SearchListener searchListener;
    protected boolean isSearchSwitched = false;
    View.OnClickListener switchClick;

    protected MyItemDecoration decor = new MyItemDecoration();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView = findViewById(R.id.recyclerView);
        llMain = findViewById(R.id.ll_main);
        ll_float = findViewById(R.id.ll_float);
        refreshLayout = findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(decor);
        mDatas = new ArrayList<>();
        mAdapter = new BaseListAdapter(initItemLayout(), mDatas);
        recyclerView.setAdapter(mAdapter);
        initView();
    }

    public void setSearch(boolean b, SearchListener searchListener) {
        this.isSearch = b;
        this.searchListener = searchListener;
        View header = LayoutInflater.from(this).inflate(R.layout.header_list_search, null);
        filter_edit = header.findViewById(R.id.filter_edit);
        iv_switch = header.findViewById(R.id.iv_switch);
        setHintText("搜索");
        iv_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSearchSwitched) {
                    isSearchSwitched = false;
                    AnimUtils.get().startRotateAnimation(v, 90, 180);
                } else {
                    isSearchSwitched = true;
                    AnimUtils.get().startRotateAnimation(v, 0, 90);
                }
                if (switchClick != null) {
                    switchClick.onClick(v);
                }
            }
        });
        filter_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                hideSoftKeyboard();
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (searchListener != null) {
                        searchListener.search(v.getText().toString());
                    }
                    return true;
                }
                return false;
            }
        });
        mAdapter.addHeaderView(header);

    }

    public EditText getSearchEdit() {
        return filter_edit;
    }

    public void setSwitchClick(View.OnClickListener onClickListener) {
        switchClick = onClickListener;
    }

    public void setFloat(View v) {
        ll_float.addView(v);
    }

    public void setHintText(String text) {
        SpannableString ss = new SpannableString(text);//定义hint的值
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(12, true);//设置字体大小 true表示单位是sp
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        filter_edit.setHint(new SpannedString(ss));
    }


    public void setOpenRefresh(boolean openRefresh) {
        isOpenRefresh = openRefresh;
        if (isOpenRefresh) {
            refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(RefreshLayout refreshlayout) {
                    refreshListener();
                    refresh = 1;
                }
            });
        }

        refreshLayout.setEnableRefresh(isOpenRefresh);


    }

    public void setOpenLoadMore(boolean openLoadMore) {
        isOpenLoadMore = openLoadMore;
        if (isOpenLoadMore) {
            refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(RefreshLayout refreshlayout) {
                    loadMoreListener();
                    refresh = 2;
                }
            });
        }

        refreshLayout.setEnableLoadMore(isOpenLoadMore);

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.layout_list);
    }


    public int getmPage() {
        return mSkipCount;
    }

    public void setmPage(int mPage) {
        this.mSkipCount = mPage;
    }

    public int getmPageSize() {
        return mPageSize;
    }

    public void setmPageSize(int mPageSize) {
        this.mPageSize = mPageSize;
    }


    protected void requestEnd(List<D> list) {
        if (list != null) {
            if (refresh == 1) {
                setDatas(list);
                refreshOK();
            } else {
                mDatas.addAll(list);
                loadMoreOK();
                mAdapter.notifyDataSetChanged();
            }
        } else {
            if (refresh == 1) {
                refreshFail();
            } else {
                loadMoreFail();
            }
        }
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore();
    }

    protected void refreshOK() {
        mSkipCount = mDatas.size();
        mPageSize = 10;
    }

    protected void refreshFail() {
    }

    protected void loadMoreOK() {
        mSkipCount = mDatas.size();
    }

    protected void loadMoreFail() {
    }

    public void setDatas(List<D> datas, int position) {
        mDatas.clear();
        mDatas.addAll(datas);
        mAdapter.notifyItemChanged(position);
    }

    public void setDatas(List<D> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }

    public FrameLayout getRootView() {
        return llMain;
    }

    /**
     * 初始化子布局
     */
    protected abstract
    @LayoutRes
    int initItemLayout();

    public class BaseListAdapter extends BaseQuickAdapter<D, BaseViewHolder> {

        public BaseListAdapter(int layoutResId, List<D> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder baseViewHolder, D t) {
            MyHolder(baseViewHolder, t);
        }
    }

    /**
     * 初始化子布局
     */
    protected abstract void refreshListener();

    /**
     * 初始化子布局
     */
    protected abstract void loadMoreListener();

    /**
     * adapter内的处理
     *
     * @param baseViewHolder BaseViewHolder
     * @param t              泛型T
     */
    protected abstract void MyHolder(BaseViewHolder baseViewHolder, D t);

    protected abstract void initView();

    protected interface SearchListener {
        void search(String content);
    }
}
