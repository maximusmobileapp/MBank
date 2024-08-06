package com.maximus.maximusbank.activity.beneficiary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;
import com.maximus.maximusbank.activity.ChequeBook.StopChequeBook;
import com.maximus.maximusbank.common.CommonBottomSheetDialogFragment;


public class PreviewBeneficiaryActivity extends AppCompatActivity {
    private TextView accountTypeTextView;
    private TextView accountNumberTextView;
    private TextView bankNameTextView;
    private TextView transferLimitTextView;
    private TextView beneficiaryNameTextView;
    private Button confirmButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_beneficiary);
        TextView headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.preview);
        ImageView backPressImg = findViewById(R.id.backButton);
        Utils.onBackPress(this, backPressImg);
        accountTypeTextView = findViewById(R.id.accountTypeTextView);
        accountNumberTextView = findViewById(R.id.accountNumberTextView);
        bankNameTextView = findViewById(R.id.bankNameTextView);
        transferLimitTextView = findViewById(R.id.transferLimitTextView);
        beneficiaryNameTextView = findViewById(R.id.beneficiaryNameTextView);
        confirmButton = findViewById(R.id.confirmButton);
        cancelButton = findViewById(R.id.cancelButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            accountTypeTextView.setText(extras.getString("accountType"));
            accountNumberTextView.setText(extras.getString("accountNumber"));
            bankNameTextView.setText(extras.getString("bankName"));
            transferLimitTextView.setText(extras.getString("transferLimit"));
            beneficiaryNameTextView.setText(extras.getString("beneficiaryName"));
        }

        // Handle button clicks
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myString = "Request Sent Successfully";
                CommonBottomSheetDialogFragment bottomSheet = CommonBottomSheetDialogFragment.newInstance(myString, PreviewBeneficiaryActivity.this);
                bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancel the action and go back
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }
}