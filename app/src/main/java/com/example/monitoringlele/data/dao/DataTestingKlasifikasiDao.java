package com.example.monitoringlele.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.monitoringlele.model.DataTestingKlasifikasiModel;

@Dao
public interface DataTestingKlasifikasiDao {

    @Query("SELECT * FROM tDataTestingKlasifikasi ORDER BY id_data DESC")
    DataTestingKlasifikasiModel[] getAllDataTestingKlasifikasi();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertDataTestingKlasifikasi(DataTestingKlasifikasiModel data);

    @Delete
    int deleteDataTestingKlasifikasi(DataTestingKlasifikasiModel data);

    @Query("DELETE FROM tDataTestingKlasifikasi")
    void deleteAllDataTestingKlasifikasi();

}
