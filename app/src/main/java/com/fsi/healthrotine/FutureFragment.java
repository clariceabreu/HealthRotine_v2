package com.fsi.healthrotine;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.fsi.healthrotine.Models.Exam;
import com.fsi.healthrotine.Models.Vaccine;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fsi.healthrotine.DataBase.DataBase;
import com.fsi.healthrotine.Models.CardObject;
import com.fsi.healthrotine.Models.MedicalAppointment;
import com.fsi.healthrotine.Models.Medicine;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.fsi.healthrotine.DataBase.Columns.*;



/**
 * A simple {@link Fragment} subclass.
 */
public class FutureFragment extends Fragment {
    private Context context;
    private DataBase db;
    private LinearLayout layout;
    private String[] typesArray = new String[]{"Todos", "Consulta", "Remédio", "Exame", "Vacina"};
    private String[] specialtiesArray = new String[]{"Selecione","Acupuntura", "Alergia e Imunologia", "Anestesiologia", "Angiologia", "Cancerologia", "Cardiologia", "Cirurgia Cardiovascular", "Cirurgia da Mão", "Cirurgia de Cabeça e Pescoço", "Cirurgia do Aparelho Digestivo", "Cirurgia Geral", "Cirurgia Pediátrica", "Cirurgia Plástica", "Cirurgia Torácica", "Cirurgia Vascular", "Clínica Médica", "Coloproctologia", "Dermatologia", "Endocrinologia e Metabologia", "Endoscopia", "Gastroenterologia", "Genética Médica", "Geriatria", "Ginecologia e Obstetrícia", "Hematologia e Hemoterapia", "Homeopatia", "Infectologia", "Mastologia", "Medicina de Família e Comunidade", "Medicina do Trabalho", "Medicina de Tráfego", "Medicina Esportiva", "Medicina Física e Reabilitação", "Medicina Intensiva", "Medicina Legal e Perícia Médica", "Medicina Nuclear", "Medicina Preventiva e Social", "Nefrologia", "Neurocirurgia", "Neurologia", "Nutrologia", "Oftalmologia", "Ortopedia e Traumatologia", "Otorrinolaringologia", "Patologia", "Patologia Clínica/Medicina Laboratorial", "Pediatria", "Pneumologia", "Psiquiatria", "Radiologia e Diagnóstico por Imagem", "Radioterapia", "Reumatologia", "Urologia"};


    public FutureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        db = new DataBase(context);

