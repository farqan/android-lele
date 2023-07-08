package com.example.monitoringlele;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

public class LoadingDialog {
    Activity activity;
    AlertDialog alertDialog;
    TextView tvProcessName;

    public LoadingDialog(Activity activity){
        this.activity = activity;
    }

    public void startDialog(int kodeProses){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.loading_dialog, null));
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();

        tvProcessName = (TextView) alertDialog.findViewById(R.id.tv_process_name);

        // kode proses 1 == proses klasifikasi
        // kode proses 2 == proses perhitungan

        if (kodeProses == 1){
            tvProcessName.setText("Proses Klasifikasi sedang berjalan");
        }else if (kodeProses == 2){
            tvProcessName.setText("Proses Perhitungan sedang berjalan");
        }


    }

    public void dismissDialog(){
        alertDialog.dismiss();
    }
}
