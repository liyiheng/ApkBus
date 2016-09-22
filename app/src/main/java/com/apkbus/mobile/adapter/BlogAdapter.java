package com.apkbus.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apkbus.mobile.R;
import com.apkbus.mobile.bean.Blog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyiheng on 16/9/22.
 */

public class BlogAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> implements View.OnClickListener {
    private final LayoutInflater mInflater;
    private List<Blog> mData;

    public BlogAdapter(Context context) {
        mInflater = ((LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE));
        this.mData = new ArrayList<>();
    }

    /**
     * Update the data resource.
     *
     * @param res New data.
     */
    public void updateRes(List<Blog> res) {
        if (res != null) {
            this.mData = res;
            notifyDataSetChanged();
        }
    }

    public void addRes(List<Blog> res) {
        if (res != null) {
            this.mData.addAll(res);
            notifyDataSetChanged();
        }
    }


    @Override
    public ArticleAdapter.ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_demos, parent, false);
        return new ArticleAdapter.ArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ArticleHolder holder, int position) {
        Blog bean = mData.get(position);
        holder.title.setText(bean.getFulltitle());
        holder.avatar.setImageURI(bean.getAvatar_middle());
        holder.nickName.setText(bean.getUsername());
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(bean);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public void onClick(View v) {
        if (mInterf == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.item_demos_root:
                Blog tag = (Blog) v.getTag();
                mInterf.onItemClick(tag);
                break;
        }
    }

    private ClickCallback mInterf;

    public void setCallback(ClickCallback cb) {
        this.mInterf = cb;
    }

    public interface ClickCallback {
        void onItemClick(Blog bean);
    }
}
