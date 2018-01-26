package com.example.student.mydiary;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by student on 2018-01-17.
 */

public class ToDoVO implements Parcelable {
    private int toDoNum;
    private String title;
    private String textColor;
    private String date;
    private String contents;
    private String category;

    public ToDoVO(){

    }
    public ToDoVO(int toDoNum,
             String title,
             String textColor,
             String date,
             String contents,
             String category){

    }
    public ToDoVO(Parcel s){
        this.toDoNum = s.readInt();
        this.title = s.readString();
        this.textColor=s.readString();
        this.date=s.readString();
        this.contents=s.readString();
        this.category= s.readString();
    }

    public String getTitle() {
        return title;
    }

    public int getToDoNum() {
        return toDoNum;
    }

    public String getCategory() {
        return category;
    }

    public String getContents() {
        return contents;
    }

    public String getDate() {
        return date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setToDoNum(int toDoNum) {
        this.toDoNum = toDoNum;
    }
    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }
    public String getTextColor() {
        return textColor;
    }

    @Override
    public String toString() {
        return "ToDoVO{" +
                "toDoNum=" + toDoNum +
                ", title='" + title + '\'' +
                ", textColor='" + textColor + '\'' +
                ", date='" + date + '\'' +
                ", contents='" + contents + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.toDoNum);
        dest.writeString(this.title);
        dest.writeString(this.textColor);
        dest.writeString(this.date);
        dest.writeString(this.contents);
        dest.writeString(this.category);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ToDoVO createFromParcel(Parcel in) {
            return new ToDoVO(in);
        }

        public ToDoVO[] newArray(int size) {
            return new ToDoVO[size];
        }
    };
}
