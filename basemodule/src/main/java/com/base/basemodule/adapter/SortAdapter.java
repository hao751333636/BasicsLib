package com.base.basemodule.adapter;

import android.content.Context;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.base.basemodule.R;
import com.base.basemodule.entity.SortModel;
import com.base.basemodule.utils.image.ImageUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author: xp
 * @date: 2017/7/19
 */

public class SortAdapter extends BaseQuickAdapter<SortModel, BaseViewHolder> {

    private boolean mIsSelect = false;


    public SortAdapter(int layoutResId, @Nullable List<SortModel> data,boolean isSelect) {
        super(layoutResId, data);
        this.mIsSelect = isSelect;
    }

    @Override
    protected void convert(BaseViewHolder holder, SortModel t) {
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, t);
                }
            });

        }
        if (mIsSelect) {
            holder.getView(R.id.iv_radio).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.iv_radio).setVisibility(View.INVISIBLE);
        }
        if (t.isSelect()) {
            holder.getView(R.id.iv_radio).setBackgroundResource(R.mipmap.ic_choose_sel);
        } else {
            holder.getView(R.id.iv_radio).setBackgroundResource(R.mipmap.ic_choose_nor);
        }
        holder.setText(R.id.tvName,t.getName());
        ImageUtil.loadCircleImage(mContext, R.mipmap.ic_default_head, holder.getView(R.id.iv_head));
        holder.getView(R.id.tvName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    mOnItemClickListener.onItemClick(holder.itemView, t);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public List<? extends SortModel> getmData() {
        return mData;
    }

    //**********************itemClick************************
    public interface OnItemClickListener {
        void onItemClick(View view, SortModel bean);
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    //**************************************************************

    public void refresh(List<SortModel> data) {
        if (mData != null) {
            mData.clear();
            mData = data;
            notifyDataSetChanged();
        }
    }

    /**
     * 提供给Activity刷新数据
     *
     * @param list
     */
    public void updateList(List<SortModel> list) {
        this.mData = list;
        notifyDataSetChanged();
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return mData.get(position).getLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getItemCount(); i++) {
            String sortStr = mData.get(i).getLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

}
