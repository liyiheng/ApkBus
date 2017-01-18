package com.apkbus.mobile.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by liyiheng on 17/1/18.
 */

public class LAnimator extends RecyclerView.ItemAnimator {

    private ArrayList<RecyclerView.ViewHolder> mPendingAddHolders =
            new ArrayList<>();
    private ArrayList<RecyclerView.ViewHolder> mPendingRemoveHolders =
            new ArrayList<>();
    private ArrayList<RecyclerView.ViewHolder> mAddAnimtions = new ArrayList<>();
    private ArrayList<RecyclerView.ViewHolder> mRemoveAnimations = new ArrayList<>();

    @Override
    public boolean animateDisappearance(@NonNull RecyclerView.ViewHolder viewHolder,
                                        @NonNull ItemHolderInfo preLayoutInfo,
                                        @Nullable ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder,
                                     @Nullable ItemHolderInfo preLayoutInfo,
                                     @NonNull ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public boolean animatePersistence(@NonNull RecyclerView.ViewHolder viewHolder,
                                      @NonNull ItemHolderInfo preLayoutInfo,
                                      @NonNull ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder,
                                 @NonNull RecyclerView.ViewHolder newHolder,
                                 @NonNull ItemHolderInfo preLayoutInfo,
                                 @NonNull ItemHolderInfo postLayoutInfo) {
        return false;
    }

    @Override
    public void runPendingAnimations() {

    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {
        item.itemView.setScaleY(1.0f);
        item.itemView.setScaleX(1.0f);
    }

    @Override
    public void endAnimations() {

    }

    @Override
    public boolean isRunning() {
        return (!mPendingAddHolders.isEmpty() ||
                !mAddAnimtions.isEmpty() ||
                !mRemoveAnimations.isEmpty() ||
                !mPendingRemoveHolders.isEmpty());
    }


}
