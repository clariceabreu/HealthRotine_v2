package com.fsi.healthrotine.Models;

import android.content.ContentValues;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.fsi.healthrotine.DataBase.Columns.COMMENTS;
import static com.fsi.healthrotine.DataBase.Columns.DATE;
import static com.fsi.healthrotine.DataBase.Columns.NAME;
import static com.fsi.healthrotine.DataBase.Columns.SPECIALTY;
import static com.fsi.healthrotine.DataBase.Columns.TIME;

public class Specialist extends Entity {

    private String specialty;
    private String certification;

    public Specialist(int _id, String _name, String specialty, String certification) {
        this.id = _id;
        this.name = _name;
        this.specialty = specialty;
        this.certification = certification;
    }

    public Specialist() {
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public static List<Specialist> getAll(Cursor cursorSpecialist) {

        List<Specialist> specialists = new ArrayList<>();
        if(cursorSpecialist.moveToFirst()){
            do {
                Specialist specialist = new Specialist();

                specialist.setId(Integer.parseInt(cursorSpecialist.getString(0)));
                specialist.setName(cursorSpecialist.getString(1));
                specialist.setSpecialty(cursorSpecialist.getString(2));
                specialist.setCertification(cursorSpecialist.getString(3));

                specialists.add(specialist);
            } while (cursorSpecialist.moveToNext());
        }

        return specialists;
    }

    public ContentValues getValues() {
        ContentValues values = new ContentValues();
        values.put(NAME, getName());
        values.put(SPECIALTY, getSpecialty());
        return values;
    }
}
