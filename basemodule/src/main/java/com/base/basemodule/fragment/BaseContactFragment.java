package com.base.basemodule.fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.basemodule.R;
import com.base.basemodule.adapter.SortAdapter;
import com.base.basemodule.entity.SortModel;
import com.base.basemodule.utils.PinyinUtils;
import com.base.basemodule.wedget.ClearEditText;
import com.base.basemodule.wedget.PinyinComparator;
import com.base.basemodule.wedget.TitleItemDecoration;
import com.base.basemodule.wedget.WaveSideBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class BaseContactFragment extends BaseFragment {


    public BaseContactFragment() {
        // Required empty public constructor
    }


    private RecyclerView mRecyclerView;
    protected WaveSideBar mSideBar;
    protected SortAdapter mAdapter;
    private ClearEditText mClearEditText;
    private LinearLayoutManager manager;
    private LinearLayout ll_root;

    private List<SortModel> mDateList;
    private List<SortModel> mCopyDateList;
    private TitleItemDecoration mDecoration;


    boolean mIsSelect;
    /**
     * 根据拼音来排列RecyclerView里面的数据类
     */
    private PinyinComparator mComparator;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_contact, container, false);
        ll_root = view.findViewById(R.id.ll_root);
        mSideBar = (WaveSideBar) view.findViewById(R.id.sideBar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv);
        mClearEditText = (ClearEditText) view.findViewById(R.id.filter_edit);
        return view;
    }

    protected void initViews(View view, boolean isSelect, List<? extends SortModel> list) {
        mComparator = new PinyinComparator();


        //设置右侧SideBar触摸监听
        mSideBar.setOnTouchLetterChangeListener(new WaveSideBar.OnTouchLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(letter.charAt(0));
                if (position != -1) {
                    manager.scrollToPositionWithOffset(position, 0);
                }
            }
        });
        mIsSelect = isSelect;
        mDateList = (List<SortModel>) list;
        mCopyDateList = Arrays.asList(new SortModel[mDateList.size()]);
        Collections.copy(mCopyDateList, mDateList);
        // 根据a-z进行排序源数据
        Collections.sort(mDateList, mComparator);

        //RecyclerView设置manager
        manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new SortAdapter(R.layout.item_sel_name, mDateList, mIsSelect);
        mRecyclerView.setAdapter(mAdapter);
        if (onItemClickListener != null) {
            mAdapter.setOnItemClickListener(onItemClickListener);
        }
        mDecoration = new TitleItemDecoration(getContext(), mDateList);
        //如果add两个，那么按照先后顺序，依次渲染。
        mRecyclerView.addItemDecoration(mDecoration);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));



        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    /**
     * 根据输入框中的值来过滤数据并更新RecyclerView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {

            mAdapter = new SortAdapter(R.layout.item_sel_name, mDateList,mIsSelect);
            mRecyclerView.setAdapter(mAdapter);
            if (onItemClickListener != null) {
                mAdapter.setOnItemClickListener(onItemClickListener);
            }
        } else {
            filterDateList.clear();
            for (SortModel sortModel : mDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1 ||
                        PinyinUtils.getFirstSpell(name).startsWith(filterStr.toString())
                        //不区分大小写
                        || PinyinUtils.getFirstSpell(name).toLowerCase().startsWith(filterStr.toString())
                        || PinyinUtils.getFirstSpell(name).toUpperCase().startsWith(filterStr.toString())
                        ) {
                    filterDateList.add(sortModel);
                }
            }
            // 根据a-z进行排序
            Collections.sort(filterDateList, mComparator);
//            mAdapter.refresh(filterDateList);
            mAdapter = new SortAdapter(R.layout.item_sel_name, filterDateList,mIsSelect);
            mRecyclerView.setAdapter(mAdapter);
            if (onItemClickListener != null) {
                mAdapter.setOnItemClickListener(onItemClickListener);
            }
        }


    }

    private SortAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(SortAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * 为RecyclerView填充数据
     *
     * @param
     * @return
     */
    protected void filledData(List<? extends SortModel> list) {

        for (int i = 0; i < list.size(); i++) {
            SortModel sortModel = list.get(i);
            sortModel.setName(list.get(i).getName());
            //汉字转换成拼音
            String pinyin = PinyinUtils.getPingYin(list.get(i).getName());
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setLetters(sortString.toUpperCase());
            } else {
                sortModel.setLetters("#");
            }

        }

    }

    protected LinearLayout getRootView() {
        return ll_root;
    }


}
