package com.france.sharereader.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.france.sharereader.R;
import com.france.sharereader.bean.Book;
import com.france.sharereader.config.Config;
import com.france.sharereader.dao.BaseDao;
import com.france.sharereader.dao.impl.BaseDaoImpl;
import com.france.sharereader.ui.view.TopicMultipleChoiceDialog;
import com.france.sharereader.ui.view.TopicSingleChoiceDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/5/18.
 */
public class BookBaseAdapter extends BaseAdapter  {
    private List<Book> books = new ArrayList<Book>();
    Context ct;
    private LayoutInflater inflater;
    public BookBaseAdapter(Context ct, List<Book> books) {
        // TODO Auto-generated constructor stub
        this.books = books;
        this.ct = ct;
        inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    static class ViewHolder {

        TextView book_name;
        TextView topic;
        ImageView book_page;
        ProgressBar progress_bar;
        TextView progress_text;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final ViewHolder holder;
        final Book book = books.get(position);
        if(convertView==null){
            convertView = inflater.inflate(R.layout.mybooks_list_item,parent,false);
            holder = new ViewHolder();
            holder.book_name=(TextView)convertView.findViewById(R.id.book_name);
            holder.topic=(TextView)convertView.findViewById(R.id.topic);
            holder.progress_text=(TextView)convertView.findViewById(R.id.progress_num);
            holder.topic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position =Integer.parseInt(v.getTag().toString());
                    TopicSingleChoiceDialog topicSingleleChoiceDialog =new TopicSingleChoiceDialog(ct, new TopicSingleChoiceDialog.OnTestListening() {
                        @Override
                        public void getTopicID(int topicID) {
                            Log.i("zjx","topicID:"+topicID);
                            if(topicID!=-1){
                                holder.topic.setText(Config.TOPICS[topicID]);
                                BaseDao baseDao=new BaseDaoImpl(ct);
                                book.setThemeId(topicID);
                                baseDao.update(book);
                            }
                        }
                    });

                }
            });
            holder.book_page=(ImageView)convertView.findViewById(R.id.book_page);
            holder.progress_bar = (ProgressBar)convertView.findViewById(R.id.progress_bar);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
//        ImageView tv1=(ImageView)convertView.findViewById(R.id.img);
//        TextView tv2=(TextView)convertView.findViewById(R.id.title);
//        tv1.setImageResource(p.getImg());
//        tv2.setText(p.getName());
        holder.book_page.setImageResource(R.drawable.book_nopicture);//目前为暂未封面，完整实现为setImageBitmap
        holder.book_name.setText(book.getBookName());
        if(book.getThemeId()==-1)
            holder.topic.setText("尚未选择");
        else holder.topic.setText(Config.TOPICS[book.getThemeId()]);
        holder.topic.setTag(position);//设置控件的位置 点击事件的时候获取
        holder.progress_bar.setProgress(book.getProgress());
        holder.progress_text.setText("已读"+book.getProgress()+"%");
        //http://www.2cto.com/kf/201108/101092.html
        return convertView;
    }

}