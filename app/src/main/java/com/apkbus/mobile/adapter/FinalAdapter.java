package com.apkbus.mobile.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;

import java.util.List;

/**
 * Created by liyiheng on 17/1/13.
 */

public class FinalAdapter<T> extends RecyclerView.Adapter<BindHolder> {
    private static final int MULTI_ITEM_TYPE = -1;
    private List<T> mData;
    private TypeSelector<T> mTypeSelector;

    private int mItemLayout = 0;
    private int mHeaderType = 0;
    private int mFooterType = 0;

    private Object mHeadData, mFootData;
    private int mHasHeader, mHasFooter = 1;

    private ClickCallback<T> mInterf;


    public void setClickCallback(ClickCallback<T> mInterf) {
        this.mInterf = mInterf;
    }

    public List<T> getData() {
        return mData;
    }

    public FinalAdapter(@LayoutRes int itemLayout) {
        this.mItemLayout = itemLayout;

    }

    public TypeSelector<T> getTypeSelector() {
        return mTypeSelector;
    }

    public void setTypeSelector(TypeSelector<T> mTypeSelector) {
        this.mTypeSelector = mTypeSelector;
        mItemLayout = MULTI_ITEM_TYPE;
    }

    public void setHeadView(@LayoutRes int i, Object data) {
        this.mHasHeader = i == 0 ? 0 : 1;
        if (this.mHasHeader == 1) {
            this.mHeaderType = i;
            this.mHeadData = data;
        }
    }

    public void setFooterView(@LayoutRes int i, Object data) {
        this.mHasFooter = i == 0 ? 0 : 1;
        if (mHasFooter == 1) {
            this.mFootData = data;
            this.mFooterType = i;
        }
    }

    public void setViewType(@LayoutRes int type) {
        this.mItemLayout = type;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0 && mHasHeader > 0) {
            return mHeaderType;
        }
        if (mHasFooter > 0 && position == getItemCount() - 1) {
            return mFooterType;
        }
        if (mItemLayout == MULTI_ITEM_TYPE) {
            Object bean = getBean(position);
            return mTypeSelector.type((T) bean);
        }
        return mItemLayout;
    }

    @Override
    public BindHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding dataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false);
        if (mInterf != null) dataBinding.setVariable(BR.callback, mInterf);

        return new BindHolder(dataBinding);
    }


    @Override
    public void onBindViewHolder(BindHolder holder, int position) {
        holder.getBinding().setVariable(BR.bean, getBean(position));
        holder.getBinding().executePendingBindings();
//        ScaleAnimation animation = new ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f);
//        animation.setDuration(200);
//        holder.itemView.startAnimation(animation);
    }

    private Object getBean(int position) {
        if (position == 0 && mHasHeader == 1) return mHeadData;
        if (position == getItemCount() - 1 && mHasFooter == 1) return mFootData;
        return mData.get(position - mHasHeader);
    }

    @Override
    public int getItemCount() {
        int extra = mHasFooter + mHasHeader;
        return mData == null ? extra : mData.size() + extra;
    }

    /**
     * Update the data resource.
     *
     * @param res New data.
     */
    public void updateRes(List<T> res) {
        if (res != null) {
            this.mData = res;
            notifyDataSetChanged();
        }
    }

    public void addRes(List<T> res) {
        if (res != null) {
            this.mData.addAll(res);
            notifyDataSetChanged();
        }
    }
}
