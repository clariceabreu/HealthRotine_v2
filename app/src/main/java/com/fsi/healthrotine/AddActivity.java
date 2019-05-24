package com.fsi.healthrotine;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fsi.healthrotine.DataBase.DataBase;
import com.fsi.healthrotine.Models.MedicalAppointment;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    private String[] typesArray = new String[]{"Selecione", "Consulta", "Remédio", "Exame"};
    private String[] specialtiesArray = new String[]{"Selecione","Clínica Médica", "Oftomologia", "Otorrinologia", "Pediatria", "Ginebologia"};
    private Integer[] daysArray = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
    private Integer[] monthsArray = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private Integer[] yearsArray = new Integer[201];
    private Integer[] hoursArray = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    private Integer[] minutesArray = new Integer[61];
    private Integer[] frequenciesArray = new Integer[365];
    private String[] frequencyUnitiesArray = new String[]{"horas", "dias"};
    private String[] durationArray = new String[367];

    DataBase db = new DataBase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //Data type
        Spinner spinnerType = (Spinner) findViewById(R.id.spinnerType);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typesArray);
        // Specify the layout to use when the list of choices appears
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerType.setAdapter(adapterType);


        //Specialty
        final TextView textViewSpecialy = (TextView) findViewById(R.id.textViewSpecialty);

        final Spinner spinnerSpecialty = (Spinner) findViewById(R.id.spinnerSpecialty);
        ArrayAdapter<String> adapterSpecialty = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, specialtiesArray);
        adapterSpecialty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecialty.setAdapter(adapterSpecialty);


        //Date
        Calendar today = Calendar.getInstance();
        int day = today.get(Calendar.DAY_OF_MONTH);
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);

        final TextView textViewDate = (TextView) findViewById(R.id.textViewDate);

        final Spinner spinnerDay = (Spinner) findViewById(R.id.spinnerDay);
        ArrayAdapter<Integer> adapterDay = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, daysArray);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapterDay);
        spinnerDay.setSelection(day - 1);

        final Spinner spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        ArrayAdapter<Integer> adapterMonth = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, monthsArray);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapterMonth);
        spinnerMonth.setSelection(month);

        int y = 1900;
        for (int i = 0; i < 201; i++){
            yearsArray[i] = (y);
            y++;
        }

        final Spinner spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        ArrayAdapter<Integer> adapterYear = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, yearsArray);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapterYear);
        spinnerYear.setSelection(year - 1900);


        //Hour
        int hour = today.get((Calendar.HOUR_OF_DAY));
        int minute = today.get((Calendar.MINUTE));
        final TextView textViewHour = (TextView) findViewById(R.id.textViewHour);

        final Spinner spinnerHour = (Spinner) findViewById(R.id.spinnerHour);
        ArrayAdapter<Integer> adapterHour = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, hoursArray);
        adapterHour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHour.setAdapter(adapterHour);
        spinnerHour.setSelection(hour);

        for (Integer i = 0; i < 61; i++){
            minutesArray[i] = i;
        }

        final Spinner spinnerMinute = (Spinner) findViewById(R.id.spinnerMinute);
        ArrayAdapter<Integer> adapterMinute = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, minutesArray);
        adapterMinute.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMinute.setAdapter(adapterMinute);
        spinnerMinute.setSelection(minute);

        //Frequency
        for (Integer i = 1; i < 366; i ++){
            frequenciesArray[i-1] = i;
        }

        final TextView textViewFrequency = (TextView) findViewById(R.id.textViewFrequency);
        final Spinner spinnerFrequency = (Spinner) findViewById(R.id.spinnerFrquency);
        ArrayAdapter<Integer> adapterFrequency = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, frequenciesArray);
        adapterFrequency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequency.setAdapter(adapterFrequency);

        final Spinner spinnerFrequencyUnity = (Spinner) findViewById(R.id.spinnerFrequencyUnity);
        ArrayAdapter<String> adapterFrequencyUnity = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, frequencyUnitiesArray);
        adapterFrequencyUnity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrequencyUnity.setAdapter(adapterFrequencyUnity);

        //Duration
        durationArray[0] = "Selecione";
        durationArray[1] = "Ininterrupto";
        for (Integer i = 1; i < 366; i ++){
            if (i == 2)
                durationArray[i + 1] = i.toString() + " dia";
            else
                durationArray[i + 1] = i.toString() + " dias";
        }

        System.out.println("duration :" + durationArray.toString());
        final TextView textViewDuration = (TextView) findViewById(R.id.textViewDuration);
        final Spinner spinnerDuration = (Spinner) findViewById(R.id.spinnerDuration);
        ArrayAdapter<String> adapterDuration = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, durationArray);
        adapterDuration.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDuration.setAdapter(adapterDuration);

        //Medicine type
        final TextView textViewType = (TextView) findViewById(R.id.textViewType);
        final EditText editTextType = (EditText) findViewById(R.id.editTextType);

        //Dosage
        final TextView textViewDosage = (TextView) findViewById(R.id.textViewDosage);
        final EditText editTextDosage = (EditText) findViewById(R.id.editTextDosage);

        //Comment
        final TextView textViewComment = (TextView) findViewById(R.id.textViewComment);
        final EditText editTextComment = (EditText) findViewById(R.id.editTextComment);


        //Button
        final Button buttonCreate = (Button) findViewById(R.id.buttonCreate);


        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle
                if (position == 1){
                    textViewSpecialy.setVisibility(View.VISIBLE);
                    spinnerSpecialty.setVisibility(View.VISIBLE);
                    textViewDate.setVisibility(View.VISIBLE);
                    spinnerDay.setVisibility(View.VISIBLE);
                    spinnerMonth.setVisibility(View.VISIBLE);
                    spinnerYear.setVisibility(View.VISIBLE);
                    textViewHour.setVisibility(View.VISIBLE);
                    spinnerHour.setVisibility(View.VISIBLE);
                    spinnerMinute.setVisibility(View.VISIBLE);
                    textViewComment.setVisibility(View.VISIBLE);
                    editTextComment.setVisibility(View.VISIBLE);
                    buttonCreate.setVisibility(View.VISIBLE);

                    textViewFrequency.setVisibility(View.GONE);
                    spinnerFrequency.setVisibility(View.GONE);
                    spinnerFrequencyUnity.setVisibility(View.GONE);
                    textViewDuration.setVisibility(View.GONE);
                    spinnerDuration.setVisibility(View.GONE);
                    textViewType.setVisibility(View.GONE);
                    editTextType.setVisibility(View.GONE);
                    textViewDosage.setVisibility(View.GONE);
                    editTextDosage.setVisibility(View.GONE);

                } else if(position == 2){
                    textViewSpecialy.setVisibility(View.GONE);
                    spinnerSpecialty.setVisibility(View.GONE);

                    textViewDate.setVisibility(View.VISIBLE);
                    spinnerDay.setVisibility(View.VISIBLE);
                    spinnerMonth.setVisibility(View.VISIBLE);
                    spinnerYear.setVisibility(View.VISIBLE);
                    textViewHour.setVisibility(View.VISIBLE);
                    spinnerHour.setVisibility(View.VISIBLE);
                    spinnerMinute.setVisibility(View.VISIBLE);
                    textViewFrequency.setVisibility(View.VISIBLE);
                    spinnerFrequency.setVisibility(View.VISIBLE);
                    spinnerFrequencyUnity.setVisibility(View.VISIBLE);
                    textViewDuration.setVisibility(View.VISIBLE);
                    spinnerDuration.setVisibility(View.VISIBLE);
                    textViewType.setVisibility(View.VISIBLE);
                    editTextType.setVisibility(View.VISIBLE);
                    textViewDosage.setVisibility(View.VISIBLE);
                    editTextDosage.setVisibility(View.VISIBLE);
                    textViewComment.setVisibility(View.VISIBLE);
                    editTextComment.setVisibility(View.VISIBLE);
                    buttonCreate.setVisibility(View.VISIBLE);

                } else{
                    textViewSpecialy.setVisibility(View.GONE);
                    spinnerSpecialty.setVisibility(View.GONE);
                    textViewDate.setVisibility(View.GONE);
                    spinnerDay.setVisibility(View.GONE);
                    spinnerMonth.setVisibility(View.GONE);
                    spinnerYear.setVisibility(View.GONE);
                    textViewHour.setVisibility(View.GONE);
                    spinnerHour.setVisibility(View.GONE);
                    spinnerMinute.setVisibility(View.GONE);
                    textViewComment.setVisibility(View.GONE);
                    editTextComment.setVisibility(View.GONE);
                    buttonCreate.setVisibility(View.GONE);
                    textViewFrequency.setVisibility(View.GONE);
                    spinnerFrequency.setVisibility(View.GONE);
                    spinnerFrequencyUnity.setVisibility(View.GONE);
                    textViewDuration.setVisibility(View.GONE);
                    spinnerDuration.setVisibility(View.GONE);
                    textViewType.setVisibility(View.GONE);
                    editTextType.setVisibility(View.GONE);
                    textViewDosage.setVisibility(View.GONE);
                    editTextDosage.setVisibility(View.GONE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MedicalAppointment medicalAppointment = new MedicalAppointment();

                String specialty = specialtiesArray[spinnerSpecialty.getSelectedItemPosition()];
                medicalAppointment.setSpecialty(specialty);

                int day = daysArray[spinnerDay.getSelectedItemPosition()];
                int month = monthsArray[spinnerMonth.getSelectedItemPosition()];
                int year = yearsArray[spinnerYear.getSelectedItemPosition()];
                medicalAppointment.setDate(new Date(year - 1900, month - 1, day)); //no ano tá -1900 e no mês tá -1 pq é assim por padrão da classe

                int hour = hoursArray[spinnerHour.getSelectedItemPosition()];
                int minutes = minutesArray[spinnerMinute.getSelectedItemPosition()];
                medicalAppointment.setTime(new Time(hour, minutes, 0));

                medicalAppointment.setComments(editTextComment.getText().toString());

                db.addMedicalAppointment(medicalAppointment);

                Snackbar.make(view, "Adicionado com sucesso", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                goToMainPage();

            }
        });
    }

    public void goToMainPage(){
        new android.os.Handler().postDelayed(
            new Runnable() {
                public void run() {
                    Intent intent = new Intent(AddActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            },
            1000);
    }
}
