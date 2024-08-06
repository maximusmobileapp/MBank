package com.maximus.maximusbank.activity.ChequeBook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;
import com.maximus.maximusbank.common.CommonBottomSheetDialogFragment;


public class StopChequeBook extends AppCompatActivity {
    Spinner SpinnerAcc, Spinreason;
    EditText etchequeno;
    Button btnconfirm, btnclear;
    TextView headerTextView;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_cheque_book);
        ImageView backPressImg = findViewById(R.id.backButton);
        Utils.onBackPress(this, backPressImg);
        TextView headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.stop_book);
        SpinnerAcc = findViewById(R.id.SpinnerAcc);
        Spinreason = findViewById(R.id.Spinreason);
        etchequeno = findViewById(R.id.etchequeno);
        btnconfirm = findViewById(R.id.btnconfirm);
        btnclear = findViewById(R.id.btnclear);
        headerTextView = findViewById(R.id.headerTextView);
        backButton = findViewById(R.id.backButton);
        headerTextView.setText(R.string.stop_book);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etchequeno.setText("");
            }
        });

        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validatefields()) {
                    confirmbottom();
                } else {
                    Toast.makeText(StopChequeBook.this, "Fill all the details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validatefields() {
        if (SpinnerAcc.getSelectedItemPosition() == 0 ) {
            Toast.makeText(this, "Account number is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (etchequeno.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Enter Cheque No", Toast.LENGTH_SHORT).show();
            return false;
        }
       else if (Spinreason.getSelectedItemPosition() == 0 ) {
            Toast.makeText(this, "Select the reason", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }

    private void confirmbottom() {
        String myString = "Request Sent Successfully";
        CommonBottomSheetDialogFragment bottomSheet = CommonBottomSheetDialogFragment.newInstance(myString, StopChequeBook.this);
        bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());
    }
}