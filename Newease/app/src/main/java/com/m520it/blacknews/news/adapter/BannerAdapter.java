package com.m520it.blacknews.news.adapter;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.m520it.blacknews.news.bean.BannerData;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author michael
 * @time 2016/6/28  4:58
 * @desc ${TODD}
 */
public class BannerAdapter extends PagerAdapter {
    //加入数据 图片集的图片跟文字
    private List<BannerData> banners;
    private ArrayList<ImageView >images;
    //由于是从网络上加载图片的，所以要用到Universal-Image_Loader
    DisplayImageOptions options;
    public BannerAdapter(List<BannerData> banners,ArrayList<ImageView >images){
        this.banners=banners;
        this.images=images;
        options=new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int p) {
        int position=p%images.size();
        ImageView image = images.get(position);
        BannerData temp = banners.get(position);
        ImageLoader.getInstance().displayImage(temp.getImgsrc(),image,options);
        //把加载完的image放进容器里面
        container.addView(image);
        return image;
    }

    @Override
    public void destroyItem(ViewGroup container, int p, Object object) {
       int position=p%images.size();
        //当子item 销毁的时候，从容器中移除子item
        container.removeView(images.get(position));
    }
}
