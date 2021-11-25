package com.fsi.healthrotine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsi.healthrotine.DataBase.DataBase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ProfileFragment extends Fragment {
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
    private Button btnExit;

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
        TextView toolbarTitle = view.findViewById(R.id.toolbarTitle);

        toolbarTitle.setText("Perfil");
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


    private void goToProfilePage(){
        Intent intent = new Intent(getContext(), ProfileFragment.class);
        startActivity(intent);
    }

    public void setTipo_Sanguineo(View view){
        Button btnSelectChoice = (Button) view.findViewById(R.id.btnSelectChoice);
        btnSelectChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                DialogFragment singleChoiceDialog = new DialogFragment();
                singleChoiceDialog.setCancelable(false);
                singleChoiceDialog.show(getActivity().getSupportFragmentManager(), "Tipo Sanguíneo");
            }
        });
    }

    public void setEditText(View view, final Button btn, final EditText imput){

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