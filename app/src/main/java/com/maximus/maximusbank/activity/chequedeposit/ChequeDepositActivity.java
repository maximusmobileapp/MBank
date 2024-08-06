package com.maximus.maximusbank.activity.chequedeposit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;


import java.io.ByteArrayOutputStream;

public class ChequeDepositActivity extends AppCompatActivity {
    ImageView backButton, image_front, image_back;
    TextView headerTextView, txtcapturefront, txtcaptureback;
    Button btnconfirm;
    private static final int PIC_ID_FRONT = 123;
    private static final int PIC_ID_BACK = 124;
    private Bitmap frontImageBitmap;
    private Bitmap backImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheque_deposit);
        ImageView backPressImg = findViewById(R.id.backButton);
        Utils.onBackPress(this, backPressImg);
        TextView headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.cheque_deposit);
        backButton = findViewById(R.id.image_back);
        image_front = findViewById(R.id.image_front);
        image_back = findViewById(R.id.image_back);
        headerTextView = findViewById(R.id.headerTextView);
        txtcapturefront = findViewById(R.id.txtcapturefront);
        txtcaptureback = findViewById(R.id.txtcaptureback);
        headerTextView.setText(R.string.cheque_deposit);
        btnconfirm = findViewById(R.id.btnconfirm);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtcapturefront.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, PIC_ID_FRONT);
            }
        });

        txtcaptureback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera_intent, PIC_ID_BACK);
            }
        });

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ChequeDepositActivity.this, DataChequeDeposit.class);
                myIntent.putExtra("key", String.valueOf(v));
                ChequeDepositActivity.this.startActivity(myIntent);
            }
        });

        findViewById(R.id.btnconfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (frontImageBitmap != null && backImageBitmap != null) {
                    Intent intent = new Intent(ChequeDepositActivity.this, DataChequeDeposit.class);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    frontImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] frontByteArray = stream.toByteArray();
                    intent.putExtra("frontImage", frontByteArray);

                    stream = new ByteArrayOutputStream();
                    backImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] backByteArray = stream.toByteArray();
                    intent.putExtra("backImage", backByteArray);

                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            if (requestCode == PIC_ID_FRONT) {
                image_front.setImageBitmap(photo);
                frontImageBitmap = photo;
            } else if (requestCode == PIC_ID_BACK) {
                image_back.setImageBitmap(photo);
                backImageBitmap = photo;
            }
        }
    }
}
