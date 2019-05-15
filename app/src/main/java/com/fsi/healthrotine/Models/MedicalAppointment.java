package com.fsi.healthrotine.Models;

import java.util.Date;
import java.sql.Time;

public class MedicalAppointment {
    private int id;

    private String specialty;
    private Date date;
    private Time time;
    private String comments;

    public MedicalAppointment(){}

    public MedicalAppointment(int _id, String _specialty, Date _date, Time _time, String _comments){
        this.id = _id;
        this.specialty = _specialty;
        this.date = _date;
        this.time = _time;
        this.comments = _comments;
    }

    public MedicalAppointment(String _specialty, Date _date, Time _time, String _comments){
        this.specialty = _specialty;
        this.date = _date;
        this.time = _time;
        this.comments = _comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
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
}
