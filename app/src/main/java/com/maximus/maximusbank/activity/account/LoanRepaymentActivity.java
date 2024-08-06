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


public class LoanRepaymentActivity extends AppCompatActivity {

    private EditText etLoanNumber, etRepaymentAmount;
    private Button btnRepay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_repayment);
        ImageView backPressImg = findViewById(R.id.backButton);
        Utils.onBackPress(this, backPressImg);
        TextView headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.loan_repayment);
        etLoanNumber = findViewById(R.id.etLoanNumber);
        etRepaymentAmount = findViewById(R.id.etRepaymentAmount);
        btnRepay = findViewById(R.id.btnRepay);

        btnRepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    // Process loan repayment
                    Toast.makeText(LoanRepaymentActivity.this, "Repayment successful", Toast.LENGTH_SHORT).show();
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
        if (TextUtils.isEmpty(etRepaymentAmount.getText().toString())) {
            etRepaymentAmount.setError("Repayment Amount is required");
            return false;
        }
        return true;
    }
}

