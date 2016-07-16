package com.m520it.blacknews.news;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.m520it.blacknews.R;
import com.m520it.blacknews.activity.IndexActivity;
import com.m520it.blacknews.news.bean.Artical;
import com.m520it.blacknews.news.bean.Image;
import com.m520it.blacknews.utils.Contance;
import com.m520it.blacknews.utils.HttpResponCallBack;
import com.m520it.blacknews.utils.HttpUtils;
import com.m520it.blacknews.utils.JsonUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author michael
 * @time 2016/6/28  13:25
 * @desc ${TODD}
 */
public class DetailActivity extends Activity {

    public static final String DOC_ID = "doc_id";
    //文章的id
    String docid;
    static final int INIT_WEB = 1;
    WebView cotent_webView;
    TextView mReply;
    EditText input;
    View right_icon;
    TextView send;
    //是否获取到焦点
    boolean hasFocus = false;
    View parent;
    MyHandler mHandler;
    ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mHandler = new MyHandler(DetailActivity.this);
        cotent_webView = (WebView) findViewById(R.id.content_webview);
        mBack = (ImageView) findViewById(R.id.news_back);
        mReply = (TextView) findViewById(R.id.replyCount);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(DetailActivity.this, IndexActivity.class);
                startActivity(intent);
            }
        });
        mReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DetailActivity.this, FeedBackActivity.class);
                intent.putExtra(DOC_ID, docid);
                startActivity(intent);
            }
        });

        input = (EditText) findViewById(R.id.input);
        right_icon = findViewById(R.id.right_icons);
        send = (TextView) findViewById(R.id.send);
        parent = findViewById(R.id.parent);
        final Drawable drawable_left = getResources().getDrawable(R.drawable.biz_pc_main_tie_icon);
        //设置图片显示的区域
        //如果需要设置drawable_left
        drawable_left.setBounds(0, 0, drawable_left.getMinimumWidth(), drawable_left.getMinimumHeight());
//监控输入框的焦点
        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean focus) {
                hasFocus = focus;
                //获取到焦点
                if (hasFocus) {
                    right_icon.setVisibility(View.GONE);
                    send.setVisibility(View.VISIBLE);
                    send.setText("发送");
                    input.setHint("");
                    input.setCompoundDrawables(null, null, null, null);

                } else {
                    right_icon.setVisibility(View.VISIBLE);
                    send.setVisibility(View.GONE);
                    input.setHint("写跟帖...");
                    input.setCompoundDrawables(drawable_left, null, null, null);

                }
            }
        });
        //获取HotFragment传过来的docid
        docid = getIntent().getStringExtra(DOC_ID);
        //handler处理
        //获取数据

        getData(docid);
    }
    //按下返回键的时候

    @Override
    public void onBackPressed() {
        //如果输入框有焦点，将焦点获取给父控件
        if (hasFocus) {
            parent.requestFocus();
            return;
        }
        finish();
    }


    public void getData(final String id) {
        String url = Contance.getDetail_url(id);
        //String url = Contance.getSpecialUrl(id);
        Log.i("it520", "url=" + url);
        HttpUtils util = HttpUtils.getInstance();
        util.doGet(url, new HttpResponCallBack<String>(String.class) {
            @Override
            public void onSuccess(String str) {

                try {
                    JSONObject js = new JSONObject(str);
                    JSONObject detail_js = js.optJSONObject(id);
                    Artical artical = JsonUtil.parse(detail_js.toString(), Artical.class);
                    if (artical != null) {
                        String body = artical.getBody();
                        List<Image> images = artical.getImg();
                        for (int i = 0; i < images.size(); i++) {
                            Image tmp_image = images.get(i);
                            String imgHTML = "<IMG src='" + tmp_image.getSrc() + "'/>";
                            // Log.i("it520", "imgHTML=" + imgHTML);
                            body = body.replaceFirst("<!--IMG#" + i + "-->", imgHTML);
                        }
                        //<P>段落</P>
                        //加粗的标题
                        // <p><span> style='font-size:18px;'><strong>ff</strong></span></p>
                        String title = "<p><span style='font-size:18px;'><strong>" + artical.getTitle() + "</strong></span></p>";
                        // Log.i("it520", "title=" + title);
                        //来源和时间加上 &nbsp 代表空格
                        String source = "<p><span style='color:#666666;'>" + artical.getSource() + "&nbsp&nbsp" + artical.getPtime() + "</span></p>";
                        body = title + source + body;
                        body = "<Html><head><style>img{width:100%}</style></head>" + body + "</Html>";
                        Log.i("it520", "body=" + body);

                        Message message = mHandler.obtainMessage(INIT_WEB);
                        message.obj = body;
                        message.arg1 = artical.getReplyCount();
                        mHandler.sendMessage(message);
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

    public void showContent(String html_body) {
        cotent_webView.loadDataWithBaseURL(null, html_body, "text/html", "utf-8", null);
    }

    public void showReply(int reply) {
        mReply.setText(reply + "");

    }

    static class MyHandler extends Handler {
        WeakReference<DetailActivity> activity;

        public MyHandler(DetailActivity activity) {
            this.activity = new WeakReference<DetailActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            DetailActivity act = activity.get();
            if (act == null) {
                return;
            }
            switch (msg.what) {
                case INIT_WEB:
                    String html_body = (String) msg.obj;
                    act.showContent(html_body);
                    act.showReply(msg.arg1);
                    break;
            }

        }
    }

}
