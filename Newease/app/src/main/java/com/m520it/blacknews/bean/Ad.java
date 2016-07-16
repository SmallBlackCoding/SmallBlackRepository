package com.m520it.blacknews.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author michael
 * @time 2016/6/23  12:58
 * @desc ${TODD}
 */
public class Ad implements Serializable {
    List<String> res_url;
    Action action_params;

    public List<String> getRes_url() {
        return res_url;
    }

    public void setRes_url(List<String> res_url) {
        this.res_url = res_url;
    }

    public Action getAction_params() {
        return action_params;
    }

    public void setAction_params(Action action_params) {
        this.action_params = action_params;
    }


    @Override
    public String toString() {
        return "Ad{" +
                "res_url=" + res_url +
                ", action_params=" + action_params +
                '}';
    }
}
