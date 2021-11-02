package com.fsi.healthrotine.Models;

import android.content.ContentValues;
import android.database.Cursor;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fsi.healthrotine.DataBase.Columns.ADMINISTRATION_TIMES;
import static com.fsi.healthrotine.DataBase.Columns.COMMENTS;
import static com.fsi.healthrotine.DataBase.Columns.DATE;
import static com.fsi.healthrotine.DataBase.Columns.DOSAGE;
import static com.fsi.healthrotine.DataBase.Columns.DURATION;
import static com.fsi.healthrotine.DataBase.Columns.END_DATE;
import static com.fsi.healthrotine.DataBase.Columns.FREQUENCY;
import static com.fsi.healthrotine.DataBase.Columns.FREQUENCY_UNITY;
import static com.fsi.healthrotine.DataBase.Columns.NAME;
import static com.fsi.healthrotine.DataBase.Columns.SPECIALIST;
import static com.fsi.healthrotine.DataBase.Columns.TIME;
import static com.fsi.healthrotine.DataBase.Columns.TYPE;
import static com.fsi.healthrotine.DataBase.Helpers.convertArrayToString;
import static com.fsi.healthrotine.Models.Helpers.*;


public class Medicine extends Entity {

    private Date date;
    private Date endDate;
    private Time time;
    private int frequency;
    private String frequencyUnity; //horas ou dias
    private int duration; //in days (set as 0 when the medicine is for continuous use)
    private String type; //pills, drops or intravenous
    private String dosage;
    private String comments;
    private String specialist;
    private List<Date> administrationTimes;

    //constructor to update
    public Medicine(int _id, String name, Date _startDate, Time _startTime, int _frequency, String _frequencyUnity, int _duration, String _type, String _dosage, String _comments, String _specialist){
        this.id = _id;
        this.date = _startDate;
        this.duration = _duration;
        this.time = _startTime;
        this.frequency = _frequency;
        this.frequencyUnity = _frequencyUnity;
        this.type = _type;
        this.dosage = _dosage;
        this.comments = _comments;
        this.specialist = _specialist;
    }

    public Medicine(String name, Date _startDate, Time _startTime, int _frequency, String _frequencyUnity, int _duration, String _type, String _dosage, String _comments, String _specialist){
        this.date = _startDate;
        this.duration = _duration;
        this.time = _startTime;
        this.frequency = _frequency;
        this.frequencyUnity = _frequencyUnity;
        this.type = _type;
        this.dosage = _dosage;
        this.comments = _comments;
        this.specialist = _specialist;
    }

    public Medicine() {

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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duratiion) {
        this.duration = duratiion;

        if (duratiion > 0){
            this.endDate = addDays(this.getDate(), this.getDuration());
        } else {
            this.endDate = new Date(3000 - 1900, 0, 1, 23, 59); //3000-01-01
        }
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;

        if (frequency != -1) {
            if (this.frequencyUnity.equals("horas")) {
                this.administrationTimes = new ArrayList<Date>();
                this.administrationTimes.add(this.getDate());
                Date admTime = addHours(this.getDate(),frequency);

                while (admTime.compareTo(this.endDate) == -1){
                    this.administrationTimes.add(admTime);
                    admTime = addHours(admTime, frequency);
                }

            } else if (this.frequencyUnity.equals("dias")){
                this.administrationTimes = new ArrayList<Date>();
                Date admTime = addDays(this.getDate(), frequency);

                while (admTime.compareTo(this.endDate) == -1){
                    this.administrationTimes.add(admTime);
                    admTime = addDays(admTime, frequency);
                }
            }
        }
    }

    public String getFrequencyUnity() {
        return frequencyUnity;
    }

    public void setFrequencyUnity(String frequencyUnity) {
        this.frequencyUnity = frequencyUnity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Date> getAdministrationTimes() {
        return administrationTimes;
    }

    public void setAdministrationTimes(List<Date> administrationTimes) {
        this.administrationTimes = administrationTimes;
    }

    public String getSpecialist() {
        return specialist;
    }

    public void setSpecialist(String specialist) {
        this.specialist = specialist;
    }

    public ContentValues getValues() {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        ContentValues values = new ContentValues();
        values.put(NAME, getName());
        values.put(DATE, dateFormat.format(getDate()));
        if (getEndDate() != null){
            values.put(END_DATE, dateFormat.format(getEndDate()));
        }
        values.put(TIME, timeFormat.format(getTime()));
        values.put(DURATION, getDuration());
        values.put(FREQUENCY, getFrequency());
        values.put(FREQUENCY_UNITY, getFrequencyUnity());
        values.put(TYPE, getType());
        values.put(DOSAGE, getDosage());
        values.put(ADMINISTRATION_TIMES, convertArrayToString(getAdministrationTimes()));
        values.put(COMMENTS, getComments());
        values.put(SPECIALIST, getSpecialist());
        return values;
    }

    public static List<Medicine> getAll(Cursor cursor) {

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
                    medicine.setSpecialist(cursor.getString(12));
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
