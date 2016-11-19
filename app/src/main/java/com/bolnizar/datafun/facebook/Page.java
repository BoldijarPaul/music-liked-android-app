package com.bolnizar.datafun.facebook;

import com.google.gson.annotations.SerializedName;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by BoldijarPaul on 19/11/2016.
 */
public class Page {
    public String name;
    public String id;
    @SerializedName("created_time")
    public Date date;

    public Page(String name, String id, Date date) {
        this.name = name;
        this.id = id;
        this.date = date;
    }

    public Page() {
    }

    public static List<Page> cursorToPages(Cursor cursor) {
        List<Page> pages = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                long date = cursor.getLong(2);
                pages.add(new Page(name, id, new Date(date)));

            } while (cursor.moveToNext());
        }
        return pages;
    }
}
