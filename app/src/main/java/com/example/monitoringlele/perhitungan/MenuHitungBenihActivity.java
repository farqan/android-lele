package com.example.monitoringlele.perhitungan;

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
import com.example.monitoringlele.adapter.AdapterDataPerhitungan;
import com.example.monitoringlele.adapter.AdapterDataTestingKlasifikasi;
import com.example.monitoringlele.data.dao.DataPerhitunganDao;
import com.example.monitoringlele.data.factory.AppDatabasePerhitungan;
import com.example.monitoringlele.data.factory.AppDatabaseTesting;
import com.example.monitoringlele.databinding.ActivityMenuHitungBenihBinding;
import com.example.monitoringlele.model.DataPerhitunganModel;

import java.util.ArrayList;
import java.util.Arrays;

public class MenuHitungBenihActivity extends AppCompatActivity {

    private ActivityMenuHitungBenihBinding binding;
    private AppDatabasePerhitungan appDatabase;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DataPerhitunganModel> daftarDataPerhitungan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu_hitung_benih);

        init();

        binding.btnKembaliMenuHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuHitungBenihActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        binding.menuDoPerhitungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuHitungBenihActivity.this, MenuPerhitunganBenihActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        binding.tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init();
                Toast.makeText(getApplicationContext(),  "Data berhasil disegarkan", Toast.LENGTH_SHORT).show();
            }
        });

        binding.btAllDataHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuHitungBenihActivity.this, MenuRiwayatDataPerhitunganActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void init()
    {
        daftarDataPerhitungan = new ArrayList<>();
        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabasePerhitungan.class,
                "perhitungan_lele")
                .allowMainThreadQueries()
                .build();

        binding.rvListPerhitungan.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        binding.rvListPerhitungan.setLayoutManager(layoutManager);

        daftarDataPerhitungan.addAll(Arrays.asList(
                appDatabase.dtPerhitunganDao()
                        .getALLDataPerhitungan()));

        adapter = new AdapterDataPerhitungan(daftarDataPerhitungan
                , this);

        binding.rvListPerhitungan.setAdapter(adapter);
    }
}