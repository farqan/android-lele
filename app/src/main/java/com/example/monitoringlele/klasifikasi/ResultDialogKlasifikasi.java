package com.example.monitoringlele.klasifikasi;

import android.app.Activity;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.monitoringlele.R;

public class ResultDialogKlasifikasi {
    Activity activity;
    AlertDialog alertDialog;
    TextView btClose, tvResult, tvTime;

    public ResultDialogKlasifikasi(Activity activity){
        this.activity = activity;
    }

    public void startDialog(String result_val, String time_val){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.result_dialog_klasifikasi, null));
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.show();

        btClose = (TextView) alertDialog.findViewById(R.id.tv_close_dialog);
        tvResult = (TextView) alertDialog.findViewById(R.id.tv_result);
        tvTime = (TextView) alertDialog.findViewById(R.id.tv_time);

        tvResult.setText(result_val);
        tvTime.setText(time_val);

        btClose.setOnClickListener(view -> dismissDialog());
    }

    void dismissDialog(){
        alertDialog.dismiss();
    }

}
