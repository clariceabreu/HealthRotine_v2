package com.fsi.healthrotine.DataBase;

import android.content.ContentValues;
import android.database.Cursor;
import com.fsi.healthrotine.Models.Medicine;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fsi.healthrotine.DataBase.Columns.*;
import static com.fsi.healthrotine.DataBase.Helpers.convertArrayToString;

public class MedicineTable {

    public ContentValues add(Medicine medicine) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            ContentValues values = new ContentValues();
            values.put(NAME, medicine.getName());
            values.put(DATE, dateFormat.format(medicine.getDate()));
            if (medicine.getEndDate() != null){
                values.put(END_DATE, dateFormat.format(medicine.getEndDate()));
            }
            values.put(TIME, timeFormat.format(medicine.getTime()));
            values.put(DURATION, medicine.getDuration());
            values.put(FREQUENCY, medicine.getFrequency());
            values.put(FREQUENCY_UNITY, medicine.getFrequencyUnity());
            values.put(TYPE, medicine.getType());
            values.put(DOSAGE, medicine.getDosage());
            values.put(ADMINISTRATION_TIMES, convertArrayToString(medicine.getAdministrationTimes()));
            values.put(COMMENTS, medicine.getComments());

            return values;
    }

    public ContentValues update(Medicine medicine) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

            ContentValues values = new ContentValues();
            values.put(NAME, medicine.getName());
            values.put(DATE, dateFormat.format(medicine.getDate()));
            values.put(END_DATE, dateFormat.format(medicine.getEndDate()));
            values.put(TIME, timeFormat.format(medicine.getTime()));
            values.put(DURATION, timeFormat.format(medicine.getDuration()));
            values.put(FREQUENCY, timeFormat.format(medicine.getFrequency()));
            values.put(FREQUENCY_UNITY, timeFormat.format(medicine.getFrequencyUnity()));
            values.put(TYPE, timeFormat.format(medicine.getType()));
            values.put(DOSAGE, timeFormat.format(medicine.getDosage()));
            values.put(ADMINISTRATION_TIMES, convertArrayToString(medicine.getAdministrationTimes()));
            values.put(COMMENTS, medicine.getComments());
            return values;
    }

    public List<Medicine> getAll(Cursor cursor) {

            List<Medicine> medicines = new ArrayList<>();
            if(cursor.moveToFirst()){
                do {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                    try {
                        Medicine medicine = new Medicine();
                        medicine.setId(Integer.parseInt(cursor.getString(0)));
                        medicine.setName(cursor.getString(1));

                        Date date = new Date(dateFormat.parse(cursor.getString(2)).getTime());
                        if (cursor.getString(3) != null) {
                            Date endDate = new Date(dateFormat.parse(cursor.getString(3)).getTime());
                            medicine.setEndDate(endDate);
                        }
                        Time time = new Time(timeFormat.parse(cursor.getString(4)).getTime());

                        medicine.setDate(date);
                        medicine.setTime(time);
                        medicine.setDuration(Integer.parseInt(cursor.getString(5)));
                        medicine.setFrequencyUnity(cursor.getString(7));
                        medicine.setFrequency(Integer.parseInt(cursor.getString(6)));
                        medicine.setType(cursor.getString(8));
                        medicine.setDosage(cursor.getString(9));
                        medicine.setComments(cursor.getString(11));
                        medicines.add(medicine);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return null;
                    }

                } while (cursor.moveToNext());
            }

            return medicines;
    }
}
