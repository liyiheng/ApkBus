package com.apkbus.mobile.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.apkbus.mobile.BasePresenter;
import com.apkbus.mobile.R;
import com.apkbus.mobile.adapter.FinalAdapter;
import com.apkbus.mobile.bean.ChatMessage;
import com.apkbus.mobile.utils.LToast;
import com.turing.androidsdk.HttpRequestListener;
import com.turing.androidsdk.TuringManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity implements HttpRequestListener, View.OnClickListener {

    private final String TURING_KRY = "9c48257c870b46b2b6e83783807c0352";
    private final String TURING_SECRET = "52f8f655ceaf76f0";
    private final String TURING_UNIQUeID = "liyihenggnehiyil@126.com";

    private EditText mEditText;
    private Toolbar toolbar;
    private TuringManager turingManager;
    private FinalAdapter<ChatMessage> mAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
       // toolbar = (Toolbar) findViewById(R.id.activity_chat_toolbar);
        turingManager = new TuringManager(this, TURING_KRY, TURING_SECRET);
        turingManager.setHttpRequestListener(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_chat);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));

        mAdapter = new FinalAdapter<>(R.layout.item_chat_msg);
        mAdapter.setFooterView(0, null);
        recyclerView.setAdapter(mAdapter);

        findViewById(R.id.activity_chat_send).setOnClickListener(this);
        findViewById(R.id.chat_btnback).setOnClickListener(this);
        mEditText = ((EditText) findViewById(R.id.chat_edit_text));
        initHello("小哥哥，约吗");
    }

    private void initHello(String tip){
        ChatMessage message = new ChatMessage(tip, System.currentTimeMillis(), ChatMessage.TYPE.RECEIVE);
        addItem(message);
    }

    @Override
    BasePresenter getPresenter() {
        return null;
    }


    @Override
    public void onSuccess(String result) {
        if (result != null) {
            try {
                JSONObject result_obj = new JSONObject(result);
                String text = result_obj.optString("text");

                ChatMessage message = new ChatMessage(text, System.currentTimeMillis(), ChatMessage.TYPE.RECEIVE);
                addItem(message);
            } catch (JSONException ignore) {
            }
        }
    }

    @Override
    public void onFail(int code, String error) {

    }

    private void addItem(ChatMessage message) {
        List<ChatMessage> data = mAdapter.getData();
        if (data == null) {
            data = new ArrayList<>();
            data.add(message);
            mAdapter.updateRes(data);
        } else {
            data.add(message);
            mAdapter.notifyItemInserted(data.size() - 1);
            recyclerView.scrollToPosition(data.size() - 1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_chat_send:
                if (turingManager == null) {
                    LToast.show(mContext, "Schnappi还没准备好");
                    break;
                }
                String text = mEditText.getText().toString().trim();
                if (text.length() == 0) {
                    LToast.show(mContext, "请输入内容");
                    break;
                }
//                JSONObject jsonObject = new JSONObject();
//                try {
//                    jsonObject.put("key","9c48257c870b46b2b6e83783807c0352");
//                    jsonObject.put("info",text);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                turingManager.requestTuring(text);
                ChatMessage message = new ChatMessage(text, System.currentTimeMillis(), ChatMessage.TYPE.SEND);
                addItem(message);
                mEditText.setText("");
                break;

            case R.id.chat_btnback:
                finish();
                break;
        }
    }
}
