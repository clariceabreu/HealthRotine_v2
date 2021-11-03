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
import com.fsi.healthrotine.Models.Medicine;
import com.fsi.healthrotine.Models.Specialist;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import static com.fsi.healthrotine.DataBase.Columns.TB_MEDICALAPPOINTMENT;
import static com.fsi.healthrotine.DataBase.Columns.TB_MEDICINE;
import static com.fsi.healthrotine.DataBase.Columns.TB_SPECIALIST;

public class AddActivity extends AppCompatActivity {
    private String[] typesArray = new String[]{"Selecione", "Consulta", "Remédio"};
    private String[] specialtiesArray = new String[]{"Selecione","Acupuntura", "Alergia e Imunologia", "Anestesiologia", "Angiologia", "Cancerologia", "Cardiologia", "Cirurgia Cardiovascular", "Cirurgia da Mão", "Cirurgia de Cabeça e Pescoço", "Cirurgia do Aparelho Digestivo", "Cirurgia Geral", "Cirurgia Pediátrica", "Cirurgia Plástica", "Cirurgia Torácica", "Cirurgia Vascular", "Clínica Médica", "Coloproctologia", "Dermatologia", "Endocrinologia e Metabologia", "Endoscopia", "Gastroenterologia", "Genética Médica", "Geriatria", "Ginecologia e Obstetrícia", "Hematologia e Hemoterapia", "Homeopatia", "Infectologia", "Mastologia", "Medicina de Família e Comunidade", "Medicina do Trabalho", "Medicina de Tráfego", "Medicina Esportiva", "Medicina Física e Reabilitação", "Medicina Intensiva", "Medicina Legal e Perícia Médica", "Medicina Nuclear", "Medicina Preventiva e Social", "Nefrologia", "Neurocirurgia", "Neurologia", "Nutrologia", "Oftalmologia", "Ortopedia e Traumatologia", "Otorrinolaringologia", "Patologia", "Patologia Clínica/Medicina Laboratorial", "Pediatria", "Pneumologia", "Psiquiatria", "Radiologia e Diagnóstico por Imagem", "Radioterapia", "Reumatologia", "Urologia"};
    private Integer[] daysArray = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
    private Integer[] monthsArray = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private Integer[] yearsArray = new Integer[201];
    private Integer[] hoursArray = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    private Integer[] minutesArray = new Integer[61];
    private Integer[] frequenciesArray = new Integer[365];
    private String[] frequencyUnitiesArray = new String[]{"horas", "dias"};
    private String[] durationArray = new String[367];
    private List<Specialist> specialists = new ArrayList<>();
    private boolean addNewSpecialist = false;

    private int selectedPosition = 0;

    DataBase db = new DataBase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        specialists = Specialist.getAll(db.getTableCursor(TB_SPECIALIST));
        String[] specialistsNamesArray = new String[specialists.size() + 1];
        specialistsNamesArray[0] = "Selecione";
        for (int i = 0; i < specialists.size(); i++) {
            specialistsNamesArray[i + 1] = specialists.get(i).getName();
        }

