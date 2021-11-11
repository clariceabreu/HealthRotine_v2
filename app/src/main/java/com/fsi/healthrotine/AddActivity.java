package com.fsi.healthrotine;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.fsi.healthrotine.ActivityHelpers.AddActivity.AddActivityComponents;
import com.fsi.healthrotine.ActivityHelpers.AddActivity.ExamHelpers;
import com.fsi.healthrotine.ActivityHelpers.AddActivity.MedicalAppointmentHelper;
import com.fsi.healthrotine.ActivityHelpers.AddActivity.MedicineHelper;
import com.fsi.healthrotine.ActivityHelpers.AddActivity.VaccineHelper;
import com.fsi.healthrotine.DataBase.DataBase;
import com.fsi.healthrotine.Models.Specialist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.fsi.healthrotine.DataBase.Columns.TB_SPECIALIST;

public class AddActivity extends AppCompatActivity {
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

        final AddActivityComponents components = new AddActivityComponents(this);
        components.buildComponents(specialistsNamesArray);
        components.hideAllComponents();

        Spinner spinnerType = (Spinner) components.getComponent("spinnerType");
        Spinner spinnerDuration = (Spinner) components.getComponent("spinnerDuration");
        Spinner spinnerSpecialist = (Spinner) components.getComponent("spinnerSpecialist");
        Button buttonSpecialist = (Button) components.getComponent("buttonSpecialist");
        Button buttonCreate = (Button) components.getComponent("buttonCreate");

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedPosition = position;

                components.hideAllComponents();

                if (position == 1) {
                    HashSet<String> componentsToShow = MedicalAppointmentHelper.getComponents();
                    components.showComponents(componentsToShow);

                    HashMap<String, String> hints = MedicalAppointmentHelper.getHints();
                    components.setHints(hints);
                } else if (position == 2) {
                    HashSet<String> componentsToShow = MedicineHelper.getComponents();
                    components.showComponents(componentsToShow);

                    HashMap<String, String> hints = MedicineHelper.getHints();
                    components.setHints(hints);
                } else if (position == 3) {
                    HashSet<String> componentsToShow = VaccineHelper.getComponents();
                    components.showComponents(componentsToShow);
                } else if (position == 4) {
                    HashSet<String> componentsToShow = ExamHelpers.getComponents();
                    components.showComponents(componentsToShow);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        spinnerDuration.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashSet<String> updateComponents = new HashSet<>(Arrays.asList(
                        "textViewFrequency",
                        "spinnerFrequency",
                        "spinnerFrequencyUnity"
                ));

                if (position != 0) {
                    components.showComponents(updateComponents);
                } else {
                    components.hideComponents(updateComponents);
                }
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
                    for (String specialty : AddActivityComponents.specialtiesArray) {
                        if (specialty.equals(specialistSelected.getSpecialty())) {
                            break;
                        }
                        specialtyIndex++;
                    }
                    Spinner spinnerSpecialty = (Spinner) components.getComponent("spinnerSpecialty");
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
                HashSet<String> componentsToShow = new HashSet<>(Arrays.asList(
                        "editTextSpecialist",
                        "textViewSpecialy",
                        "spinnerSpecialty"
                ));
                HashSet<String> componentsToHide = new HashSet<>(Arrays.asList("spinnerSpecialist"));

                components.showComponents(componentsToShow);
                components.hideComponents(componentsToHide);

                addNewSpecialist = true;
            }
        });

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //to do => check if all information are null and if it's show error and don't go to main page
                if (selectedPosition == 1){
                    try {
                        MedicalAppointmentHelper.saveMedicalAppointment(components, db, addNewSpecialist, specialists);

                        Snackbar.make(view, "Adicionado com sucesso", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        goToMainPage();
                    } catch (Exception e) {
                        Snackbar.make(view, "Erro ao adicionar consulta", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                } else if (selectedPosition == 2){
                    try {
                        MedicineHelper.saveMedicine(components, db, addNewSpecialist, specialists);

                        Snackbar.make(view, "Adicionado com sucesso", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        goToMainPage();
                    } catch (Exception e) {
                        Snackbar.make(view, "Erro ao adicionar consulta", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                } else if (selectedPosition == 3) {
                    try {
                        MedicineHelper.saveMedicine(components, db, addNewSpecialist, specialists);

                        Snackbar.make(view, "Adicionado com sucesso", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        goToMainPage();
                    } catch (Exception e) {
                        Snackbar.make(view, "Erro ao adicionar consulta", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                } else if (selectedPosition == 4) {
                    try {
                        ExamHelpers.saveExam(components, db, addNewSpecialist, specialists);

                        Snackbar.make(view, "Adicionado com sucesso", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        goToMainPage();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Snackbar.make(view, "Erro ao adicionar exame", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
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
