package com.example.student.mydiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by student on 2018-01-17.
 */

public class ToDoListActivity extends Activity {
    private ExpandableAdapter expandableAdapter;
    private ArrayList<String> groupList;
    private HashMap<String, ArrayList<ToDoVO>> childList;
    private ExpandableListView expandableListView;
    private DiaryDBHelper helper;
    private Button btnSort;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);


        btnSort = findViewById(R.id.btn_sort);

        expandableListView = findViewById(R.id.expandable_listview);
        ListDataByDate();

        expandableAdapter = new ExpandableAdapter(this, groupList, childList);
        expandableListView.setAdapter(expandableAdapter);

        for (int i = 0; i < expandableAdapter.getGroupCount(); i++) {
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(ToDoListActivity.this,OneTodoActivity.class);
                intent.putExtra("oneTodo",helper.selectOneToDo(expandableAdapter.getChild(groupPosition,childPosition).getToDoNum()));
                startActivity(intent);
                return false;
            }
        });

        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ToDoListActivity.this, v);

                popupMenu.getMenuInflater().inflate(R.menu.popup_memu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String title = item.getTitle() + "";
                        Log.d("popup", title);
                        if (title.equals("날짜순")) {
                            ListDataByDate();
                        } else {
                            ListDataByCategory();
                        }
                        expandableAdapter = new ExpandableAdapter(getApplicationContext(), groupList, childList);
                        expandableListView.setAdapter(expandableAdapter);
                        for (int i = 0; i < expandableAdapter.getGroupCount(); i++) {
                            expandableListView.expandGroup(i);
                        }
                        return false;
                    }

                });
                popupMenu.show();
            }
        });

    }

    private void ListDataByDate() {
        groupList = new ArrayList<>();
        childList = new HashMap<>();

        helper = new DiaryDBHelper(this);

        groupList = helper.selectDateList();

        for (int i = 0; i < groupList.size(); i++) { // 날짜별 리스트 불러와서 child Map에 담기
            ArrayList<ToDoVO> toDoVOList = helper.selectToDoListByDate(groupList.get(i));
            childList.put(groupList.get(i), toDoVOList);
        }
    }

    private void ListDataByCategory() {
        groupList = new ArrayList<>();
        childList = new HashMap<>();

        helper = new DiaryDBHelper(this);

        groupList = helper.selectCategoryList();

        for (int i = 0; i < groupList.size(); i++) { // 날짜별 리스트 불러와서 child Map에 담기
            ArrayList<ToDoVO> toDoVOList = helper.selectToDoListByCategory(groupList.get(i));
            childList.put(groupList.get(i), toDoVOList);
        }
    }

}

