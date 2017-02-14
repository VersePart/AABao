package com.aabao.adnroid.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Verse Part on 2017/2/8.
 * email: versepartwang@163.com
 */

public class AADataBaseManager {

    private final BillDataBaseHelper mHelper;

    public AADataBaseManager(Context context){
        mHelper = new BillDataBaseHelper(context);
    }

    public boolean add(String name, String money, String farCla, String ChildCla, String dateTime, String remark, String account){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BillDataBaseHelper.NAME, name);
        values.put(BillDataBaseHelper.MONEY, money);
        values.put(BillDataBaseHelper.FARTHER_CLASS, farCla);
        values.put(BillDataBaseHelper.CHILD_CLASS, ChildCla);
        values.put(BillDataBaseHelper.TIME_DATE, dateTime);
        values.put(BillDataBaseHelper.REMARK, remark);
        values.put(BillDataBaseHelper.LASE_ACCOUNT, account);
        long update = db.insert(BillDataBaseHelper.USER_TABLE, null, values);
        if (update > 0 ){
            return true;
        }
        return false;
    }

    public Cursor query(String table, String orderBy){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        Cursor cursor = db.query(table, null, null, null, null, null, orderBy, null);
        return cursor;
    }

    public void delete(String table, String id){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(table, "_id = ?", new String[]{id});
    }

}
