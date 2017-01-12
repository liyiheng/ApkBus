package com.apkbus.mobile.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by liyiheng on 17/1/12.
 */

public class BindHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding mBinding;

    public BindHolder(View itemView,ViewDataBinding binding) {
        super(itemView);
        mBinding = binding;
    }

    public ViewDataBinding getBinding() {
        return mBinding;
    }

    public void setBinding(ViewDataBinding mBinding) {
        this.mBinding = mBinding;
    }
}