package com.maximus.maximusbank;

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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class SelfTransferActivity extends AppCompatActivity {

    private ImageView imgvector;
    private TextView textself,textAccount,txtavailblce,txtaccount,txtamount,txtremark;
    private Button btncontinue;
    private EditText etAvailblce, etamu,etremark;

    private Spinner spinnerFromAcc,spinnerToAcc;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_transfer);

        imgvector = findViewById(R.id.imgvector);
        textself = findViewById(R.id.textself);
        textAccount = findViewById(R.id.textAccount);
        txtavailblce = findViewById(R.id.txtavailblce);
        txtaccount = findViewById(R.id.txtaccount);
        txtamount = findViewById(R.id.txtamount);
        etAvailblce = findViewById(R.id.etAvailblce);
        etamu = findViewById(R.id.etamu);
        etremark = findViewById(R.id.etremark);
        spinnerFromAcc = findViewById(R.id.spinnerFromAcc);
        spinnerToAcc = findViewById(R.id.spinnerToAcc);
        btncontinue = findViewById(R.id.btncontinue);


        try {
            Resources resources = getResources();
            InputStream inputStream = resources.openRawResource(R.raw.self);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            // Parse JSON
            JSONObject jsonObject = new JSONObject(json);
            JSONObject screenObject = jsonObject.getJSONObject("screen");
            JSONArray components = screenObject.getJSONArray("components");

            TextView[] textViews = new TextView[] { textself,textAccount,txtavailblce,txtaccount,txtamount,txtremark };

            EditText[] editTexts = new EditText[] {};

            Button[] buttons = new Button[] { btncontinue};

            applyTextConfig(components,textViews);

            applyButtonConfig(components,buttons);

        } catch (Exception e) {
            e.printStackTrace();
        }

        imgvector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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

    private void applyButtonConfig(JSONArray components, Button[] buttons) {
    }

    private void applyTextConfig(JSONArray components, TextView[] textViews) {
        try {
            for (int i = 0; i < components.length(); i++) {
                JSONObject config = components.getJSONObject(i);
                TextView textView = textViews[i];
                String text = config.getString("text");
                textView.setText(text);
                textView.setTextColor(Color.parseColor(config.getString("textColor")));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, config.getInt("textSize"));

                if (text.equals("Self Transfer")) {
                    textView.setTypeface(null, Typeface.BOLD);
                } else {
                    textView.setTypeface(null, Typeface.NORMAL);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}