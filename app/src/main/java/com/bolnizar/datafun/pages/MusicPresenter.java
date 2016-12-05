package com.bolnizar.datafun.pages;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bolnizar.datafun.MainApp;
import com.bolnizar.datafun.Presenter;
import com.bolnizar.datafun.facebook.Page;

/**
 * Created by BoldijarPaul on 19/11/2016.
 */
public class MusicPresenter extends Presenter<MusicView> {

    public MusicPresenter(MusicView musicView) {
        super(musicView);
    }

    public void loadPages() {
        String sql = "select * from page";
        SQLiteDatabase database = MainApp.getSqlHelper().getReadableDatabase();
        Log.d("sql", sql);
        Cursor cursor = database.rawQuery(sql, null);
        getView().showPages(Page.cursorToPages(cursor));
        cursor.close();
        database.close();
    }

    public void loadPages(long mBeforeDate, long mAfterDate, String mContains, int spinnerIndex) {
        String sql = "select * from page where name like '%" + mContains + "%'";
        if (mAfterDate != 0) {
            sql += " and date > " + mAfterDate;
        }
        if (mBeforeDate != 0) {
            sql += " and date < " + mBeforeDate;
        }
        if (spinnerIndex == 1) {
            sql += " and category = 'music'";
        } else if (spinnerIndex == 2) {
            sql += " and category = 'books'";
        }
        Log.d("sql", sql);
        SQLiteDatabase database = MainApp.getSqlHelper().getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);
        getView().showPages(Page.cursorToPages(cursor));
        cursor.close();
        database.close();
    }
}
