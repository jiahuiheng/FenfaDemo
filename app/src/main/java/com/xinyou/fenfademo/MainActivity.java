package com.xinyou.fenfademo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xinyou.fenfademo.fragment.AboutFragment;
import com.xinyou.fenfademo.fragment.MainFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private  ViewPager idViewpager;
    private ViewPagerAdapter mPagerAdapter;
    private final int PAGE_SIZE = 4;
    private Fragment[] rootFragments = new Fragment[PAGE_SIZE];
    private LinearLayout id_indicator_one, id_indicator_two, id_indicator_three, id_indicator_four;
    private TextView text1,text2,text3,text4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        idViewpager = findViewById(R.id.id_viewpager);
        id_indicator_one = findViewById(R.id.id_indicator_one);
        id_indicator_two = findViewById(R.id.id_indicator_two);
        id_indicator_three = findViewById(R.id.id_indicator_three);
        id_indicator_four = findViewById(R.id.id_indicator_four);
        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        text4 = findViewById(R.id.text4);
        id_indicator_one.setOnClickListener(this);
        id_indicator_two.setOnClickListener(this);
        id_indicator_three.setOnClickListener(this);
        id_indicator_four.setOnClickListener(this);
        initData();
    }
    public void setViewpagerNoSCroll(boolean scroll) {
        if (!scroll) {
            idViewpager.requestDisallowInterceptTouchEvent(true);
        }
    }

    private void initData() {
        if (idViewpager != null) {
            setupViewPager(idViewpager);
        }
    }
    private void setupViewPager(ViewPager viewPager) {
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < PAGE_SIZE; i++) {
            switch (i) {
                case 0:
                    rootFragments[i] = new MainFragment();
                    break;
                case 1:
                    rootFragments[i] = new AboutFragment();
                    break;
                case 2:
                    rootFragments[i] = new AboutFragment();
                    break;
                case 3:
                    rootFragments[i] = new AboutFragment();
                    break;
            }
            Log.e("rootFragments", i + "= " + rootFragments[i]);
            rootFragments[i].setRetainInstance(true);
            mPagerAdapter.addFragment(rootFragments[i], getFragmentTag(i));
        }
        viewPager.setAdapter(mPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOffscreenPageLimit(PAGE_SIZE);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_indicator_one:
                clickTab(0);
                break;
            case R.id.id_indicator_two:
                clickTab(1);
                break;
            case R.id.id_indicator_three:
                clickTab(2);
                break;
            case R.id.id_indicator_four:
                clickTab(3);
                break;
        }
    }
    private void clickTab(int tabType) {
        idViewpager.setCurrentItem(tabType, true);
        changeTab(tabType);
    }
    private void changeTab(int tabType) {
//        Toast.makeText(MainActivity.this, "这是第" + tabType, Toast.LENGTH_SHORT).show();
    }
    private String getFragmentTag(int pos) {
        String tag = "pos_default";
        switch (pos) {
            case 0:
                tag = "功能1";
                break;
            case 1:
                tag = "功能2";
                break;
            case 2:
                tag = "功能3";
                break;
            case 3:
                tag = "功能4";
                break;
        }
        return tag;
    }
    class ViewPagerAdapter  extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }
        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
        }
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

    }
}