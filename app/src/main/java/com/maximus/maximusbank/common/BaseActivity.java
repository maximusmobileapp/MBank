package com.maximus.maximusbank.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.maximus.maximusbank.R;

public class BaseActivity extends AppCompatActivity {

    private ImageView backButton;
    private TextView headerTextView;
    private ImageView headerIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View header = findViewById(R.id.headerLayout);
        if (header != null) {
            ImageView backButton = header.findViewById(R.id.backButton);
            TextView headerTextView = header.findViewById(R.id.headerTextView);
            ImageView headerIcon = header.findViewById(R.id.headerIcon);

            if (backButton != null) {
                backButton.setOnClickListener(v -> onBackPressed());
            }

            // Optionally configure the header text and icon here
            // E.g., headerTextView.setText("Your Header Title");
            //       headerIcon.setImageResource(R.drawable.ic_some_icon);
        }
    }

    protected void setHeaderText(String text) {
        if (headerTextView != null) {
            headerTextView.setText(text);
        }
    }

    protected void setHeaderIcon(int resId) {
        if (headerIcon != null) {
            headerIcon.setImageResource(resId);
        }
    }

    protected void hideBackButton() {
        if (backButton != null) {
            backButton.setVisibility(View.GONE);
        }
    }
}

