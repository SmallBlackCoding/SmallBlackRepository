package com.m520it.blacknews.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author michael
 * @time 2016/6/23  18:26
 * @desc ${TODD}
 */
public class Md5Helper {
    //使用MD5算法对传入的key进行加密并返回
    private static MessageDigest mDigetst=null;
    static {
        try {
            mDigetst=MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    //对key进行MD5加密，如果无MD5加密算法，则直接使用key对应的hash值
    public static String toMD5(String key){
        String cacheKey;
        //获取MD5算法失败时，直接使用key的hash值
        if(mDigetst==null) {
            return String.valueOf(key.hashCode());
        }
        mDigetst.update(key.getBytes());
        cacheKey=bytesToHexString(mDigetst.digest());
        return  cacheKey;
    }

    private static String bytesToHexString(byte[] digest) {
    StringBuilder sb=new StringBuilder();
        for (int i=0;i<digest.length;i++){
            String hex=Integer.toHexString(0xFF&digest[i]);
        if(hex.length()==1) {
            sb.append('0');
        }
            sb.append(hex);
        }
        return sb.toString();
    }
    public static String MD5(String md5){
        try {
            MessageDigest md=MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i=0;i<array.length;i++){
                sb.append(Integer.toHexString(array[i]&0xFF|0x100).substring(1,3));
            }
            return  sb.toString();


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }


    }
}
