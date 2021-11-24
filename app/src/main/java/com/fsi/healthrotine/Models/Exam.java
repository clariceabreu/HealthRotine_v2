package com.fsi.healthrotine.Models;

import android.content.ContentValues;
import android.database.Cursor;

import com.fsi.healthrotine.DataBase.DataBase;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fsi.healthrotine.DataBase.Columns.*;

public class Exam extends Entity {

    private String type;
    private Date date;
    private Time time;
    private String fileLocation;
    private Specialist specialist;
    private String comments;

    public Exam(){}

    public Exam(String type, Date date, Time time, String fileLocation, Specialist specialist, String comments) {
        this.type = type;
        this.date = date;
        this.time = time;
        this.fileLocation = fileLocation;
        this.specialist = specialist;
        this.comments = comments;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
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
        values.put(DATE, dateFormat.format(getDate()));
        values.put(TIME, timeFormat.format(getTime()));
        values.put(COMMENTS, getComments());
        if(getSpecialist() != null )values.put(SPECIALIST_ID, getSpecialist().getId());
        else values.put(SPECIALIST_ID, 0);
        values.put(TYPE, getType());
        values.put(FILE_LOCATION, getFileLocation());
        return values;
    }

    public static List<Exam> getAll(Cursor cursor, DataBase dataBase) {
        List<Exam> exams = new ArrayList<Exam>();
        if(cursor.moveToFirst()){
            do {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                try {
                    Exam exam = new Exam();
                    exam.setId(Integer.parseInt(cursor.getString(0)));
                    Date date = new Date(dateFormat.parse(cursor.getString(1)).getTime());
                    Time time = new Time(timeFormat.parse(cursor.getString(2)).getTime());

                    exam.setDate(date);
                    exam.setType(cursor.getString(3));
                    exam.setFileLocation(cursor.getString(4));
                    exam.setTime(time);
                    exam.setComments(cursor.getString(6));

                    int specialist_id = cursor.getInt(5);
                    if (specialist_id != 0) {
                        Cursor cursorSpecialist = dataBase.getTableCursorForId(TB_SPECIALIST, specialist_id);
                        if (cursorSpecialist != null) {
                            Specialist specialist = new Specialist();
                            specialist.setId(Integer.parseInt(cursorSpecialist.getString(0)));
                            specialist.setName(cursorSpecialist.getString(1));
                            specialist.setSpecialty(cursorSpecialist.getString(2));
                            exam.setSpecialist(specialist);
                        }
                    }

                    exams.add(exam);
                } catch (ParseException e) {
                    e.printStackTrace();
                    return null;
                }

            } while (cursor.moveToNext());
        }
        return exams;
    }
}
