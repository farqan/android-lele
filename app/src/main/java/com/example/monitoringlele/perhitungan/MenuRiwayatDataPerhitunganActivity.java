package com.example.monitoringlele.perhitungan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.monitoringlele.MainActivity;
import com.example.monitoringlele.R;
import com.example.monitoringlele.adapter.AdapterDataPerhitungan;
import com.example.monitoringlele.adapter.AdapterDataTestingKlasifikasi;
import com.example.monitoringlele.data.factory.AppDatabasePerhitungan;
import com.example.monitoringlele.databinding.ActivityMenuRiwayatDataPerhitunganBinding;
import com.example.monitoringlele.klasifikasi.MenuRiwayatDataTestingKlasifikasiActivity;
import com.example.monitoringlele.model.DataPerhitunganModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuRiwayatDataPerhitunganActivity extends AppCompatActivity {

    ActivityMenuRiwayatDataPerhitunganBinding binding;
    private AppDatabasePerhitungan appDatabase;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataPerhitunganModel> daftarDataPerhitungan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu_riwayat_data_perhitungan);

        init();

        binding.btDeleteAllDataHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MenuHitungBenihActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        binding.btDeleteAllDataHitung.setOnClickListener(view -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
            builder.setTitle("Hapus Seluruh Data?");
            builder.setBackground(getResources().getDrawable(R.drawable.bg_white, null));
            builder.setMessage("Apakah ingin menghapus seluruh data?");

            builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    onDeleteAllDataTestingKlasifikasi();
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
        });
    }

    private void onDeleteAllDataTestingKlasifikasi(){
        appDatabase.dtPerhitunganDao().deleteAllDataPerhitungan();
        daftarDataPerhitungan.removeAll(daftarDataPerhitungan);
        binding.rvAllDataHitung.setAdapter(null);
        Toast.makeText(getApplicationContext(),
                "Berhasil menghapus seluruh data", Toast.LENGTH_LONG).show();
    }

    private void init()
    {
        daftarDataPerhitungan = new ArrayList<>();
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabasePerhitungan.class,
                "perhitungan_lele")
                .allowMainThreadQueries()
                .build();

        binding.rvAllDataHitung.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        binding.rvAllDataHitung.setLayoutManager(layoutManager);

        daftarDataPerhitungan.addAll(Arrays.asList(
                appDatabase.dtPerhitunganDao()
                        .getALLDataPerhitungan()));

        adapter = new AdapterDataPerhitungan(daftarDataPerhitungan
                , this);

        binding.rvAllDataHitung.setAdapter(adapter);
    }
}