package com.m520it.blacknews.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.m520it.blacknews.R;
import com.m520it.blacknews.Widget.SmartTabLayout;
import com.m520it.blacknews.adapter.DemoAdapter;
import com.m520it.blacknews.demofragment.Fivefragment;
import com.m520it.blacknews.demofragment.Fourfragment;
import com.m520it.blacknews.demofragment.Onefragment;
import com.m520it.blacknews.demofragment.Sixfragment;
import com.m520it.blacknews.demofragment.Threefragment;
import com.m520it.blacknews.demofragment.Twofragment;

import java.util.ArrayList;

/**
 * @author michael
 * @time 2016/6/25  17:04
 * @desc ${TODD}
 */
public class DemoActivity extends FragmentActivity {
    private ViewPager viewPager;
    private DemoAdapter mAdapter;
    private SmartTabLayout smartTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        ViewGroup tab = (ViewGroup) findViewById(R.id.tab_demo);
        tab.addView(LayoutInflater.from(this).inflate(R.layout.demo_custom_tab_text, tab, false));
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        smartTabLayout = (SmartTabLayout) findViewById(R.id.viewpagertab);
        Fivefragment five = new Fivefragment();
        Sixfragment six = new Sixfragment();
        Fourfragment four = new Fourfragment();
        Threefragment three = new Threefragment();
        Twofragment two = new Twofragment();
        Onefragment one = new Onefragment();

        String[] titles = new String[]{"娱乐", "新闻", "体育", "阅读", "八卦", "电影"};
        ArrayList<Fragment> fmlist = new ArrayList<>();
        fmlist.add(one);
        fmlist.add(two);
        fmlist.add(three);
        fmlist.add(four);
        fmlist.add(five);
        fmlist.add(six);
        mAdapter = new DemoAdapter(getSupportFragmentManager(), fmlist, titles);
        viewPager.setAdapter(mAdapter);
        smartTabLayout.setViewPager(viewPager);


    }
}
