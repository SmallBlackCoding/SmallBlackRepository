package com.m520it.blacknews.news.bean;

/**
 * @author michael
 * @time 2016/6/26  19:24
 * @desc ${TODD}
 */
public class BannerData {
    //第一行的图片很数据
    String subtitle;
    String imgsrc;
    String title;
    String tag;

    @Override
    public String toString() {
        return "BannerData{" +
                "subtitle='" + subtitle + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", title='" + title + '\'' +
                ", tag='" + tag + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    String url;

}
