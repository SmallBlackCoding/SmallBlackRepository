package com.m520it.blacknews.news.bean;

import java.util.List;

/**
 * @author michael
 * @time 2016/6/26  19:10
 * @desc ${TODD}
 */
public class NewsDetail {
    List <BannerData>ads;
    String imgsrc;
    String title;
    String specialID;
    int replyCount;
    String docid;
    String source;
    public List<BannerData> getAds() {
        return ads;
    }

    public void setAds(List<BannerData> ads) {
        this.ads = ads;
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

    public String getSpecialID() {
        return specialID;
    }

    public void setSpecialID(String specialID) {
        this.specialID = specialID;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "NewsDetail{" +
                "ads=" + ads +
                ", imgsrc='" + imgsrc + '\'' +
                ", title='" + title + '\'' +
                ", specialID='" + specialID + '\'' +
                ", replyCount='" + replyCount + '\'' +
                ", docid='" + docid + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
