package com.example.idscomercial.testalertpatrocinio;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements DialogPatrocinioListener {

    SharedPreferences prefPatrocinio;
    SharedPreferences.Editor editorPatrocinio;

    boolean dPatrocinio;
    boolean checkPat = false;

    private static final String SET_ALERT = "SET_ALERT_PATROCINIO";
    private static final String OCULTA_ALERT = "OCULTA_ALERT_PATROCINIO";

    private PatrocinioAlert alertCancelar;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting())
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                prefPatrocinio = getSharedPreferences(SET_ALERT, Context.MODE_PRIVATE);
                dPatrocinio = prefPatrocinio.getBoolean(OCULTA_ALERT, false);

                if (!dPatrocinio)
                    showAlert();
            }
    }

    private void showAlert() { //muestra de alert al dar clic en cancelacion
        alertCancelar = new PatrocinioAlert();

        alertCancelar.setCancelable(false);
        alertCancelar.show(getSupportFragmentManager(), "ayuda_cancelar");
    }

    @Override
    public void onPositive(DialogFragment dialog) {
        if (checkPat) {
            prefPatrocinio = getSharedPreferences(SET_ALERT, Context.MODE_PRIVATE);
            editorPatrocinio = prefPatrocinio.edit();

            editorPatrocinio.putBoolean(OCULTA_ALERT, true);
            editorPatrocinio.apply();
        }

        alertCancelar.dismiss();
    }

    @Override
    public void onCheck(boolean check) {
        checkPat = check;
    }
}