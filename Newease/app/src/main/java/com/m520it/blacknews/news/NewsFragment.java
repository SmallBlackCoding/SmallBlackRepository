package com.m520it.blacknews.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.m520it.blacknews.R;
import com.m520it.blacknews.Widget.SmartTabLayout;
import com.m520it.blacknews.adapter.DemoAdapter;

import java.util.ArrayList;

/**
 * @author michael
 * @time 2016/6/25  19:34
 * @desc ${TODD}
 */
public class NewsFragment extends Fragment {
    private ViewPager viewPager;
    private DemoAdapter mAdapter;
    private SmartTabLayout smartTabLayout;
    private View mFragmentView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       mFragmentView= inflater.inflate(R.layout.fragment_news,null);
        return mFragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewGroup tab = (ViewGroup) mFragmentView.findViewById(R.id.tab_index);
        tab.addView(LayoutInflater.from(getContext()).inflate(R.layout.demo_custom_tab_text, tab, false));
        viewPager = (ViewPager) mFragmentView.findViewById(R.id.viewpager_index);
        smartTabLayout = (SmartTabLayout) mFragmentView.findViewById(R.id.viewpagertab);

        String[] titles = getResources().getStringArray(R.array.news_titles);
        //在newsFragment里面嵌入HotFragment
        ArrayList<Fragment> fmlist = new ArrayList<>();
        for (int i=0;i<titles.length;i++ ){
            if(i==0) {
                fmlist.add(new HotFragment());
            }
            else{
                fmlist.add(new EmptyFragment());
            }
        }

        mAdapter = new DemoAdapter(getFragmentManager(), fmlist, titles);
        viewPager.setAdapter(mAdapter);
        smartTabLayout.setDividerColors(Color.TRANSPARENT);
        smartTabLayout.setViewPager(viewPager);
    }

}
