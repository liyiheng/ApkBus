package com.apkbus.mobile.ui.activity;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.apkbus.mobile.BasePresenter;
import com.apkbus.mobile.R;
import com.apkbus.mobile.adapter.ChatAdapter;
import com.apkbus.mobile.bean.ChatMessage;
import com.apkbus.mobile.utils.LToast;
import com.turing.androidsdk.InitListener;
import com.turing.androidsdk.SDKInit;
import com.turing.androidsdk.SDKInitBuilder;
import com.turing.androidsdk.TuringApiManager;

import org.json.JSONException;
import org.json.JSONObject;

import turing.os.http.core.ErrorMessage;
import turing.os.http.core.HttpConnectionListener;
import turing.os.http.core.RequestResult;

public class ChatActivity extends BaseActivity implements InitListener, HttpConnectionListener, View.OnClickListener {

    private EditText mEditText;
    private TuringApiManager turingApiManager;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        SDKInit.init(new SDKInitBuilder(getApplicationContext())
                .setSecret("52f8f655ceaf76f0")
                .setUniqueId("liyihenggnehiyil@126.com")
                .setTuringKey("9c48257c870b46b2b6e83783807c0352"), this);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview_chat);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        chatAdapter = new ChatAdapter(mContext);
        recyclerView.setAdapter(chatAdapter);
        findViewById(R.id.activity_chat_send).setOnClickListener(this);
        mEditText = ((EditText) findViewById(R.id.chat_edit_text));
    }

    @Override
    BasePresenter getPresenter() {
        return null;
    }

    @Override
    public void onComplete() {
        turingApiManager = new TuringApiManager(this);
        turingApiManager.setHttpListener(this);
    }

    @Override
    public void onFail(String s) {
        LToast.show(mContext, s);

    }

    @Override
    public void onError(ErrorMessage errorMessage) {

    }

    @Override
    public void onSuccess(RequestResult requestResult) {
        if (requestResult != null) {
            try {
                JSONObject result_obj = new JSONObject(requestResult.getContent().toString());
                String text = result_obj.optString("text");

                ChatMessage message = new ChatMessage(text, System.currentTimeMillis(), ChatMessage.TYPE.RECEIVE);
                chatAdapter.addItem(message);

            } catch (JSONException e) {
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_chat_send:
                if (turingApiManager == null) {
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
                turingApiManager.requestTuringAPI(text);
                ChatMessage message = new ChatMessage(text, System.currentTimeMillis(), ChatMessage.TYPE.SEND);
                chatAdapter.addItem(message);
                mEditText.setText("");
                break;

        }
    }
}
