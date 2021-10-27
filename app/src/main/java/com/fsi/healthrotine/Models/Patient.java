package com.fsi.healthrotine.Models;

import android.content.ContentValues;


import static com.fsi.healthrotine.DataBase.Columns.*;

public class Patient extends Entity{

    private String name;
    private int age;
    private String blood_type;
    private int weight;
    private int height;
    private String allergies;
    private int sus_card;
    private int health_insurance_card;
    private int emergency_contacts;


    public Patient(){}

    //constructor to update
    public Patient(int _id, String _name, int _age, String _blood_type, int _weight, int _height, String _allergies, int _sus_card, int _health_isurance_card, int _emergency_contacts){
        this.id = _id;
        this.age = _age;
        this.blood_type = _blood_type;
        this.weight = _weight;
        this.height = _height;
        this.allergies = _allergies;
        this.sus_card = _sus_card;
        this.health_insurance_card = _health_isurance_card;
        this.emergency_contacts = _emergency_contacts;
    }

    public Patient(String _name, int _age, String _blood_type, int _weight, int _height, String _allergies, int _sus_card, int _health_isurance_card, int _emergency_contacts){
        this.age = _age;
        this.blood_type = _blood_type;
        this.weight = _weight;
        this.height = _height;
        this.allergies = _allergies;
        this.sus_card = _sus_card;
        this.health_insurance_card = _health_isurance_card;
        this.emergency_contacts = _emergency_contacts;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public int getSus_card() {
        return sus_card;
    }

    public void setSus_card(int sus_card) {
        this.sus_card = sus_card;
    }

    public int getHealth_insurance_card() {
        return health_insurance_card;
    }

    public void setHealth_insurance_card(int health_insurance_card) {
        this.health_insurance_card = health_insurance_card; }

    public int getEmergency_contacts() {
        return emergency_contacts;
    }

    public void setEmergency_contacts(int emergency_contacts) {
        this.emergency_contacts = emergency_contacts;
    }



    public ContentValues getValues() {

        ContentValues values = new ContentValues();
        values.put(AGE, getAge());
        values.put(BLOOD_TYPE, getBlood_type());
        values.put(WEIGHT, getWeight());
        values.put(HEIGHT, getHeight());
        values.put(ALLERGIES, getAllergies());
        values.put(SUS_CARD, getSus_card());
        values.put(HEALTH_INSURANCE, getHealth_insurance_card());
        values.put(EMERGENCY_CONTACTS, getEmergency_contacts());
        return values;
    }
}
