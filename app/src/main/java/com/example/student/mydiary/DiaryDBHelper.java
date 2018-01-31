package com.example.student.mydiary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by student on 2017-12-27.
 */

public class DiaryDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "my_diary.db";
    private static final int DB_VERSION = 4;

    private SQLiteDatabase db;

    public DiaryDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

//        db = getWritableDatabase();
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블 생성 SQL
        String sql = "CREATE TABLE IF NOT EXISTS TODO_TABLE("
                    +"TODO_NUMBER	INTEGER PRIMARY KEY AUTOINCREMENT,"
                    +"TITLE TEXT,"
                    +"DATE	TEXT NOT NULL,"
                    +"CONTENTS	TEXT,"
                    +"CATEGORY	TEXT NOT NULL,"
                    +"TEXT_COLOR TEXT NOT NULL"
                    +");";
        db.execSQL(sql);

        String sql2 = "CREATE TABLE IF NOT EXISTS CATEGORY_TABLE("
                    +"CATEGORY TEXT"
                    +");";
        db.execSQL(sql2);

        db.execSQL("INSERT INTO CATEGORY_TABLE VALUES('선택안함');");
        db.execSQL("INSERT INTO CATEGORY_TABLE VALUES('일정');");
        db.execSQL("INSERT INTO CATEGORY_TABLE VALUES('할일');");
        db.execSQL("INSERT INTO CATEGORY_TABLE VALUES('일기');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TODO_TABLE");
        db.execSQL("DROP TABLE IF EXISTS CATEGORY_TABLE");
        onCreate(db);
    }
/////////////////////////////////////////////////////////
    public void insertDiary(ToDoVO toDoVO){
        ContentValues values = new ContentValues();
        values.put("TITLE", toDoVO.getTitle());
        values.put("DATE",toDoVO.getDate());
        values.put("CONTENTS",toDoVO.getContents());
        values.put("CATEGORY",toDoVO.getCategory());
        values.put("TEXT_COLOR",toDoVO.getTextColor());
        db.insert("TODO_TABLE", null, values);
    }
    public void insertCategory(String category){
        db.execSQL("INSERT INTO CATEGORY_TABLE VALUES('"+category+"');");
    }

    public ArrayList<String> selectCategoryList(){
        String sql = "SELECT * FROM CATEGORY_TABLE";
        Cursor cursor = db.rawQuery(sql,null);
        ArrayList<String> groupList= new ArrayList<>();
        while(cursor.moveToNext()){
            groupList.add(cursor.getString(0));
        }
        return groupList;
    }

    public ArrayList<String> selectDateList(){
        String sql = "SELECT DATE FROM TODO_TABLE GROUP BY DATE ORDER BY DATE"; // 중복 제거
        Cursor cursor = db.rawQuery(sql,null);
        ArrayList<String> dateList= new ArrayList<>();
        while(cursor.moveToNext()){
            dateList.add(cursor.getString(0));
        }
        return dateList;
    }

    public ArrayList<String> selectDateListByMonth(String year,String month){
        String sql = "SELECT DATE FROM TODO_TABLE WHERE DATE LIKE '"+year+"-"+month+"-%'GROUP BY DATE ORDER BY DATE"; // 중복 제거
        Cursor cursor = db.rawQuery(sql,null);
        ArrayList<String> dateList= new ArrayList<>();
        while(cursor.moveToNext()){
            dateList.add(cursor.getString(0));
        }
        return dateList;
    }

    public ArrayList<ToDoVO> selectToDoListByDate(String date){
        String sql = "SELECT TODO_NUMBER, TITLE, DATE ,CONTENTS ,CATEGORY , TEXT_COLOR "
                +" FROM TODO_TABLE where date ='"+date+"' ORDER BY DATE";

        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<ToDoVO> TodoVOList = new ArrayList<>();

        while(cursor.moveToNext()){
            // 칼럼 번호 인덱스는 0부터 시작
            ToDoVO toDoVO = new ToDoVO();
            toDoVO.setToDoNum(cursor.getInt(0));
            toDoVO.setTitle(cursor.getString(1));
            toDoVO.setDate(cursor.getString(2));
            toDoVO.setContents(cursor.getString(3));
            toDoVO.setCategory(cursor.getString(4));
            toDoVO.setTextColor(cursor.getString(5));

            TodoVOList.add(toDoVO);
        }
        return TodoVOList;
    }

    public void updateDiary(ToDoVO toDoVO){
        ContentValues values = new ContentValues();
        values.put("TITLE", toDoVO.getTitle());
        values.put("DATE",toDoVO.getDate());
        values.put("CONTENTS",toDoVO.getContents());
        values.put("CATEGORY",toDoVO.getCategory());
        values.put("TEXT_COLOR",toDoVO.getTextColor());
        db.update("TODO_TABLE", values, "TODO_NUMBER="+toDoVO.getToDoNum(),null);
    }
    public void deleteDialry(int todoNum){
        db.delete("TODO_TABLE","TODO_NUMBER="+todoNum,null);
    }

    public ArrayList<ToDoVO> selectToDoListByCategory(String category){
        String sql = "SELECT TODO_NUMBER, TITLE, CATEGORY,CONTENTS,DATE, TEXT_COLOR "
                +" FROM TODO_TABLE WHERE CATEGORY ='"+category+"' ORDER BY DATE";

        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<ToDoVO> TodoVOList = new ArrayList<>();

        while(cursor.moveToNext()){
            // 칼럼 번호 인덱스는 0부터 시작
            ToDoVO toDoVO = new ToDoVO();
            toDoVO.setToDoNum(cursor.getInt(0));
            toDoVO.setTitle(cursor.getString(1));
            toDoVO.setDate(cursor.getString(2));
            toDoVO.setContents(cursor.getString(3));
            toDoVO.setCategory(cursor.getString(4));
            toDoVO.setTextColor(cursor.getString(5));

            TodoVOList.add(toDoVO);
        }
        return TodoVOList;
    }
    public ToDoVO selectOneToDo(int todoNum){
        String sql = "SELECT TODO_NUMBER, TITLE, DATE ,CONTENTS ,CATEGORY , TEXT_COLOR "
                +" FROM TODO_TABLE where TODO_NUMBER ="+todoNum+" ORDER BY DATE";

        Cursor cursor = db.rawQuery(sql, null);
        ToDoVO todoOne = new ToDoVO();

        while(cursor.moveToNext()){
            // 칼럼 번호 인덱스는 0부터 시작
            todoOne.setToDoNum(cursor.getInt(0));
            todoOne.setTitle(cursor.getString(1));
            todoOne.setDate(cursor.getString(2));
            todoOne.setContents(cursor.getString(3));
            todoOne.setCategory(cursor.getString(4));
            todoOne.setTextColor(cursor.getString(5));

        }
        return todoOne;
    }
}
