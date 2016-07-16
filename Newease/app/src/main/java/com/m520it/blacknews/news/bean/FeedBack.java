package com.m520it.blacknews.news.bean;

/**
 * @author michael
 * @time 2016/6/30  14:11
 * @desc ${TODD}
 */
public class FeedBack {
   //body
    String b;
    //name
    String n;
    //来自
    String f;
    //点赞数
    String v;
    //头像
    String timg;
    //是否是vip
    String vip;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    //每一个对象有一个index
    int index;


    @Override
    public String toString() {
        return "FeedBack{" +
                "b='" + b + '\'' +
                ", n='" + n + '\'' +
                ", f='" + f + '\'' +
                ", v='" + v + '\'' +
                ", timg='" + timg + '\'' +
                ", vip='" + vip + '\'' +
                '}';
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getF() {
        return f;
    }

    public void setF(String f) {
        this.f = f;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getTimg() {
        return timg;
    }

    public void setTimg(String timg) {
        this.timg = timg;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }
}
