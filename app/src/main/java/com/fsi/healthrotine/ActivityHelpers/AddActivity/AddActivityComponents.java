package com.fsi.healthrotine.ActivityHelpers.AddActivity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fsi.healthrotine.AddActivity;
import com.fsi.healthrotine.Models.Specialist;
import com.fsi.healthrotine.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class AddActivityComponents {

    //Constants
    public static String[] typesArray = new String[]{"Selecione", "Consulta", "Remédio", "Vacina", "Exame"};
    public static String[] specialtiesArray = new String[]{"Selecione","Acupuntura", "Alergia e Imunologia", "Anestesiologia", "Angiologia", "Cancerologia", "Cardiologia", "Cirurgia Cardiovascular", "Cirurgia da Mão", "Cirurgia de Cabeça e Pescoço", "Cirurgia do Aparelho Digestivo", "Cirurgia Geral", "Cirurgia Pediátrica", "Cirurgia Plástica", "Cirurgia Torácica", "Cirurgia Vascular", "Clínica Médica", "Coloproctologia", "Dermatologia", "Endocrinologia e Metabologia", "Endoscopia", "Gastroenterologia", "Genética Médica", "Geriatria", "Ginecologia e Obstetrícia", "Hematologia e Hemoterapia", "Homeopatia", "Infectologia", "Mastologia", "Medicina de Família e Comunidade", "Medicina do Trabalho", "Medicina de Tráfego", "Medicina Esportiva", "Medicina Física e Reabilitação", "Medicina Intensiva", "Medicina Legal e Perícia Médica", "Medicina Nuclear", "Medicina Preventiva e Social", "Nefrologia", "Neurocirurgia", "Neurologia", "Nutrologia", "Oftalmologia", "Ortopedia e Traumatologia", "Otorrinolaringologia", "Patologia", "Patologia Clínica/Medicina Laboratorial", "Pediatria", "Pneumologia", "Psiquiatria", "Radiologia e Diagnóstico por Imagem", "Radioterapia", "Reumatologia", "Urologia"};
    public static Integer[] daysArray = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
    public static Integer[] monthsArray = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    public static Integer[] yearsArray = new Integer[201];
    public static Integer[] hoursArray = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    public static Integer[] minutesArray = new Integer[61];
    public static Integer[] frequenciesArray = new Integer[365];
    public static String[] frequencyUnitiesArray = new String[]{"horas", "dias"};
    public static String[] durationArray = new String[367];
    public static String[] vaccineTypeArray = new String[]{"Selecione", "Intravenosa", "Oral", "Intramuscular"};

    //Components
    private Spinner spinnerSpecialist;
    private EditText editTextSpecialist;
    private Spinner spinnerSpecialty;
    private Spinner spinnerType;
    private Spinner spinnerDay;
    private Spinner spinnerMonth;
    private Spinner spinnerYear;
    private Spinner spinnerHour;
    private Spinner spinnerMinute;
    private Spinner spinnerFrequency;
    private Spinner spinnerFrequencyUnity;
    private Spinner spinnerDuration;
    private EditText editTextType;
    private EditText editTextDosage;
    private EditText editTextComment;
    private EditText editTextName;
    private Spinner spinnerVaccineType;
    private EditText editTextVaccineManufacturer;
    private EditText editTextVaccineBatch;
    private EditText editTextVaccinePlace;
    private TextView textViewSpecialist;
    private Button buttonSpecialist;
    private TextView textViewSpecialty;
    private TextView textViewName;
    private TextView textViewDate;
    private TextView textViewHour;
    private TextView textViewFrequency;
    private TextView textViewDuration;
    private TextView textViewType;
    private TextView textViewDosage;
    private TextView textViewComment;
    private TextView textViewVaccineType;
    private TextView textViewVaccineManufacturer;
    private TextView textViewVaccineBatch;
    private TextView textViewVaccinePlace;
    private Button buttonCreate;

    private HashMap<String, View> componentsNames = new HashMap<>();

    public Activity activity;

    public AddActivityComponents(Activity activity) {
        this.activity = activity;
    }

    public void buildComponents(String[] specialistsNamesArray) {
        //Type
        spinnerType = (Spinner) activity.findViewById(R.id.spinnerType);
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, typesArray);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterType);

        //Specialist
        textViewSpecialist = (TextView) activity.findViewById(R.id.textViewSpecialist);
        spinnerSpecialist = (Spinner) activity.findViewById(R.id.spinnerSpecialist);
        ArrayAdapter<String> adapterSpecialist = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, specialistsNamesArray);
        adapterSpecialist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecialist.setAdapter(adapterSpecialist);
        editTextSpecialist = (EditText) activity.findViewById(R.id.editTextSpecialist);
        buttonSpecialist = (Button) activity.findViewById(R.id.buttonAddSpecialist);

        //Specialty
        textViewSpecialty = (TextView) activity.findViewById(R.id.textViewSpecialty);

        spinnerSpecialty = (Spinner) activity.findViewById(R.id.spinnerSpecialty);
        ArrayAdapter<String> adapterSpecialty = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, specialtiesArray);
        adapterSpecialty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecialty.setAdapter(adapterSpecialty);

        //Name
        textViewName = (TextView) activity.findViewById(R.id.textViewName);
        editTextName = (EditText) activity.findViewById(R.id.editTextName);

        //Date
        Calendar today = Calendar.getInstance();
        int day = today.get(Calendar.DAY_OF_MONTH);
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);

        textViewDate = (TextView) activity.findViewById(R.id.textViewDate);

        spinnerDay = (Spinner) activity.findViewById(R.id.spinnerDay);
        ArrayAdapter<Integer> adapterDay = new ArrayAdapter<Integer>(activity, android.R.layout.simple_spinner_dropdown_item, daysArray);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapterDay);
        spinnerDay.setSelection(day - 1);

        spinnerMonth = (Spinner) activity.findViewById(R.id.spinnerMonth);
        ArrayAdapter<Integer> adapterMonth = new ArrayAdapter<Integer>(activity, android.R.layout.simple_spinner_dropdown_item, monthsArray);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapterMonth);
        spinnerMonth.setSelection(month);

        int y = 1900;
        for (int i = 0; i < 201; i++) {
            yearsArray[i] = (y);
            y++;
        }

        spinnerYear = (Spinner) activity.findViewById(R.id.spinnerYear);
        ArrayAdapter<Integer> adapterYear = new ArrayAdapter<Integer>(activity, android.R.layout.simple_spinner_dropdown_item, yearsArray);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapterYear);
        spinnerYear.setSelection(year - 1900);


        //Hour
        int hour = today.get((Calendar.HOUR_OF_DAY));
        final int minute = today.get((Calendar.MINUTE));
        textViewHour = (TextView) activity.findViewById(R.id.textViewHour);

        spinnerHour = (Spinner) activity.findViewById(R.id.spinnerHour);
        ArrayAdapter<Integer> adapterHour = new ArrayAdapter<Integer>(activity, android.R.layout.simple_spinner_dropdown_item, hoursArray);
        adapterHour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHour.setAdapter(adapterHour);
        spinnerHour.setSelection(hour);

        for (Integer i = 0; i < 61; i++) {
            minutesArray[i] = i;
        }

        spinnerMinute = (Spinner) activity.findViewById(R.id.spinnerMinute);
        ArrayAdapter<Integer> adapterMinute = new ArrayAdapter<Integer>(activity, android.R.layout.simple_spinner_dropdown_item, minutesArray);
        adapterMinute.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMinute.setAdapter(adapterMinute);
        spinnerMinute.setSelection(minute);

        //Frequency
        for (Integer i = 1; i < 366; i++) {
            frequenciesArray[i - 1] = i;
        }

        textViewFrequency = (TextView) activity.findViewById(R.id.textViewFrequency);
        spinnerFrequency = (Spinner) activity.findViewById(R.id.spinnerFrquency);
        ArrayAdapter<Integer> adapterFrequency = new ArrayAdapter<Integer>(activity, android.R.layout.simple_spinner_dropdown_item, frequenciesArray);
        adapterFrequency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequency.setAdapter(adapterFrequency);

        spinnerFrequencyUnity = (Spinner) activity.findViewById(R.id.spinnerFrequencyUnity);
        ArrayAdapter<String> adapterFrequencyUnity = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, frequencyUnitiesArray);
        adapterFrequencyUnity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequencyUnity.setAdapter(adapterFrequencyUnity);
        spinnerFrequency.setSelection(7);

        //Duration
        durationArray[0] = "Selecione";
        durationArray[1] = "Ininterrupto";
        for (Integer i = 1; i < 366; i++) {
            if (i == 1)
                durationArray[i + 1] = i.toString() + " dia";
            else
                durationArray[i + 1] = i.toString() + " dias";
        }

        System.out.println("duration :" + durationArray.toString());
        textViewDuration = (TextView) activity.findViewById(R.id.textViewDuration);
        spinnerDuration = (Spinner) activity.findViewById(R.id.spinnerDuration);
        ArrayAdapter<String> adapterDuration = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, durationArray);
        adapterDuration.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDuration.setAdapter(adapterDuration);

        //Medicine type
        textViewType = (TextView) activity.findViewById(R.id.textViewType);
        editTextType = (EditText) activity.findViewById(R.id.editTextType);

        //Dosage
        textViewDosage = (TextView) activity.findViewById(R.id.textViewDosage);
        editTextDosage = (EditText) activity.findViewById(R.id.editTextDosage);

        //Comment
        textViewComment = (TextView) activity.findViewById(R.id.textViewComment);
        editTextComment = (EditText) activity.findViewById(R.id.editTextComment);

        //Vaccine Type
        textViewVaccineType = (TextView) activity.findViewById(R.id.textVaccineType);
        spinnerVaccineType = (Spinner) activity.findViewById(R.id.spinnerVaccineType);
        ArrayAdapter<String> adapterVaccineType = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, vaccineTypeArray);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVaccineType.setAdapter(adapterVaccineType);

        //Vaccine Manufacturer
        textViewVaccineManufacturer = (TextView) activity.findViewById(R.id.textVaccineManufacturer);
        editTextVaccineManufacturer = (EditText) activity.findViewById(R.id.editTextVaccineManufacturer);

        //Vaccine Batch
        textViewVaccineBatch = (TextView) activity.findViewById(R.id.textVaccineBatch);
        editTextVaccineBatch = (EditText) activity.findViewById(R.id.editTextVaccineBatch);

        //Vaccine Place
        textViewVaccinePlace = (TextView) activity.findViewById(R.id.textVaccinePlace);
        editTextVaccinePlace = (EditText) activity.findViewById(R.id.editTextVaccinePlace);

        //Button
        buttonCreate = (Button) activity.findViewById(R.id.buttonCreate);

        populateComponentsNames();
    }

    public void hideAllComponents() {
        for (String component : componentsNames.keySet()) {
            if (component.equals("spinnerType")) continue;
            componentsNames.get(component).setVisibility(View.GONE);
        }
    }

    public void showComponents(HashSet<String> components) {
        for (String component : components) {
            System.out.println(component);
            componentsNames.get(component).setVisibility(View.VISIBLE);
        }
    }

    public void hideComponents(HashSet<String> components) {
        for (String component : components) {
            componentsNames.get(component).setVisibility(View.GONE);
        }
    }

    public void setHints(HashMap<String, String> hints) {
        for (String component : hints.keySet()) {
            TextView textView = (TextView) componentsNames.get(component);
            textView.setHint(hints.get(component));
        }
    }

    public String getComponentText(String component) {
        EditText editText = (EditText) componentsNames.get(component);
        return editText.getText().toString();
    }

    public int getComponentPosition(String component) {
        Spinner spinner = (Spinner) componentsNames.get(component);
        return spinner.getSelectedItemPosition();
    }

    public View getComponent(String component) {
        return componentsNames.get(component);
    }

    private void populateComponentsNames() {
        componentsNames.put("spinnerType", spinnerType);
        componentsNames.put("spinnerSpecialist", spinnerSpecialist);
        componentsNames.put("editTextSpecialist", editTextSpecialist);
        componentsNames.put("spinnerSpecialty", spinnerSpecialty);
        componentsNames.put("spinnerDay", spinnerDay);
        componentsNames.put("spinnerMonth", spinnerMonth);
        componentsNames.put("spinnerYear", spinnerYear);
        componentsNames.put("spinnerHour", spinnerHour);
        componentsNames.put("spinnerMinute", spinnerMinute);
        componentsNames.put("spinnerFrequency", spinnerFrequency);
        componentsNames.put("spinnerFrequencyUnity", spinnerFrequencyUnity);
        componentsNames.put("spinnerDuration", spinnerDuration);
        componentsNames.put("editTextType", editTextType);
        componentsNames.put("editTextDosage", editTextDosage);
        componentsNames.put("editTextComment", editTextComment);
        componentsNames.put("editTextName", editTextName);
        componentsNames.put("spinnerVaccineType", spinnerVaccineType);
        componentsNames.put("editTextVaccineManufacturer", editTextVaccineManufacturer);
        componentsNames.put("editTextVaccineBatch", editTextVaccineBatch);
        componentsNames.put("editTextVaccinePlace", editTextVaccinePlace);
        componentsNames.put("textViewSpecialist", textViewSpecialist);
        componentsNames.put("buttonSpecialist", buttonSpecialist);
        componentsNames.put("textViewSpecialty", textViewSpecialty);
        componentsNames.put("textViewName", textViewName);
        componentsNames.put("textViewDate", textViewDate);
        componentsNames.put("textViewHour", textViewHour);
        componentsNames.put("textViewFrequency", textViewFrequency);
        componentsNames.put("textViewDuration", textViewDuration);
        componentsNames.put("textViewType", textViewType);
        componentsNames.put("textViewDosage", textViewDosage);
        componentsNames.put("textViewComment", textViewComment);
        componentsNames.put("textViewVaccineType", textViewVaccineType);
        componentsNames.put("textViewVaccineManufacturer", textViewVaccineManufacturer);
        componentsNames.put("textViewVaccineBatch", textViewVaccineBatch);
        componentsNames.put("textViewVaccinePlace", textViewVaccinePlace);
        componentsNames.put("buttonCreate", buttonCreate);
    }

}