        View view = inflater.inflate(R.layout.fragment_future, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAddPage();
            }
        });
        FloatingActionButton profileBtn = view.findViewById(R.id.buttonProfile);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, new ProfileFragment());
                fragmentTransaction.commit();
            }
        });

        FloatingActionButton exportBtn = view.findViewById(R.id.buttonExport);
        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context, ExportActivity.class);
                startActivity(intent);
            }
        });
        layout = (LinearLayout) view.findViewById(R.id.layout);
        layout.setPadding(80,80,80,80);

        addCardViews(null, null);

        final Button buttonFilter = (Button) view.findViewById(R.id.buttonFilter);

        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                View alertView = getLayoutInflater().inflate(R.layout.dialog_layout, null);

                final TextView textViewType = alertView.findViewById(R.id.dialogTextViewType);

                final Spinner spinnerType = alertView.findViewById(R.id.dialogSpinnerType);
                ArrayAdapter<String> adapterType = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, typesArray);
                adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerType.setAdapter(adapterType);


                final TextView textViewSpecialty = (TextView) alertView.findViewById(R.id.dialogTextViewType);

                final Spinner spinnerSpecialty = (Spinner) alertView.findViewById(R.id.dialogSpinnerSpecialty);
                ArrayAdapter<String> adapterSpecialty = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, specialtiesArray);
                adapterSpecialty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerSpecialty.setAdapter(adapterSpecialty);


                builder.setPositiveButton(
                        "Filtrar",

                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id) {
                                layout.removeAllViews();

                                Button buttonFilter = (Button) view.findViewById(R.id.buttonFilter);
                                layout.addView(buttonFilter);

                                String type = null;
                                if (spinnerType.getSelectedItemPosition() == 1){
                                    type = "medicalAppointment";
                                } else if (spinnerType.getSelectedItemPosition() == 2){
                                    type = "medicine";
                                }else if (spinnerType.getSelectedItemPosition() == 3){
                                    type = "exam";
                                }else if (spinnerType.getSelectedItemPosition() == 4){
                                    type = "vaccine";
                                }


                                String specialty = null;
                                if (spinnerSpecialty.getSelectedItemPosition() != 0){
                                    specialty = specialtiesArray[spinnerSpecialty.getSelectedItemPosition()];
                                }

                                addCardViews(type, specialty);

                                dialog.cancel();
                            }
                        });

                builder.setNegativeButton(
                        "Cancelar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                final AlertDialog alert = builder.create();

                alert.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#008577"));
                        alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#008577"));
                    }
                });


                alert.setView(alertView, 30, 10, 30, 10);
                alert.show();


            }
        });


        return view;
    }

    public void addCardViews(String type, String speacilaty){
        Date today = new Date(Calendar.getInstance().getTime().getTime());
        List<CardObject> cardObjects = new ArrayList<CardObject>();

        List<Medicine> medicines = new ArrayList<Medicine>();
        if (speacilaty == null && (type == "medicine" || type == null)) {
            medicines = Medicine.getAll(db.getTableCursor(TB_MEDICINE));
//            medicines = db.getAllMedicines();
            for (Medicine medicine : medicines) {
                if (today.compareTo(medicine.getDate()) == -1) {
                    CardObject cardObject = new CardObject();
                    cardObject.setId(medicine.getId());
                    cardObject.setType("Medicine");
                    cardObject.setDate(medicine.getDate());
                    cardObject.setTime(medicine.getTime());

                    cardObjects.add(cardObject);
                }
            }
        }

        List<MedicalAppointment> medicalAppointments = new ArrayList<MedicalAppointment>();
        if (type == "medicalAppointment" || type == null) {
              medicalAppointments = MedicalAppointment.getAll(db.getTableCursor(TB_MEDICALAPPOINTMENT), db);
            for (MedicalAppointment appointment : medicalAppointments) {
                if (today.compareTo(appointment.getDate()) == -1 && (speacilaty == null || speacilaty.equals(appointment.getSpecialty()))) {
                    CardObject cardObject = new CardObject();
                    cardObject.setId(appointment.getId());
                    cardObject.setType("MedicalAppointment");
                    cardObject.setDate(appointment.getDate());
                    cardObject.setTime(appointment.getTime());

                    cardObjects.add(cardObject);
                }
            }
        }

        List<Exam> exams = new ArrayList<Exam>();
        if (type == "exam" || type == null) {
            exams = Exam.getAll(db.getTableCursor(TB_EXAM), db);
            for (Exam exam : exams) {
                if (today.compareTo(exam.getDate()) == -1) {
                    CardObject cardObject = new CardObject();
                    cardObject.setId(exam.getId());
                    cardObject.setType("Exam");
                    cardObject.setDate(exam.getDate());
                    cardObject.setTime(exam.getTime());

                    cardObjects.add(cardObject);
                }
            }
        }

        List<Vaccine> vaccines = new ArrayList<Vaccine>();
        if (type == "vaccine" || type == null) {
            vaccines = Vaccine.getAll(db.getTableCursor(TB_VACCINE));
            for (Vaccine vaccine : vaccines) {
                if (today.compareTo(vaccine.getDate()) == -1) {
                    CardObject cardObject = new CardObject();
                    cardObject.setId(vaccine.getId());
                    cardObject.setType("Vaccine");
                    cardObject.setDate(vaccine.getDate());

                    cardObjects.add(cardObject);
                }
            }
        }

        //Ordena o array a partir da data e hora de forma decrescente
        Collections.sort(cardObjects, new Comparator<CardObject>() {
            @Override
            public int compare(CardObject o1, CardObject o2) {
                if (o1.getDate().compareTo(o2.getDate()) > 0){
                    return -1;
                }
                else if (o1.getDate().compareTo(o2.getDate()) == 0){
                    if (o1.getTime().compareTo(o2.getTime()) > 0){
                        return - 1;
                    }
                    if (o1.getTime().compareTo(o2.getTime()) < 0){
                        return 1;
                    }
                    if (o1.getTime().compareTo(o2.getTime()) == 0){
                        return 0;
                    }
                }
                return 1;
            }
        });

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
            card.setCardBackgroundColor(Color.parseColor("#FFC6D6C3"));
            card.setMaxCardElevation(15);
            card.setCardElevation(9);

            LinearLayout innerLayout = new LinearLayout(context);
            innerLayout.setLayoutParams(params);
            innerLayout.setOrientation(LinearLayout.VERTICAL);

            String titleText = null;
            String dateText = null;
            String specialtyText = null;
            String timeText = null;
            String nameText = null;
            String durationText = null;
            String frequencyText = null;
            String medicineTypeText = null;
            String dosageText = null;
            String commentsText = null;
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
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
                    nameText = "<b>Nome: </b> " + medicine.getName();
                }
                dateText =  "<b>Data: </b> " + dateFormat.format(medicine.getDate());
                timeText =  "<b>Hora: </b> " + timeFormat.format(medicine.getTime());
                if (medicine.getDuration() > 0) {
                    durationText = "<b>Duração: </b> " + medicine.getDuration() + " dias";
                } else if (medicine.getDuration() == 0 ){
                    durationText = "<b>Duração: </b> ininterrupto";
                }
                if (medicine.getFrequency() != -1) {
                    frequencyText = "<b>Frequência: </b>" + medicine.getFrequency() + " em " + medicine.getFrequency() + " " + medicine.getFrequencyUnity();
                }
                if (medicine.getType() != null && medicine.getType().length() != 0) {
                    medicineTypeText = "<b>Tipo: </b> " + medicine.getType();
                }
                if (medicine.getDosage() != null && medicine.getDosage().length() != 0) {
                    dosageText = "<b>Dosagem: </b> " + medicine.getDosage();
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

                if (medicalAppointment.getSpecialty() != null && medicalAppointment.getSpecialty().length() != 0) {
                    specialtyText = "<b>Especialidade: </b> " + medicalAppointment.getSpecialty();
                }
                dateText =  "<b>Data: </b> " + dateFormat.format(medicalAppointment.getDate());
                timeText =  "<b>Hora: </b> " + timeFormat.format(medicalAppointment.getTime());
                if (medicalAppointment.getComments() != null && medicalAppointment.getComments().length() != 0) {
                    commentsText = "<b>Comentários: </b> " + medicalAppointment.getComments();
                }
            }else if (obj.getType() == "Exam") {
                titleText = "<b>Exame</b> ";

                Exam exam = null;
                for (Exam a : exams){
                    if (a.getId() == obj.getId()){
                        exam = a;
                        break;
                    }
                }
                if (exam.getComments() != null && exam.getComments().length() != 0) {
                    commentsText = "<b>Comentários: </b> " + exam.getComments();
                }
            }else if (obj.getType() == "Vaccine") {
                titleText = "<b>Vacina</b> ";
            }

            TextView title = new TextView(context);
            title.setText(Html.fromHtml(titleText));
            title.setTextSize(15);
            title.setPadding(0,0,0,20);
            innerLayout.addView(title);

            if(obj.getType() == "MedicalAppointment" && specialtyText != null){
                TextView specialty = new TextView(context);
                specialty.setText(Html.fromHtml(specialtyText));
                innerLayout.addView(specialty);
            }

            if (obj.getType() == "Medicine" && nameText != null){
                TextView name = new TextView(context);
                name.setText(Html.fromHtml(nameText));
                innerLayout.addView(name);
            }

            TextView date = new TextView(context);
            date.setText(Html.fromHtml(dateText));
            innerLayout.addView(date);

            TextView time = new TextView(context);
            time.setText(Html.fromHtml(timeText));
            innerLayout.addView(time);

            if (obj.getType() == "Medicine"){
                if (durationText != null) {
                    TextView duration = new TextView(context);
                    duration.setText(Html.fromHtml(durationText));
                    innerLayout.addView(duration);
                }
                if(frequencyText != null) {
                    TextView frequency = new TextView(context);
                    frequency.setText(Html.fromHtml(frequencyText));
                    innerLayout.addView(frequency);
                }
                if (medicineTypeText != null) {
                    TextView medicineType = new TextView(context);
                    medicineType.setText(Html.fromHtml(medicineTypeText));
                    innerLayout.addView(medicineType);
                }
                if (dosageText != null) {
                    TextView dosage = new TextView(context);
                    dosage.setText(Html.fromHtml(dosageText));
                    innerLayout.addView(dosage);
                }
            }

            if (commentsText != null) {
                TextView comments = new TextView(context);
                comments.setText(Html.fromHtml(commentsText));
                innerLayout.addView(comments);
            }

            card.addView(innerLayout);
            layout.addView(card);
        }
    }

    public void goToAddPage(){
        Intent intent = new Intent(context, AddActivity.class);
        startActivity(intent);
    }

}
