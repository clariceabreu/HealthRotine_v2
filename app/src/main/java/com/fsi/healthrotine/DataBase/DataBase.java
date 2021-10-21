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

    private MedicineTable medicineTable = new MedicineTable();
    private MedicalAppointmentTable medicalAppointmentTable = new MedicalAppointmentTable();

    public DataBase(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MEDICALAPPOINTMENT_QUERY);
        db.execSQL(MEDICINE_QUERY);
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

    public void addMedicalAppointment(MedicalAppointment medicalAppointment){
        insertOnTable(TB_MEDICALAPPOINTMENT, this.medicalAppointmentTable.add(medicalAppointment));
    }
    public void addMedicine(Medicine medicine){
        insertOnTable(TB_MEDICINE, this.medicineTable.add(medicine));
    }
    public void deleteMedicalAppointment(MedicalAppointment medicalAppointment){
        deleteFromTable(TB_MEDICALAPPOINTMENT, medicalAppointment.getId());
    }

    public void deleteMedicine(Medicine medicine){
        deleteFromTable(TB_MEDICINE, medicine.getId());
    }

    public MedicalAppointment getMedicalAppointment(MedicalAppointment _medicalAppointment){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TB_MEDICALAPPOINTMENT, new String[] {ID, SPECIALTY, DATE, TIME, COMMENTS},
                ID + " = ?",
                new String[] {String.valueOf(_medicalAppointment.getId())}, null, null, null, null);
        return this.medicalAppointmentTable.getOne(cursor);

    }

    public List<MedicalAppointment> getAllMedicalAppointments(){
        return this.medicalAppointmentTable.getAll(getTableCursor(TB_MEDICALAPPOINTMENT));
    }

    public List<Medicine> getAllMedicines(){
        return this.medicineTable.getAll(getTableCursor(TB_MEDICINE));
    }

    public void updateMedicalAppointment(MedicalAppointment medicalAppointment){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TB_MEDICALAPPOINTMENT, this.medicalAppointmentTable.update(medicalAppointment), ID + " = ? ", new String[]{String.valueOf(medicalAppointment.getId())});
    }

    public void updateMedicine(Medicine medicine){
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TB_MEDICINE, this.medicineTable.update(medicine), ID + " = ? ", new String[]{String.valueOf(medicine.getId())});

    }
}
