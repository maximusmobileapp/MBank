package com.maximus.maximusbank.activity.share;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.google.android.datatransport.backend.cct.BuildConfig;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ShareQR extends AppCompatActivity {
    private static final String TAG = "ShareQR";
    private TextView headerTextView;
    private ImageView backButton, idIVQrcode;
    private EditText et_accno;
    private Button generateQrBtn, btnshare,btnsave;
    private Bitmap qrBitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_qr);

        headerTextView = findViewById(R.id.headerTextView);
        backButton = findViewById(R.id.backButton);
        idIVQrcode = findViewById(R.id.idIVQrcode);
        et_accno = findViewById(R.id.et_accno);
        generateQrBtn = findViewById(R.id.idBtnGenerateQR);
        btnshare = findViewById(R.id.btnshare);
        btnsave = findViewById(R.id.btnsave);

        headerTextView.setText(R.string.share_qr);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        generateQrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountNo = et_accno.getText().toString();
                if (TextUtils.isEmpty(accountNo)) {
                    Toast.makeText(ShareQR.this, "Enter Account No to generate QR Code", Toast.LENGTH_SHORT).show();
                } else {
                    generateQrCode(accountNo);
                }
            }
        });

        btnshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (qrBitmap != null) {
                    shareQrCode();
                } else {
                    Toast.makeText(ShareQR.this, "Generate QR Code first", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    saveImage();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }
    private void saveImage() {
        if (qrBitmap == null) {
            Toast.makeText(this, "No QR Code to save", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = "image_" + System.currentTimeMillis() + ".jpg";
        File dest = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File folder = new File(dest, "My image");

        if (!folder.exists()) {
            if (!folder.mkdirs()) {
                Toast.makeText(this, "Failed to create directory", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        File file = new File(folder, fileName);
        try (FileOutputStream out = new FileOutputStream(file)) {
            qrBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            Toast.makeText(this, "Image saved successfully", Toast.LENGTH_SHORT).show();

            // Notify the media scanner of the new file
            MediaScannerConnection.scanFile(this, new String[]{file.getAbsolutePath()}, null, null);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show();
        }
    }

    private void generateQrCode(String text) {
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            qrBitmap = barcodeEncoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 400, 400);
            idIVQrcode.setImageBitmap(qrBitmap);
            Log.d(TAG, "QR Code generated successfully");
        } catch (WriterException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to generate QR Code", Toast.LENGTH_SHORT).show();
        }
    }

//    private void shareQrCode() {
//        if (qrBitmap == null) {
//            Toast.makeText(this, "Generate QR Code first", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        // Save QR code to file
//        File file = saveBitmapToFile(qrBitmap);
//        if (file == null) {
//            Toast.makeText(this, "Failed to save QR Code", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        Uri uri = null;
//
////        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
////            uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);
////        } else {
////            uri = Uri.fromFile(file);
////        }
//
//        Intent sendIntent = new Intent(Intent.ACTION_SEND);
//        sendIntent.setType("image/jpeg");
//        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
//        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//
//        try {
//            startActivity(Intent.createChooser(sendIntent, "Share QR Code"));
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Could not find an application to share the QR Code", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void shareQrCode() {
        if (qrBitmap == null) {
            Toast.makeText(this, "Generate QR Code first", Toast.LENGTH_SHORT).show();
            return;
        }

        File file = saveBitmapToFile(qrBitmap);
        if (file == null) {
            Toast.makeText(this, "Failed to save QR Code", Toast.LENGTH_SHORT).show();
            return;
        }

        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);
        } else {
            uri = Uri.fromFile(file);
        }

        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/jpeg");
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        try {
            startActivity(Intent.createChooser(sendIntent, "Share QR Code"));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Could not find an application to share the QR Code", Toast.LENGTH_SHORT).show();
        }
    }
    
    private File saveBitmapToFile(Bitmap bitmap) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "QRCode.jpg");
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            Log.d(TAG, "QR Code saved to file: " + file.getAbsolutePath());
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error saving QR Code to file", e);
            return null;
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
