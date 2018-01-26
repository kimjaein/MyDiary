package com.example.student.mydiary;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by student on 2018-01-17.
 */

public class ExpandableAdapter extends BaseExpandableListAdapter {
    private  ArrayList<String> arrayList;
    private HashMap<String,ArrayList<ToDoVO>> hashMap;
    private LayoutInflater inflater;
    private Context context;

   public ExpandableAdapter(Context context, ArrayList<String> arrayList, HashMap<String,ArrayList<ToDoVO>> hashMap){
       this.arrayList = arrayList;
       this.hashMap= hashMap;
       this.context=context;
       this.inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   }

    @Override
    public int getGroupCount() {
        return arrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return hashMap.get(arrayList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.arrayList.get(groupPosition);
    }

    @Override
    public ToDoVO getChild(int groupPosition, int childPosition) {
        return this.hashMap.get(this.arrayList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
       if(convertView == null){
           convertView = inflater.from(context).inflate(android.R.layout.simple_expandable_list_item_1,parent,false);
       }
        TextView textView = convertView.findViewById(android.R.id.text1);
       textView.setText(getGroup(groupPosition).toString());

       return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.from(context).inflate(R.layout.item_todo_list_child,parent,false);
        }
        Button textColor = convertView.findViewById(R.id.item_text_color);
        TextView textTitle = convertView.findViewById(R.id.item_title);
        TextView textCategory = convertView.findViewById(R.id.item_category);
        if(getGroupCount() == 0 ){
            textTitle.setText("일정이 없습니다.");
        }else if(childPosition == hashMap.get(getGroup(groupPosition)).size()) {
        }else{
            textTitle.setText(getChild(groupPosition, childPosition).getTitle());
            textColor.setBackgroundColor(Integer.parseInt(getChild(groupPosition, childPosition).getTextColor()));
            textCategory.setText(getChild(groupPosition, childPosition).getCategory());
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
