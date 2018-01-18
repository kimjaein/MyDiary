package com.example.student.mydiary;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

/**
 * Created by student on 2018-01-12.
 */
public class DayGridAdapter extends BaseAdapter {
    private final List<String> list;
    private final LayoutInflater inflater;
    private Context context;
    Calendar mCal,nowCal;
    /**
     * 생성자
     *
     * @param context
     * @param list
     */
    public DayGridAdapter(Context context, List<String> list,Calendar mCal) {
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
    } // 날짜
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_calendar_day, parent, false);
            holder = new ViewHolder();
            holder.calendarItemDay= (TextView) convertView.findViewById(R.id.calendar_item_day);
            holder.itemLayout = convertView.findViewById(R.id.item_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //요일, 날짜 넣는 부분
        holder.calendarItemDay.setText("" + getItem(position));

        //해당 날짜 텍스트 컬러,배경 변경
        nowCal = Calendar.getInstance();

        //오늘 day 가져옴
        Integer today = nowCal.get(Calendar.DAY_OF_MONTH);
        String sToday = String.valueOf(today);

        int nowMonth=nowCal.get(Calendar.MONTH);
        int nowYear= nowCal.get(Calendar.YEAR);
        int mMonth= mCal.get(Calendar.MONTH);
        int mYear = mCal.get(Calendar.YEAR);

        if (sToday.equals(getItem(position)) && nowMonth == mMonth && nowYear == mYear) { //오늘 day 텍스트 컬러 변경
            holder.calendarItemDay.setTextColor(ContextCompat.getColor(context, R.color.Blue));
        }else if(position%7 == 0){ // 일요일 색 변경
            holder.calendarItemDay.setTextColor(ContextCompat.getColor(context, R.color.Red));
        }
        return convertView;
    }

    private class ViewHolder {
        LinearLayout itemLayout;
        TextView calendarItemDay;
        TextView calendarItemFirst;
        TextView calendarItemSecond;
        TextView calendarItemThird;
    } //보여지는 할일 갯수 3개까지

}
