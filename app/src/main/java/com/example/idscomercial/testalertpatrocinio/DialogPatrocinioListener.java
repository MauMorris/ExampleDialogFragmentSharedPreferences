package com.example.idscomercial.testalertpatrocinio;


import android.support.v4.app.DialogFragment;

public interface DialogPatrocinioListener {
        void onPositive(DialogFragment dialog);
        void onCheck(boolean check);
}