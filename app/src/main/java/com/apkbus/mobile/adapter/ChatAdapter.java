package com.apkbus.mobile.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.apkbus.mobile.BR;
import com.apkbus.mobile.R;
import com.apkbus.mobile.bean.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyiheng on 16/10/19.
 */

public class ChatAdapter extends RecyclerView.Adapter<BindHolder> {
    private List<ChatMessage> mData;
    private LayoutInflater mInflater;
    private SimpleDateFormat simpleDateFormat;


    public ChatAdapter(Context context) {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = new ArrayList<>();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public void addItem(ChatMessage message) {
        mData.add(message);
        notifyItemInserted(mData.size() - 1);
    }

    @Override
    public BindHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(mInflater, R.layout.item_chat_msg, parent, false);
        return new BindHolder(binding.getRoot(), binding);
        //View view = mInflater.inflate(R.layout.item_chat_msg, parent, false);
        //return new ChatItemHolder(view);
    }

    @Override
    public void onBindViewHolder(BindHolder holder, int position) {
        ChatMessage message = mData.get(position);
        //holder.avatar.setVisibility(message.getType() == ChatMessage.TYPE.RECEIVE ? View.VISIBLE : View.INVISIBLE);
        //holder.text.setText(message.getContent());
        //holder.time.setText(simpleDateFormat.format(message.getTime()));
        holder.getBinding().setVariable(BR.msg, message);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
