package com.maximus.maximusbank.activity.beneficiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;


public class AddBeneficiaryActivity extends AppCompatActivity {

    private Spinner accountTypeSpinner;
    private EditText accountNumberEditText;
    private EditText bankNameEditText;
    private EditText transferLimitEditText;
    private EditText beneficiaryNameEditText;
    private Button previewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_beneficiary);
        ImageView backPressImg = findViewById(R.id.backButton);
        Utils.onBackPress(this, backPressImg);
        TextView headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.add_benef);
        accountTypeSpinner = findViewById(R.id.accountTypeSpinner);
        accountNumberEditText = findViewById(R.id.accountNumberEditText);
        bankNameEditText = findViewById(R.id.bankNameEditText);
        transferLimitEditText = findViewById(R.id.transferLimitEditText);
        beneficiaryNameEditText = findViewById(R.id.beneficiaryNameEditText);
        previewButton = findViewById(R.id.previewButton);

        previewButton.setOnClickListener(v -> {
            if (validateFields()) {
                Intent intent = new Intent(AddBeneficiaryActivity.this, PreviewBeneficiaryActivity.class);
                intent.putExtra("accountType", accountTypeSpinner.getSelectedItem().toString());
                intent.putExtra("accountNumber", accountNumberEditText.getText().toString().trim());
                intent.putExtra("bankName", bankNameEditText.getText().toString().trim());
                intent.putExtra("transferLimit", transferLimitEditText.getText().toString().trim());
                intent.putExtra("beneficiaryName", beneficiaryNameEditText.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }

    private boolean validateFields() {
        if (accountTypeSpinner.getSelectedItemPosition() == 0) {
            Utils.showSnackbar(AddBeneficiaryActivity.this,"Please select an account type");
            return false;
        }
        if (TextUtils.isEmpty(accountNumberEditText.getText().toString().trim())) {
            Utils.showSnackbar(AddBeneficiaryActivity.this,"Please select an account type");
            accountNumberEditText.setError("Please enter account number");
            return false;
        }
        if (TextUtils.isEmpty(bankNameEditText.getText().toString().trim())) {
            Utils.showSnackbar(AddBeneficiaryActivity.this,"Please enter bank name");
            return false;
        }
        if (TextUtils.isEmpty(transferLimitEditText.getText().toString().trim())) {
            Utils.showSnackbar(AddBeneficiaryActivity.this,"Please enter transfer limit");
            return false;
        }
        if (TextUtils.isEmpty(beneficiaryNameEditText.getText().toString().trim())) {
            Utils.showSnackbar(AddBeneficiaryActivity.this,"Please enter beneficiary name");
            return false;
        }
        return true;
    }
}