package com.fsi.healthrotine;

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

public class ExportActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                System.out.println("oi");
            }
        });

    }


}
