package com.maximus.maximusbank.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;
import com.maximus.maximusbank.activity.sharedPref.PreferenceManager;


import java.util.Locale;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout layoutChangePassword;
    private LinearLayout layoutChangeTheme;
    private LinearLayout layoutChangeLanguage;
    private PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        TextView headerTextView = findViewById(R.id.headerTextView);
        headerTextView.setText(R.string.settings);
        ImageView backPressImg = findViewById(R.id.backButton);
        Utils.onBackPress(this, backPressImg);
        preferenceManager = new PreferenceManager(this);
        layoutChangePassword = findViewById(R.id.layoutChangePassword);
        layoutChangePassword.setOnClickListener(this);
        layoutChangeTheme = findViewById(R.id.layoutChangeTheme);
        layoutChangeTheme.setOnClickListener(this);
        layoutChangeLanguage = findViewById(R.id.layoutChangeLanguage);
        layoutChangeLanguage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.layoutChangePassword) {

        } else if (v.getId() == R.id.layoutChangeTheme) {
            showThemeSelectionDialog();
        } else if (v.getId() == R.id.layoutChangeLanguage) {
            showLanguageSelectionDialog();
        }
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        preferenceManager.setValue("language", lang);

        startActivity(new Intent(SettingActivity.this, DashboardActivity.class));
        finish();
    }

    private void showThemeSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_theme_selection, null);
        builder.setView(dialogView);

        RadioGroup radioGroupTheme = dialogView.findViewById(R.id.radioGroupTheme);
        RadioButton radioLight = dialogView.findViewById(R.id.radioLight);
        RadioButton radioDark = dialogView.findViewById(R.id.radioDark);

        // Set the initial state based on the current theme
        if (preferenceManager.getValue("dark_mode", false)) {
            radioDark.setChecked(true);
        } else {
            radioLight.setChecked(true);
        }

        builder.setPositiveButton("OK", (dialog, which) -> {
            if (radioGroupTheme.getCheckedRadioButtonId() == radioLight.getId()) {
                preferenceManager.setValue("dark_mode", false);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            } else if (radioGroupTheme.getCheckedRadioButtonId() == radioDark.getId()) {
                preferenceManager.setValue("dark_mode", true);
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showLanguageSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_language_selection, null);
        builder.setView(dialogView);

        RadioGroup radioGroupLanguage = dialogView.findViewById(R.id.radioGroupTheme);
        RadioButton radioEnglish = dialogView.findViewById(R.id.radioEnglish);
        RadioButton radioHindi = dialogView.findViewById(R.id.radioHindi);

        // Set the initial state based on the current language
        if (preferenceManager.getValue("language", "en").equals("hi")) {
            radioHindi.setChecked(true);
        } else {
            radioEnglish.setChecked(true);
        }

        builder.setPositiveButton("OK", (dialog, which) -> {
            if (radioGroupLanguage.getCheckedRadioButtonId() == radioEnglish.getId()) {
                setLocale("en");
            } else if (radioGroupLanguage.getCheckedRadioButtonId() == radioHindi.getId()) {
                setLocale("hi");
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}