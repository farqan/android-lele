package com.example.monitoringlele.klasifikasi;

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
import com.example.monitoringlele.adapter.AdapterDataTestingKlasifikasi;
import com.example.monitoringlele.data.factory.AppDatabaseTesting;
import com.example.monitoringlele.databinding.ActivityMenuRiwayatDataTestingKlasifikasiBinding;
import com.example.monitoringlele.model.DataTestingKlasifikasiModel;
import com.example.monitoringlele.perhitungan.MenuHitungBenihActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuRiwayatDataTestingKlasifikasiActivity extends AppCompatActivity {

    ActivityMenuRiwayatDataTestingKlasifikasiBinding binding;
    private AppDatabaseTesting appDatabase;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataTestingKlasifikasiModel> daftarDataTestingKlasifikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MenuRiwayatDataTestingKlasifikasiActivity.this, R.layout.activity_menu_riwayat_data_testing_klasifikasi);

        init();

        binding.btnKembaliMenuPengujian2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuRiwayatDataTestingKlasifikasiActivity.this, MenuKlasifikasiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        binding.btDeleteAllDataTestingKlasifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MenuRiwayatDataTestingKlasifikasiActivity.this);
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
            }
        });

    }

    private void onDeleteAllDataTestingKlasifikasi(){
        appDatabase.dtTestingKlasifikasiDao().deleteAllDataTestingKlasifikasi();
        daftarDataTestingKlasifikasi.removeAll(daftarDataTestingKlasifikasi);
        binding.rvAllDataTesting.setAdapter(null);
        Toast.makeText(MenuRiwayatDataTestingKlasifikasiActivity.this,
                "Berhasil menghapus seluruh data", Toast.LENGTH_LONG).show();
    }

    private void init()
    {
        daftarDataTestingKlasifikasi = new ArrayList<>();
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabaseTesting.class,
                "monitoring_lele")
                .allowMainThreadQueries()
                .build();

        binding.rvAllDataTesting.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        binding.rvAllDataTesting.setLayoutManager(layoutManager);

        daftarDataTestingKlasifikasi.addAll(Arrays.asList(
                appDatabase.dtTestingKlasifikasiDao()
                        .getAllDataTestingKlasifikasi()));

        adapter = new AdapterDataTestingKlasifikasi(daftarDataTestingKlasifikasi
                , this);

        binding.rvAllDataTesting.setAdapter(adapter);
    }
}