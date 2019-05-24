package com.fsi.healthrotine.Models;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class Medicine {
    private int id;
    private Date date;
    private Date endDate;
    private Time time;
    private int frequency; //in hours
    private int duration; //in days (set as -1 when the medicine is for continuous use
    private String type; //pills, drops or intravenous
    private String dosage;
    private String comments;

    public Medicine(){}

    //constructor to update
    public Medicine(int _id, Date _startDate, Time _startTime, int _frequency, int _duratiion, String _type, String _dosage, String _comments){
        this.id = _id;
        this.date = _startDate;
        this.duration = _duratiion;
        this.endDate = this.addDays(this.getDuration());
        this.time = _startTime;
        this.frequency = _frequency;
        this.type = _type;
        this.dosage = _dosage;
        this.comments = _comments;
    }

    public Medicine(Date _startDate, Time _startTime, int _frequency, int _duration, String _type, String _dosage, String _comments){
        this.date = _startDate;
        this.duration = _duration;
        this.endDate = this.addDays(this.getDuration());
        this.time = _startTime;
        this.frequency = _frequency;
        this.type = _type;
        this.dosage = _dosage;
        this.comments = _comments;
    }

    private Date addDays(int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(this.getDate());
        c.add(Calendar.DATE, days);
        return new Date(c.getTimeInMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
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
}
