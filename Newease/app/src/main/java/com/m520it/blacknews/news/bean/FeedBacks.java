package com.m520it.blacknews.news.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author michael
 * @time 2016/6/30  14:42
 * @desc ${TODD}
 */
public class FeedBacks {
    ArrayList<FeedBack> feedBacks;
    //判断这条数据是标题还是内容
    boolean isTitle=false ;
    //如果是标题还要给他设置名称
    String titleName;

    public ArrayList<FeedBack> getFeedBacks() {
        return feedBacks;
    }

    public void setFeedBacks(ArrayList<FeedBack> feedBacks) {
        this.feedBacks = feedBacks;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public FeedBacks() {
        //创建一个空的集合，但有FeedBack的时候再一个一个加进来
        this.feedBacks = new ArrayList<>();
    }

    public void add(FeedBack back) {
        feedBacks.add(back);
    }

    //对集合进行排序
    public void sort() {
        Collections.sort(feedBacks, new FeedBackCompare());
    }
    class FeedBackCompare implements Comparator {

        @Override
        public int compare(Object obj_one, Object obj_two) {
            FeedBack feeback_one = (FeedBack) obj_one;
            FeedBack feeback_two = (FeedBack) obj_two;
            if (feeback_one.getIndex() > feeback_two.getIndex()) {
                return 1;
            } else if (feeback_one.getIndex() < feeback_two.getIndex()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public String toString() {
        return "FeedBacks{" +
                "feedBacks=" + feedBacks +
                '}';
    }
}
