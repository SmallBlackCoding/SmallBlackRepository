package com.m520it.blacknews.news.bean;

import java.util.List;

/**
 * @author michael
 * @time 2016/6/28  21:02
 * @desc ${TODD}
 */
public class Artical {
    String body;
    String title;
    String ptime;
    List<Image>img;
    String source;
    int replyCount;
    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Image> getImg() {
        return img;
    }

    public void setImg(List<Image> img) {
        this.img = img;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    @Override
    public String toString() {
        return "Artical{" +
                "body='" + body + '\'' +
                ", title='" + title + '\'' +
                ", ptime='" + ptime + '\'' +
                ", img=" + img +
                ", source='" + source + '\'' +
                ", replyCount=" + replyCount +
                '}';
    }
}
