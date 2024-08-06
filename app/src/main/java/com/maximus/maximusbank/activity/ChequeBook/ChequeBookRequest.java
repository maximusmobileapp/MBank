package com.maximus.maximusbank.activity.ChequeBook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;


public class ChequeBookRequest extends AppCompatActivity {
    ImageView imgvector;
    Button btncheque,btncancel;
    TextView headerTextView;
    ImageView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheque_book_request);
        ImageView backPressImg = findViewById(R.id.backButton);
        Utils.onBackPress(this, backPressImg);
        TextView headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.cheque_book_request);
        imgvector = findViewById(R.id.imgvector);
        btncheque = findViewById(R.id.btncheque);
        btncancel = findViewById(R.id.btncancel);
        headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.cheque_book);
        backButton = findViewById(R.id.backButton);

       backButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onBackPressed();
           }
       });


        btncheque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =  new Intent(ChequeBookRequest.this, ChequeBookActivity.class);
                startActivity(intent);

            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChequeBookRequest.this,StopChequeBook.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }
}