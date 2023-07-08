package com.example.monitoringlele.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.monitoringlele.model.DataPerhitunganModel;
import com.example.monitoringlele.model.DataTestingKlasifikasiModel;

@Dao
public interface DataPerhitunganDao {

    @Query("SELECT * FROM tDataPerhitungan ORDER BY id_data DESC")
    DataPerhitunganModel[] getALLDataPerhitungan();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertDataPerhitungan(DataPerhitunganModel data);

    @Delete
    int deleteDataPerhitungan(DataPerhitunganModel data);

    @Query("DELETE FROM tDataPerhitungan")
    void deleteAllDataPerhitungan();

}
