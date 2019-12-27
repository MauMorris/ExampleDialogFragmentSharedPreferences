package com.example.idscomercial.testalertpatrocinio;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements DialogPatrocinioListener {

    private SharedPreferences prefPatrocinio;
    boolean patCheckboxResult = false;
    private PatrocinioAlert alertCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //valores utilizados para connectivityManager quien contiene la informacion de estado de connexion a red
        Context mContext = MainActivity.this;
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = null;
        if (connectivityManager != null) {
            activeNetwork = connectivityManager.getActiveNetworkInfo();
        }
        if (activeNetwork != null)
            if(activeNetwork.isConnectedOrConnecting()){
                int networkType = activeNetwork.getType();

                if (networkType == ConnectivityManager.TYPE_MOBILE) {
                    prefPatrocinio = getSharedPreferences(getString(R.string.shared_preferences_patrocinio_alert), Context.MODE_PRIVATE);
                    boolean resultPatrocinio = prefPatrocinio.getBoolean(getString(R.string.key_patrocinio_alert), false);

                    if (!resultPatrocinio)
                        showAlert();
                }
            }
    }

    //muestra de alert al dar clic en cancelacion
    private void showAlert() {
        alertCancelar = new PatrocinioAlert();

        alertCancelar.setCancelable(false);
        alertCancelar.show(getSupportFragmentManager(), "ayuda_cancelar");
    }

    @Override
    public void onPositive(DialogFragment dialog) {
        if (patCheckboxResult) {
            prefPatrocinio = getSharedPreferences(getString(R.string.shared_preferences_patrocinio_alert), Context.MODE_PRIVATE);
            SharedPreferences.Editor editorPatrocinio = prefPatrocinio.edit();

            editorPatrocinio.putBoolean(getString(R.string.key_patrocinio_alert), patCheckboxResult);
            editorPatrocinio.apply();
        }

        alertCancelar.dismiss();
    }

    @Override
    public void onCheck(boolean check) {
        patCheckboxResult = check;
    }
}