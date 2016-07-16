package com.m520it.blacknews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * @author michael
 * @time 2016/6/25  17:21
 * @desc ${TODD}
 */
public class DemoAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment>fragments;
    String titles[];
    public DemoAdapter(FragmentManager fm,ArrayList<Fragment>fragments, String titles[]) {
        super(fm);
        this.fragments=fragments;
        this.titles=titles;
    }

    @Override
    //返回每个页面需要显示的item
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    //根据每个页面返回一个字符串 我们的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
