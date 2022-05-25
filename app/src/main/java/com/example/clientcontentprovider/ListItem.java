package com.example.clientcontentprovider;

import android.net.Uri;

public class ListItem {
    public int uid;
    public String color;
    public String firstName;
    public String lastName;

    public ListItem(int uid, String color, String firstName, String lastName) {
        this.uid = uid;
        this.color = color;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static final Uri CONTENT_URI = Uri.parse("content://com.example.basededatoslocalconroom.provider/user");
    public static final String[] COLUMNS_NAME = new String[]{
            "uid", "first_name", "last_name"
    };
    public static final String COLUMN_ID = "uid";
    public static final String COLUMN_FIRSTNAME = "first_name";
    public static final String COLUMN_LASTNAME = "last_name";
}
