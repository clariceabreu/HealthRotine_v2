package com.fsi.healthrotine.ActivityHelpers.AddActivity;

import com.fsi.healthrotine.DataBase.DataBase;
import com.fsi.healthrotine.Models.Exam;
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

public class ExamHelpers {

    public static HashSet<String> getComponents() {
        return new HashSet<>(Arrays.asList(
                "textViewSpecialist",
                "spinnerSpecialist",
                "buttonSpecialist",
                "textViewDate",
                "spinnerDay",
                "spinnerMonth",
                "spinnerYear",
                "textViewHour",
                "spinnerHour",
                "spinnerMinute",
                "textViewComment",
                "editTextComment",
                "textViewType",
                "editTextType",
                "buttonCreate"
        ));
    }

    public static HashMap<String, String> getHints() {
        HashMap<String, String> hints = new HashMap<>();
        hints.put("editTextComment", "Endereço, informações importantes, lembretes...");
        return hints;
    }

    public static void saveExam(AddActivityComponents components, DataBase db, boolean addNewSpecialist, List<Specialist> specialists) {
        Exam exam = new Exam();

        Specialist specialist = null;
        if (addNewSpecialist) {
            specialist = new Specialist();
            specialist.setName(components.getComponentText("editTextSpecialist"));
            int id = db.insertOnTable(TB_SPECIALIST, specialist.getValues());
            specialist.setId(id);
        } else if (components.getComponentPosition("spinnerSpecialist") != 0){
            specialist = specialists.get(components.getComponentPosition("spinnerSpecialist") - 1);
        }

        exam.setSpecialist(specialist);
        int day = AddActivityComponents.daysArray[components.getComponentPosition("spinnerDay")];
        int month = AddActivityComponents.monthsArray[components.getComponentPosition("spinnerMonth")];
        int year = AddActivityComponents.yearsArray[components.getComponentPosition("spinnerYear")];
        int hour = AddActivityComponents.hoursArray[components.getComponentPosition("spinnerHour")];
        int minutes = AddActivityComponents.minutesArray[components.getComponentPosition("spinnerMinute")];

        exam.setTime(new Time(hour, minutes, 0));
        exam.setDate(new Date(year - 1900, month - 1, day, hour, minutes));
        exam.setComments(components.getComponentText("editTextComment"));
        exam.setType(components.getComponentText("editTextType"));

        db.insertOnTable(TB_MEDICINE, exam.getValues());
    }
}
