package com.maximus.maximusbank.activity.fundTransfer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.maximus.maximusbank.R;

import java.util.ArrayList;

public class PreviewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        ArrayList<String> stepData = getIntent().getStringArrayListExtra("stepData");

        TextView previewTextView = findViewById(R.id.previewTextView);
        StringBuilder previewText = new StringBuilder();
        for (String data : stepData) {
            previewText.append(data).append("\n");
        }
        previewTextView.setText(previewText.toString());
    }
}
