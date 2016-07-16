package com.m520it.blacknews.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * @author michael
 * @time 2016/6/23  13:44
 * @desc ${TODD}
 */
public abstract  class HttpResponCallBack<T> {
    Class<T>t;
    public HttpResponCallBack(Class<T>t){
        this.t=t;
    }
    public abstract  void onSuccess(T t);
    public abstract  void onError(String msg);

    public void parse(String json){
        if(TextUtils.isEmpty(json)) {
            onError("网络请求失败TextUtils.isEmpty(json)");
        }
        //如果httpResonpse 是String类型的话

        if(t==String.class) {
           onSuccess((T)json);
            return;
        }
        //tmp_t 就是我们根据网络请求后解析的结果,它的类型是有T 来决定
        T temp_t = JsonUtil.parse(json, t);
        Log.v("it520",json);
        if(temp_t==null) {
            onError("网络请求失败temp_t==null");
        }
        else{
            onSuccess(temp_t);
        }
    }
}
