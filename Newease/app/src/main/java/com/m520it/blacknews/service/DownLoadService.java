package com.m520it.blacknews.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;

import com.m520it.blacknews.MainActivity;
import com.m520it.blacknews.bean.Ad;
import com.m520it.blacknews.bean.Ads;
import com.m520it.blacknews.utils.Contance;
import com.m520it.blacknews.utils.ImageUtil;
import com.m520it.blacknews.utils.Md5Helper;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * @author michael
 * @time 2016/6/23  13:53
 * @desc ${TODD}
 */
public class DownLoadService extends IntentService {


    public DownLoadService() {
        super("DownLoadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("进入了service");
        try {
            Ads ads = (Ads) intent.getSerializableExtra(MainActivity.ADS_NAME);
            System.out.println("ads的内容："+ads.toString());
            List<Ad> data = ads.getAds();
            //判断data内容不为空
            if (data == null && data.size() == 0) {
                return;
            }
            for (int i=0;i<data.size();i++) {
                Ad tmp =data.get(i);
                //获取下载图片的地址
                //ad 是list 第0位imageurl
                List<String> images = tmp.getRes_url();
                //先判断这个图片集合不为空,接着判断第一张图片的地址是否为空
                if (images != null && !TextUtils.isEmpty(images.get(0))) {
                    String imageurl = images.get(0);
                    //因为先用MD5文件加入ulr,将文件名写入sd卡
                    String name = Md5Helper.toMD5(imageurl);
                    //如果没有下载->文件已加载,文件的内容是否完整

                    if (!ImageUtil.checkifImageExists(name)) {
                        //开始下载
                        downLoadImage(imageurl, name);
                        System.out.println("下载完了一张"+i);
                    }


                }
            }
            System.out.println("下载完成");
            //下载完图片之后发送一个广播 告诉mainActivity图片下载好了
            sendBroadcast(new Intent("liuhuanyu"));
        } catch (Exception e) {
            System.out.println("出错了！");
            e.printStackTrace();
        }
    }

    //下载图片的实际操作
    private void downLoadImage(String imageurl, String imagename) {
        URL url = null;

        try {
            //生成一个url
            url = new URL(imageurl);
            URLConnection conn = url.openConnection();
            //根据一个流来生成一张图片，如果是返回null,意味着下载失败
            Bitmap bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            if (bitmap != null) {
                //缓存
                saveToSdCard(bitmap, imagename);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //保存图片
    private void saveToSdCard(Bitmap bitmap, String filename) {

        //获取我们的sd卡路径 /sd
        File sdcard = Environment.getExternalStorageDirectory();
        //File(dir,name)dir需要生成的文件夹
        File folder = new File(sdcard.getAbsoluteFile(), Contance.FLODERNAME);
        //创建一个文件夹
        folder.mkdir();

        //生成我们需要的图片在 .xiaomage的文件夹内
        File file = new File(folder.getAbsoluteFile(), filename + ".jpg");
//如果文件已经下载成功，不重复下载
        if (file.exists()) {
            return;
        }

        try {
            //.xiaomage/xxxxx.jpg->out
            FileOutputStream out = new FileOutputStream(file);
            //传需要保存的文件格式,
            //压缩率 0~100
            //fileOutPutStream -> 需要保存文件的位置
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            //全部写出来
            out.flush();
            //刷新

            //断流
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
