package com.apkbus.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apkbus.mobile.R;
import com.apkbus.mobile.bean.GiftWrapper;
import com.apkbus.mobile.widget.ListFooter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyiheng on 16/9/29.
 */

public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.ImageHolder> {
    private List<GiftWrapper.Gift> mData;
    private LayoutInflater mInflater;

    /**
     * Normal item type.
     */
    private static final int TYPE_COMMON = 0;
    /**
     * Footer type.
     */
    private static final int TYPE_FOOTER = 1;
    /**
     * The footer shows "Loading,TheEnd,Error or Normal"
     */
    private ListFooter mFooter;
    /**
     * Current status of the footer.
     */
    private ListFooter.State footerStatus = ListFooter.State.Loading;

    public GiftAdapter(Context context) {
        this.mData = new ArrayList<>();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mFooter = new ListFooter(context);
    }

    /**
     *
     */
    public void setFooterStatus(ListFooter.State status) {
        this.footerStatus = status;
        notifyItemChanged(getItemCount() - 1);
    }

    public void updateRes(List<GiftWrapper.Gift> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void addRes(List<GiftWrapper.Gift> data) {
        if (data != null) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_FOOTER:
                return new ImageHolder(mFooter);
            default:
                View view = mInflater.inflate(R.layout.item_gift, parent, false);
                return new ImageHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_FOOTER:
                ((ListFooter) holder.itemView).setState(footerStatus);
                break;
            case TYPE_COMMON:
                GiftWrapper.Gift gift = mData.get(position);
                holder.image.setImageURI(gift.getUrl());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 1 : mData.size() + 1;
    }

    /**
     * The last one is the footer.
     */
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_COMMON;
    }

    class ImageHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView image;

        public ImageHolder(View itemView) {
            super(itemView);
            if (itemView == mFooter) return;
            image = ((SimpleDraweeView) itemView.findViewById(R.id.item_gift_image));
        }
    }
}
