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
import com.fsi.healthrotine.Models.Medicine;

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

    private static final String TB_MEDICINE = "tb_medicine";
    private static final String END_DATE_COLUMN = "endDate";
    private static final String DURATION_COLUMN = "duration";
    private static final String FREQUENCY_COLUMN = "frequency";
    private static final String FREQUENCY_UNITY_COLUMN = "frequencyUnity";
    private static final String TYPE_COLUMN = "type";
    private static final String DOSAGE_COLUMN = "dosage";


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

        String QUERRY_MEDICINE = "CREATE TABLE " + TB_MEDICINE + "("
                + ID_COLUMN + " INTEGER PRIMARY KEY, "
                + DATE_COLUMN + " TEXT, "
                + END_DATE_COLUMN + " TEXT, "
                + TIME_COLUMN + " TEXT, "
                + DURATION_COLUMN + " TEXT, "
                + FREQUENCY_COLUMN + " TEXT, "
                + FREQUENCY_UNITY_COLUMN + " TEXT, "
                + TYPE_COLUMN + " TEXT, "
                + DOSAGE_COLUMN + " TEXT, "
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

    public void addMedicine(Medicine medicine){
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        ContentValues values = new ContentValues();
        values.put(DATE_COLUMN, dateFormat.format(medicine.getDate()));
        values.put(END_DATE_COLUMN, dateFormat.format(medicine.getEndDate()));
        values.put(TIME_COLUMN, timeFormat.format(medicine.getTime()));
        values.put(DURATION_COLUMN, timeFormat.format(medicine.getDuration()));
        values.put(FREQUENCY_COLUMN, timeFormat.format(medicine.getFrequency()));
        values.put(FREQUENCY_UNITY_COLUMN, timeFormat.format(medicine.getFrequencyUnity()));
        values.put(TYPE_COLUMN, timeFormat.format(medicine.getType()));
        values.put(DOSAGE_COLUMN, timeFormat.format(medicine.getDosage()));
        values.put(COMMENTS_COLUMN, medicine.getComments());

        db.insert(TB_MEDICINE, null, values);
        db.close();
    }



    public void deleteMedicalAppointment(MedicalAppointment medicalAppointment){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TB_MEDICALAPPOINTMENT, ID_COLUMN + " = ?", new String[]{String.valueOf(medicalAppointment.getId())});
        db.close();
    }

    public void deleteMedicine(MedicalAppointment medicine){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TB_MEDICINE,ID_COLUMN + " = ?", new String[]{String.valueOf(medicine.getId())});
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

    public List<Medicine> getAllMedicines(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Medicine> medicines = new ArrayList<Medicine>();
        String query = "SELECT * FROM " + TB_MEDICINE;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                try {
                    Medicine medicine = new Medicine();
                    medicine.setId(Integer.parseInt(cursor.getString(0)));

                    Date date = new Date(dateFormat.parse(cursor.getString(1)).getTime());
                    Date endDate = new Date(dateFormat.parse(cursor.getString(2)).getTime());
                    Time time = new Time(timeFormat.parse(cursor.getString(3)).getTime());

                    medicine.setDate(date);
                    medicine.setEndDate(endDate);
                    medicine.setTime(time);
                    medicine.setDuration(Integer.parseInt(cursor.getString(4)));
                    medicine.setFrequency(Integer.parseInt(cursor.getString(5)));
                    medicine.setFrequencyUnity(cursor.getString(6));
                    medicine.setType(cursor.getString(7));
                    medicine.setDosage(cursor.getString(8));
                    medicine.setComments(cursor.getString(9));

                    medicines.add(medicine);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }

            } while (cursor.moveToNext());
        }

        return medicines;
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

    public void updateMedicine(Medicine medicine){
        SQLiteDatabase db = this.getWritableDatabase();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        ContentValues values = new ContentValues();
        values.put(DATE_COLUMN, dateFormat.format(medicine.getDate()));
        values.put(END_DATE_COLUMN, dateFormat.format(medicine.getEndDate()));
        values.put(TIME_COLUMN, timeFormat.format(medicine.getTime()));
        values.put(DURATION_COLUMN, timeFormat.format(medicine.getDuration()));
        values.put(FREQUENCY_COLUMN, timeFormat.format(medicine.getFrequency()));
        values.put(FREQUENCY_UNITY_COLUMN, timeFormat.format(medicine.getFrequencyUnity()));
        values.put(TYPE_COLUMN, timeFormat.format(medicine.getType()));
        values.put(DOSAGE_COLUMN, timeFormat.format(medicine.getDosage()));
        values.put(COMMENTS_COLUMN, medicine.getComments());

        db.update(TB_MEDICINE, values, ID_COLUMN + " = ? ", new String[]{String.valueOf(medicine.getId())});
    }

}
