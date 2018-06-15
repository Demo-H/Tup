package com.tupperware.huishengyi.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by dhunter on 2018/3/19.
 */

public class SearchHelper extends SQLiteOpenHelper {

    public SearchHelper(Context context) {
        super(context, "search.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists search(_id integer primary key autoincrement,name varchar(20))");
        for (int x=0;x<2;x++){
            db.execSQL("insert into search(name) values(?)",new String[]{"1234232323243"+x});
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
