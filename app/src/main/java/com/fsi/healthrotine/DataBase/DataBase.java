package com.fsi.healthrotine.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.v4.app.NavUtils;

import com.fsi.healthrotine.Models.MedicalAppointment;

import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;

public class DataBase extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DBNAME = "db_HealthRotine";

    private static final String TB_MEDICALAPPOINTMENT = "tb_medicalAppointment";
    private static final String ID_COLUMN = "id";
    private static final String SPECIALTY_COLUMN = "specialty";
    private static final String DATE_COLUMN = "date";
    private static final String TIME_COLUMN = "time";
    private static final String COMMENTS_COLUMN = "comments";


    public DataBase(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String QUERRY_MEDICALAPPOINTMENT = "CREATE TABLE " + TB_MEDICALAPPOINTMENT + "("
                + ID_COLUMN + " INTEGER PRIMARY KEY, "
                + SPECIALTY_COLUMN + " TEXT, "
                + DATE_COLUMN + " TEXT, "
                + TIME_COLUMN + " TEXT, "
                + COMMENTS_COLUMN + " TEXT)";

        db.execSQL(QUERRY_MEDICALAPPOINTMENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addMedicalAppointment(MedicalAppointment medicalAppointment){
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        ContentValues values = new ContentValues();
        values.put(SPECIALTY_COLUMN, medicalAppointment.getSpecialty());
        values.put(DATE_COLUMN, dateFormat.format(medicalAppointment.getDate()));
        values.put(TIME_COLUMN, timeFormat.format(medicalAppointment.getTime()));
        values.put(COMMENTS_COLUMN, medicalAppointment.getComments());

        db.insert(TB_MEDICALAPPOINTMENT, null, values);
        db.close();
    }
}
