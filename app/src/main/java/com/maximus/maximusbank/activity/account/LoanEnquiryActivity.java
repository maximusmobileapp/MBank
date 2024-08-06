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


public class LoanEnquiryActivity extends AppCompatActivity {

    private EditText etLoanNumber;
    private Button btnEnquire;
    private TextView tvEnquiryResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_enquiry);
        ImageView backPressImg = findViewById(R.id.backButton);
        Utils.onBackPress(this, backPressImg);
        TextView headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.loan_enquiry);
        etLoanNumber = findViewById(R.id.etLoanNumber);
        btnEnquire = findViewById(R.id.btnEnquire);
        tvEnquiryResult = findViewById(R.id.tvEnquiryResult);

        btnEnquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    // Perform loan enquiry
                    tvEnquiryResult.setText("Enquiry Result: Loan details...");
                    tvEnquiryResult.setVisibility(View.VISIBLE);
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
        if (TextUtils.isEmpty(etLoanNumber.getText().toString())) {
            etLoanNumber.setError("Loan Number is required");
            return false;
        }
        return true;
    }
}

