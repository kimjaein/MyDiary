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
    private static final int DB_VERSION = 2;

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

        String sql2 = "CREATE TABLE IF NOT EXISTS GROUP_TABLE("
                    +"GROUP_NAME TEXT"
                    +");";
        db.execSQL(sql2);
        db.execSQL("INSERT INTO GROUP_TABLE VALUES('일정');");
        db.execSQL("INSERT INTO GROUP_TABLE VALUES('할일');");
        db.execSQL("INSERT INTO GROUP_TABLE VALUES('일기');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TODO_TABLE");
        db.execSQL("DROP TABLE IF EXISTS GROUP_TABLE");
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
    public void insert(String category){
        db.execSQL("INSERT INTO GROUP_TABLE VALUES('"+category+"');");
    }

    public ArrayList<String> selectGroupList(){
        String sql = "SELECT * FROM GROUP_TABLE ";
        Cursor cursor = db.rawQuery(sql,null);
        ArrayList<String> groupList= new ArrayList<>();
        while(cursor.moveToNext()){
            groupList.add(cursor.getString(0));
        }
        return groupList;
    }

    public ArrayList<ToDoVO> selectToDoList(String date){
        String sql = "SELECT TODO_NUMBER, TITLE, DATE ,CONTENTS ,CATEGORY , TEXT_COLOR "
                +" FROM TODO_TABLE WHERE DATE='"+date+"'";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<ToDoVO> TodoVOList = new ArrayList<>();
        Log.d("msg db","while 전"+date);
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
            Log.d("msg db",date + " / "+toDoVO.toString());
        }
        return TodoVOList;
    }

    public ArrayList<String> selectToDoListByDate(){
        String sql = "SELECT DATE FROM TODO_TABLE GROUP BY DATE"; // 중복 제거
        Cursor cursor = db.rawQuery(sql,null);
        ArrayList<String> dateList= new ArrayList<>();
        while(cursor.moveToNext()){
            dateList.add(cursor.getString(0));
        }
        return dateList;
    }
    public ArrayList<ToDoVO> selectToDoListAll(){
        String sql = "SELECT TODO_NUMBER, TITLE, DATE ,CONTENTS ,CATEGORY , TEXT_COLOR "
                +" FROM TODO_TABLE";
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
}
