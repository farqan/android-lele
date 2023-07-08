package com.example.monitoringlele.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "tDataTestingKlasifikasi")
public class DataTestingKlasifikasiModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id_data;

    @ColumnInfo(name = "nama_video")
    public String nama_video;

    @ColumnInfo(name = "rerata")
    public String rerata;

    @ColumnInfo(name = "banyak_box")
    public String banyak_box;

    @ColumnInfo(name = "persentase_nilai_nol")
    public String persentase_nilai_nol;

    @ColumnInfo(name = "nilai_kemunculan_poc_bukan_satu")
    public String nilai_kemunculan_poc_bukan_satu;

    @ColumnInfo(name = "label")
    public String label;

    @ColumnInfo(name = "date_created")
    public String date_created;

    @ColumnInfo(name = "time_created")
    public String time_created;

    public String getNama_video() {
        return nama_video;
    }

    public void setNama_video(String nama_video) {
        this.nama_video = nama_video;
    }

    public String getRerata() {
        return rerata;
    }

    public void setRerata(String rerata) {
        this.rerata = rerata;
    }

    public String getBanyak_box() {
        return banyak_box;
    }

    public void setBanyak_box(String banyak_box) {
        this.banyak_box = banyak_box;
    }

    public String getPersentase_nilai_nol() {
        return persentase_nilai_nol;
    }

    public void setPersentase_nilai_nol(String persentase_nilai_nol) {
        this.persentase_nilai_nol = persentase_nilai_nol;
    }

    public String getNilai_kemunculan_poc_bukan_satu() {
        return nilai_kemunculan_poc_bukan_satu;
    }

    public void setNilai_kemunculan_poc_bukan_satu(String nilai_kemunculan_poc_bukan_satu) {
        this.nilai_kemunculan_poc_bukan_satu = nilai_kemunculan_poc_bukan_satu;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
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
