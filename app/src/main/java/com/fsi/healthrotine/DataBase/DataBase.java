package com.fsi.healthrotine.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.v4.app.NavUtils;

import com.fsi.healthrotine.Models.Entity;
import com.fsi.healthrotine.Models.MedicalAppointment;
import com.fsi.healthrotine.Models.Medicine;

import java.util.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.SimpleTimeZone;

import static com.fsi.healthrotine.DataBase.Columns.*;
import static com.fsi.healthrotine.DataBase.Queries.*;

public class DataBase extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DBNAME = "db_HealthRotine";

    public DataBase(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MEDICALAPPOINTMENT_QUERY);
        db.execSQL(MEDICINE_QUERY);
        db.execSQL(PATIENT_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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
    public void insertOnTable(String table, ContentValues values){
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(table, null, values);
        db.close();
    }
    public void updateTable(String table, ContentValues values, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(table, values, ID + " = ? ", new String[]{String.valueOf(id)});
        db.close();
    }
}
