package com.example.student.mydiary;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

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
        Dialog custom = new Dialog(this);

        return custom;
    }

}
