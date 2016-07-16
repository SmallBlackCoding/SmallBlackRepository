package com.m520it.blacknews.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author michael
 * @time 2016/6/23  13:00
 * @desc ${TODD}
 */
public class Ads implements Serializable {
    private int result;
    private int next_req;
    private List<Ad> ads;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getNext_req() {
        return next_req;
    }

    public void setNext_req(int next_req) {
        this.next_req = next_req;
    }

    public List<Ad> getAds() {
        return ads;
    }

    public void setAds(List<Ad> ads) {
        this.ads = ads;
    }


    @Override
    public String toString() {
        return "Ads{" +
                "result=" + result +
                ", next_req=" + next_req +
                ", ads=" + ads +
                '}';
    }
}
