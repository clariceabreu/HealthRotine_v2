package com.fsi.healthrotine.ActivityHelpers.AddActivity;

import com.fsi.healthrotine.DataBase.DataBase;
import com.fsi.healthrotine.Models.MedicalAppointment;
import com.fsi.healthrotine.Models.Specialist;

import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.fsi.healthrotine.DataBase.Columns.TB_MEDICINE;
import static com.fsi.healthrotine.DataBase.Columns.TB_SPECIALIST;

public  class MedicalAppointmentHelper {

    public static HashSet<String> getComponents() {
        return new HashSet<>(Arrays.asList(
                "textViewSpecialist",
                "spinnerSpecialist",
                "buttonSpecialist",
                "textViewSpecialy",
                "spinnerSpecialty",
                "textViewDate",
                "spinnerDay",
                "spinnerMonth",
                "spinnerYear",
                "textViewHour",
                "spinnerHour",
                "spinnerMinute",
                "textViewComment",
                "editTextComment",
                "buttonCreate"
        ));
    }

    public static HashMap<String, String> getHints() {
        HashMap<String, String> hints = new HashMap<>();
        hints.put("editTextComment", "Endereço, informações importantes, lembretes...");
        return hints;
    }

    public static void saveMedicalAppointment(AddActivityComponents components, DataBase db, boolean addNewSpecialist, List<Specialist> specialists) {
        MedicalAppointment medicalAppointment = new MedicalAppointment();

        String specialty = null;
        if (components.getComponentPosition("spinnerSpecialty") != 0) {
            specialty = AddActivityComponents.specialtiesArray[components.getComponentPosition("spinnerSpecialty")];
            medicalAppointment.setSpecialty(specialty);
        }

        Specialist specialist = null;
        if (addNewSpecialist) {
            specialist = new Specialist();
            specialist.setName(components.getComponentText("editTextSpecialist"));
            specialist.setSpecialty(specialty);
            int id = db.insertOnTable(TB_SPECIALIST, specialist.getValues());
            specialist.setId(id);
        } else if (components.getComponentPosition("spinnerSpecialist") != 0){
            specialist = specialists.get(components.getComponentPosition("spinnerSpecialist") - 1);
        }

        medicalAppointment.setSpecialist(specialist);
        int day = AddActivityComponents.daysArray[components.getComponentPosition("spinnerDay")];
        int month = AddActivityComponents.monthsArray[components.getComponentPosition("spinnerMonth")];
        int year = AddActivityComponents.yearsArray[components.getComponentPosition("spinnerYear")];
        int hour = AddActivityComponents.hoursArray[components.getComponentPosition("spinnerHour")];
        int minutes = AddActivityComponents.minutesArray[components.getComponentPosition("spinnerMinute")];
        medicalAppointment.setTime(new Time(hour, minutes, 0));
        medicalAppointment.setDate(new Date(year - 1900, month - 1, day, hour, minutes));

        medicalAppointment.setComments(components.getComponentText("editTextComment"));

        db.insertOnTable(TB_MEDICINE, medicalAppointment.getValues());
    }
}
