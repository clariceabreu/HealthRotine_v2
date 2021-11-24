package com.fsi.healthrotine.ActivityHelpers.AddActivity;


import com.fsi.healthrotine.DataBase.DataBase;
import com.fsi.healthrotine.Models.Medicine;
import com.fsi.healthrotine.Models.Specialist;

import java.sql.Time;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.fsi.healthrotine.DataBase.Columns.TB_MEDICINE;
import static com.fsi.healthrotine.DataBase.Columns.TB_SPECIALIST;

public class MedicineHelper {

    public static HashSet<String> getComponents() {
        return new HashSet<>(Arrays.asList(
                "textViewSpecialist",
                "spinnerSpecialist",
                "buttonSpecialist",
                "textViewName",
                "editTextName",
                "textViewDate",
                "spinnerDay",
                "spinnerMonth",
                "spinnerYear",
                "textViewHour",
                "spinnerHour",
                "spinnerMinute",
                "textViewComment",
                "editTextComment",
                "textViewDuration",
                "spinnerDuration",
                "textViewType",
                "editTextType",
                "textViewDosage",
                "editTextDosage",
                "textViewComment",
                "editTextComment",
                "buttonCreate"
        ));
    }

    public static HashMap<String, String> getHints() {
        HashMap<String, String> hints = new HashMap<>();
        hints.put("editTextComment", "Reações alérgicas, efeitos colaterais...");

        return hints;
    }

    public static void saveMedicine(AddActivityComponents components, DataBase db, boolean addNewSpecialist, List<Specialist> specialists) {
        Medicine medicine = new Medicine();

        Specialist specialist = null;
        if (addNewSpecialist) {
            String specialty = null;
            if (components.getComponentPosition("spinnerSpecialty") != 0) {
                specialty = AddActivityComponents.specialtiesArray[components.getComponentPosition("spinnerSpecialty")];
            }

            specialist = new Specialist();
            specialist.setName(components.getComponentText("editTextSpecialist"));
            specialist.setSpecialty(specialty);
            int id = db.insertOnTable(TB_SPECIALIST, specialist.getValues());
            specialist.setId(id);
        } else if (components.getComponentPosition("spinnerSpecialist") != 0){
            specialist = specialists.get(components.getComponentPosition("spinnerSpecialist") - 1);
        }

        medicine.setSpecialist(specialist);
        medicine.setName(components.getComponentText("editTextName"));
        int day = AddActivityComponents.daysArray[components.getComponentPosition("spinnerDay")];
        int month = AddActivityComponents.monthsArray[components.getComponentPosition("spinnerMonth")];
        int year = AddActivityComponents.yearsArray[components.getComponentPosition("spinnerYear")];
        int hour = AddActivityComponents.hoursArray[components.getComponentPosition("spinnerHour")];
        int minutes = AddActivityComponents.minutesArray[components.getComponentPosition("spinnerMinute")];
        medicine.setDate(new Date(year - 1900, month - 1, day, hour, minutes));
        medicine.setTime(new Time(hour, minutes, 0));

        int duration = components.getComponentPosition("spinnerDuration") - 1; //if uninterrumpt the frequency will be 0
        medicine.setDuration(duration);


        if (components.getComponentPosition("spinnerDuration") != 0) {
            String frequencyUnit = AddActivityComponents.frequencyUnitiesArray[components.getComponentPosition("spinnerFrequencyUnity")];
            medicine.setFrequencyUnity(frequencyUnit);

            int frequency = AddActivityComponents.frequenciesArray[components.getComponentPosition("spinnerFrequency")];
            medicine.setFrequency(frequency);
        } else {
            medicine.setFrequency(-1);
        }

        medicine.setType(components.getComponentText("editTextType"));
        medicine.setDosage(components.getComponentText("editTextDosage"));
        medicine.setComments(components.getComponentText("editTextComment"));
        db.insertOnTable(TB_MEDICINE, medicine.getValues());
    }
}
