package com.fsi.healthrotine.Models;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class Specialty extends Entity {
    public Specialty(int _id, String _name) {
        this.id = _id;
        this.name = _name;
    }

    public Specialty() {
    }

    public static List<Specialty> getAll(Cursor cursor) {

        List<Specialty> specialties = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                Specialty specialty = new Specialty();

                specialty.setId(Integer.parseInt(cursor.getString(0)));
                specialty.setName(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        return specialties;
    }
}
