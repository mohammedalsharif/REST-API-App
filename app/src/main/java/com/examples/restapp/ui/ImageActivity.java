package com.examples.restapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.examples.restapp.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class ImageActivity extends AppCompatActivity {
    private String imageUrl;
    ImageView img;
    ProgressBar progressBar;
    Button btn_download;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        progressBar = findViewById(R.id.spin_image);
        img = findViewById(R.id.im_image);
        btn_download=findViewById(R.id.materialButton);
        imageUrl = getIntent().getStringExtra(MainActivity.INTENT_IMG);
        PRDownloader.initialize(getApplicationContext());

        Picasso.get().load(imageUrl).fit()
                .into(img, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);

                    }

                    @Override
                    public void onError(Exception e) {
                        progressBar.setVisibility(View.VISIBLE);
                        Sprite doubleBounce = new DoubleBounce();
                        progressBar.setIndeterminateDrawable(doubleBounce);
                    }
                });

        btn_download.setOnClickListener(view -> {
            checkPermission();

        });


    }


    private void checkPermission() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE

                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()){
                    
                    downloadImage();
                }else {
                    Toast.makeText(ImageActivity.this, "Please allow all Permission", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                
            }
        }).check();
    }

    private void downloadImage() {
        ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Downloading...");
        pd.setCancelable(false);
        pd.show();
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        PRDownloader.download(imageUrl, file.getPath(), URLUtil.guessFileName(imageUrl,null,null))
                .build()

                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                      long per=  progress.currentBytes*100 / progress.totalBytes;
                      pd.setMessage("Downloading : "+per+"%");

                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        pd.dismiss();
                        Toast.makeText(ImageActivity.this, " Downloading Completed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Error error) {
                        pd.dismiss();
                        Toast.makeText(ImageActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }


                });
    }

}