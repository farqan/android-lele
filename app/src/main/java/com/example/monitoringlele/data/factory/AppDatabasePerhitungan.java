package com.example.monitoringlele.data.factory;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.monitoringlele.data.dao.DataPerhitunganDao;
import com.example.monitoringlele.data.dao.DataTestingKlasifikasiDao;
import com.example.monitoringlele.model.DataPerhitunganModel;
import com.example.monitoringlele.model.DataTestingKlasifikasiModel;

@Database(entities = {DataPerhitunganModel.class}, version = 1)
public abstract class AppDatabasePerhitungan extends RoomDatabase{
    public abstract DataPerhitunganDao dtPerhitunganDao();
}
