package com.m520it.blacknews.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.m520it.blacknews.R;
import com.m520it.blacknews.mine.MineFragment;
import com.m520it.blacknews.news.NewsFragment;
import com.m520it.blacknews.news.modify_support_v4.FragmentTabHost;

/**
 * @author michael
 * @time 2016/6/25  11:00
 * @desc ${TODD}
 */
public class IndexActivity extends FragmentActivity {
    private FrameLayout content;
    private FragmentTabHost tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_activity);
        content = (FrameLayout) findViewById(R.id.content);
        tab = (FragmentTabHost) findViewById(R.id.tab);
        //用setup初始化  容器id
        tab.setup(this, getSupportFragmentManager(), R.id.content);
        int imageResource[] = {R.drawable.news_selector, R.drawable.read_selector, R.drawable.video_selector, R.drawable.topic_selector, R.drawable.mine_selector};
        String[] titles = new String[]{"新闻", "阅读", "视频", "话题", "我"};
        for (int i = 0; i < titles.length; i++) {
            View view = getView(imageResource[i], titles[i]);
            if (i == 4) {
                tab.addTab(tab.newTabSpec(String.valueOf(i)).setIndicator(view), MineFragment.class, null);
            } else {
                tab.addTab(tab.newTabSpec(String.valueOf(i)).setIndicator(view), NewsFragment.class, null);
            }


        }

    }

    public View getView(int image_src, String title_content) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.item_title, null);
        ImageView image = (ImageView) view.findViewById(R.id.item_icon);
        TextView text = (TextView) view.findViewById(R.id.item_text);
        image.setImageResource(image_src);
        text.setText(title_content);
        return view;
    }
}
