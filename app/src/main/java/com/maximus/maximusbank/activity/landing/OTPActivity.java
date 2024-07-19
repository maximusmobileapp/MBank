package com.maximus.maximusbank.activity.landing;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.maximus.maximusbank.R;

public class OTPActivity extends AppCompatActivity {
    private StringBuilder otpBuilder = new StringBuilder();
    private TextView[] otpDigits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpDigits = new TextView[]{
                findViewById(R.id.otp_digit_1),
                findViewById(R.id.otp_digit_2),
                findViewById(R.id.otp_digit_3),
                findViewById(R.id.otp_digit_4),
                findViewById(R.id.otp_digit_5),
                findViewById(R.id.otp_digit_6)
        };

        setOtpButtonListeners();
    }

    private void setOtpButtonListeners() {
        int[] buttonIds = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3,
                R.id.button4, R.id.button5, R.id.button6, R.id.button7,
                R.id.button8, R.id.button9, R.id.button_delete
        };

        for (int id : buttonIds) {
            Button button = findViewById(id);
            button.setOnClickListener(v -> {
                animateButton(v);  // Add animation here
                onOtpButtonClick(((Button) v).getText().toString());
            });
        }
    }

    private void onOtpButtonClick(String text) {
        if (text.equals("X")) {
            if (otpBuilder.length() > 0) {
                otpBuilder.deleteCharAt(otpBuilder.length() - 1);
                updateOtpDisplay();
            }
        } else {
            if (otpBuilder.length() < 6) {
                otpBuilder.append(text);
                updateOtpDisplay();
                if (otpBuilder.length() == 6) {
                    checkOtp();
                }
            }
        }
    }

    private void updateOtpDisplay() {
        for (int i = 0; i < otpDigits.length; i++) {
            if (i < otpBuilder.length()) {
                otpDigits[i].setText(String.valueOf(otpBuilder.charAt(i)));
            } else {
                otpDigits[i].setText("");
            }
        }
    }

    private void checkOtp() {
        String enteredOtp = otpBuilder.toString();
       startActivity(new Intent(OTPActivity.this, SetMpin.class));
        Toast.makeText(this, "OTP Entered: " + enteredOtp, Toast.LENGTH_SHORT).show();
        otpBuilder.setLength(0); // Reset the OTP builder
        updateOtpDisplay();
    }

    private void animateButton(View button) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(button, "scaleX", 1f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(button, "scaleY", 1f, 1.2f, 1f);
        scaleX.setDuration(200);
        scaleY.setDuration(200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.start();
    }
}
