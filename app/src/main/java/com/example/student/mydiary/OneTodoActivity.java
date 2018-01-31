package com.example.student.mydiary;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;


public class OneTodoActivity extends Activity {
    private Button delete,update,textColor;
    private EditText editTitle,editDate,editCategory,editContents;
    private DiaryDBHelper helper;
    private int mDefaultColor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_todo);

        delete = findViewById(R.id.btn_delete_read);
        update = findViewById(R.id.btn_update_read);

        editTitle = findViewById(R.id.edit_title_read);
        textColor = findViewById(R.id.selected_text_color_read);
        editDate =findViewById(R.id.edit_date_read);
        editCategory = findViewById(R.id.edit_category_read);
        editContents = findViewById(R.id.edit_memo_read);


        Intent intent = getIntent();
        final ToDoVO oneTodo = (ToDoVO) intent.getParcelableExtra("oneTodo");

        helper=new DiaryDBHelper(this);

        mDefaultColor = Integer.parseInt(oneTodo.getTextColor());

        editTitle.setText(oneTodo.getTitle());
        textColor.setBackgroundColor(mDefaultColor);
        textColor.setText(mDefaultColor+"");
        editCategory.setText(oneTodo.getCategory());
        editDate.setText(oneTodo.getDate());
        editContents.setText(oneTodo.getContents());

        textColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorSelectDialog();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delteDialog(oneTodo.getToDoNum()).show();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTitle.getText().length() > 0) {

                    ToDoVO toDoVO = new ToDoVO();
                    toDoVO.setToDoNum(oneTodo.getToDoNum());
                    toDoVO.setTitle(editTitle.getText().toString());
                    toDoVO.setDate(editDate.getText().toString().trim());
                    toDoVO.setTextColor(textColor.getText().toString().trim());
                    toDoVO.setContents(editContents.getText().toString());
                    toDoVO.setCategory(editCategory.getText().toString());

                    updateDialog(toDoVO).show();
                }else{
                    Toast.makeText(getApplicationContext(),"제목을 입력해 주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public Dialog delteDialog(final int todoNum){
        AlertDialog.Builder builder=  new AlertDialog.Builder(this);

        Dialog dialog =
                builder.setTitle("일정 삭제").setMessage("정말 삭제하시겠습니까?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                helper.deleteDialry(todoNum);
                                OneTodoActivity.this.finish();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create();
        return dialog;
    }

    public Dialog updateDialog(final ToDoVO toDoVO){
        AlertDialog.Builder builder=  new AlertDialog.Builder(this);

        Dialog dialog =
                builder.setTitle("일정 수정").setMessage("변경사항을 저장하시겠습니까?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                helper.updateDiary(toDoVO);
                                OneTodoActivity.this.finish();
                                finish();
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create();
        return dialog;
    }

    public void colorSelectDialog(){
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
                textColor.setBackgroundColor(mDefaultColor);
                textColor.setText(mDefaultColor+"");
            }
        });
        colorPicker.show();
    }
}
