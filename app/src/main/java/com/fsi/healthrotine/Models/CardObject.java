package com.fsi.healthrotine.Models;

import java.sql.Time;
import java.util.Date;

public class CardObject {
    private Date date;
    private Date endDate;
    private Time time;
    private String type; //MedicalAppoitment or Medicine

    public  CardObject() { }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
