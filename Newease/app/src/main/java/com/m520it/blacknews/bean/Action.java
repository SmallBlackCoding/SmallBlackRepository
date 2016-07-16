package com.m520it.blacknews.bean;

import java.io.Serializable;

/**
 * @author michael
 * @time 2016/6/23  12:53
 * @desc ${TODD}
 */
public class Action implements Serializable {

     String link_url;


    public String getLink_url() {
        return link_url;
    }

    public void setLink_url(String link_url) {
        this.link_url = link_url;
    }

    @Override
    public String toString() {
        return "Action{" +
                "link_url='" + link_url + '\'' +
                '}';
    }
}
