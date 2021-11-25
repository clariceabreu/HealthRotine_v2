package com.fsi.healthrotine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class SingleChoiceDialogFragment extends DialogFragment {

    int position = 0;

    private Button btnSelectChoice;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final String[] list = getActivity().getResources().getStringArray(R.array.choice_items);
        btnSelectChoice = getActivity().findViewById(R.id.btnSelectChoice);
        builder.setTitle("Tipo Sanguíneo").
                setSingleChoiceItems(list, position, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        position=i;
                    }
            })
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i){
                        btnSelectChoice.setText(list[position]);
                    }
        });

        return builder.create();
    }
}
