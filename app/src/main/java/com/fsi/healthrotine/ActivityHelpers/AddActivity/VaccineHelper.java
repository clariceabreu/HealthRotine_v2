package com.fsi.healthrotine.ActivityHelpers.AddActivity;

import com.fsi.healthrotine.DataBase.DataBase;
import com.fsi.healthrotine.Models.Specialist;
import com.fsi.healthrotine.Models.Vaccine;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static com.fsi.healthrotine.DataBase.Columns.TB_VACCINE;

public class VaccineHelper {
    public static HashSet<String> getComponents() {
        return new HashSet<>(Arrays.asList(
                "textViewName",
                "editTextName",
                "textViewVaccineType",
                "spinnerVaccineType",
                "textViewDate",
                "spinnerDay",
                "spinnerMonth",
                "spinnerYear",
                "textViewHour",
                "spinnerHour",
                "spinnerMinute",
                "textViewVaccineManufacturer",
                "editTextVaccineManufacturer",
                "textViewVaccineBatch",
                "editTextVaccineBatch",
                "textViewVaccinePlace",
                "editTextVaccinePlace",
                "buttonCreate"
        ));
    }

    public static void saveVaccine(AddActivityComponents components, DataBase db, boolean addNewSpecialist, List<Specialist> specialists) {
        Vaccine vaccine = new Vaccine();

        vaccine.setName(components.getComponentText("editTextName"));
        vaccine.setType(AddActivityComponents.vaccineTypeArray[components.getComponentPosition("spinnerVaccineType")]);
        int day = AddActivityComponents.daysArray[components.getComponentPosition("spinnerDay")];
        int month = AddActivityComponents.monthsArray[components.getComponentPosition("spinnerMonth")];
        int year = AddActivityComponents.yearsArray[components.getComponentPosition("spinnerYear")];
        int hour = AddActivityComponents.hoursArray[components.getComponentPosition("spinnerHour")];
        int minutes = AddActivityComponents.minutesArray[components.getComponentPosition("spinnerMinute")];
        vaccine.setDate(new Date(year - 1900, month - 1, day, hour, minutes));

        vaccine.setManufacturer(components.getComponentText("editTextVaccineManufacturer"));
        vaccine.setBatch(components.getComponentText("editTextVaccineBatch"));
        vaccine.setPlace(components.getComponentText("editTextVaccinePlace"));

        db.insertOnTable(TB_VACCINE, vaccine.getValues());
    }
}
