package com.apkbus.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkbus.mobile.R;
import com.apkbus.mobile.bean.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyiheng on 16/10/19.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatItemHolder> {
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
    public ChatItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_chat_msg, parent, false);
        return new ChatItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatItemHolder holder, int position) {
        ChatMessage message = mData.get(position);
        holder.avatar.setVisibility(message.getType() == ChatMessage.TYPE.RECEIVE ? View.VISIBLE : View.INVISIBLE);
        holder.text.setText(message.getContent());
        holder.time.setText(simpleDateFormat.format(message.getTime()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ChatItemHolder extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView text;
        TextView time;

        public ChatItemHolder(View itemView) {
            super(itemView);
            avatar = (ImageView) itemView.findViewById(R.id.item_chat_msg_avatar);
            text = (TextView) itemView.findViewById(R.id.item_chat_msg_content);
            time = (TextView) itemView.findViewById(R.id.item_chat_msg_time);
        }
    }
}
