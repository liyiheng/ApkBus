package com.apkbus.mobile;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.apkbus.mobile.apis.LSubscriber;
import com.apkbus.mobile.apis.RxAPI;
import com.apkbus.mobile.bean.BeanWrapper;
import com.apkbus.mobile.bean.FirstBean;
import com.apkbus.mobile.utils.LToast;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class Main2Activity extends BaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        initView();
    }

    private void initView() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener((View view) -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show());
//        TextView tv = (TextView) findViewById(R.id.textView);
//        tv.setOnClickListener((View v) -> {
//                    Subscription s = Observable.timer(2, TimeUnit.SECONDS)
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe((Long aLong) -> LToast.show(mContext, "Test"));
//                    compositeSubscription.add(s);
//                }
//        );
//
//        Observable<BeanWrapper<FirstBean>> tttest = RxAPI.getInstance().getDemos();
//        LSubscriber<BeanWrapper<FirstBean>> lSubscriber = new LSubscriber<BeanWrapper<FirstBean>>() {
//            @Override
//            public void onNext(BeanWrapper<FirstBean> firstBeanBeanWrapper) {
//                StringBuilder sb = new StringBuilder();
//                List<FirstBean> res = firstBeanBeanWrapper.getRes();
//                for (FirstBean b : res) {
//                    sb.append(b.getFulltitle()).append("\n");
//                }
//                tv.setText(sb);
//            }
//
//            @Override
//            protected void onError(int httpStatusCode, int code) {
//
//            }
//        };
//        tttest.subscribe(lSubscriber);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return ArticleFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
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
