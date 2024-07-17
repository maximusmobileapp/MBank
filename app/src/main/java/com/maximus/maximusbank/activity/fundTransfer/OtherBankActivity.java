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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.maximus.maximusbank.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class OtherBankActivity extends AppCompatActivity {

    private ImageView imgvector;

    private TextView txtOFT,txtacc,txtavailblce,txtBN,txtaccno,txtIFSC,txtamount,txtremark;

    private Button btncon,btnaddben;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_bank);


        txtacc = findViewById(R.id.txtacc);
        txtavailblce = findViewById(R.id.txtavailblce);
        txtBN = findViewById(R.id.txtBN);
        txtaccno = findViewById(R.id.txtaccno);
        txtIFSC = findViewById(R.id.txtIFSC);
        txtamount = findViewById(R.id.txtamount);
        txtremark = findViewById(R.id.txtremark);
        btncon = findViewById(R.id.btncon);
        btnaddben = findViewById(R.id.btnaddben);



        btncon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OtherBankActivity.this, "Other Continue", Toast.LENGTH_SHORT).show();
            }
        });

        btnaddben.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OtherBankActivity.this, "Other Add Beneficiary", Toast.LENGTH_SHORT).show();
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

                if (text.equals("Other Fund Transfer")) {
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
    }
}