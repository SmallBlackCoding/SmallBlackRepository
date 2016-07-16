package com.m520it.blacknews.news.bean;

/**
 * @author michael
 * @time 2016/6/28  21:11
 * @desc ${TODD}
 */
public class Image {
    @Override
    public String toString() {
        return "Image{" +
                "src='" + src + '\'' +
                '}';
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    String src;
}
