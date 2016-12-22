package com.apkbus.mobile.ui.fragment;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.apkbus.mobile.R;
import com.apkbus.mobile.bean.event.ScrollSignal;
import com.apkbus.mobile.utils.LToast;
import com.apkbus.mobile.utils.RxBus;
import com.github.piasy.rxandroidaudio.PlayConfig;
import com.github.piasy.rxandroidaudio.RxAudioPlayer;
import com.luolc.emojirain.EmojiRainLayout;

import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.sharesdk.onekeyshare.OnekeyShare;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChristmasFragment extends BaseFragment implements View.OnClickListener {


    private RxAudioPlayer mRxAudioPlayer;
    private ImageView mImageView;
    private EditText mEditText;

    public ChristmasFragment() {
        // Required empty public constructor
    }

    private void rain() {
        mContainer.startDropping();
        Observable
                .timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    mContainer.stopDropping();
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()) {
            rain();
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mRxAudioPlayer.stopPlay();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRxAudioPlayer = RxAudioPlayer.getInstance();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_christmas, container, false);
    }

    private EmojiRainLayout mContainer;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // bind view
        mContainer = (EmojiRainLayout) view.findViewById(R.id.group_emoji_container);

        // add emoji sources
        mContainer.addEmoji(R.drawable.emoji_1_3);
        mContainer.addEmoji(R.drawable.emoji_2_3);
        mContainer.addEmoji(R.drawable.emoji_3_3);
        mContainer.addEmoji(R.drawable.emoji_4_3);
        mContainer.addEmoji(R.drawable.emoji_5_3);

        mImageView = ((ImageView) view.findViewById(R.id.christmas_img));
        mEditText = ((EditText) view.findViewById(R.id.christmas_edit));

        view.findViewById(R.id.christmas_music).setOnClickListener(this);
        view.findViewById(R.id.christmas_fly).setOnClickListener(this);
        mImageView.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Subscription subscription = RxBus.getInstance().toSubscription(ScrollSignal.class, scrollSignal -> {
            if (scrollSignal.tabPosition == 0) {
                rain();
            }
        });
        mSubscriptions.add(subscription);
    }

    private String mImagePath;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.christmas_img:
                GalleryFinal.openGallerySingle(1234, new GalleryFinal.OnHanlderResultCallback() {
                    @Override
                    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
                        if (resultList == null || resultList.size() == 0) return;
                        mImagePath = resultList.get(0).getPhotoPath();
                        Bitmap bitmap = BitmapFactory.decodeFile(mImagePath);
                        mImageView.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onHanlderFailure(int requestCode, String errorMsg) {
                        LToast.show(getContext(), errorMsg);
                        mImagePath = null;
                    }
                });
                break;
            case R.id.christmas_fly:
                String text = mEditText.getText().toString().trim();
                OnekeyShare onekeyShare = new OnekeyShare();
                onekeyShare.disableSSOWhenAuthorize();
                onekeyShare.setTitle(text);
                onekeyShare.setTitleUrl("https://github.com/XanthusL");
                onekeyShare.setText(text);
                onekeyShare.setUrl("https://github.com/XanthusL");
                if (!TextUtils.isEmpty(mImagePath)) {
                    onekeyShare.setImagePath(mImagePath);
                }
                onekeyShare.setSiteUrl("https://github.com/XanthusL");
                onekeyShare.setSite("ApkBus");
                onekeyShare.show(getContext());
                break;
            case R.id.christmas_music:
                musicCtrl();
                break;
        }
    }

    private static final int STATE_IDLE = 0;
    private static final int STATE_PLAYING = 1;
    private static final int STATE_PAUSED = 2;


    @IntDef(value = {STATE_IDLE, STATE_PAUSED, STATE_PLAYING})
    private @interface Status {
    }

    @Status
    private int mState;


    private void musicCtrl() {
        rain();
        MediaPlayer mediaPlayer = mRxAudioPlayer.getMediaPlayer();
        switch (mState) {
            case STATE_IDLE:
                mState = STATE_PLAYING;
                mRxAudioPlayer.play(PlayConfig.res(getContext(), R.raw.bila).looping(true).build())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                                aBoolean -> mState = STATE_IDLE,
                                throwable -> mState = STATE_IDLE);
                break;
            case STATE_PAUSED:
                if (mediaPlayer != null) {
                    mState = STATE_PLAYING;
                    mediaPlayer.start();
                }
                break;
            case STATE_PLAYING:
                if (mediaPlayer != null) {
                    mState = STATE_PAUSED;
                    mediaPlayer.pause();
                }

                break;
        }
    }

}
