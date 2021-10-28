package com.fsi.healthrotine.Models;

import android.database.Cursor;

import com.fsi.healthrotine.DataBase.DataBase;

import java.util.ArrayList;
import java.util.List;

import static com.fsi.healthrotine.DataBase.Columns.TB_SPECIALTY;

public class Specialist extends Entity {

    private Specialty specialty;
    private String certification;

    public Specialist(int _id, String _name, Specialty specialty, String certification) {
        this.id = _id;
        this.name = _name;
        this.specialty = specialty;
        this.certification = certification;
    }

    public Specialist() {
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public static List<Specialist> getAll(Cursor cursorSpecialist, DataBase dataBase) {

        List<Specialist> specialists = new ArrayList<>();
        if(cursorSpecialist.moveToFirst()){
            do {
                Specialist specialist = new Specialist();

                specialist.setId(Integer.parseInt(cursorSpecialist.getString(0)));
                specialist.setName(cursorSpecialist.getString(1));

                int specialty_id = cursorSpecialist.getInt(2);
                Cursor cursorSpecialty = dataBase.getTableCursorForId(TB_SPECIALTY, specialty_id);

                Specialty specialty = new Specialty();

                if (cursorSpecialty != null) {
                    specialist.setId(Integer.parseInt(cursorSpecialty.getString(0)));
                    specialist.setName(cursorSpecialty.getString(1));
                }

                specialist.setSpecialty(specialty);
                specialist.setCertification(cursorSpecialist.getString(3));
            } while (cursorSpecialist.moveToNext());
        }

        return specialists;
    }
}