        //Data type
        Spinner spinnerType = (Spinner) findViewById(R.id.spinnerType);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typesArray);
        // Specify the layout to use when the list of choices appears
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerType.setAdapter(adapterType);


        //Specialist
        final TextView textViewSpecialist = (TextView) findViewById(R.id.textViewSpecialist);

        final Spinner spinnerSpecialist = (Spinner) findViewById(R.id.spinnerSpecialist);
        ArrayAdapter<String> adapterSpecialist = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, specialistsNamesArray);
        adapterSpecialist.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecialist.setAdapter(adapterSpecialist);

        final EditText editTextSpecialist = (EditText) findViewById(R.id.editTextSpecialist);
        final Button buttonSpecialist = (Button) findViewById(R.id.buttonAddSpecialist);

        //Specialty
        final TextView textViewSpecialy = (TextView) findViewById(R.id.textViewSpecialty);

        final Spinner spinnerSpecialty = (Spinner) findViewById(R.id.spinnerSpecialty);
        ArrayAdapter<String> adapterSpecialty = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, specialtiesArray);
        adapterSpecialty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecialty.setAdapter(adapterSpecialty);

        //Name
        final TextView textViewName = (TextView) findViewById(R.id.textViewName);
        final EditText editTextName = (EditText) findViewById(R.id.editTextName);

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
        for (int i = 0; i < 201; i++) {
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
        final int minute = today.get((Calendar.MINUTE));
        final TextView textViewHour = (TextView) findViewById(R.id.textViewHour);

        final Spinner spinnerHour = (Spinner) findViewById(R.id.spinnerHour);
        ArrayAdapter<Integer> adapterHour = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, hoursArray);
        adapterHour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHour.setAdapter(adapterHour);
        spinnerHour.setSelection(hour);

        for (Integer i = 0; i < 61; i++) {
            minutesArray[i] = i;
        }

        final Spinner spinnerMinute = (Spinner) findViewById(R.id.spinnerMinute);
        ArrayAdapter<Integer> adapterMinute = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, minutesArray);
        adapterMinute.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMinute.setAdapter(adapterMinute);
        spinnerMinute.setSelection(minute);

        //Frequency
        for (Integer i = 1; i < 366; i++) {
            frequenciesArray[i - 1] = i;
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
                selectedPosition = position;

                if (position == 1) {
                    textViewName.setVisibility(View.GONE);
                    editTextName.setVisibility(View.GONE);
                    textViewFrequency.setVisibility(View.GONE);
                    spinnerFrequency.setVisibility(View.GONE);
                    spinnerFrequencyUnity.setVisibility(View.GONE);
                    textViewDuration.setVisibility(View.GONE);
                    spinnerDuration.setVisibility(View.GONE);
                    textViewType.setVisibility(View.GONE);
                    editTextType.setVisibility(View.GONE);
                    textViewDosage.setVisibility(View.GONE);
                    editTextDosage.setVisibility(View.GONE);

                    textViewSpecialist.setVisibility(View.VISIBLE);
                    spinnerSpecialist.setVisibility(View.VISIBLE);
                    buttonSpecialist.setVisibility(View.VISIBLE);
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

                    editTextComment.setHint("Endereço, informações importantes, lembretes...");
                } else if (position == 2) {
                    textViewSpecialy.setVisibility(View.GONE);
                    spinnerSpecialty.setVisibility(View.GONE);
                    spinnerFrequency.setVisibility(View.GONE);
                    spinnerFrequencyUnity.setVisibility(View.GONE);
                    spinnerFrequencyUnity.setVisibility(View.GONE);

                    textViewSpecialist.setVisibility(View.VISIBLE);
                    spinnerSpecialist.setVisibility(View.VISIBLE);
                    buttonSpecialist.setVisibility(View.VISIBLE);
                    textViewName.setVisibility(View.VISIBLE);
                    editTextName.setVisibility(View.VISIBLE);
                    textViewDate.setVisibility(View.VISIBLE);
                    spinnerDay.setVisibility(View.VISIBLE);
                    spinnerMonth.setVisibility(View.VISIBLE);
                    spinnerYear.setVisibility(View.VISIBLE);
                    textViewHour.setVisibility(View.VISIBLE);
                    spinnerHour.setVisibility(View.VISIBLE);
                    spinnerMinute.setVisibility(View.VISIBLE);
                    textViewDuration.setVisibility(View.VISIBLE);
                    spinnerDuration.setVisibility(View.VISIBLE);
                    textViewType.setVisibility(View.VISIBLE);
                    editTextType.setVisibility(View.VISIBLE);
                    textViewDosage.setVisibility(View.VISIBLE);
                    editTextDosage.setVisibility(View.VISIBLE);
                    textViewComment.setVisibility(View.VISIBLE);
                    editTextComment.setVisibility(View.VISIBLE);
                    buttonCreate.setVisibility(View.VISIBLE);

                    editTextComment.setHint("Reações alérgicas, efeitos colaterais...");

                } else {
                    textViewSpecialist.setVisibility(View.GONE);
                    spinnerSpecialist.setVisibility(View.GONE);
                    editTextSpecialist.setVisibility(View.GONE);
                    buttonSpecialist.setVisibility(View.GONE);
                    textViewSpecialy.setVisibility(View.GONE);
                    spinnerSpecialty.setVisibility(View.GONE);
                    textViewName.setVisibility(View.GONE);
                    editTextName.setVisibility(View.GONE);
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

                spinnerDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position != 0) {
                            textViewFrequency.setVisibility(View.VISIBLE);
                            spinnerFrequency.setVisibility(View.VISIBLE);
                            spinnerFrequencyUnity.setVisibility(View.VISIBLE);
                        } else {
                            textViewFrequency.setVisibility(View.GONE);
                            spinnerFrequency.setVisibility(View.GONE);
                            spinnerFrequencyUnity.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Another interface callback
                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        spinnerSpecialist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    Specialist specialistSelected = specialists.get(position - 1);
                    int specialtyIndex = 0;
                    for (String specialty : specialtiesArray) {
                        if (specialty.equals(specialistSelected.getSpecialty())) {
                            break;
                        }
                        specialtyIndex++;
                    }
                    spinnerSpecialty.setSelection(specialtyIndex);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
          });

        buttonSpecialist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerSpecialist.setVisibility(View.GONE);
                editTextSpecialist.setVisibility(View.VISIBLE);
                textViewSpecialy.setVisibility(View.VISIBLE);
                spinnerSpecialty.setVisibility(View.VISIBLE);
                addNewSpecialist = true;
            }
        });

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to do => check if all information are null and if it's show error and don't go to main page

                if (selectedPosition == 1){
                    MedicalAppointment medicalAppointment = new MedicalAppointment();

                    String specialty = null;
                    if (spinnerSpecialty.getSelectedItemPosition() != 0) {
                        specialty = specialtiesArray[spinnerSpecialty.getSelectedItemPosition()];
                        medicalAppointment.setSpecialty(specialty);
                    }

                    Specialist specialist = null;
                    if (addNewSpecialist) {
                        specialist = new Specialist();
                        specialist.setName(editTextSpecialist.getText().toString());
                        specialist.setSpecialty(specialty);
                        int id = db.insertOnTable(TB_SPECIALIST, specialist.getValues());
                        specialist.setId(id);
                        specialists.add(specialist);
                    } else if (spinnerSpecialist.getSelectedItemPosition() != 0){
                        specialist = specialists.get(spinnerSpecialist.getSelectedItemPosition() - 1);
                    }

                    medicalAppointment.setSpecialist(specialist);
                    int day = daysArray[spinnerDay.getSelectedItemPosition()];
                    int month = monthsArray[spinnerMonth.getSelectedItemPosition()];
                    int year = yearsArray[spinnerYear.getSelectedItemPosition()];
                    int hour = hoursArray[spinnerHour.getSelectedItemPosition()];
                    int minutes = minutesArray[spinnerMinute.getSelectedItemPosition()];
                    medicalAppointment.setTime(new Time(hour, minutes, 0));
                    medicalAppointment.setDate(new Date(year - 1900, month - 1, day, hour, minutes));

                    medicalAppointment.setComments(editTextComment.getText().toString());

//                    db.addMedicalAppointment(medicalAppointment);
                    db.insertOnTable(TB_MEDICINE, medicalAppointment.getValues());

                    Snackbar.make(view, "Adicionado com sucesso", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                } else if (selectedPosition == 2){
                    Medicine medicine = new Medicine();

                    Specialist specialist = null;
                    if (addNewSpecialist) {
                        String specialty = null;
                        if (spinnerSpecialty.getSelectedItemPosition() != 0) {
                            specialty = specialtiesArray[spinnerSpecialty.getSelectedItemPosition()];
                        }

                        specialist = new Specialist();
                        specialist.setName(editTextSpecialist.getText().toString());
                        specialist.setSpecialty(specialty);
                        int id = db.insertOnTable(TB_SPECIALIST, specialist.getValues());
                        specialist.setId(id);
                    } else if (spinnerSpecialist.getSelectedItemPosition() != 0){
                        specialist = specialists.get(spinnerSpecialist.getSelectedItemPosition());
                    }

                    medicine.setSpecialist(specialist);
                    medicine.setName(editTextName.getText().toString());
                    int day = daysArray[spinnerDay.getSelectedItemPosition()];
                    int month = monthsArray[spinnerMonth.getSelectedItemPosition()];
                    int year = yearsArray[spinnerYear.getSelectedItemPosition()];
                    int hour =  hoursArray[spinnerHour.getSelectedItemPosition()];
                    int minutes = minutesArray[spinnerMinute.getSelectedItemPosition()];
                    medicine.setDate(new Date(year - 1900, month - 1, day, hour, minutes));
                    medicine.setTime(new Time(hour, minutes, 0));

                    int duration = spinnerDuration.getSelectedItemPosition() - 1; //if uninterrumpt the frequency will be 0
                    medicine.setDuration(duration);


                    if (spinnerFrequency.getVisibility() == View.VISIBLE) {
                        String frequencyUnit = frequencyUnitiesArray[spinnerFrequencyUnity.getSelectedItemPosition()];
                        medicine.setFrequencyUnity(frequencyUnit);

                        int frequency = frequenciesArray[spinnerFrequency.getSelectedItemPosition()];
                        medicine.setFrequency(frequency);
                    } else {
                        medicine.setFrequency(-1);
                    }

                    medicine.setType(editTextType.getText().toString());
                    medicine.setDosage(editTextDosage.getText().toString());
                    medicine.setComments(editTextComment.getText().toString());
//                    db.addMedicine(medicine);
                    db.insertOnTable(TB_MEDICINE, medicine.getValues());

                    Snackbar.make(view, "Adicionado com sucesso", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }

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
