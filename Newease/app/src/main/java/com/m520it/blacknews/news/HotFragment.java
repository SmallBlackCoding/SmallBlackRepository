package com.m520it.blacknews.news;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.m520it.blacknews.R;
import com.m520it.blacknews.news.adapter.BannerAdapter;
import com.m520it.blacknews.news.adapter.HotAdapter;
import com.m520it.blacknews.news.bean.BannerData;
import com.m520it.blacknews.news.bean.Hot;
import com.m520it.blacknews.news.bean.NewsDetail;
import com.m520it.blacknews.utils.Contance;
import com.m520it.blacknews.utils.HttpResponCallBack;
import com.m520it.blacknews.utils.HttpUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author michael
 * @time 2016/6/26  18:32
 * @desc ${TODD}
 */
public class HotFragment extends Fragment {
    //网络数据请求成功
    static final int INTERNET_LIST_SUCCESS = 1;
    static final int LOAD_MORE_SUCCESS = 2;
    HotAdapter mAdapter;
    InnerHandler mHandler;
    ListView mListView;
    List<NewsDetail> all;
    List<BannerData> banners;
    int index;//当前页数的起点
    int pagesize;
    //判断是否到底了 默认是false 在没有触摸滑动之前
    boolean isToEnd = false;
    TextView banner_Title;
    //建立一个存放小点的集合
    ArrayList<ImageView> dots_list;
   // LinearLayout ll_loading;
    RelativeLayout loading_pic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, null);
        mListView = (ListView) view.findViewById(R.id.hot_listview);
        loading_pic = (RelativeLayout) view.findViewById(R.id.loading_pic);
       // ll_loading = (LinearLayout) view.findViewById(R.id.ll_loading);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //mAdapter=new HotAdapter(getActivity());
        mHandler = new InnerHandler(this);
        //一个存放Feedbacks的集合，一层一层包裹上去

        // mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //Log.v("it520", "--onScrollStateChanged---" + scrollState);
                if (scrollState == SCROLL_STATE_IDLE && isToEnd) {

                    //滑动结束，判断是否为最后一个item
                    //如果是的话重新获取一次数据
                    //滑动到底部的时候意味着加载第二页，这个时候不需要删除图集信息
                    getData(false);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                //最后一个可见的位置刚好等于data的size-1；
               // Log.v("it520", "last--->" + view.getLastVisiblePosition());
                if (mAdapter != null && view.getLastVisiblePosition() == mAdapter.getLast()) {
                    isToEnd = true;
                } else {
                    isToEnd = false;
                }
            }
        });
        //ListView的setEmptyView当没有数据的时候就会显示指定的view
        mListView.setEmptyView(loading_pic);

        //为ListView的子item设置点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int p, long id) {
                int header_size = mListView.getHeaderViewsCount();
                //因为加了头部，要减去头部的数量
            int    position=p - header_size;
                NewsDetail detail = (NewsDetail)mAdapter.getItem(position);
                Log.i("it520", "position=" + position);
                
                //把参数传递给要跳转到的activity
                Intent intent=new Intent();
                intent.setClass(getActivity(),DetailActivity.class);
                intent.putExtra(DetailActivity.DOC_ID,detail.getDocid());
                startActivity(intent);
            }
        });
