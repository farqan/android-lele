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
import com.example.monitoringlele.data.factory.AppDatabaseTesting;
import com.example.monitoringlele.model.DataTestingKlasifikasiModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class AdapterDataTestingKlasifikasi extends RecyclerView.Adapter<AdapterDataTestingKlasifikasi.AdapterDataTestingKlasifikasiViewHolder> {

    private ArrayList<DataTestingKlasifikasiModel> daftarDataTestingKlasifikasi;
    private Context context;
    private AppDatabaseTesting appDatabase;

    public AdapterDataTestingKlasifikasi(ArrayList<DataTestingKlasifikasiModel> daftarDataTestingKlasifikasi, Context context){
        this.daftarDataTestingKlasifikasi = daftarDataTestingKlasifikasi;
        this.context = context;

        appDatabase = Room.databaseBuilder(
                context.getApplicationContext(),
                AppDatabaseTesting.class,
                "klasifikasi_lele")
                .allowMainThreadQueries()
                .build();
    }

    public class AdapterDataTestingKlasifikasiViewHolder extends RecyclerView.ViewHolder{
        TextView tvNamaVideo, tvTanggal, tvWaktu, tvLabel;
        ConstraintLayout clItem;

        public AdapterDataTestingKlasifikasiViewHolder(View v) {
            super(v);
            tvNamaVideo = v.findViewById(R.id.tv_nama_video);
            tvTanggal = v.findViewById(R.id.tv_date_taken);
            tvWaktu = v.findViewById(R.id.tv_time_taken);
            tvLabel = v.findViewById(R.id.tv_label);
            clItem = v.findViewById(R.id.cl_item);
        }
    }

    @NonNull
    @Override
    public AdapterDataTestingKlasifikasiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_data_testing, parent, false);
        AdapterDataTestingKlasifikasiViewHolder vh = new AdapterDataTestingKlasifikasiViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataTestingKlasifikasiViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final String namaVideo = daftarDataTestingKlasifikasi.get(position).getNama_video();
        final String createdDate = daftarDataTestingKlasifikasi.get(position).getDate_created();
        final String timeCreated = daftarDataTestingKlasifikasi.get(position).getTime_created();
        final String label = daftarDataTestingKlasifikasi.get(position).getLabel();

        holder.tvNamaVideo.setText(namaVideo);

        if (label.equals("Bagus")){
            holder.tvLabel.setTextColor(context.getResources().getColor(R.color.teal_700, null));
        }else{
            holder.tvLabel.setTextColor(context.getResources().getColor(R.color.orange, null));
        }

        holder.tvLabel.setText(label);
        holder.tvWaktu.setText(timeCreated);
        holder.tvTanggal.setText(createdDate);

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
        appDatabase.dtTestingKlasifikasiDao().deleteDataTestingKlasifikasi(daftarDataTestingKlasifikasi.get(position));
        daftarDataTestingKlasifikasi.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeRemoved(position, getItemCount());
        Toast.makeText(context.getApplicationContext(), "Hapus item berhasil", Toast.LENGTH_LONG).show();
    }

    @Override
    public int getItemCount() {
        return daftarDataTestingKlasifikasi.size();
    }

}
