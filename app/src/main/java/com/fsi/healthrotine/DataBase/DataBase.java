package com.fsi.healthrotine.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.v4.app.NavUtils;

import com.fsi.healthrotine.Models.MedicalAppointment;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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

    public void deleteMedicalAppointment(MedicalAppointment medicalAppointment){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TB_MEDICALAPPOINTMENT, ID_COLUMN + " = ?", new String[]{String.valueOf(medicalAppointment.getId())});
        db.close();
    }

    public MedicalAppointment getMedicalAppointment(MedicalAppointment _medicalAppointment){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TB_MEDICALAPPOINTMENT, new String[] {ID_COLUMN, SPECIALTY_COLUMN, DATE_COLUMN, TIME_COLUMN, COMMENTS_COLUMN},
                ID_COLUMN + " = ?",
                new String[] {String.valueOf(_medicalAppointment.getId())}, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date = new Date(dateFormat.parse(cursor.getString(2)).getTime());
            Time time = new Time(timeFormat.parse(cursor.getString(3)).getTime());

            MedicalAppointment medicalAppointment = new MedicalAppointment(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                    date, time, cursor.getString(4));
            return medicalAppointment;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<MedicalAppointment> getAllMedicalAppointments(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<MedicalAppointment> medicalAppointments = new ArrayList<MedicalAppointment>();
        String query = "SELECT * FROM " + TB_MEDICALAPPOINTMENT;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                try {
                    MedicalAppointment medicalAppointment = new MedicalAppointment();
                    medicalAppointment.setId(Integer.parseInt(cursor.getString(0)));
                    medicalAppointment.setSpecialty(cursor.getString(1));

                    Date date = new Date(dateFormat.parse(cursor.getString(2)).getTime());
                    Time time = new Time(timeFormat.parse(cursor.getString(3)).getTime());

                    medicalAppointment.setDate(date);
                    medicalAppointment.setTime(time);
                    medicalAppointment.setComments(cursor.getString(4));

                    medicalAppointments.add(medicalAppointment);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }

            } while (cursor.moveToNext());
        }

        return medicalAppointments;
    }

    public void updateMedicalAppointment(MedicalAppointment medicalAppointment){
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        ContentValues values = new ContentValues();
        values.put(SPECIALTY_COLUMN, medicalAppointment.getSpecialty());
        values.put(DATE_COLUMN, dateFormat.format(medicalAppointment.getDate()));
        values.put(TIME_COLUMN, timeFormat.format(medicalAppointment.getTime()));
        values.put(COMMENTS_COLUMN, medicalAppointment.getComments());

        db.update(TB_MEDICALAPPOINTMENT, values, ID_COLUMN + " = ? ", new String[]{String.valueOf(medicalAppointment.getId())});
    }

}
