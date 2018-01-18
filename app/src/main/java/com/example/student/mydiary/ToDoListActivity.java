package com.example.student.mydiary;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by student on 2018-01-17.
 */

public class ToDoListActivity extends Activity {
    private ExpandableAdapter expandableAdapter;
    private ArrayList<String> groupList;
    private HashMap<String,ArrayList<ToDoVO>> childList;
    private ExpandableListView expandableListView;
    private DiaryDBHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);

        expandableListView =  findViewById(R.id.expandable_listview);
        ListData();

        expandableAdapter =  new ExpandableAdapter(this,groupList,childList);
        expandableListView.setAdapter(expandableAdapter);

        for(int i=0;i<expandableAdapter.getGroupCount();i++){
            expandableListView.expandGroup(i);
        }
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                return false;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                return false;
            }
        });

    }
    private void ListData(){
        groupList =  new ArrayList<>();
        childList =  new HashMap<>();

        helper =new DiaryDBHelper(this);
        groupList = helper.selectToDoListByDate();

        for(int i=0;i<groupList.size();i++){ // 날짜별 리스트 불러와서 child Map에 담기
            ArrayList<ToDoVO> toDoVOList = helper.selectToDoList(groupList.get(i));
            childList.put(groupList.get(i),toDoVOList);
        }
    //    childList.put(groupList.get(0),helper.selectToDoListAll());
        ArrayList<ToDoVO> toDoVOList =helper.selectToDoListAll();
        for(ToDoVO list : toDoVOList){
        }
    }
}
