package com.maximus.maximusbank.activity.fundTransfer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;


import java.util.HashMap;
import java.util.Map;

public class FT_Confirmation extends AppCompatActivity {

    private StringBuilder pinBuilder = new StringBuilder();
    private TextView[] pinDigits;
    private static final String CORRECT_PIN = "4567";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_transfer_mpin);
        TextView headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.confirmation);
        ImageView backPressImg = findViewById(R.id.backButton);
        Utils.onBackPress(this, backPressImg);
        pinDigits = new TextView[]{
                findViewById(R.id.pin_digit_1),
                findViewById(R.id.pin_digit_2),
                findViewById(R.id.pin_digit_3),
                findViewById(R.id.pin_digit_4)
        };

        setPinButtonListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }

    private void setPinButtonListeners() {
        int[] buttonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.button_delete
        };

        for (int id : buttonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> {
//                animateButton(v);  // Add animation here
                onPinButtonClick(((Button) v).getText().toString());
            });
        }
    }

    private void onPinButtonClick(String text) {
        if (text.equals("X")) {
            if (pinBuilder.length() > 0) {
                pinBuilder.deleteCharAt(pinBuilder.length() - 1);
                updatePinDisplay();
            }
        } else {
            if (pinBuilder.length() < 4) {
                pinBuilder.append(text);
                updatePinDisplay();
                if (pinBuilder.length() == 4) {
                    checkPin();
                }
            }
        }
    }

    private void checkPin() {
        String enteredPin = pinBuilder.toString();
        if (enteredPin.equals(CORRECT_PIN)) {

            Intent intent = new Intent(this, FT_Receipt.class);
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Map<String, String> stepDataMap = (Map<String, String>) extras.getSerializable("stepData");
                if (stepDataMap != null) {
                    intent.putExtra("stepData", new HashMap<>(stepDataMap));
                    intent.putExtra("refNo", extras.getString("refNo"));
                    intent.putExtra("paymentTime", extras.getString("paymentTime"));
                    intent.putExtra("bankType", extras.getString("bankType"));
                }
            }
            startActivity(intent);

            Toast.makeText(this, "PIN Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "PIN Incorrect!", Toast.LENGTH_SHORT).show();
        }
        pinBuilder.setLength(0);
        updatePinDisplay();
    }

    private void updatePinDisplay() {
        for (int i = 0; i < pinDigits.length; i++) {
            if (i < pinBuilder.length()) {
                pinDigits[i].setText(String.valueOf(pinBuilder.charAt(i)));
            } else {
                pinDigits[i].setText("");
            }
        }
    }
}