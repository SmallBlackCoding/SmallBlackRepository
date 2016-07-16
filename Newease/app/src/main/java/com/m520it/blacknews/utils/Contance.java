package com.m520it.blacknews.utils;

/**
 * @author michael
 * @time 2016/6/23  13:03
 * @desc ${TODD}
 */
public class Contance {
    public static final String SPLASH_URL = "http://g1.163.com/madr?app=7A16FBB6&platform=android&category=STARTUP&location=1&timestamp=1462779408364&uid=OaBKRDb%2B9FBz%2FXnwAuMBWF38KIbARZdnRLDJ6Kkt9ZMAI3VEJ0RIR9SBSPvaUWjrFtfw1N%2BgxquT0B2pjMN5zsxz13RwOIZQqXxgjCY8cfS8XlZuu2bJj%2FoHqOuDmccGyNEtV%2FX%2FnBofofdcXyudJDmBnAUeMBtnIzHPha2wl%2FQnUPI4%2FNuAdXkYqX028puyLDhnigFtrX1oiC2F7UUuWhDLo0%2BE0gUyeyslVNqLqJCLQ0VeayQa%2BgbsGetk8JHQ";
    //在缓存文件名前加.文件变成隐藏文件
    public static final String FLODERNAME = "xiaomage";

    //首页的数据接口
    public static final String INDEX_URL = "http://c.m.163.com/nc/article/headline/T1348647909107/%startIndex-%pageSize.html?from=toutiao&size=20&prog=&fn=1&passport=&devId=44t6%2B5mG3ACAOlQOCLuIHg%3D%3D&lat=&lon=&version=11.0&net=wifi&ts=1467292575&sign=luRlzuIQPOeQZQelJmOXmCxDjalviM01K509PmvUpHh48ErR02zJ6%2FKXOnxX046I&encryption=1&canal=hiapk_news&mac=36jEZiFWmLwrAYCBdOS4gqhnYB%2BxK6YGLcdcZR%2BsrK8%3D";

    public static String getIndexUrl(int index, int pagesize) {
        String url = new String(INDEX_URL);
        url = url.replace("%startIndex", String.valueOf(index * pagesize));
        url = url.replace("%pageSize", String.valueOf(pagesize));
        return url;
    }

    public static String detail_special_url="http://c.m.163.com/nc/special/%S.html";
    public static String getSpecialUrl(String docId){
        String new_string=detail_special_url.replace("%S",docId);
        return new_string;
    }


    public static String detail_url="http://c.m.163.com/nc/article/%S/full.html";

    //获取详细内容的url 根据docId来获取的
    public static String getDetail_url(String docId){
        String new_string=detail_url.replace("%S",docId);
       return  new_string;
    }
//通过docId获取到评论的内容
    public static String FEEDBACK_BACK_URL="http://comment.api.163.com/api/json/post/list/new/hot/news_shehui7_bbs/%S/0/10/10/2/2";
    public static String getFeedBackUrl(String docId){
        String new_url=FEEDBACK_BACK_URL.replace("%S",docId);
        return new_url;
    }

}
