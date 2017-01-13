package com.apkbus.mobile.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by liyiheng on 17/1/12.
 */

public class BindHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding mBinding;

    public BindHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public ViewDataBinding getBinding() {
        return mBinding;
    }

}