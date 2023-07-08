package com.example.monitoringlele.data.factory;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.monitoringlele.data.dao.DataPerhitunganDao;
import com.example.monitoringlele.data.dao.DataTestingKlasifikasiDao;
import com.example.monitoringlele.model.DataTestingKlasifikasiModel;

@Database(entities = {DataTestingKlasifikasiModel.class}, version = 1)
public abstract class AppDatabaseTesting extends RoomDatabase {
    public abstract DataTestingKlasifikasiDao dtTestingKlasifikasiDao();
}
