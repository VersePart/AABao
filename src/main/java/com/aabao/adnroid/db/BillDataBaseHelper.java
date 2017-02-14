package com.aabao.adnroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Verse Part on 2017/2/7.
 * email: versepartwang@163.com
 */

public class BillDataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "launcher.db";
    public static final String USER_TABLE = "user";
    private static final int VERSION_DB = 1;
    public static final String NAME = "name";
    public static final String MONEY = "money";
    public static final String FARTHER_CLASS = "farther_class";
    public static final String CHILD_CLASS = "child_class";
    public static final String TIME_DATE = "time_date";
    public static final String REMARK = "remark";
    public static final String LASE_ACCOUNT = "lase_account";
    public static final String ACCOUNT_MARK = "yes";

    public BillDataBaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ USER_TABLE + " (_id integer primary key autoincrement, "+ NAME + " TEXT,"+ MONEY + " TEXT,"
        + FARTHER_CLASS + " TEXT," + CHILD_CLASS + " TEXT," + TIME_DATE + " TEXT," + REMARK + " TEXT," + LASE_ACCOUNT + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
