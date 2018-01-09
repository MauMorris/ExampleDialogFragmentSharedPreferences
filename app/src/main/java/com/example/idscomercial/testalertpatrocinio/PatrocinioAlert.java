package com.example.idscomercial.testalertpatrocinio;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;


public class PatrocinioAlert extends DialogFragment {

    public DialogPatrocinioListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try { // Verifica que la actividad host implementa la interfaz
            mListener = (DialogPatrocinioListener) activity; //instancia el Listener para pasar eventos
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() //la actividad no implementa la interfaz, error
                    + " must implement NoticeDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.alert_datos_patrocinados, null);

        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(alertLayout);

        AppCompatButton ok = (AppCompatButton) alertLayout.findViewById(R.id.btn_ok_patrocinio);
        AppCompatCheckBox chCancel = (AppCompatCheckBox) alertLayout.findViewById(R.id.ch_mostrar_patrocinio);

        ok.setOnClickListener(positiveClic);
        chCancel.setOnCheckedChangeListener(checkClic);

        return alert.create();
    }

    private View.OnClickListener positiveClic = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mListener.onPositive(PatrocinioAlert.this);
        }
    };

    private CompoundButton.OnCheckedChangeListener checkClic = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            mListener.onCheck(b);
        }
    };
}