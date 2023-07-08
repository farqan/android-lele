package com.example.monitoringlele.perhitungan;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.monitoringlele.R;

public class ResultDialogPerhitungan {
    Activity activity;
    AlertDialog alertDialog;
    TextView btClose, tvResult, tvTime;
    ImageView ivResult;

    ResultDialogPerhitungan(Activity activity){
        this.activity = activity;
    }

    void startDialog(String jumlahBenih, Bitmap img, String timeTaken){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.result_dialog_perhitungan, null));
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.show();

        btClose = (TextView) alertDialog.findViewById(R.id.tv_close_dialog2);
        tvResult = (TextView) alertDialog.findViewById(R.id.tv_result_hitung);
        tvTime = (TextView) alertDialog.findViewById(R.id.tv_time_hitung);
        ivResult = (ImageView) alertDialog.findViewById(R.id.iv_result_hitung);

        tvResult.setText(jumlahBenih);
        tvTime.setText(timeTaken);
        ivResult.setImageBitmap(img);

        btClose.setOnClickListener(view -> dismissDialog());
    }

    void dismissDialog(){
        alertDialog.dismiss();
    }
}
