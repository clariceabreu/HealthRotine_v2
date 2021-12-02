package com.fsi.healthrotine;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.fsi.healthrotine.ActivityHelpers.AddActivity.AddActivityComponents;
import com.fsi.healthrotine.ActivityHelpers.AddActivity.ExamHelpers;
import com.fsi.healthrotine.ActivityHelpers.AddActivity.FilesHelper;
import com.fsi.healthrotine.ActivityHelpers.AddActivity.MedicalAppointmentHelper;
import com.fsi.healthrotine.ActivityHelpers.AddActivity.MedicineHelper;
import com.fsi.healthrotine.ActivityHelpers.AddActivity.VaccineHelper;
import com.fsi.healthrotine.DataBase.DataBase;
import com.fsi.healthrotine.Models.Specialist;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static com.fsi.healthrotine.DataBase.Columns.TB_SPECIALIST;

public class AddActivity extends AppCompatActivity {

    private static final int PICK_FILE_REQUEST = 100;
    private static final String TAG = "AddActivity";

    private List<Specialist> specialists = new ArrayList<>();
    private boolean addNewSpecialist = false;

    private int selectedPosition = 0;

    TextView textViewUploaded;
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
        TextView toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText("");
        ImageButton imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        toolbarTitle.setText("");
        final AddActivityComponents components = new AddActivityComponents(this);
        components.buildComponents(specialistsNamesArray);
        components.hideAllComponents();

        Spinner spinnerType = (Spinner) components.getComponent("spinnerType");
        Spinner spinnerDuration = (Spinner) components.getComponent("spinnerDuration");
        Spinner spinnerSpecialist = (Spinner) components.getComponent("spinnerSpecialist");
        Button buttonSpecialist = (Button) components.getComponent("buttonSpecialist");
        Button buttonCreate = (Button) components.getComponent("buttonCreate");
        FloatingActionButton uploadFile = (FloatingActionButton) components.getComponent("uploadFile");
        textViewUploaded = (TextView) components.getComponent("textViewUploaded");

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
                        "textViewSpecialty",
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
                        e.printStackTrace();
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
                        e.printStackTrace();
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

        uploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upload(view);
            }
        });
    }

    private void goToProfilePage(){
        Intent intent = new Intent(this, ProfileFragment.class);
        startActivity(intent);
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


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {
        if(requestCode == PICK_FILE_REQUEST){
            if(resultData == null){
                //no data present
                return;
            }

            // Get the Uri of the selected file
            Uri selectedFileUri = resultData.getData();
            Log.e(TAG, "selected File Uri: "+selectedFileUri );
            // Get the path
            String selectedFilePath = FilesHelper.getPath(this, selectedFileUri);
            Log.e(TAG,"Selected File Path:" + selectedFilePath);


            if(selectedFilePath != null && !selectedFilePath.equals("")){
                try {
                    String[] pathSplit = selectedFilePath.split("/");
                    FileOutputStream fileout = openFileOutput(pathSplit[pathSplit.length - 1], MODE_PRIVATE);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                    FileInputStream fileIn=new FileInputStream (new File(selectedFilePath));
                    outputWriter.write(String.valueOf(fileIn));
                    outputWriter.close();
                    textViewUploaded.setText(selectedFilePath);
                    //display file saved message
                    Toast.makeText(getBaseContext(), "Arquivo carregado com sucesso!",
                            Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Erro ao salvar arquivo :(",
                            Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }else{
                Toast.makeText(this,"Erro ao salvar arquivo :(",Toast.LENGTH_SHORT).show();
            }
        }

        super.onActivityResult(requestCode, resultCode, resultData);
    }

    // This method will get call when user click on upload file button
    public void upload(View view) {

        Intent intent;
        if (android.os.Build.MANUFACTURER.equalsIgnoreCase("samsung")) {
            intent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
            intent.putExtra("CONTENT_TYPE", "*/*");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
        } else {

            String[] mimeTypes =
                    {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                            "application/vnd.ms-powerpoint", "application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                            "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                            "text/plain",
                            "application/pdf",
                            "application/zip", "application/vnd.android.package-archive"};


            intent = new Intent(Intent.ACTION_GET_CONTENT); // or ACTION_OPEN_DOCUMENT
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            Log.e(TAG, "uploadFile: else" );
        }
        startActivityForResult(Intent.createChooser(intent,"Escolha o arquivo.."),PICK_FILE_REQUEST);
    }
}
