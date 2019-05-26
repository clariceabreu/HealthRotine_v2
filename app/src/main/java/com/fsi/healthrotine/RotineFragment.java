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
        Date dateLimit = new Date(year - 1900, month, day, 23, 59, 59);

        List<Medicine> medicines = db.getAllMedicines();
        for (Medicine medicine : medicines) {
            List<Date> administrationTimes = medicine.getAdministrationTimes();

            if (administrationTimes != null && dateLimit.compareTo(medicine.getEndDate()) == -1) {
                for (Date admTime : medicine.getAdministrationTimes()){

                    if (dateLimit.compareTo(admTime) == 1){
                        CardObject cardObject = new CardObject();
                        cardObject.setType("Medicine");
                        cardObject.setTime(medicine.getTime());

                        cardObjects.add(cardObject);
                    }
                }
            }

        }

        List<MedicalAppointment> medicalAppointments = db.getAllMedicalAppointments();
        if (medicalAppointments.size() > 0){
            for (MedicalAppointment medicalAppointment : medicalAppointments){

                if (dateLimit.compareTo(medicalAppointment.getDate()) == 1){
                    CardObject cardObject = new CardObject();
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

        System.out.println(cardObjects);

        /*LinearLayout layout = (LinearLayout) view.findViewById(R.id.layout);
        layout.setPadding(80,80,80,80);

        for(Medicine medicine : medicines){
            //Date today = new Date(Calendar.getInstance().getTime().getTime());
            if (today.compareTo(medicine.getDate()) == 1){
                CardView card = new CardView(context);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(50,50,50, 50);
                card.setLayoutParams(params);

                card.setRadius(9);
                card.setContentPadding(20, 15, 20, 15);
                card.setCardBackgroundColor(Color.parseColor("#FF7F7F"));
                card.setMaxCardElevation(15);
                card.setCardElevation(9);

                LinearLayout innerLayout = new LinearLayout(context);
                innerLayout.setLayoutParams(params);
                innerLayout.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(context);
                String titleText =  "<b>Remédio</b> ";
                title.setText(Html.fromHtml(titleText));
                title.setTextSize(15);
                title.setPadding(0,0,0,20);
                innerLayout.addView(title);


                TextView date = new TextView(context);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateText =  "<b>Nome: </b> " + dateFormat.format(medicine.getDate());
                date.setText(Html.fromHtml(dateText));
                innerLayout.addView(date);

                TextView time = new TextView(context);
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String timeText =  "<b>Hora: </b> " + timeFormat.format(medicine.getTime());
                time.setText(Html.fromHtml(timeText));
                innerLayout.addView(time);


                card.addView(innerLayout);
                layout.addView(card);
            }
        }*/


        return view;
    }

    public void goToAddPage(){
        Intent intent = new Intent(context, AddActivity.class);
        startActivity(intent);
    }

}
