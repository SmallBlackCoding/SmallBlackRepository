package com.m520it.blacknews;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.m520it.blacknews.Widget.RingTextView;
import com.m520it.blacknews.activity.IndexActivity;
import com.m520it.blacknews.bean.Action;
import com.m520it.blacknews.bean.Ad;
import com.m520it.blacknews.bean.Ads;
import com.m520it.blacknews.onclicklistener.RingTextOnclickListener;
import com.m520it.blacknews.service.DownLoadService;
import com.m520it.blacknews.utils.Contance;
import com.m520it.blacknews.utils.HttpResponCallBack;
import com.m520it.blacknews.utils.HttpUtils;
import com.m520it.blacknews.utils.ImageUtil;
import com.m520it.blacknews.utils.JsonUtil;
import com.m520it.blacknews.utils.Md5Helper;
import com.m520it.blacknews.utils.SharePreferenceUtil;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //splash界面自定义的圆环
    public RingTextView ringtext;
    private MyHandler mHandler;
    //跳到indexActivity的标识
    public static final int GOTO_MAIN = 0x001;
    public static final int RING_SET = 0x002;
    private ImageView splash_image;
    public static final String ADS_NAME = "ads";
    //保存网络请求的json的key
    public static final String JASON_SAVE_NAME = "all_json";
    //保存上次显示图片的角标
    public static final String SHOW_IAMGE_INDEX = "image_index";
    //保存上一次获取成功http的时间
    public static final String HTTP_LAST_TIME = "http_last_time";
    //保存接口超时的时间
    public static final String HTTP_TIME_OUT = "time_out";
    private String json_all;
    private MyReceiver receiver;

    //当前圆环的进度
    int now = 0;
    int all = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        //初始化handler
        mHandler = new MyHandler(MainActivity.this);
        splash_image = (ImageView) findViewById(R.id.splash_image);
        ringtext = (RingTextView) findViewById(R.id.ringtext);
        ringtext.setOnclickListener(new RingTextOnclickListener() {
            @Override
            public void click() {
                //被点击的是直接计入index页面
                gotoMain();
            }
        });
        //读出缓存->不想多次重复请求网络
        json_all = SharePreferenceUtil.getString(MainActivity.this, JASON_SAVE_NAME);
        //第二次进入这个程序，我们还是会调用create 如果广播注册在这里，还是会注册广播，但是我们这个时候已经不需要广播
        //第一次进去的时候，只是获取json 把它放进sharePerference
        //开启服务下载好图片放在内存卡
        // getDate(json_all);
        //图片还没下载好 就开始给imageview设置图片 空指针
        getDate(json_all);

        //第一次进去 json=null -->执行getDate方法-->getAds-->doGet-->请求到一个json
        //再把请求到的json解析为Ads传给downloadService服务，让服务区下载图片
        //因为ok那一部分请求的代码是异步的，所以getDate还没有执行完就会执行getImage
        //解决方案，就是在下载图片的服务中，当图片下载完毕的时候就发送一个广播
        //在mainActivity注册一个广播，当它收到图片下号的广播就调用getImage
        //我们应该在mainActivity的那个位置注册一个广播呢? 这个广播只是用一次而已
        //广播是给getAds绑定的 执行getAds的时候再注册广播，因为getAds不执行的时候广播是没有什么卵用的 只有当json=null 或者超时 才会执行getAds（）
        //取出可以下次可以复用的message
        Message message = mHandler.obtainMessage(RING_SET, now, 0);
        mHandler.sendMessage(message);

    }

    //http请求数据
    public void getDate(String json) {
        //
        if (TextUtils.isEmpty(json)) {
            getAds();
        } else {
            //读出的缓存不为空->是不是接口数据已经超时
            long lasttime = SharePreferenceUtil.getLong(MainActivity.this, HTTP_LAST_TIME);
            long timeout = SharePreferenceUtil.getLong(MainActivity.this, HTTP_TIME_OUT);
            long nowtime = System.currentTimeMillis();
            //已经超时->重新获取http
            if ((nowtime - lasttime) > timeout * 60 * 1000) {
                getAds();
            } else {
                //这里已经不会再进入getAds 没有人帮他设置图片了 自己来搞了
                getImage();
            }
        }

    }

    private void getAds() {
        HttpUtils httpUtil = HttpUtils.getInstance();
        //观察者，成功或者失败
        httpUtil.doGet(Contance.SPLASH_URL, new HttpResponCallBack<String>(String.class) {
            @Override
            public void onSuccess(String t) {
                //当它请求json数据成功的时候，我们注册的这个广播才有意义
                //得到一个json
                System.out.println("onSuccess");
                Ads temp = JsonUtil.parse(t, Ads.class);
                long lasttime = System.currentTimeMillis();
                SharePreferenceUtil.putLong(MainActivity.this, HTTP_LAST_TIME, lasttime);
                SharePreferenceUtil.putString(MainActivity.this, JASON_SAVE_NAME, t);
                SharePreferenceUtil.putLong(MainActivity.this, HTTP_TIME_OUT, temp.getNext_req());
//广播的作用就是注册好了之后，当它接收到它就会执行onReceive里面的内容
                receiver = new MyReceiver();
                IntentFilter filter = new IntentFilter("liuhuanyu");
                registerReceiver(receiver, filter);

                Intent intent = new Intent();
                intent.setClass(MainActivity.this, DownLoadService.class);
                intent.putExtra(ADS_NAME, temp);
                startService(intent);
            }

            @Override
            public void onError(String msg) {
                Log.v("it520", "----onError----" + msg);
            }
        });

    }

    public void getImage() {
        String json = SharePreferenceUtil.getString(MainActivity.this, JASON_SAVE_NAME);

        if (TextUtils.isEmpty(json)) {
            //跳转到主界面

        }
        //获取上一次的缓存的图片
        int index = SharePreferenceUtil.getInt(MainActivity.this, SHOW_IAMGE_INDEX);
        //将缓存的数据转换成需要的对象
        Ads temp = JsonUtil.parse(json, Ads.class);

        System.out.println("1");

        List<Ad> adverts = temp.getAds();
        System.out.println("2");
        index = index % adverts.size();
        System.out.println("3");
        Ad ad = adverts.get(index);
        System.out.println("4");
        String url = ad.getRes_url().get(0);
        System.out.println("5");
        //生成图片md5字符串
        String imageName = Md5Helper.toMD5(url);
        System.out.println("6");
        //判断图片是否下载成功
        if (ImageUtil.checkifImageExists(imageName)) {
            System.out.println("7");
            try {
                //加载图片
                File file = ImageUtil.getImage("/" + imageName + ".jpg");
                System.out.println("8");
                if (file != null) {
                    Bitmap bitmap = ImageUtil.getImageBitmap(file.getAbsolutePath());
                    if (bitmap == null) {
                        return;
                    }
                    splash_image.setImageBitmap(bitmap);
                    index++;
                    //缓存起来上一次显示的图片的角标
                    SharePreferenceUtil.putInt(MainActivity.this, SHOW_IAMGE_INDEX, index);
                    splash_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Action action = (Action) splash_image.getTag();
                            if (action != null && !TextUtils.isEmpty(action.getLink_url())) {
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, WebViewActivity.class);
                                intent.putExtra(WebViewActivity.ACTION_NAME, action);
                                startActivity(intent);
                            }

                        }
                    });
                    splash_image.setTag(ad.getAction_params());

                }
                System.out.println("9");
            } catch (Exception e) {
                System.out.println("加载图片出错了");
                e.printStackTrace();
            }
        }


    }

    //第二次的时候json是有值的，它又没有超时，所以不会执行getAds自然也不会注册广播，所以receiver=null
    @Override
    protected void onDestroy() {
        if (receiver != null) {
            unregisterReceiver(receiver);
        }


        super.onDestroy();
    }

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //收到下载好的广播就开始给ImageVIew设置图片
            getImage();
        }
    }

    private static class MyHandler extends Handler {
        //弱引用
        WeakReference<MainActivity> activity;

        public MyHandler(MainActivity act) {
            this.activity = new WeakReference<MainActivity>(act);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity act = activity.get();
            if (act == null) {
                return;
            }
            switch (msg.what) {
                case GOTO_MAIN:
                    act.gotoMain();
                    break;
                case RING_SET:
                    int time = msg.arg1;
                    if (time <= act.all) {
                        act.setRing(time, act.all);
                        act.now++;
                        //把信息做好
                        Message tmp_message = act.mHandler.obtainMessage(RING_SET, act.now, 0);
                        //延时250发送信息
                        act.mHandler.sendMessageDelayed(tmp_message, 250);
                    }else{
                        //过了5秒，跳到index界面
                        act.gotoMain();
                    }

            }
        }
    }

    //修改splah界面的小圆形的进度
    public void setRing(int progress, int all) {

        ringtext.setProgress(progress, all);
    }

    public void gotoMain() {
        //进入到index页面就要把handler干掉
        mHandler.removeMessages(RING_SET);
        Intent intent = new Intent(MainActivity.this, IndexActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_in_animation, R.anim.activity_out_animation);
        finish();
    }
}
