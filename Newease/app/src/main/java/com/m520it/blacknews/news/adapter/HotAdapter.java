package com.m520it.blacknews.news.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.m520it.blacknews.R;
import com.m520it.blacknews.news.bean.NewsDetail;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author michael
 * @time 2016/6/26  20:40
 * @desc ${TODD}
 */
public class HotAdapter extends BaseAdapter {
    List<NewsDetail>data;
    LayoutInflater inflater;
    //图片加载器的显示配置
    DisplayImageOptions options;

    public HotAdapter(List<NewsDetail> data ,Context context)  {
        this.data = data;
        this.inflater=LayoutInflater.from(context);
    }
    public HotAdapter(Context context){
      //建立一个空的集合，在把数据传进来
        this.data=new ArrayList<>();
        this.inflater=LayoutInflater.from(context);

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)//使用内存缓存
                .cacheOnDisk(true)//是否缓存到sd卡
                .bitmapConfig(Bitmap.Config.RGB_565)//缓存图片的编码
                .build();
    }

    public void add(List<NewsDetail> tmp){
        this.data.addAll(tmp);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        NewsDetail detail;
        if(convertView==null) {
            convertView=inflater.inflate(R.layout.item_hot,null);
            holder=new ViewHolder();
           holder.icon= (ImageView) convertView.findViewById(R.id.content_icon);
            holder.title= (TextView) convertView.findViewById(R.id.content_title);
            holder.reply= (TextView) convertView.findViewById(R.id.content_reply);
            holder.souce= (TextView) convertView.findViewById(R.id.content_source);
            holder.totop= (TextView) convertView.findViewById(R.id.content_toTop);
           convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        detail=data.get(position);
        initHolder(holder,detail);
        return convertView;
    }

    class ViewHolder{
        ImageView icon;
        TextView title;
        TextView souce;
        TextView totop;
        TextView reply;
    }
    private void initHolder(ViewHolder holder,NewsDetail detail){
        holder.title.setText(detail.getTitle());
       String source= detail.getSource();
        if(!TextUtils.isEmpty(source)) {
           source=source.replace("$", "");
            source= source.replace("#","");
            holder.souce.setText(source);
        }
      String replayCount;
        int reply=detail.getReplyCount();
        if(reply<10000) {
            replayCount=String.valueOf(reply)+"跟帖";
        }else{
            replayCount=String.valueOf(reply/10000)+"万跟帖";
        }
        holder.reply.setText(replayCount);
        if(detail.getImgsrc()==null) {
            holder.icon.setImageResource(R.drawable.base_common_default_icon_small);
        }
        else{
            //加载图片
            ImageLoader.getInstance().displayImage(detail.getImgsrc(),holder.icon,options);
        }
    }

    public int getLast(){
        //删除一条数据
        return  data.size()-1;
    }
}
