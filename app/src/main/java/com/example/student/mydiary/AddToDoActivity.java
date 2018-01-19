package com.example.student.mydiary;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by student on 2018-01-15.
 */

public class AddToDoActivity extends Activity {
    private EditText editDate, editTitle, editMemo, editCategory;
    private Button btnCancle, btnSave;
    private TextView selectedTextColor;
    private DiaryDBHelper helper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        btnCancle = findViewById(R.id.btn_cancle);
        btnSave = findViewById(R.id.btn_save);
        editDate = findViewById(R.id.edit_date);
        editTitle = findViewById(R.id.edit_title);
        editMemo = findViewById(R.id.edit_memo);
        selectedTextColor = findViewById(R.id.selected_text_color);
        editCategory = findViewById(R.id.edit_category);

        helper = new DiaryDBHelper(this);

        editDate.setText(String.format(MainActivity.selectedCal.get(Calendar.YEAR) + "-" + "%02d" + "-" + MainActivity.selectedDate, (MainActivity.selectedCal.get(Calendar.MONTH) + 1)));
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDateDialog().show();
            }
        });
        editDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    makeDateDialog().show();
                }
            }
        }); //editText가 focus를 가진후에 클릭해야 클릭리스너가 뜸//두번눌러야함// 이를 해결하고자 focus가 잡힐때도 동일하게 작동하게 함

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToDoVO toDoVO = new ToDoVO();
                toDoVO.setTitle(editTitle.getText().toString());
                toDoVO.setDate(editDate.getText().toString().trim());
                Log.d("color", selectedTextColor.getText() + "");
                toDoVO.setTextColor(selectedTextColor.getText().toString().trim());
                toDoVO.setContents(editMemo.getText().toString());
                toDoVO.setCategory(editCategory.getText().toString());

                helper.insertDiary(toDoVO);
                finish();
            }
        });
        editCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectCategoryDialog().show();
            }
        });
        editCategory.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus== true){
                    selectCategoryDialog().show();
                }
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Dialog makeDateDialog() {
        int nowYear, nowMonth, nowDay;
        Calendar now = Calendar.getInstance();

        nowYear = now.get(Calendar.YEAR);
        nowMonth = now.get(Calendar.MONTH);
        nowDay = now.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                // 날짜 선택 완료
                editDate.setText(String.format(year + "-" + "%02d" + "-" + dayOfMonth, (month + 1)));
            }
        };

        DatePickerDialog dateDialog = new DatePickerDialog(this, listener, nowYear, nowMonth, nowDay);

        return dateDialog;
    }

    public Dialog selectCategoryDialog() {
        final Dialog custom = new Dialog(this);
        custom.setContentView(R.layout.dialog_category_list);

        ListView listView = custom.findViewById(R.id.category_list);
        Button btnAddCategory = custom.findViewById(R.id.btn_add_category);
        Button btnClose = custom.findViewById(R.id.btn_close_category);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custom.cancel();
            }
        });
        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCategory().show();
                custom.cancel();
            }
        });


        final ArrayList<String> categoryList =helper.selectCategoryList();

        CategoryListAdapter adapter = new CategoryListAdapter(this,android.R.layout.simple_expandable_list_item_1,categoryList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editCategory.setText(categoryList.get(position));
                custom.cancel();
            }
        });

        return custom;
    }
    public Dialog addCategory(){
        final Dialog custom = new Dialog(this);
        custom.setContentView(R.layout.dialog_add_category);

        final EditText editAddCategory = custom.findViewById(R.id.edit_add_category);
        Button btnSave = custom.findViewById(R.id.btn_add_category_save);
        Button btnClose = custom.findViewById(R.id.btn_add_category_close);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=editAddCategory.getText().toString().trim();
                if(str.length()>0) {
                    helper.insertCategory(editAddCategory.getText().toString().trim());
                    custom.cancel();
                }else{
                    Toast.makeText(getApplicationContext(),"한글자 이상 입력해 주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                custom.cancel();
            }
        });
        return custom;
    }
    public Dialog colorSelectDialog(){
        final Dialog custom =  new Dialog(this);
        Dialog
        return custom;
    }

}
