package com.fsi.healthrotine.Models;

import android.content.ContentValues;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fsi.healthrotine.DataBase.Columns.*;

public class Vaccine extends Entity {

    private Date date;
    private String type;
    private String manufacturer;
    private String batch;
    private String place;

    //constructor to update
    public Vaccine(int id, String name, Date date, String type, String manufacturer, String batch, String place) {
        this.id = id;
        this.name= name;
        this.date = date;
        this.type = type;
        this.manufacturer = manufacturer;
        this.batch = batch;
        this.place = place;
    }

    public Vaccine(String name, Date date, String type, String manufacturer, String batch, String place) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.manufacturer = manufacturer;
        this.batch = batch;
        this.place = place;
    }

    public Vaccine() {

    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public ContentValues getValues() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ContentValues values = new ContentValues();
        values.put(NAME, getName());
        values.put(DATE, dateFormat.format(getDate()));
        values.put(TYPE, getType());
        values.put(MANUFACTURER, getManufacturer());
        values.put(BATCH, getBatch());
        values.put(PLACE, getPlace());
        return values;
    }

    public static List<Vaccine> getAll(Cursor cursor) {

        List<Vaccine> vaccines = new ArrayList<>();
        if(cursor.moveToFirst()){
            do {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Vaccine vaccine = new Vaccine();
                    vaccine.setId(Integer.parseInt(cursor.getString(0)));
                    vaccine.setName(cursor.getString(1));
                    Date date = new Date(dateFormat.parse(cursor.getString(2)).getTime());
                    vaccine.setDate(date);
                    vaccine.setType(cursor.getString(3));
                    vaccine.setBatch(cursor.getString(4));
                    vaccine.setPlace(cursor.getString(5));
                    vaccine.setManufacturer(cursor.getString(6));
                    vaccines.add(vaccine);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }
            } while (cursor.moveToNext());
        }
        return vaccines;
    }
}
