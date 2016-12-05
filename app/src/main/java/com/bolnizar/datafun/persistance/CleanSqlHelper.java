package com.bolnizar.datafun.persistance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BoldijarPaul on 19/11/2016.
 */
public class CleanSqlHelper extends SQLiteOpenHelper {

    private static final String TABLES_CREATE =
            "create table page(" +
                    "id text primary key," +
                    "name text," +
                    "date integer," +
                    "category text)";

    private static final int VERSION_DATABASE = 1;

    public CleanSqlHelper(Context context) {
        super(context, "dtb", null, VERSION_DATABASE);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLES_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists page");
        onCreate(sqLiteDatabase);
    }
}
