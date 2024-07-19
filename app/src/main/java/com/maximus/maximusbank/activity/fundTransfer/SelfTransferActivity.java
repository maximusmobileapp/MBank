package com.maximus.maximusbank.activity.fundTransfer;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.maximus.maximusbank.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class SelfTransferActivity extends AppCompatActivity {

    private Button btncontinue;
    private EditText  etamu;

    TextView headerTextView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_transfer);

        headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.self_transfer);
        etamu = findViewById(R.id.etamu);
        btncontinue = findViewById(R.id.btncontinue);

        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    Toast.makeText(SelfTransferActivity.this, "Self Transfer Continue", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateFields() {
        boolean isValid = true;
        if (etamu == null || etamu.getText().toString().trim().isEmpty()) {
            etamu.setError("Enter Amount");
            isValid = false;
        }

        return isValid;
    }
}