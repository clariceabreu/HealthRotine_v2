package com.fsi.healthrotine;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SymbolTable;
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
import com.fsi.healthrotine.Models.CardObject;
import com.fsi.healthrotine.Models.MedicalAppointment;
import com.fsi.healthrotine.Models.Medicine;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class RotineFragment extends Fragment {
    private DataBase db;
    private Context context;


    public RotineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = container.getContext();
        db = new DataBase(context);

        View view = inflater.inflate(R.layout.fragment_rotine, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddPage();
            }
        });

        List<CardObject> cardObjects = new ArrayList<CardObject>();

        Calendar today  = Calendar.getInstance();
        int day = today.get(Calendar.DAY_OF_MONTH);
        int month = today.get(Calendar.MONTH);
        int year = today.get(Calendar.YEAR);
        Date dateLimitStart = new Date(year - 1900, month, day, 00, 00, 00);
        Date dateLimitEnd = new Date(year - 1900, month, day, 23, 59, 59);

        List<Medicine> medicines = db.getAllMedicines();
        for (Medicine medicine : medicines) {
            List<Date> administrationTimes = medicine.getAdministrationTimes();

            if (administrationTimes != null) {
                for (Date admTime : medicine.getAdministrationTimes()){

                    if (dateLimitStart.compareTo(admTime) == -1 && dateLimitEnd.compareTo(admTime) == 1){
                        System.out.println("medicine Id: " + medicine.getId() + " admTime :" + admTime);

                        CardObject cardObject = new CardObject();
                        cardObject.setId(medicine.getId());
                        cardObject.setType("Medicine");
                        cardObject.setTime(new Time(admTime.getHours(), admTime.getMinutes(), 0));

                        cardObjects.add(cardObject);
                    }
                }
            }

        }


        List<MedicalAppointment> medicalAppointments = db.getAllMedicalAppointments();
        if (medicalAppointments.size() > 0){
            for (MedicalAppointment medicalAppointment : medicalAppointments){
                
                if (dateLimitStart.compareTo(medicalAppointment.getDate()) == 0){
                    CardObject cardObject = new CardObject();
                    cardObject.setId(medicalAppointment.getId());
                    cardObject.setType("MedicalAppointment");
                    cardObject.setTime(medicalAppointment.getTime());

                    cardObjects.add(cardObject);
                }

            }
        }


        //Order the array ascending
        Collections.sort(cardObjects, new Comparator<CardObject>() {
            @Override
            public int compare(CardObject o1, CardObject o2) {
                if (o1.getTime().compareTo(o2.getTime()) > 0){
                    return 1;
                }
                else if (o1.getTime().compareTo(o2.getTime()) == 0){
                    return 0;
                }
                return -1;
            }
        });

        LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
        layout.setPadding(80,80,80,80);

        for(CardObject obj : cardObjects){
            CardView card = new CardView(context);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(50,50,50, 50);
            card.setLayoutParams(params);

            card.setRadius(9);
            card.setContentPadding(20, 15, 20, 15);
            card.setCardBackgroundColor(Color.parseColor("#84a27f"));
            card.setMaxCardElevation(15);
            card.setCardElevation(9);

            LinearLayout innerLayout = new LinearLayout(context);
            innerLayout.setLayoutParams(params);
            innerLayout.setOrientation(LinearLayout.VERTICAL);

            String titleText = null;
            String timeText = null;
            String nameText = null;
            String commentsText = null;
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            if (obj.getType() == "Medicine") {
                titleText =  "<b>Remédio</b> ";

                Medicine medicine = null;
                for (Medicine m : medicines){
                    if (m.getId() == obj.getId()){
                        medicine = m;
                        break;
                    }
                }

                if (medicine.getName() != null && medicine.getName().length() != 0) {
                    nameText = "<b>Name: </b> " + medicine.getName();
                }
                if (medicine.getComments() != null && medicine.getComments().length() != 0) {
                    commentsText = "<b>Comentários: </b> " + medicine.getComments();
                }
            } else if (obj.getType() == "MedicalAppointment") {
                titleText = "<b>Consulta</b> ";

                MedicalAppointment medicalAppointment = null;
                for (MedicalAppointment a : medicalAppointments){
                    if (a.getId() == obj.getId()){
                        medicalAppointment = a;
                        break;
                    }
                }
                if (medicalAppointment.getComments() != null && medicalAppointment.getComments().length() != 0) {
                    commentsText = "<b>Comentários: </b> " + medicalAppointment.getComments();
                }
            }

            TextView title = new TextView(context);
            title.setText(Html.fromHtml(titleText));
            title.setTextSize(15);
            title.setPadding(0,0,0,20);
            innerLayout.addView(title);

            if (obj.getType() == "Medicine" && nameText != null) {
                TextView name = new TextView(context);
                name.setText(Html.fromHtml(nameText));
                innerLayout.addView(name);
            }

            TextView time = new TextView(context);
            timeText =  "<b>Hora: </b> " + timeFormat.format(obj.getTime());
            time.setText(Html.fromHtml(timeText));
            innerLayout.addView(time);

            if (commentsText != null){
                TextView comments = new TextView(context);
                comments.setText(Html.fromHtml(commentsText));
                innerLayout.addView(comments);
            }


            card.addView(innerLayout);
            layout.addView(card);
        }


        return view;
    }

    public void goToAddPage(){
        Intent intent = new Intent(context, AddActivity.class);
        startActivity(intent);
    }

}
