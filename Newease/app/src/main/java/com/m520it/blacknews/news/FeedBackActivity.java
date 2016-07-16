package com.m520it.blacknews.news;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.m520it.blacknews.R;
import com.m520it.blacknews.news.adapter.FeedBackAdapter;
import com.m520it.blacknews.news.bean.FeedBack;
import com.m520it.blacknews.news.bean.FeedBacks;
import com.m520it.blacknews.utils.Contance;
import com.m520it.blacknews.utils.HttpResponCallBack;
import com.m520it.blacknews.utils.HttpUtils;
import com.m520it.blacknews.utils.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author michael
 * @time 2016/6/28  19:38
 * @desc ${TODD}
 */
public class FeedBackActivity extends Activity {
    ImageView mBack;
    ArrayList<FeedBacks>backs;
    MyHandler mHandler;
    public static  final  int SUCCESS_INIT=1;
    ListView mListview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        backs=new ArrayList<>();
        mHandler=new MyHandler(this);
        mListview= (ListView) findViewById(R.id.replay_listView);
        mBack = (ImageView) findViewById(R.id.reply_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FeedBackActivity.this, DetailActivity.class);
                startActivity(intent);
            }
        });
//获取从DetailActivity传过来的docId
        String docId = getIntent().getStringExtra(DetailActivity.DOC_ID);
        getData(docId);
    }

     public void initListView(){
         FeedBackAdapter adapter=new FeedBackAdapter(backs,FeedBackActivity.this);
         mListview.setAdapter(adapter);
     }

    public void getData(String docId) {
        HttpUtils util = HttpUtils.getInstance();
        String feedBackUrl = Contance.getFeedBackUrl(docId);
        util.doGet(feedBackUrl, new HttpResponCallBack<String>(String.class) {
            @Override
            public void onSuccess(String back) {
                Log.i("it520", "back=" + back);
                try {
                    JSONObject js = new JSONObject(back);
                    JSONArray hotPosts = js.optJSONArray("hotPosts");
                    //设置标题，除了标题以外都是内容

                    FeedBacks title=new FeedBacks();
                    title.setTitle(true);
                    title.setTitleName("最新跟帖");
                    //把标题放在集合的第一位
                    backs.add(title);

                    //for循环，取出hotPosts里面的内容
                    for (int i = 0; i < hotPosts.length(); i++) {
                        //每循环一次，就要建立一个Feedbacks来存放FeedBack
                        FeedBacks feedBacks = new FeedBacks();

                        JSONObject tmp = hotPosts.optJSONObject(i);
                        //使用迭代器取出对象的key值
                        Iterator<String> keys = tmp.keys();
                        while (keys.hasNext()) {
                            String key = keys.next();
                            JSONObject feedback_js = tmp.optJSONObject(key);
                            FeedBack feedBack = JsonUtil.parse(feedback_js.toString(), FeedBack.class);
                            if (feedBack != null) {
                                feedBack.setIndex(Integer.parseInt(key));
                                //把feeBack对象加进集合
                                feedBacks.add(feedBack);
                            }
                        }
                        //告诉集合我们解析出来的是内容
                        feedBacks.setTitle(false);
                        //把feeBacks对象加进大集合
                        backs.add(feedBacks);
                        //发送一个空消息给handler。然handler更改ＵＩ，填充数据
                        mHandler.sendEmptyMessage(SUCCESS_INIT);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String msg) {

            }
        });
    }
    static  class MyHandler extends Handler{
        WeakReference<FeedBackActivity>activity;
        public MyHandler(FeedBackActivity activity){
            this.activity=new WeakReference<FeedBackActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            FeedBackActivity act = activity.get();
            if(act==null) {
                return;
            }
            switch (msg.what){
                case SUCCESS_INIT:
                    //设置界面填充listview
                 act.initListView();
                    break;
            }
        }
    }
}
