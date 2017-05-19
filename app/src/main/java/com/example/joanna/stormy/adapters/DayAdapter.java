package com.example.joanna.stormy.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.joanna.stormy.R;
import com.example.joanna.stormy.weather.Day;

public class DayAdapter extends BaseAdapter {
    private Context mContext;
    private Day[] mDays;

    public DayAdapter(Context context, Day[] days) {
        mContext = context;
        mDays = days;
    }

    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int position) {
        return mDays[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            //create it
            convertView=LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1,null);
            holder=new ViewHolder();
            holder.iconImage= (ImageView) convertView.findViewById(R.id.iconWeaView);
            holder.temperatureLab= (TextView) convertView.findViewById(R.id.tempView);
            holder.dayLab= (TextView) convertView.findViewById(R.id.weekdayView);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        Day day=mDays[position];
        holder.iconImage.setImageResource(day.getIconId());
        holder.temperatureLab.setText(day.getTemperatureMax()+"");
        holder.dayLab.setText(day.getDayOfWeek());
        return convertView;
    }
    //hold the views added into the list item layout
    //purpose:recycling views for items in the list
    public static class ViewHolder{
        ImageView iconImage;
        TextView temperatureLab;
        TextView dayLab;
    }
}
