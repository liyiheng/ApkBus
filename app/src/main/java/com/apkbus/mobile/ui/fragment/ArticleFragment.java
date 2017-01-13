package com.apkbus.mobile.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.apkbus.mobile.R;
import com.apkbus.mobile.adapter.ClickCallback;
import com.apkbus.mobile.adapter.FinalAdapter;
import com.apkbus.mobile.bean.Bean;
import com.apkbus.mobile.constract.ArticleContract;
import com.apkbus.mobile.presenter.ArticlePresenter;
import com.apkbus.mobile.utils.LToast;
import com.apkbus.mobile.utils.SwipeRefresh;

import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by liyiheng on 16/9/19.
 */
public class ArticleFragment extends BaseFragment implements ArticleContract.View, SwipeRefreshLayout.OnRefreshListener, ClickCallback<Bean> {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";
    @ArticlePresenter.ListType
    private int mType;

    private ArticleContract.Presenter mPresenter;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    //private ArticleAdapter mAdapter;
    private FinalAdapter<Bean> adapter;

    public ArticleFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ArticleFragment newInstance(@ArticlePresenter.ListType int type) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_article, container, false);
        //noinspection WrongConstant
        mType = getArguments().getInt(ARG_SECTION_NUMBER);
        mPresenter = new ArticlePresenter(this, mType, mSubscriptions);
        mSwipeRefresh = ((SwipeRefreshLayout) layout.findViewById(R.id.fragment_article_refresh));
        SwipeRefresh.initSwipeRefreshLayout(mSwipeRefresh, getContext());
        mSwipeRefresh.setOnRefreshListener(this);
        mRecyclerView = ((RecyclerView) layout.findViewById(R.id.fragment_article_recycler));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new FinalAdapter<>(R.layout.item_demos);
        adapter.setFooterView(R.layout.item_footer, true);
        adapter.setClickCallback(this);
        mRecyclerView.setAdapter(adapter);

        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSwipeRefresh.setRefreshing(true);
        mPresenter.initData();
    }

    @Override
    public void onRefresh() {
        mPresenter.initData();
    }


    @Override
    public void onItemClick(Bean bean) {
        Intent intent = new Intent(Intent.ACTION_DEFAULT, Uri.parse(bean.getURL() + "&mobile=yes"));
        startActivity(intent);
        // WebActivity.loadURL(getContext(), bean.getUrl() + "&mobile=yes");
    }

    @Override
    public boolean onItemLongClick(Bean bean) {
        MaterialDialog dialog = new MaterialDialog
                .Builder(getContext())
                .title(bean.getTitle())
                .items("分享", "呵呵呵", "嘿嘿嘿")
                .itemsCallback((MaterialDialog d, View itemView, int position, CharSequence text)
                        -> {
                    switch (position) {
                        case 0:
                            OnekeyShare onekeyShare = new OnekeyShare();
                            onekeyShare.disableSSOWhenAuthorize();
                            onekeyShare.setTitle(bean.getTitle());
                            onekeyShare.setTitleUrl(bean.getURL());
                            onekeyShare.setText(bean.getNickname());
                            onekeyShare.setUrl(bean.getURL());
                            onekeyShare.setImageUrl(bean.getAuthorAvatar());
                            onekeyShare.setSiteUrl(bean.getURL());
                            onekeyShare.setSite("ApkBus");
                            onekeyShare.show(getContext());
                            break;
                        default:
                            LToast.show(getContext(), text);
                            break;
                    }
                })
                .build();
        TextView titleView = dialog.getTitleView();
        titleView.setSingleLine(true);
        titleView.setEllipsize(TextUtils.TruncateAt.END);
        dialog.show();
        return true;
    }


    @Override
    public void showMsg(CharSequence msg) {
        LToast.show(getContext(), msg);
    }

    @Override
    public void updateData(List<Bean> data) {
        //mAdapter.updateRes(data);
        adapter.updateRes(data);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void scroll2Top() {
        if (mRecyclerView != null) {
            mRecyclerView.scrollToPosition(0);
        }
    }

}