//第一次获得数据需要删除第一条图集信息
       // ll_loading.setVisibility(View.VISIBLE);
        getData(true);
        //把进度条显示出来

    }

    //初始化数据
    public void initData(List<NewsDetail> details) {
        //初始化mAdaper
        mAdapter = new HotAdapter(details, getActivity());
        mListView.setAdapter(mAdapter);
    }

    //初始化HeaderView，并把HeaderView添加到mListview中去
    public void setBannerData() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View headerView = inflater.inflate(R.layout.layout_header, null);
        //图集布局
        ViewPager head_viewPager = (ViewPager) headerView.findViewById(R.id.head_viewpager);
        //小圆点布局
        LinearLayout dots_view = (LinearLayout) headerView.findViewById(R.id.dots);
        //图集的文字的布局
        banner_Title = (TextView) headerView.findViewById(R.id.banner_title);
        //建立一个集合存放空的imageview
        final ArrayList<ImageView> images = new ArrayList<>();
        dots_list=new ArrayList<>();
        //先为ViewPager建立一个Adapter
        for (int i = 0; i < banners.size(); i++) {
            ImageView image = new ImageView(getActivity());
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            images.add(image);

            ImageView dot = new ImageView(getActivity());
            dot.setImageResource(R.drawable.gray_dot);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 0, 0);
            dots_list.add(dot);
            dots_view.addView(dot, params);
        }
        //默认第一个点为白色
        dots_list.get(0).setImageResource(R.drawable.white_dot);
        banner_Title.setText(banners.get(0).getTitle());
        BannerAdapter bannerAdapter = new BannerAdapter(banners, images);
        head_viewPager.setAdapter(bannerAdapter);
        //设置当前位置
        int middle = Integer.MAX_VALUE / 2;
        head_viewPager.setCurrentItem(middle- middle % banners.size());
        //当页面变化的时候响应的小圆点也要跟着变化
        head_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int p) {
                int position = p % images.size();
                banner_Title.setText(banners.get(position).getTitle());
                changeDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//把初始化完成的HeaderView加到Listview的第一个位置
        mListView.addHeaderView(headerView);
    }

    //改变小白点
    public void changeDots(int position) {
        for (int i = 0; i < dots_list.size(); i++) {
            ImageView dot = dots_list.get(i);
            if (i == position) {
                dot.setImageResource(R.drawable.white_dot);
            } else {
                dot.setImageResource(R.drawable.gray_dot);
            }
        }
    }


    //更新数据
    public void updateData(List<NewsDetail> details) {
        //进入后开始拉出第二页的时候
        mAdapter.add(details);
    }

    public void getData(final boolean isInit) {

        //动态地去获取每一次刷新分页的数据
        String url = Contance.getIndexUrl(index, pagesize);
        HttpUtils util = HttpUtils.getInstance();
        util.doGet(url, new HttpResponCallBack<Hot>(Hot.class) {
            @Override
            public void onSuccess(Hot hotInfo) {

                if (hotInfo == null || hotInfo.getT1348647909107() == null) {
                    //判断是否取到值，没有的话直接返回
                    return;
                }
                //获取数据成功就把数据通过handler传给UI线程更新数据
                //取出第一个图集数据再把集合的第一位删除
                //这里需要做一个判断，只有在第一次初始化进来的时候
                //先初始化一个message
                Message message;
                if (isInit) {
                    all = hotInfo.getT1348647909107();
                    //拿到图集数据了
                    banners = all.get(0).getAds();
                    //接着把all的第一位移除
                    all.remove(0);
                    //第一次进来需要加headerview的时候
                    message = mHandler.obtainMessage(INTERNET_LIST_SUCCESS);
                } else {
                    //加载更多的时候
                    message = mHandler.obtainMessage(LOAD_MORE_SUCCESS);
                    all = hotInfo.getT1348647909107();
                }
                //才需要把hotInfo.getT1348647909107()

                //把两种不同的信息发回去handler中分别处理
                message.obj = all;
                mHandler.sendMessage(message);
                index++;
            }

            @Override
            public void onError(String msg) {
                Log.v("it520", "获取数据失败");
            }
        });
    }

    static class InnerHandler extends Handler {
        WeakReference<HotFragment> frg;

        public InnerHandler(HotFragment tmp) {
            //new 一个HotFragment的对象
            this.frg = new WeakReference<HotFragment>(tmp);
        }

        @Override
        public void handleMessage(Message msg) {
            HotFragment hotFragment = frg.get();
            if (hotFragment == null) {
                //说明该弱引用被虚拟机回收了
                return;
            }
            List<NewsDetail> all;
            switch (msg.what) {
                case INTERNET_LIST_SUCCESS:

                   // hotFragment.ll_loading.setVisibility(View.GONE);
                    all = (List<NewsDetail>) msg.obj;
                    //设置完Listview的头后
                    hotFragment.setBannerData();
                    //在给ListView设置Adapter
                    hotFragment.initData(all);

                    break;
                case LOAD_MORE_SUCCESS:
                    all = (List<NewsDetail>) msg.obj;
                    hotFragment.updateData(all);
                    break;
            }
        }
    }

}
