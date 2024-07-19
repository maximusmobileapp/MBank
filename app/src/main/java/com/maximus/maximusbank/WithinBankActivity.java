package com.maximus.maximusbank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class WithinBankActivity extends AppCompatActivity {

    private TextView title,txtFromAcc,txtavailblce,txtbenef,txtaccount,txtamount,txtremark;
    private ImageView imgvector;
    private Button btncontinue,btnaddbenef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_within_bank);

        imgvector = findViewById(R.id.imgvector);
        title = findViewById(R.id.title_WFT);
        txtFromAcc = findViewById(R.id.txtFromAcc);
        txtavailblce = findViewById(R.id.txtavailblce);
        txtbenef = findViewById(R.id.txtbenef);
        txtaccount = findViewById(R.id.txtaccount);
        txtamount = findViewById(R.id.txtamount);
        txtremark = findViewById(R.id.txtremark);
        btncontinue = findViewById(R.id.btncontinue);
        btnaddbenef = findViewById(R.id.btnaddbenef);

        try {
            Resources resources = getResources();
            InputStream inputStream = resources.openRawResource(R.raw.within);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            // Parse JSON
            JSONObject jsonObject = new JSONObject(json);
            JSONObject screenObject = jsonObject.getJSONObject("screen");
            JSONArray components = screenObject.getJSONArray("components");

            TextView[] textViews = new TextView[] { title,txtFromAcc,txtavailblce,txtbenef,txtaccount,txtamount,txtremark };

            EditText[] editTexts = new EditText[] {};

            Button[] buttons = new Button[] { btncontinue, btnaddbenef};

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
                Toast.makeText(WithinBankActivity.this, "Within Continue", Toast.LENGTH_SHORT).show();
            }
        });

        btnaddbenef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WithinBankActivity.this, "Within Add beneficiary", Toast.LENGTH_SHORT).show();
            }
        });
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

                //fontstyle from json
                /*String fontStyle = config.getString("fontStyle");
                Typeface typeface = ResourcesCompat.getFont(this, getResources().getIdentifier(fontStyle, "font", getPackageName()));
                textView.setTypeface(typeface);*/


                if (text.equals("Within Fund Transfer")) {
                    textView.setTypeface(null, Typeface.BOLD);
                } else {
                    textView.setTypeface(null, Typeface.NORMAL);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void applyButtonConfig(JSONArray components, Button[] buttons) {
        try {
            for (int i = 0; i < components.length(); i++) {
                JSONObject config = components.getJSONObject(i);
                Button button = buttons[i];
                String type = config.getString("Button");
                button.setText(type);
                button.setTextColor(Color.parseColor(config.getString("textColor")));
                button.setTextSize(TypedValue.COMPLEX_UNIT_SP, config.getInt("textSize"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}