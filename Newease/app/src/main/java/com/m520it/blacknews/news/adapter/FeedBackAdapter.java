package com.m520it.blacknews.news.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.m520it.blacknews.R;
import com.m520it.blacknews.news.bean.FeedBack;
import com.m520it.blacknews.news.bean.FeedBacks;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author michael
 * @time 2016/6/30  16:11
 * @desc ${TODD}
 */
public class FeedBackAdapter extends BaseAdapter {
    static final int TITLE = 0;
    static final int CONTENT = 1;
    ArrayList<FeedBacks> datas;
    LayoutInflater inflater;
    DisplayImageOptions options;

    public FeedBackAdapter(ArrayList<FeedBacks> datas, Context context) {
        this.datas = datas;
        this.inflater = LayoutInflater.from(context);

        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageForEmptyUri(R.drawable.biz_tie_user_avater_default)
                .showImageOnFail(R.drawable.biz_tie_user_avater_default)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        FeedBacks feedBacks = datas.get(position);
        if (type == CONTENT) {
            ContentHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_feedback, null);
                holder = new ContentHolder();
                holder.icon = (CircleImageView) convertView.findViewById(R.id.feedback_icon);
                holder.title = (TextView) convertView.findViewById(R.id.feedback_title);
                holder.content = (TextView) convertView.findViewById(R.id.feedback_content);
                holder.from = (TextView) convertView.findViewById(R.id.feedback_from);
                holder.vote = (TextView) convertView.findViewById(R.id.feedback_vote);
                convertView.setTag(holder);
            } else {
                holder = (ContentHolder) convertView.getTag();
            }

            initHolder(feedBacks, holder);
        }

        else {
              TitleHolder tHolder;
            if(convertView==null) {
                convertView=inflater.inflate(R.layout.item_feedback_title,null);
                tHolder=new TitleHolder();
                tHolder.top_title= (TextView) convertView.findViewById(R.id.feedback_top_title);
                convertView.setTag(tHolder);
            }else{
               tHolder= (TitleHolder) convertView.getTag();
            }
            tHolder.top_title.setText(feedBacks.getTitleName());
        }

        return convertView;
    }

    //获取每个子item的类型
    @Override
    public int getItemViewType(int position) {
        return datas.get(position).isTitle() ? TITLE : CONTENT;
    }

    //获取类型的总数
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    class ContentHolder {
        public CircleImageView icon;
        public TextView title;
        public TextView from;
        public TextView content;
        public TextView vote;
    }
    class TitleHolder{
      public  TextView top_title;
    }

    //初始化viewholder
    public void initHolder(FeedBacks feedBacks, ContentHolder holder) {
        ArrayList<FeedBack> feedBackList = feedBacks.getFeedBacks();
        if (feedBackList.size() > 0) {
            //如果有值的话就拿到它最后一条数据
            FeedBack feedBack = feedBackList.get(feedBackList.size() - 1);
            //加载图片
            ImageLoader.getInstance().displayImage(feedBack.getTimg(), holder.icon, options);
            //加载标题
           String name= feedBack.getN();
            if(TextUtils.isEmpty(name)) {
                name="网易用户";
            }
            holder.title.setText(name);
            //加载点赞
            holder.vote.setText(feedBack.getV());
            //加载评论内容
            holder.content.setText(feedBack.getB());
            //加载来自谁谁谁
            String realFrom="";
           String from= feedBack.getF();
            if(from.contains(":")) {
                from.replaceAll(":","");
            }
            if(from.lastIndexOf("&nbsp")>0) {
                String[] splits = from.split("&nbsp");
                realFrom=splits[0];
            }else{
                realFrom=from;
            }
            holder.from.setText(realFrom);
        }
    }
}
