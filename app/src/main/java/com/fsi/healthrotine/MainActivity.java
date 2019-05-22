package com.fsi.healthrotine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fsi.healthrotine.DataBase.DataBase;
import com.fsi.healthrotine.Models.MedicalAppointment;

import java.sql.Date;
import java.sql.Time;
import java.text.CollationElementIterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage;
    DataBase db = new DataBase(this);
    LinearLayout layout;
    Context context;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_historic:
                    mTextMessage.setText(R.string.title_historic);
                    return true;
                case R.id.navigation_rotine:
                    mTextMessage.setText(R.string.title_rotine);
                    return true;
                case R.id.navigation_future:
                    mTextMessage.setText(R.string.title_future);
                    return true;
            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddPage();
            }
        });

        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        layout.setPadding(80,80,80,80);

        List<MedicalAppointment> medicalAppointments = db.getAllMedicalAppointments();

        //Ordena o array a partir da data e hora de forma decrescente
        Collections.sort(medicalAppointments, new Comparator<MedicalAppointment>() {
            @Override
            public int compare(MedicalAppointment m1, MedicalAppointment m2) {
                if (m1.getDate().compareTo(m2.getDate()) > 0){
                    return -1;
                }
                else if (m1.getDate().compareTo(m2.getDate()) == 0){
                    if (m1.getTime().compareTo(m2.getTime()) > 0){
                        return - 1;
                    }
                    if (m1.getTime().compareTo(m2.getTime()) < 0){
                        return 1;
                    }
                    if (m1.getTime().compareTo(m2.getTime()) == 0){
                        return 0;
                    }
                }
                return 1;
            }
        });

        for(MedicalAppointment appointment : medicalAppointments){
            Date today = new Date(Calendar.getInstance().getTime().getTime());
            if (today.compareTo(appointment.getDate()) == 1){
                CardView card = new CardView(context);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(50,50,50, 50);
                card.setLayoutParams(params);

                card.setRadius(9);
                card.setContentPadding(20, 15, 20, 15);
                card.setCardBackgroundColor(Color.parseColor("#FFC6D6C3"));
                card.setMaxCardElevation(15);
                card.setCardElevation(9);

                LinearLayout innerLayout = new LinearLayout(context);
                innerLayout.setLayoutParams(params);
                innerLayout.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                String titleText =  "<b>Consulta</b> ";
                title.setText(Html.fromHtml(titleText));
                title.setTextSize(15);
                title.setPadding(0,0,0,20);
                innerLayout.addView(title);


                if (appointment.getSpecialty() != "Selecione"){ //por algum motivo esse if não tá funcionando
                    TextView specialty = new TextView(context);
                    String specialtyText =  "<b>Especialidade: </b> " + appointment.getSpecialty();
                    specialty.setText(Html.fromHtml(specialtyText));
                    innerLayout.addView(specialty);
                }

                TextView date = new TextView(context);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateText =  "<b>Data: </b> " + dateFormat.format(appointment.getDate());
                date.setText(Html.fromHtml(dateText));
                innerLayout.addView(date);

                TextView time = new TextView(context);
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String timeText =  "<b>Hora: </b> " + timeFormat.format(appointment.getTime());
                time.setText(Html.fromHtml(timeText));
                innerLayout.addView(time);

                TextView comments = new TextView(context);
                String commentsText =  "<b>Comentários: </b> " + appointment.getComments();
                comments.setText(Html.fromHtml(commentsText));
                innerLayout.addView(comments);

                card.addView(innerLayout);
                layout.addView(card);
            }
        }
    }

    public void goToAddPage(){
        Intent intent = new Intent(this, AddActivity.class);
        startActivity(intent);
    }

}
