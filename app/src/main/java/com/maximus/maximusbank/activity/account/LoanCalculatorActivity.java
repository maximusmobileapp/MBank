package com.maximus.maximusbank.activity.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;


public class LoanCalculatorActivity extends AppCompatActivity {

    private EditText etPrincipalAmount, etRateOfInterest, etLoanTenure;
    private Button btnCalculate;
    private TextView tvEmiResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_calculator);
        TextView headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.loan_calculator);
        ImageView backPressImg = findViewById(R.id.backButton);
        Utils.onBackPress(this, backPressImg);
        etPrincipalAmount = findViewById(R.id.etPrincipalAmount);
        etRateOfInterest = findViewById(R.id.etRateOfInterest);
        etLoanTenure = findViewById(R.id.etLoanTenure);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvEmiResult = findViewById(R.id.tvEmiResult);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    calculateEMI();
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
        if (TextUtils.isEmpty(etPrincipalAmount.getText().toString())) {
            etPrincipalAmount.setError("Principal Amount is required");
            return false;
        }
        if (TextUtils.isEmpty(etRateOfInterest.getText().toString())) {
            etRateOfInterest.setError("Rate of Interest is required");
            return false;
        }
        if (TextUtils.isEmpty(etLoanTenure.getText().toString())) {
            etLoanTenure.setError("Loan Tenure is required");
            return false;
        }
        return true;
    }

    private void calculateEMI() {
        double principal = Double.parseDouble(etPrincipalAmount.getText().toString());
        double rateOfInterest = Double.parseDouble(etRateOfInterest.getText().toString()) / 12 / 100;
        int loanTenure = Integer.parseInt(etLoanTenure.getText().toString()) * 12;

        double emi = (principal * rateOfInterest * Math.pow(1 + rateOfInterest, loanTenure)) /
                (Math.pow(1 + rateOfInterest, loanTenure) - 1);

        tvEmiResult.setText(String.format("EMI: %.2f", emi));
        tvEmiResult.setVisibility(View.VISIBLE);
    }
}
