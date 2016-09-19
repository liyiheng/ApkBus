package com.apkbus.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apkbus.mobile.R;
import com.apkbus.mobile.bean.FirstBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyiheng on 16/9/19.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder> {
    private final LayoutInflater mInflater;
    private List<FirstBean> mData;

    public ArticleAdapter(Context context) {
        mInflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        mData = new ArrayList<>();
    }

    /**
     * Update the data resource.
     *
     * @param res New data.
     */
    public void updateRes(List<FirstBean> res) {
        if (res != null) {
            this.mData = res;
            notifyDataSetChanged();
        }
    }

    public void addRes(List<FirstBean> res) {
        if (res != null) {
            this.mData.addAll(res);
            notifyDataSetChanged();
        }
    }

    @Override
    public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_demos, parent, false);
        return new ArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleHolder holder, int position) {
        FirstBean bean = mData.get(position);
        holder.title.setText(bean.getFulltitle());
        //holder.draweeView.setImageURI(bean.getAvatar_big());
    }

    @Override
    public int getItemCount() {
        //return mData == null ? 0 : mData.size();
        return mData.size();
    }

    class ArticleHolder extends RecyclerView.ViewHolder {
        //SimpleDraweeView draweeView;
        TextView title;

        public ArticleHolder(View itemView) {
            super(itemView);
            //draweeView = (SimpleDraweeView) itemView.findViewById(R.id.item_demos_img);
            title = (TextView) itemView.findViewById(R.id.item_demos_title);
        }
    }
}
