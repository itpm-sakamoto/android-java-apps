package com.example.monster_picture_book;

import androidx.annotation.NonNull;

public class MonsterDataItem {

    private int id;
    private String no;
    private String name;

    public MonsterDataItem(int id, String no, String name) {
        this.id = id;
        this.no = no;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return this.id + ", " + this.no + ", " + this.name;
    }
}
