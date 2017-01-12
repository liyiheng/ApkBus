package com.apkbus.mobile.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apkbus.mobile.BR;
import com.apkbus.mobile.R;
import com.apkbus.mobile.bean.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyiheng on 16/9/19.
 */
public class ArticleAdapter extends RecyclerView.Adapter<BindHolder> implements View.OnClickListener, View.OnLongClickListener {
    private final LayoutInflater mInflater;
    private List<Bean> mData;

    public ArticleAdapter(Context context) {
        mInflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        mData = new ArrayList<>();
    }

    /**
     * Update the data resource.
     *
     * @param res New data.
     */
    public void updateRes(List<Bean> res) {
        if (res != null) {
            this.mData = res;
            notifyDataSetChanged();
        }
    }

    public void addRes(List<Bean> res) {
        if (res != null) {
            this.mData.addAll(res);
            notifyDataSetChanged();
        }
    }

    @Override
    public BindHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(mInflater, R.layout.item_demos, parent, false);
        return new BindHolder(binding.getRoot(),binding);
        //View view = mInflater.inflate(R.layout.item_demos, parent, false);
        //return new ArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(BindHolder holder, int position) {
        Bean bean = mData.get(position);

        holder.itemView.setOnClickListener(this);
        holder.itemView.setOnLongClickListener(this);
        holder.itemView.setTag(bean);

        //holder.title.setText(bean.getTitle());
        //holder.avatar.setImageURI(bean.getAuthorAvatar());
        //holder.nickName.setText(bean.getNickname());
        //
        holder.getBinding().setVariable(BR.bean, bean);
        holder.getBinding().executePendingBindings();

    }

    @Override
    public int getItemCount() {
        //return mData == null ? 0 : mData.size();
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (mInterf == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.item_demos_root:
                Bean tag = (Bean) v.getTag();
                mInterf.onItemClick(tag);
                break;
        }
    }

    private ClickCallback mInterf;

    public void setCallback(ClickCallback cb) {
        this.mInterf = cb;
    }

    @Override
    public boolean onLongClick(View v) {
        switch (v.getId()) {
            case R.id.item_demos_root:
                if (mInterf != null) {
                    Bean tag = (Bean) v.getTag();
                    mInterf.onItemLongClick(tag);
                }
                return true;
        }
        return false;
    }

    /**
     * CallBack for click events.
     */
    public interface ClickCallback {
        void onItemClick(Bean bean);

        void onItemLongClick(Bean bean);
    }

}
