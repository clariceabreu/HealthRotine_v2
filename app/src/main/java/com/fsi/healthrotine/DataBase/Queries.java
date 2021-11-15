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
            + SPECIALTY + " TEXT, "
            + DATE + " TEXT, "
            + TIME + " TEXT, "
            + COMMENTS + " TEXT, "
            + SPECIALIST_ID + " INTEGER DEFAULT 0)";

    public static final String SPECIALIST_QUERY = "CREATE TABlE " + TB_SPECIALIST + "("
            + ID + " INTEGER PRIMARY KEY, "
            + NAME + " TEXT, "
            + SPECIALTY + " TEXT, "
            + CERTIFICATION + " TEXT)";

    public static final String PATIENT_QUERY =
            "CREATE TABLE " + TB_PATIENT + "("
            + ID + " TEXT, "
            + NAME + " TEXT, "
            + AGE + " TEXT, "
            + BLOOD_TYPE + " TEXT, "
            + WEIGHT + " TEXT, "
            + HEIGHT + " TEXT, "
            + ALLERGIES + " TEXT, "
            + SUS_CARD + " TEXT, "
            + HEALTH_INSURANCE + " TEXT, "
            + EMAIL + " TEXT, "
            + EMERGENCY_CONTACTS + " TEXT)";

    public static final String VACCINE_QUERY = "CREATE TABLE " + TB_VACCINE + "("
                + ID + " INTEGER PRIMARY KEY, "
                + NAME + " TEXT, "
                + DATE + " TEXT, "
                + TYPE + " TEXT, "
                + BATCH + " TEXT, "
                + PLACE + " TEXT, "
                + MANUFACTURER + " TEXT)";
}
