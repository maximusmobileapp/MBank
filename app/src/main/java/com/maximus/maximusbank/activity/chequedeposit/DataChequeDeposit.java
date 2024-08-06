package com.maximus.maximusbank.activity.chequedeposit;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;


public class DataChequeDeposit extends AppCompatActivity {




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_cheque_deposit);



    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }


}