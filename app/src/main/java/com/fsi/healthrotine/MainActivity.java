package com.fsi.healthrotine;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    private String[] typesArray = new String[]{"Selecione", "Consulta", "Exame", "Remédio"};
    private String[] specialtiesArray = new String[]{"Selecione","Clínica Médica", "Oftomologia", "Otorrinologia", "Pediatria", "Ginebologia"};
    private Integer[] daysArray = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
    private Integer[] monthsArray = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private Integer[] yearsArray = new Integer[200];
    private Integer[] hoursArray = new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23};
    private Integer[] minutesArray = new Integer[61];

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Tipo
        Spinner spinnerType = (Spinner) findViewById(R.id.spinnerType);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, typesArray);
        // Specify the layout to use when the list of choices appears
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerType.setAdapter(adapterType);


        //Especialidade
        final TextView textViewSpecialy = (TextView) findViewById(R.id.textViewSpecialty);

        final Spinner spinnerSpecialty = (Spinner) findViewById(R.id.spinnerSpecialty);
        ArrayAdapter<String> adapterSpecialty = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, specialtiesArray);
        adapterSpecialty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSpecialty.setAdapter(adapterSpecialty);


        //Data
        final TextView textViewDate = (TextView) findViewById(R.id.textViewDate);

        final Spinner spinnerDay = (Spinner) findViewById(R.id.spinnerDay);
        ArrayAdapter<Integer> adapterDay = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, daysArray);
        adapterDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.setAdapter(adapterDay);

        final Spinner spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        ArrayAdapter<Integer> adapterMonth = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, monthsArray);
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonth.setAdapter(adapterMonth);

        int y = 1900;
        for (int i = 0; i < 100; i++){
            yearsArray[i] = (y);
            y++;
        }

        final Spinner spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        ArrayAdapter<Integer> adapterYear = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, yearsArray);
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(adapterYear);


        //Hora
        final TextView textViewHour = (TextView) findViewById(R.id.textViewHour);

        final Spinner spinnerHour = (Spinner) findViewById(R.id.spinnerHour);
        ArrayAdapter<Integer> adapterHour = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, hoursArray);
        adapterHour.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHour.setAdapter(adapterHour);

        for (Integer i = 0; i < 61; i++){
            minutesArray[i] = i;
        }

        final Spinner spinnerMinute = (Spinner) findViewById(R.id.spinnerMinute);
        ArrayAdapter<Integer> adapterMinute = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, minutesArray);
        adapterMinute.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMinute.setAdapter(adapterMinute);


        //Comentário
        final TextView textViewComment = (TextView) findViewById(R.id.textViewComment);
        final EditText editTextComment = (EditText) findViewById(R.id.editTextComment);


        //Botão
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

                } else {
                    textViewSpecialy.setVisibility(View.INVISIBLE);
                    spinnerSpecialty.setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

}
