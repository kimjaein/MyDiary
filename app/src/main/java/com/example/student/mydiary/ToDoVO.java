package com.example.student.mydiary;

/**
 * Created by student on 2018-01-17.
 */

public class ToDoVO {
    private int toDoNum;
    private String title;
    private String textColor;
    private String date;
    private String contents;
    private String category;

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
}
