package com.fsi.healthrotine.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import static com.fsi.healthrotine.DataBase.Columns.*;
import static com.fsi.healthrotine.DataBase.Queries.*;

public class DataBase extends SQLiteOpenHelper {
    private static final int VERSION = 11;
    private static final String DBNAME = "db_HealthRotine";

    public DataBase(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MEDICALAPPOINTMENT_QUERY);
        db.execSQL(MEDICINE_QUERY);
        db.execSQL(SPECIALIST_QUERY);
        db.execSQL(PATIENT_QUERY);
        db.execSQL(VACCINE_QUERY);
        db.execSQL(EXAM_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL(DROP3);
            db.execSQL(EXAM_QUERY);

        }
    }

    public void deleteFromTable(String table, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table,ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
    public Cursor getTableCursor(String table){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + table, null);
        return cursor;
    }
    public Cursor getTableCursorForId(String table, int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM " + table + " WHERE " + ID + " = " + id, null);
        return cursor;
    }
    public int insertOnTable(String table, ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();
        Long id = db.insert(table, null, values);
        db.close();

        return id.intValue();
    }
    public void updateTable(String table, ContentValues values, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(table, values, ID + " = ? ", new String[]{String.valueOf(id)});
        db.close();
    }
    public Cursor getExamsFilePaths(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT "+ FILE_LOCATION +  " FROM " + TB_EXAM, null);
        return cursor;
    }
}
