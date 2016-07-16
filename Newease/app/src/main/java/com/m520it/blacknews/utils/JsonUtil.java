package com.m520it.blacknews.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

/**
 * @author michael
 * @time 2016/6/23  13:34
 * @desc ${TODD}
 */
public class JsonUtil {
    public static <T> T parse(String json,Class<T>tClass) {
        try {
            if(TextUtils.isEmpty(json)) {
                return null;
            }
            Gson gson=new Gson();
            //json解析转换后的类
            T t = gson.fromJson(json, tClass);
            Log.v("it520","进入jsonUtil的parse方法");
            return t;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
