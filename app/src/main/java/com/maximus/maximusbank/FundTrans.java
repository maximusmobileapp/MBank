package com.maximus.maximusbank;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


public class FundTrans extends AppCompatActivity {

    private TextView titleView,txtWithin,txtOther,txtSelf;
    private CardView card1,card2,card3;
    private ImageView imgvector;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_trans);

        titleView = findViewById(R.id.titleview);
        txtWithin = findViewById(R.id.txtwithin);
        txtOther = findViewById(R.id.txtother);
        txtSelf = findViewById(R.id.txtself);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);
        imgvector = findViewById(R.id.imgvector);

        try {
            Resources resources = getResources();
            InputStream inputStream = resources.openRawResource(R.raw.fundtrans);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            // Parse JSON
            JSONObject jsonObject = new JSONObject(json);
            JSONObject screenObject = jsonObject.getJSONObject("screen");
            JSONArray components = screenObject.getJSONArray("components");


            TextView[] textViews = new TextView[] { titleView, txtWithin, txtOther, txtSelf };

            applyTextConfig(components,textViews);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        imgvector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundTrans.this, WithinBankActivity.class);
                startActivity(intent);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundTrans.this, OtherBankActivity.class);
                startActivity(intent);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundTrans.this, SelfTransferActivity.class);
                startActivity(intent);
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

                if (text.equals("Fund Transfer")) {
                    textView.setTypeface(null, Typeface.BOLD);
                } else if (text.equals("Within Bank")) {
                    textView.setTypeface(null, Typeface.BOLD);
                } else if (text.equals("Other Bank")) {
                    textView.setTypeface(null, Typeface.BOLD);
                } else if (text.equals("Self Transfer")) {
                    textView.setTypeface(null, Typeface.BOLD);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}