package com.fsi.healthrotine.Models;

import java.sql.Time;
import java.util.Date;

public class CardObject {
    private int id;
    private Date date;
    private Time time;
    private String type; //MedicalAppoitment or Medicine

    public  CardObject() { }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
