package com.example.student.mydiary;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by student on 2018-01-12.
 */

public class CalendarFragment extends Fragment {

    private DayGridAdapter dayGridAdapter;
    private ArrayList<String> dayList;
    private GridView gridViewDay;
    private Calendar mCal;
    DiaryDBHelper helper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_calendar,container,false);

        int year = getArguments().getInt("year");
        int month= getArguments().getInt("month");
        Log.d("bundle",year + "/ "+month);
        helper = new DiaryDBHelper(getContext());

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
        //////////////////////갱신되어야하는부분////////////////////////////////////////////////////////////////////////////////
        ArrayList<String> dateList = helper.selectDateListByMonth(year+"",String.format("%02d",(month+1)));

        final HashMap<String, ArrayList<ToDoVO>>  todoListByDate = new HashMap<>();
        for(int i=0;i<dateList.size();i++){
            todoListByDate.put(dateList.get(i).substring(8),helper.selectToDoListByDate(dateList.get(i)));
        }
        dayGridAdapter = new DayGridAdapter(getContext(), dayList,mCal,todoListByDate);


        /////////////////////////////////////////////////////////////////////////////////////////////////////////////
        gridViewDay.setAdapter(dayGridAdapter);
        gridViewDay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MainActivity.selectedDate = Integer.parseInt(dayGridAdapter.getItem(position));
                MainActivity.selectedCal =mCal;
                if(todoListByDate.containsKey(dayGridAdapter.getItem(position))){
                    ArrayList<ToDoVO> list = todoListByDate.get(dayGridAdapter.getItem(position));
                    //dialog 띄우기
                    oneDayTodoListDialog(list,dayGridAdapter.getItem(position)).show();
                }
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

    public Dialog oneDayTodoListDialog(final ArrayList<ToDoVO> oneDayList, String day) {
        final Dialog custom = new Dialog(getContext());
        custom.setContentView(R.layout.dialog_todo_list);

        ListView listView = custom.findViewById(R.id.todo_list);
        TextView date= custom.findViewById(R.id.todo_dialog_day);
        TextView month =  custom.findViewById(R.id.todo_dialog_month);
        ImageButton btnAddTodo = custom.findViewById(R.id.todo_list_btn_add);

        final ArrayList<String> categoryList =helper.selectCategoryList();

        CalendarFragment.todoListAdapter adapter = new CalendarFragment.todoListAdapter(getContext(),R.layout.item_todo_listview,oneDayList);
        listView.setAdapter(adapter);

        date.setText(day+"일");
        Calendar Cal =Calendar.getInstance();
        Cal.set(MainActivity.selectedCal.get(Calendar.YEAR),MainActivity.selectedCal.get(Calendar.MONTH),Integer.parseInt(day));
        month.setText(dayOfWeek(Cal.get(Calendar.DAY_OF_WEEK)));

        btnAddTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddToDoActivity.class);
                startActivity(intent);
                custom.cancel();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),OneTodoActivity.class);
                intent.putExtra("oneTodo",oneDayList.get(position));
                startActivity(intent);
                custom.cancel();
            }
        });

        return custom;
    }

    class todoListAdapter  extends ArrayAdapter<ToDoVO> {
        private Context context;        // MainActiviy.this
        private int itemLayout;         // R.layout.my_item
        private List<ToDoVO> todoList;  // data 집합

        public todoListAdapter(@NonNull Context context, int resource, List<ToDoVO> objects) {
            super(context,resource,objects);
            this.context = context;
            this.itemLayout = resource;
            this.todoList = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            final CalendarFragment.todoListAdapter.MyItemHolder holder ;

            if(convertView == null) {
                Log.i("convert test", "null");
                // getView 메소드 처음 실행 시 inflate 함.
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                // my_item.xml 파일 팽창해서 객체화.false
                convertView = inflater.inflate(itemLayout, parent, false);

                holder = new CalendarFragment.todoListAdapter.MyItemHolder();
                holder.textColor = convertView.findViewById(R.id.item_text_color);
                holder.title = convertView.findViewById(R.id.item_title);
                holder.category = convertView.findViewById(R.id.item_category);

                // inflate결과 홀더에 기억시키기
                convertView.setTag(holder);
            }else{
                Log.i("convert test", "not null");
                // getView 메소드 첫실행 아닌경우 이전 inflate 재활용
                holder = (CalendarFragment.todoListAdapter.MyItemHolder)convertView.getTag();
            }

            holder.textColor.setBackgroundColor(Integer.parseInt(todoList.get(position).getTextColor()));
            holder.title.setText(todoList.get(position).getTitle());
            holder.category.setText(todoList.get(position).getCategory());

            return convertView;
        }

        class MyItemHolder{
            Button textColor;
            TextView title;
            TextView category;
        }
    }
    private String dayOfWeek(int what){
        switch (what){
            case 1:
                return "일요일";
            case 2:
                return "월요일";
            case 3:
                return "화요일";
            case 4:
                return "수요일";
            case 5:
                return "목요일";
            case 6:
                return "금요일";
            case 7:
                return "토요일";
        }
        return "";
    }
}
