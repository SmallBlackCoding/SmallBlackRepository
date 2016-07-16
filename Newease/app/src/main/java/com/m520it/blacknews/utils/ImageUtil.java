package com.m520it.blacknews.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;

/**
 * @author michael
 * @time 2016/6/23  18:53
 * @desc ${TODD}
 */
public class ImageUtil {
    public static File getImage(String imagename){
        File mediaImage=null;
        //获取到SD卡的详细路径
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            //判断SD卡是否有安装
            File myDir = new File(root);
            if(!myDir.exists()) {
                return null;
            }
            //SD卡路径+/xiaomage/xxxxx.jpg
            mediaImage=new File(myDir.getPath()+"/"+Contance.FLODERNAME+imagename);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mediaImage;
    }
    //根据一个图片的路径获取一张bitmap
    public static Bitmap getImageBitmap(String photoPath){
        //生成一张bitmap的时候的配置参数
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPreferredConfig= Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath, options);
        return  bitmap;
    }
    //判断某张图片是否已经下载,name->md5字符串
    public static boolean checkifImageExists(String imagename){
        Bitmap b=null;
        //根据文件名来获取文件/xxxxx.jpg
        File file=getImage("/"+imagename+".jpg");
        if(!file.exists()) {
            return false;
        }
        String path=file.getAbsolutePath();
        //检验我们的文件是否下载
        //检验我们的图片内容是否完整->尝试读一张bitmap
        if(path!=null) {
            b=BitmapFactory.decodeFile(path);
        }
        if(b==null) {
            return false;
        }
        return  true;
    }

}
