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

public class SetMpin extends AppCompatActivity {
    private StringBuilder pinBuilder = new StringBuilder();
    private StringBuilder confirmPinBuilder = new StringBuilder();
    private boolean isConfirming = false; // To check if the user is confirming the PIN
    private TextView[] pinDigits;
    private TextView[] confirmPinDigits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpin);

        pinDigits = new TextView[]{
                findViewById(R.id.pin_digit_1),
                findViewById(R.id.pin_digit_2),
                findViewById(R.id.pin_digit_3),
                findViewById(R.id.pin_digit_4)
        };

        confirmPinDigits = new TextView[]{
                findViewById(R.id.pin_confirm_1),
                findViewById(R.id.pin_confirm_2),
                findViewById(R.id.pin_confirm_3),
                findViewById(R.id.pin_confirm_4)
        };

        setPinButtonListeners();
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
                animateButton(v);  // Add animation here
                onPinButtonClick(((Button) v).getText().toString());
            });
        }
    }

    private void onPinButtonClick(String text) {
        if (text.equals("X")) {
            if (isConfirming) {
                if (confirmPinBuilder.length() > 0) {
                    confirmPinBuilder.deleteCharAt(confirmPinBuilder.length() - 1);
                    updatePinDisplay(confirmPinBuilder, confirmPinDigits);
                } else if (pinBuilder.length() > 0) {
                    isConfirming = false; // Switch back to PIN input
                    pinBuilder.deleteCharAt(pinBuilder.length() - 1);
                    updatePinDisplay(pinBuilder, pinDigits);
                }
            } else {
                if (pinBuilder.length() > 0) {
                    pinBuilder.deleteCharAt(pinBuilder.length() - 1);
                    updatePinDisplay(pinBuilder, pinDigits);
                }
            }
        } else {
            if (isConfirming) {
                if (confirmPinBuilder.length() < 4) {
                    confirmPinBuilder.append(text);
                    updatePinDisplay(confirmPinBuilder, confirmPinDigits);
                    if (confirmPinBuilder.length() == 4) {
                        checkPin();
                    }
                }
            } else {
                if (pinBuilder.length() < 4) {
                    pinBuilder.append(text);
                    updatePinDisplay(pinBuilder, pinDigits);
                    if (pinBuilder.length() == 4) {
                        isConfirming = true; // Move to confirm PIN stage
                    }
                }
            }
        }
    }

    private void updatePinDisplay(StringBuilder builder, TextView[] digits) {
        for (int i = 0; i < digits.length; i++) {
            if (i < builder.length()) {
                digits[i].setText(String.valueOf(builder.charAt(i)));
            } else {
                digits[i].setText("");
            }
        }
    }

    private void checkPin() {
        String enteredPin = pinBuilder.toString();
        String confirmPin = confirmPinBuilder.toString();
        if (enteredPin.equals(confirmPin)) {
            startActivity(new Intent(SetMpin.this, PasscodeActivity.class));
            Toast.makeText(this, "PIN Match!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "PIN does not match!", Toast.LENGTH_SHORT).show();
        }
        resetPin(); // Reset the PIN builders
    }

    private void resetPin() {
        pinBuilder.setLength(0);
        confirmPinBuilder.setLength(0);
        updatePinDisplay(pinBuilder, pinDigits);
        updatePinDisplay(confirmPinBuilder, confirmPinDigits);
        isConfirming = false;
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
