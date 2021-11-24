package com.fsi.healthrotine.Models;

import android.content.ContentValues;
import android.database.Cursor;

import com.fsi.healthrotine.DataBase.DataBase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;
import java.util.List;

import static com.fsi.healthrotine.DataBase.Columns.COMMENTS;
import static com.fsi.healthrotine.DataBase.Columns.DATE;
import static com.fsi.healthrotine.DataBase.Columns.SPECIALIST_ID;
import static com.fsi.healthrotine.DataBase.Columns.SPECIALTY;
import static com.fsi.healthrotine.DataBase.Columns.TB_SPECIALIST;
import static com.fsi.healthrotine.DataBase.Columns.TIME;

public class MedicalAppointment extends Entity{

    private String specialty;
    private Specialist specialist;
    private Date date;
    private Time time;
    private String comments;

    public MedicalAppointment(){}

    //constructor to update
    public MedicalAppointment(int _id, String _specialty, Date _date, Time _time, String _comments, Specialist _specialist){
        this.id = _id;
        this.specialty = _specialty;
        this.specialist = _specialist;
        this.date = _date;
        this.time = _time;
        this.comments = _comments;
    }

    public MedicalAppointment(String _specialty, Date _date, Time _time, String _comments, Specialist _specialist){
        this.specialty = _specialty;
        this.specialist = _specialist;
        this.date = _date;
        this.time = _time;
        this.comments = _comments;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ContentValues getValues() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        ContentValues values = new ContentValues();
        values.put(SPECIALTY, getSpecialty());
        values.put(DATE, dateFormat.format(getDate()));
        values.put(TIME, timeFormat.format(getTime()));
        values.put(COMMENTS, getComments());
        values.put(SPECIALIST_ID, getSpecialist() != null ? getSpecialist().getId() : -1);
        return values;
    }

    public static MedicalAppointment getOne(Cursor cursor){
        if (cursor != null){
            cursor.moveToFirst();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date = new Date(dateFormat.parse(cursor.getString(2)).getTime());
            Time time = new Time(timeFormat.parse(cursor.getString(3)).getTime());

            MedicalAppointment medicalAppointment = new MedicalAppointment(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                    date, time, cursor.getString(4), null);
            return medicalAppointment;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<MedicalAppointment> getAll(Cursor cursor, DataBase dataBase) {
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

                    int specialist_id = cursor.getInt(5);
                    if (specialist_id != 0) {
                        Cursor cursorSpecialist = dataBase.getTableCursorForId(TB_SPECIALIST, specialist_id);
                        if (cursorSpecialist.getCount() > 0) {
                            Specialist specialist = new Specialist();
                            specialist.setId(Integer.parseInt(cursorSpecialist.getString(0)));
                            specialist.setName(cursorSpecialist.getString(1));
                            specialist.setSpecialty(cursorSpecialist.getString(2));
                            specialist.setCertification(cursorSpecialist.getString(3));
                            medicalAppointment.setSpecialist(specialist);
                        }
                    }

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
