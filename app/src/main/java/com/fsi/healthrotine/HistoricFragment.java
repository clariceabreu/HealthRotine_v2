package com.fsi.healthrotine;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsi.healthrotine.DataBase.DataBase;
import com.fsi.healthrotine.Models.MedicalAppointment;
import com.fsi.healthrotine.Models.Medicine;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoricFragment extends Fragment {
    private DataBase db;
    private Context context;


    public HistoricFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        db = new DataBase(context);

        View view = inflater.inflate(R.layout.fragment_historic, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddPage();
            }
        });

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
        layout.setPadding(80,80,80,80);

        System.out.println("teste 2");

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

                //to do
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

        return view;
    }

    public void goToAddPage(){
        Intent intent = new Intent(context, AddActivity.class);
        startActivity(intent);
    }

}
