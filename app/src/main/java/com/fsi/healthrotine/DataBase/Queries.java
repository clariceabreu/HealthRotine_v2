package com.fsi.healthrotine.DataBase;
import static com.fsi.healthrotine.DataBase.Columns.*;
public class Queries {
    public static final String
            MEDICINE_QUERY = "CREATE TABLE tb_medicine ("
            + ID + " INTEGER PRIMARY KEY, "
            + NAME + " TEXT, "
            + DATE + " TEXT, "
            + END_DATE + " TEXT, "
            + TIME + " TEXT, "
            + DURATION + " TEXT, "
            + FREQUENCY + " TEXT, "
            + FREQUENCY_UNITY + " TEXT, "
            + TYPE+ " TEXT, "
            + DOSAGE + " TEXT, "
            + ADMINISTRATION_TIMES + " TEXT, "
            + COMMENTS + " TEXT)";

    public static final String MEDICALAPPOINTMENT_QUERY = "CREATE TABLE " + TB_MEDICALAPPOINTMENT + "("
            + ID + " INTEGER PRIMARY KEY, "
            + NAME + " TEXT, "
            + SPECIALTY + " TEXT, "
            + SPECIALIST_ID + " INTEGER, "
            + DATE + " TEXT, "
            + TIME + " TEXT, "
            + COMMENTS + " TEXT)";

    public static final String SPECIALIST_QUERY = "CREATE TABlE " + TB_SPECIALIST + "("
            + ID + " INTEGER PRIMARY KEY, "
            + NAME + " TEXT, "
            + SPECIALTY_ID + " INTEGER, "
            + CERTIFICATION + " TEXT)";

    public static final String SPECIALTY_QUERY = "CREATE TABlE " + TB_SPECIALTY + "("
            + ID + " INTEGER PRIMARY KEY, "
            + NAME + " TEXT)";

}
