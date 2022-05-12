package com.example.drivingtheoryapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;


public class DialogPositiveNegative extends AppCompatDialogFragment {

    private String username;
    private String questionExplanation;
    private String title;
    private String positiveButton;
    private String negativeButton;
    private ExampleDialogListener listener;



    public DialogPositiveNegative(String username, String questionExplanation, String title, String positiveButton, String negativeButton){
        this.username = username;
        this.questionExplanation = questionExplanation;
        this.title = title;
        this.positiveButton = positiveButton;
        this.negativeButton = negativeButton;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(questionExplanation)

                .setNegativeButton(negativeButton, null) // dismisses by default

                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.applyChoice(username);
                    }
                });




        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void applyChoice(String username);
    }
}

