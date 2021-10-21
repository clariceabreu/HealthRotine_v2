package com.fsi.healthrotine.Models;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public Medicine(int _id, String name, Date _startDate, Time _startTime, int _frequency, String _frequencyUnity, int _duratiion, String _type, String _dosage, String _comments){
        this.id = _id;
        this.date = _startDate;
        this.duration = _duratiion;
        this.time = _startTime;
        this.frequency = _frequency;
        this.frequencyUnity = _frequencyUnity;
        this.type = _type;
        this.dosage = _dosage;
        this.comments = _comments;
    }

    public Medicine(String name, Date _startDate, Time _startTime, int _frequency, String _frequencyUnity, int _duration, String _type, String _dosage, String _comments){
        this.date = _startDate;
        this.duration = _duration;
        this.time = _startTime;
        this.frequency = _frequency;
        this.frequencyUnity = _frequencyUnity;
        this.type = _type;
        this.dosage = _dosage;
        this.comments = _comments;
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
}
