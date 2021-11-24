package com.fsi.healthrotine;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.fsi.healthrotine.DataBase.DataBase;
import com.fsi.healthrotine.Models.Patient;

import org.w3c.dom.Text;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.fsi.healthrotine.DataBase.Columns.*;

import com.fsi.healthrotine.DataBase.DataBase;

public class ProfileFragment extends Fragment  {
    private DataBase db;
    private Context context;
    private LinearLayout layout;
    private ImageButton imgBack;
    private Button btnNome;
    private Button btnIdade;
    private Button btnPeso;
    private Button btnAltura;
    private Button btnAlergias;
    private Button btnSus;
    private Button btnPlano;
    private Button btnEmail;
    private Button btnContatos;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        context = container.getContext();
        db = new DataBase(context);

        View view = inflater.inflate(R.layout.activity_patient, container, false);


        imgBack = (ImageButton) view.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });

        btnNome = view.findViewById(R.id.btnNome);
        final EditText nomeImput = new EditText(context);
        setEditText(view, btnNome, nomeImput);

        btnIdade = view.findViewById(R.id.btnIdade);
        final EditText idadeImput = new EditText(context);
        setEditText(view, btnIdade, idadeImput);

        setTipo_Sanguineo(view);

        btnPeso = view.findViewById(R.id.btnPeso);
        final EditText pesoImput = new EditText(context);
        setEditText(view, btnPeso, pesoImput);

        btnAltura = view.findViewById(R.id.btnAltura);
        final EditText alturaImput = new EditText(context);
        setEditText(view, btnAltura, alturaImput);

        btnAlergias = view.findViewById(R.id.btnAlergias);
        final EditText alergiaImput = new EditText(context);
        setEditText(view, btnAlergias, alergiaImput);

        btnSus = view.findViewById(R.id.btnSus);
        final EditText susImput = new EditText(context);
        setEditText(view, btnSus, susImput);

        btnPlano = view.findViewById(R.id.btnPlano);
        final EditText planoImput = new EditText(context);
        setEditText(view, btnPlano, planoImput);

        btnEmail = view.findViewById(R.id.btnEmail);
        final EditText emailImput = new EditText(context);
        setEditText(view, btnEmail, emailImput);

        btnContatos = view.findViewById(R.id.btnContatos);
        final EditText contatosImput = new EditText(context);
        setEditText(view, btnContatos, contatosImput);


        return view;
    };



    public void setTipo_Sanguineo(View view){
        Button btnSelectChoice = (Button) view.findViewById(R.id.btnSelectChoice);
        btnSelectChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                DialogFragment singleChoiceDialog = new SingleChoiceDialogFragment();
                singleChoiceDialog.setCancelable(false);
                singleChoiceDialog.show(getActivity().getSupportFragmentManager(), "Tipo Sanguíneo");
            }
        });
    }

    public void setEditText(View view, Button btn, EditText imput){

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Editar");


                imput.setInputType(InputType.TYPE_CLASS_TEXT);
                dialog.setView(imput);

                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        btn.setText(imput.getText().toString());
                    }
                });
                dialog.show();
            }
        });
    }
}