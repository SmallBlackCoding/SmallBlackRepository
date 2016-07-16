package com.m520it.blacknews.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author michael
 * @time 2016/6/23  13:10
 * @desc ${TODD}
 */
public class HttpUtils {
    private static HttpUtils httpUtils;
    private OkHttpClient client;

    //使用单例模式
    //私有化构造器
    private HttpUtils() {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).build();
    }

    //生成http请求的单例模式
    public static HttpUtils getInstance() {
        if (httpUtils == null) {
            synchronized (HttpUtils.class) {
                if (httpUtils == null) {
                    httpUtils = new HttpUtils();
                }
            }
        }
        return httpUtils;
    }


    public void doGet(String url, final HttpResponCallBack respon) {
      //如果使用okHttp->需要导入okio
        Request request = new Request.Builder().url(url).build();
      client.newCall(request).enqueue(new Callback() {
         //请求失败->超时,网络异常
          @Override
          public void onFailure(Call call, IOException e) {
              //使用回调函数
              respon.onError("网络连接错误doGet/onFailure");
          }
         //请求成功200
          @Override
          public void onResponse(Call call, Response response) throws IOException {
             //失败
              if(!response.isSuccessful()) {
                  //响应异常
                  respon.onError("onResponse网络连接错误!response.isSuccessful()");

                  //使用回调函数
              }
              //请求体 string-对象
              String content = response.body().string();
              respon.parse(content);

          }
      });
    }

}
