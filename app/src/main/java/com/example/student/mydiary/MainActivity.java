package com.example.student.mydiary;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView tvMonth ,tvYear;
    private DayOfWeekGridAdapter dayOfWeekGridAdapter;
    private ArrayList<String> dayOfWeekList;
    private GridView gridViewDayOfWeek;
    static SimpleDateFormat curYearFormat,curMonthFormat;
    private Calendar mCal;
    private ViewPager viewPager;
    private Fragment[] frags = new Fragment[120];
    private Button btnToday,btnList;
    private ImageButton btnAdd;
    public static int selectedDate;
    public static Calendar selectedCal;
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMonth = (TextView) findViewById(R.id.tv_month);
        tvYear = (TextView) findViewById(R.id.tv_year);
        gridViewDayOfWeek = (GridView)findViewById(R.id.gridview_day_of_week);
        viewPager = findViewById(R.id.viewpager);
        btnToday = findViewById(R.id.btn_today);
        btnAdd =findViewById(R.id.btn_add);
        btnList = findViewById(R.id.btn_todo_list);

        int year = 2018;
        int month = 01;
        for(int i =0 ;i<frags.length ;i ++){
            frags[i] = new CalendarFragment();
            Bundle bundle = new Bundle();
            year = year +i/12;
            month = i%12;
            bundle.putInt("year",year);
            bundle.putInt("month",month);
            frags[i].setArguments(bundle);
        }
        // 오늘에 날짜를 세팅 해준다.
        long now = System.currentTimeMillis();
        final Date date = new Date(now);

        //연,월,일을 따로 저장
        curYearFormat = new SimpleDateFormat("yyyy", Locale.KOREA);
        curMonthFormat = new SimpleDateFormat("MM", Locale.KOREA);
        // curDayFormat = new SimpleDateFormat("dd", Locale.KOREA);
        mCal = Calendar.getInstance();
        // selected 초기화
        selectedCal = mCal;
        selectedDate = mCal.get(Calendar.DAY_OF_MONTH);

        //현재 날짜 텍스트뷰에 뿌려줌
        tvMonth.setText(curMonthFormat.format(date)+"월");
        tvYear.setText(curYearFormat.format(date) + "년");

        //gridview 요일 표시
        dayOfWeekList =  new ArrayList<>();
        dayOfWeekList.add("일");
        dayOfWeekList.add("월");
        dayOfWeekList.add("화");
        dayOfWeekList.add("수");
        dayOfWeekList.add("목");
        dayOfWeekList.add("금");
        dayOfWeekList.add("토");

        dayOfWeekGridAdapter = new DayOfWeekGridAdapter(getApplicationContext(), dayOfWeekList,mCal);
        gridViewDayOfWeek.setAdapter(dayOfWeekGridAdapter);

        myPagerAdapter = new MyPagerAdapter
                (getSupportFragmentManager());

        viewPager.setAdapter(myPagerAdapter);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position != 0) {
                    tvYear.setText((2018 + (position / 12)) + "년");
                    tvMonth.setText(String.format("%02d"+"월",(position%12+1)));
                }else{
                    tvYear.setText("2018년");
                    tvMonth.setText("01월");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        setToday(viewPager);// 오늘 날짜를 첫 페이지로 설정

        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToday(viewPager);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddToDoActivity.class);
                startActivity(intent);
            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  intent = new Intent(MainActivity.this,ToDoListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setToday(ViewPager viewPager){
        int todayPosition=0;
        Calendar today  = Calendar.getInstance();
       todayPosition =  today.get(Calendar.YEAR)-2018 + today.get(Calendar.MONTH);
       viewPager.setCurrentItem(todayPosition);
    }

    class MyPagerAdapter extends FragmentStatePagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            // listview 어댑터 작성할 때 getView 메소드와 비슷
            return frags[position];
        }

        @Override
        public int getCount() {
            return frags.length;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        myPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(myPagerAdapter);
       viewPager.setCurrentItem(selectedCal.get(Calendar.MONTH));
    }
}
