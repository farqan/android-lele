package com.example.monitoringlele.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tDataPerhitungan")
public class DataPerhitunganModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_data;

    @ColumnInfo(name = "nama_video")
    public String nama_video;

    @ColumnInfo(name = "jumlah_benih")
    public String jumlah_benih;

    @ColumnInfo(name = "date_created")
    public String date_created;

    @ColumnInfo(name = "time_created")
    public String time_created;

    public int getId_data() {
        return id_data;
    }

    public void setId_data(int id_data) {
        this.id_data = id_data;
    }

    public String getNama_video() {
        return nama_video;
    }

    public void setNama_video(String nama_video) {
        this.nama_video = nama_video;
    }

    public String getJumlah_benih() {
        return jumlah_benih;
    }

    public void setJumlah_benih(String jumlah_benih) {
        this.jumlah_benih = jumlah_benih;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getTime_created() {
        return time_created;
    }

    public void setTime_created(String time_created) {
        this.time_created = time_created;
    }
}
