package com.m520it.blacknews;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * @author michael
 * @time 2016/6/27  23:52
 * @desc ${TODD}
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //创建ImageLoader的配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration.createDefault(this);
        //初始化配置参数
        ImageLoader.getInstance().init(configuration);
    }
}
