package com.maximus.maximusbank.activity.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;


public class OpenLoanActivity extends AppCompatActivity {

    private EditText etFdNumber, etLoanAmount, etInterestRate;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_loan);
        ImageView backPressImg = findViewById(R.id.backButton);
        Utils.onBackPress(this, backPressImg);
        TextView headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.open_loan);
        etFdNumber = findViewById(R.id.etFdNumber);
        etLoanAmount = findViewById(R.id.etLoanAmount);
        etInterestRate = findViewById(R.id.etInterestRate);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {

                    Toast.makeText(OpenLoanActivity.this, "Form submitted successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }

    private boolean validateFields() {
        if (TextUtils.isEmpty(etFdNumber.getText().toString())) {
            etFdNumber.setError("FD Number is required");
            return false;
        }
        if (TextUtils.isEmpty(etLoanAmount.getText().toString())) {
            etLoanAmount.setError("Loan Amount is required");
            return false;
        }
        if (TextUtils.isEmpty(etInterestRate.getText().toString())) {
            etInterestRate.setError("Interest Rate is required");
            return false;
        }
        return true;
    }
}

