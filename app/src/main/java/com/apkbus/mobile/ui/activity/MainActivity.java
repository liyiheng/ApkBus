package com.apkbus.mobile.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.apkbus.mobile.R;
import com.apkbus.mobile.bean.LoginInfo;
import com.apkbus.mobile.bean.User;
import com.apkbus.mobile.bean.UserProfile;
import com.apkbus.mobile.constract.MainContract;
import com.apkbus.mobile.presenter.MainPresenter;
import com.apkbus.mobile.ui.fragment.ArticleFragment;
import com.apkbus.mobile.utils.LToast;
import com.apkbus.mobile.utils.SharedPreferencesHelper;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class MainActivity extends BaseActivity<MainContract.Presenter> implements MainContract.View, View.OnClickListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TextView mTextUsername;
    private ImageView mAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mPresenter.initData();
    }

    @Override
    MainPresenter getPresenter() {
        return new MainPresenter(this);
    }


    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        SwitchCompat item = (SwitchCompat) navigationView.getMenu().getItem(0).getActionView();
        item.setChecked(SharedPreferencesHelper.getInstance(this).needAutoRenew());
        item.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) ->
                SharedPreferencesHelper.getInstance(mContext).setAutoRenew(isChecked)
        );
        navigationView.setNavigationItemSelectedListener((MenuItem menuItem) -> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_item_autorenew:
                    //menuItem.getActionView().
                    break;
                case R.id.navigation_item_logout:
                    SharedPreferencesHelper.getInstance(mContext).saveToken(new LoginInfo());
                    startActivity(new Intent(mContext, LoginActivity.class));
                    finish();
                    break;
                case R.id.navigation_item_share:
                    // new OneKeyShare();
                    OnekeyShare share = new OnekeyShare();
                    share.disableSSOWhenAuthorize();
                    share.setTitle("ApkBus");
                    share.setTitleUrl("http://fir.im/ve7m");
                    share.setText("ApkBus_哈哈哈哦啦啦");
                    share.setUrl("http://fir.im/ve7m");
                    share.setImageUrl("http://www.apkbus.com/static/image/common/logo.png");
                    share.setSiteUrl("http://fir.im/ve7m");
                    share.setSite("ApkBus");
                    share.show(this);
                    drawerLayout.closeDrawers();
                    break;
            }
            //menuItem.setChecked(!menuItem.isChecked());
            //drawerLayout.closeDrawers();
            return true;
        });
        mTextUsername = ((TextView) navigationView.getHeaderView(0).findViewById(R.id.navigation_header_username));
        mAvatar = ((ImageView) navigationView.getHeaderView(0).findViewById(R.id.navigation_header_avatar));
        mTextUsername.setOnClickListener(view -> {
            drawerLayout.closeDrawers();
            new MaterialDialog.Builder(mContext)
                    .input("昵称", "", (@NonNull MaterialDialog dialog, CharSequence input) -> {
                        if (input.length() == 0) {
                            LToast.show(mContext, "请输入昵称");
                            return;
                        }
                        mPresenter.setUserProfile(UserProfile.NICKNAME, input.toString());
                        dialog.dismiss();
                        loadingDialog.show();
                    }).build().show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void showMsg(CharSequence msg) {
        LToast.show(this, msg);
    }

    @Override
    public void bindData(User data) {
        loadingDialog.dismiss();
        if (data == null || TextUtils.isEmpty(data.getNickname())) return;
        mTextUsername.setText(data.getNickname());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Snackbar.make(v, "Developer? Help us expand this app", Snackbar.LENGTH_LONG)
                        .setAction("Join us", (View view) -> {
                            Intent intent = new Intent(Intent.ACTION_DEFAULT, Uri.parse("https://github.com/XanthusL/ApkBus"));
                            startActivity(intent);
                        }).show();
                break;
        }
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ArticleFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "热门博文";
                case 1:
                    return "最新博文";
                case 2:
                    return "精品源码";
                case 3:
                    return "一周热点";
                case 4:
                    return "实例教程";
            }
            return null;
        }
    }
}
