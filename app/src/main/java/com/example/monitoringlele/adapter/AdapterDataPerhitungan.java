package com.example.monitoringlele.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.monitoringlele.R;
import com.example.monitoringlele.data.factory.AppDatabasePerhitungan;
import com.example.monitoringlele.data.factory.AppDatabaseTesting;
import com.example.monitoringlele.model.DataPerhitunganModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class AdapterDataPerhitungan extends RecyclerView.Adapter<AdapterDataPerhitungan.AdapterDataPerhitunganViewHolder> {

    private ArrayList<DataPerhitunganModel> dataPerhitungan;
    private Context context;
    private AppDatabasePerhitungan appDatabase;

    public AdapterDataPerhitungan(ArrayList<DataPerhitunganModel> dataPerhitungan, Context context) {
        this.dataPerhitungan = dataPerhitungan;
        this.context = context;

        appDatabase = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabasePerhitungan.class,
                "perhitungan_lele")
                .allowMainThreadQueries()
                .build();
    }

    public class AdapterDataPerhitunganViewHolder extends RecyclerView.ViewHolder {

        TextView tvNamaVideo, tvTanggal, tvWaktu, tvJumlah;
        ConstraintLayout clItem;

        public AdapterDataPerhitunganViewHolder(@NonNull View v) {
            super(v);
            tvNamaVideo = v.findViewById(R.id.tv_nama_video_2);
            tvTanggal = v.findViewById(R.id.tv_date_taken_2);
            tvWaktu = v.findViewById(R.id.tv_time_taken_2);
            tvJumlah = v.findViewById(R.id.tv_count);
            clItem = v.findViewById(R.id.cl_item_2);

        }
    }

    @NonNull
    @Override
    public AdapterDataPerhitunganViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data_perhitungan, parent, false);
        AdapterDataPerhitunganViewHolder vh = new AdapterDataPerhitunganViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataPerhitunganViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final String namaVideo = dataPerhitungan.get(position).getNama_video();
        final String createdDate = dataPerhitungan.get(position).getDate_created();
        final String timeCreated = dataPerhitungan.get(position).getTime_created();
        final String jumlah = dataPerhitungan.get(position).getJumlah_benih();

        holder.tvNamaVideo.setText(namaVideo);
        holder.tvTanggal.setText(createdDate);
        holder.tvWaktu.setText(timeCreated);
        holder.tvJumlah.setText(jumlah);

        holder.clItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
                builder.setTitle("Hapus Data?");
                builder.setBackground(context.getResources().getDrawable(R.drawable.bg_white, null));
                builder.setMessage("Apakah ingin menghapus data?");

                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onDeleteItem(position);
                        dialogInterface.dismiss();
                    }
                });

                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.show();
                return false;
            }
        });
    }

    private void onDeleteItem(int position){
        appDatabase.dtPerhitunganDao().deleteDataPerhitungan(dataPerhitungan.get(position));
        dataPerhitungan.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position, getItemCount());
        Toast.makeText(context.getApplicationContext(), "Hapus item berhasil", Toast.LENGTH_LONG).show();
    }

    @Override
    public int getItemCount() {
        return dataPerhitungan.size();
    }


}
