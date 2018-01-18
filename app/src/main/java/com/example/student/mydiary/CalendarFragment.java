package com.example.student.mydiary;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by student on 2018-01-12.
 */

public class CalendarFragment extends Fragment {

    private DayGridAdapter dayGridAdapter;
    private ArrayList<String> dayList;
    private GridView gridViewDay;
    private Calendar mCal;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_calendar,container,false);

        int year = getArguments().getInt("year");
        int month= getArguments().getInt("month");
        Log.d("bundle",year + "/ "+month);

        gridViewDay = (GridView) view.findViewById(R.id.gridview_day);


        dayList = new ArrayList<String>();

        mCal =  Calendar.getInstance();

        //이번달 1일 무슨요일인지 판단 mCal.set(Year,Month,Day)
        mCal.set(year,month ,1);
        int dayNum = mCal.get(Calendar.DAY_OF_WEEK);

        //1일 - 요일 매칭 시키기 위해 공백 add
        for (int i = 1; i < dayNum; i++) {
            dayList.add("");
        }
        //daylist에 해당 월의 날짜 입력
        setCalendarDate(mCal.get(Calendar.MONTH) );

        dayGridAdapter = new DayGridAdapter(getContext(), dayList,mCal);
        gridViewDay.setAdapter(dayGridAdapter);

        gridViewDay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),"date :  "+dayGridAdapter.getItem(position) , Toast.LENGTH_SHORT).show();
                MainActivity.selectedDate = Integer.parseInt(dayGridAdapter.getItem(position));
                MainActivity.selectedCal =mCal;

            }
        });

        return view;
    }


    /**
     * 해당 월에 표시할 일 수 구함
     *
     * @param month
     */
    private void setCalendarDate(int month) {
        mCal.set(Calendar.MONTH, month );
        for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add("" + (i + 1));
        }
    }
}
