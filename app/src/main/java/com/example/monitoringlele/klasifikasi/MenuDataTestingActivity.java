package com.example.monitoringlele.klasifikasi;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.room.Room;

import com.example.monitoringlele.GlideApp;
import com.example.monitoringlele.LoadingDialog;
import com.example.monitoringlele.R;
import com.example.monitoringlele.data.factory.AppDatabaseTesting;
import com.example.monitoringlele.databinding.ActivityMenuDataTestingBinding;
import com.example.monitoringlele.model.DataTestingKlasifikasiModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MenuDataTestingActivity extends AppCompatActivity {

    private static final int SELECT_VIDEO_FROM_GALLERY = 1;
    private static final int SELECT_VIDEO_FROM_CAMERA = 2;
    private static final int KODE_PROSES_KLASIFIKASI = 1;

    private ActivityMenuDataTestingBinding binding;
    private OkHttpClient okHttpClient;
    private AppDatabaseTesting appDatabase;
    private String selectedVideoPath = "";
    private String selectedFileName = "";
    private boolean isReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu_data_testing);
        LoadingDialog loadingDialog = new LoadingDialog(MenuDataTestingActivity.this);
        ResultDialogKlasifikasi resulDialog = new ResultDialogKlasifikasi(MenuDataTestingActivity.this);

        init();

        //kembali ke halaman klasifikasi
        binding.btnKembaliDataTesting.setOnClickListener(view -> {
            Intent intent = new Intent(MenuDataTestingActivity.this, MenuKlasifikasiActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        binding.btnAmbilVideo.setOnClickListener(view -> {
            String source = binding.spGerakanLeleTesting.getSelectedItem().toString();

            binding.tvVideoName.setText("Belum memilih video");
            binding.ivThumbnail.setImageDrawable(getResources().getDrawable(R.drawable.dummy_picture));

            if (source.equals("Galeri")) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, SELECT_VIDEO_FROM_GALLERY);
            } else {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
                startActivityForResult(intent, SELECT_VIDEO_FROM_CAMERA);
            }
        });

        binding.btMulaiPengujian.setOnClickListener(view -> {
            if (!isReady) {
                Toast.makeText(MenuDataTestingActivity.this
                        , "Belum memilih video."
                        , Toast.LENGTH_LONG).show();
            } else {
                loadingDialog.startDialog(KODE_PROSES_KLASIFIKASI);

                long startTime = System.currentTimeMillis();
                File file = new File(selectedVideoPath);

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("file",
                                selectedFileName,
                                RequestBody.create(MediaType.parse("*/*"),
                                        file))
                        .build();

                Request request = new Request.Builder().url("http://192.168.1.9:5000/upload")
                        .post(requestBody)
                        .build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MenuDataTestingActivity.this
                                        , "Koneksi internet bermasalah"
                                        , Toast.LENGTH_SHORT)
                                        .show();

                                loadingDialog.dismissDialog();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        final String myResponse = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    JSONObject json = new JSONObject(myResponse);
                                    long difference = (System.currentTimeMillis() - startTime) / 1000;

                                    String namaVideo = selectedFileName;
                                    String rerata = json.getString("rerata");
                                    String banyakBox = json.getString("banyak_box");
                                    String persentaseNilaiNol = json.getString("persentase");
                                    String nilaiKemunculanPocBukanSatu = json.getString(
                                            "nilai_kemunculan_poc_bukan_satu");
                                    String label = json.getString("label");
                                    String dateCreated = json.getString("date_created");
                                    String timeCreated = json.getString("time_created");
                                    String time = String.valueOf(difference).toString() + " Detik";

                                    DataTestingKlasifikasiModel dt = new DataTestingKlasifikasiModel();
                                    dt.setNama_video(namaVideo);
                                    dt.setRerata(rerata);
                                    dt.setBanyak_box(banyakBox);
                                    dt.setPersentase_nilai_nol(persentaseNilaiNol);
                                    dt.setNilai_kemunculan_poc_bukan_satu(nilaiKemunculanPocBukanSatu);
                                    dt.setLabel(label);
                                    dt.setDate_created(dateCreated);
                                    dt.setTime_created(timeCreated);

                                    insertDataTestingKlasifikasi(dt);

                                    loadingDialog.dismissDialog();
                                    resulDialog.startDialog(label, time);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            selectedVideoPath = getPath(data.getData());
            if (resultCode == RESULT_OK && requestCode == SELECT_VIDEO_FROM_GALLERY) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
                builder.setTitle("Pratinjau Video");
                builder.setMessage("Video yang akan digunakan :");
                builder.setBackground(getResources().getDrawable(R.drawable.bg_white, null));
                VideoView videoView = new VideoView(this);
                videoView.setVideoURI(Uri.parse(selectedVideoPath));
                videoView.start();

                builder.setPositiveButton("Lanjut", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        setFileNameDisplay(selectedVideoPath);
                        setThumbnail(selectedVideoPath);
                    }
                });

                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setView(videoView).show();

            } else if (resultCode == RESULT_OK && requestCode == SELECT_VIDEO_FROM_CAMERA) {
                setFileNameDisplay(selectedVideoPath);
                setThumbnail(selectedVideoPath);
            }

            isReady = true;
        }
    }

    private void setThumbnail(String path) {
        Log.d("PATH", "setThumbnail: " + Uri.parse(path));
        GlideApp.with(MenuDataTestingActivity.this)
                .asBitmap()
                .load(Uri.fromFile(new File(path)))
                .into(binding.ivThumbnail);
    }

    private void setFileNameDisplay(String path) {
        File file = new File(path);
        selectedFileName = file.getName();
        binding.tvVideoName.setText(selectedFileName);
    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else return null;
    }

    @SuppressLint("StaticFieldLeak")
    private void insertDataTestingKlasifikasi(DataTestingKlasifikasiModel dataTestingKlasifikasi){
        new AsyncTask<Void, Void, Long>(){
            @Override
            protected Long doInBackground(Void... voids) {
                return appDatabase.dtTestingKlasifikasiDao().insertDataTestingKlasifikasi(dataTestingKlasifikasi);
            }

            @Override
            protected void onPostExecute(Long status) {
                Toast.makeText(MenuDataTestingActivity.this, "Data berhasil diklasifikasikan", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }

    private void init() {
        if (ContextCompat.checkSelfPermission(MenuDataTestingActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MenuDataTestingActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(0, TimeUnit.MILLISECONDS)
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .writeTimeout(0, TimeUnit.MILLISECONDS)
                .build();

        appDatabase = Room.databaseBuilder(getApplicationContext(),
                AppDatabaseTesting.class,
                "perhitungan_lele")
                .build();
    }

}