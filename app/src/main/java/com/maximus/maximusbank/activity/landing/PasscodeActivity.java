package com.maximus.maximusbank.activity.landing;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;
import com.maximus.maximusbank.activity.DashboardActivity;

import java.util.concurrent.Executor;

public class PasscodeActivity extends AppCompatActivity {
    private StringBuilder pinBuilder = new StringBuilder();
    private static final String CORRECT_PIN = "1234";
    private TextView[] pinDigits;
    private ImageView fingerprintIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);

        pinDigits = new TextView[]{
                findViewById(R.id.pin_digit_1),
                findViewById(R.id.pin_digit_2),
                findViewById(R.id.pin_digit_3),
                findViewById(R.id.pin_digit_4)
        };

        fingerprintIcon = findViewById(R.id.use_fingerprint);
        checkFingerprintAvailability();
        fingerprintIcon.setOnClickListener(v -> authenticateWithFingerprint());
        setPinButtonListeners();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showLogoutDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }

    @NonNull
    @Override
    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        return super.getOnBackInvokedDispatcher();
    }

    private void checkFingerprintAvailability() {
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate()) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                // Fingerprint is available
                fingerprintIcon.setVisibility(View.VISIBLE);
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // No fingerprint hardware, hardware unavailable, or no fingerprints enrolled
                fingerprintIcon.setVisibility(View.GONE);
                break;
        }
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        showLogoutDialog();
    }*/

    private void showLogoutDialog() {
        new AlertDialog.Builder(PasscodeActivity.this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void authenticateWithFingerprint() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                Toast.makeText(PasscodeActivity.this, "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                startActivity(new Intent(PasscodeActivity.this, DashboardActivity.class));
                Toast.makeText(PasscodeActivity.this, "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                // Navigate to the next screen or perform the action
            }

            @Override
            public void onAuthenticationFailed() {
                Toast.makeText(PasscodeActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });

        androidx.biometric.BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Fingerprint Authentication")
                .setSubtitle("Log in using your fingerprint")
                .setNegativeButtonText("Cancel")
                .build();

        biometricPrompt.authenticate(promptInfo);
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

    private void updatePinDisplay() {
        for (int i = 0; i < pinDigits.length; i++) {
            if (i < pinBuilder.length()) {
                pinDigits[i].setText(String.valueOf(pinBuilder.charAt(i)));
            } else {
                pinDigits[i].setText("");
            }
        }
    }

    private void checkPin() {
        String enteredPin = pinBuilder.toString();
        if (enteredPin.equals(CORRECT_PIN)) {
            startActivity(new Intent(PasscodeActivity.this, DashboardActivity.class));
            Toast.makeText(this, "PIN Correct!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "PIN Incorrect!", Toast.LENGTH_SHORT).show();
        }
        pinBuilder.setLength(0); // Reset the PIN builder
        updatePinDisplay();
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
