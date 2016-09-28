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
import com.apkbus.mobile.constract.ArticleContract;
import com.apkbus.mobile.R;
import com.apkbus.mobile.adapter.ArticleAdapter;
import com.apkbus.mobile.bean.Bean;
import com.apkbus.mobile.presenter.ArticlePresenter;
import com.apkbus.mobile.utils.LToast;
import com.apkbus.mobile.utils.SwipeRefresh;

import java.util.List;

/**
 * Created by liyiheng on 16/9/19.
 */
public class ArticleFragment extends BaseFragment implements ArticleContract.View, SwipeRefreshLayout.OnRefreshListener, ArticleAdapter.ClickCallback {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    private int SECTION_NUMBER;

    private ArticleContract.Presenter mPresenter;
    private SwipeRefreshLayout mSwipeRefresh;
    private RecyclerView mRecyclerView;
    private ArticleAdapter mAdapter;

    public ArticleFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ArticleFragment newInstance(int sectionNumber) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_article, container, false);
        SECTION_NUMBER = getArguments().getInt(ARG_SECTION_NUMBER);
        mPresenter = new ArticlePresenter(this, SECTION_NUMBER, mSubscriptions);
        mSwipeRefresh = ((SwipeRefreshLayout) layout.findViewById(R.id.fragment_article_refresh));
        SwipeRefresh.initSwipeRefreshLayout(mSwipeRefresh, getContext());
        mSwipeRefresh.setOnRefreshListener(this);
        mRecyclerView = ((RecyclerView) layout.findViewById(R.id.fragment_article_recycler));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter = new ArticleAdapter(getContext());
        mAdapter.setCallback(this);
        mRecyclerView.setAdapter(mAdapter);
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
    public void onItemLongClick(Bean bean) {
        MaterialDialog dialog = new MaterialDialog
                .Builder(getContext())
                .title(bean.getTitle())
                .items("哈哈哈", "呵呵呵", "嘿嘿嘿")
                .itemsCallback((MaterialDialog d, View itemView, int position, CharSequence text)
                        -> LToast.show(getContext(), text))
                .build();
        TextView titleView = dialog.getTitleView();
        titleView.setSingleLine(true);
        titleView.setEllipsize(TextUtils.TruncateAt.END);
        dialog.show();
    }


    @Override
    public void showMsg(CharSequence msg) {
        LToast.show(getContext(), msg);
    }

    @Override
    public void updateData(List<Bean> data) {
        if (mAdapter != null) {
            mAdapter.updateRes(data);
        }
        mSwipeRefresh.setRefreshing(false);
    }

}
