package com.fsi.healthrotine.DataBase;

import android.content.ContentValues;
import android.database.Cursor;

import com.fsi.healthrotine.Models.MedicalAppointment;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fsi.healthrotine.DataBase.Columns.*;

public class MedicalAppointmentTable {


    public ContentValues add(MedicalAppointment medicalAppointment) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        ContentValues values = new ContentValues();
        values.put(SPECIALTY, medicalAppointment.getSpecialty());
        values.put(DATE, dateFormat.format(medicalAppointment.getDate()));
        values.put(TIME, timeFormat.format(medicalAppointment.getTime()));
        values.put(COMMENTS, medicalAppointment.getComments());

        return values;
    }
    public MedicalAppointment getOne(Cursor cursor){
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
    public ContentValues update(MedicalAppointment medicalAppointment) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        ContentValues values = new ContentValues();
        values.put(SPECIALTY, medicalAppointment.getSpecialty());
        values.put(DATE, dateFormat.format(medicalAppointment.getDate()));
        values.put(TIME, timeFormat.format(medicalAppointment.getTime()));
        values.put(COMMENTS, medicalAppointment.getComments());
        return values;
    }

    public List<MedicalAppointment> getAll(Cursor cursor) {
        List<MedicalAppointment> medicalAppointments = new ArrayList<MedicalAppointment>();
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
}

