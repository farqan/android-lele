package com.example.monitoringlele.klasifikasi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.monitoringlele.MainActivity;
import com.example.monitoringlele.R;
import com.example.monitoringlele.adapter.AdapterDataTestingKlasifikasi;
import com.example.monitoringlele.data.factory.AppDatabaseTesting;
import com.example.monitoringlele.databinding.ActivityMenuKlasifikasiBinding;
import com.example.monitoringlele.model.DataTestingKlasifikasiModel;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuKlasifikasiActivity extends AppCompatActivity {

    private ActivityMenuKlasifikasiBinding binding;
    private AppDatabaseTesting appDatabase;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataTestingKlasifikasiModel> daftarDataTestingKlasifikasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu_klasifikasi);

        initData();

        //pindah ke halaman lakukan klasifikasi
        binding.menuDoKlasifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuKlasifikasiActivity.this, MenuDataTestingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        //pindah ke halaman main menu
        binding.btnKembaliMenuKlasifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuKlasifikasiActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        binding.tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
                Toast.makeText(MenuKlasifikasiActivity.this, "Data berhasil disegarkan."
                        ,Toast.LENGTH_SHORT)
                        .show();
            }
        });

        binding.btAllDataTestingKlasifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuKlasifikasiActivity.this,
                        MenuRiwayatDataTestingKlasifikasiActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void initData()
    {
        daftarDataTestingKlasifikasi = new ArrayList<>();
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabaseTesting.class,
                "klasifikasi_lele")
                .allowMainThreadQueries()
                .build();

        binding.rvListTesting.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        binding.rvListTesting.setLayoutManager(layoutManager);

        daftarDataTestingKlasifikasi.addAll(Arrays.asList(
                        appDatabase.dtTestingKlasifikasiDao()
                        .getAllDataTestingKlasifikasi()));

        adapter = new AdapterDataTestingKlasifikasi(daftarDataTestingKlasifikasi
                , this);

        binding.rvListTesting.setAdapter(adapter);
    }
}