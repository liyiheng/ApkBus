package com.apkbus.mobile.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * ViewHolder for FinalAdapter.
 * Created by liyiheng on 17/1/12.
 */

class BindHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding mBinding;

    BindHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    ViewDataBinding getBinding() {
        return mBinding;
    }

}