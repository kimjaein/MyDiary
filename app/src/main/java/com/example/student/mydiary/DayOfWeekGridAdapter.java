package com.example.student.mydiary;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by student on 2018-01-12.
 */

public class DayOfWeekGridAdapter extends BaseAdapter{
    private final List<String> list;
    private final LayoutInflater inflater;
    private Context context;
    Calendar mCal;

    public DayOfWeekGridAdapter(Context context, List<String> list,Calendar mCal) {
        this.context=context;
        this.mCal=mCal;
        this.list = list;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return list.size();
    }
    @Override
    public String getItem(int position) {
        return list.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_calendar_day_of_week, parent, false);
            holder = new ViewHolder();
            holder.calendarItemDayOfWeek= (TextView) convertView.findViewById(R.id.calendar_item_day_of_week);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //요일, 날짜 넣는 부분
        holder.calendarItemDayOfWeek.setText("" + getItem(position));

        if (position ==0) { //일요일 텍스트 컬러 변경
            holder.calendarItemDayOfWeek.setTextColor(ContextCompat.getColor(context, R.color.Red));
        }

        return convertView;
    }
    private class ViewHolder {
        TextView calendarItemDayOfWeek;
    }
}
