package com.maximus.maximusbank.activity.fundTransfer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maximus.maximusbank.FundTransferActivity;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class FundTransferDashboard extends AppCompatActivity {

    private TextView titleView,txtWithin,txtOther,txtSelf;
    private CardView card1,card2,card3;
    private ImageView imgvector;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fund_transfer_dashboard);

        txtWithin = findViewById(R.id.txtwithin);
        txtOther = findViewById(R.id.txtother);
        txtSelf = findViewById(R.id.txtself);
        card1 = findViewById(R.id.card1);
        card2 = findViewById(R.id.card2);
        card3 = findViewById(R.id.card3);




        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundTransferDashboard.this, ThirdPartyTransferActivity.class);
                startActivity(intent);
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundTransferDashboard.this, OtherBankActivity.class);
                startActivity(intent);
            }
        });

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundTransferDashboard.this, SelfTransferActivity.class);
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