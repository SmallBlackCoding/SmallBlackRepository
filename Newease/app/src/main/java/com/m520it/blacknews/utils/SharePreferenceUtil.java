package com.m520it.blacknews.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author michael
 * @time 2016/6/23  19:32
 * @desc ${TODD}
 */
public class SharePreferenceUtil {
    public static final String FILENAME="newsave";
    //string类型的缓存
    public  static void putString(Context context,String title,String content){
        SharedPreferences sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(title,content);
        edit.commit();
    }
    public static String getString(Context context,String title){
        SharedPreferences sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sp.getString(title,"");
    }

    //int类型的缓存
    public static void putInt(Context context,String title,int  content){
        SharedPreferences sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(title,content);
        edit.commit();
    }
    public static int getInt(Context context,String title){
        SharedPreferences sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sp.getInt(title,0);
    }
    //long类型的缓存
    public static void putLong(Context context,String title,long content){
        SharedPreferences sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(title,content);
        edit.commit();
    }
    public static long getLong(Context context,String title){
        SharedPreferences sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sp.getLong(title,0L);
    }
}
