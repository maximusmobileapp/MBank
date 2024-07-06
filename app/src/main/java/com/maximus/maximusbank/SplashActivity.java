package com.maximus.maximusbank;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private static final long SPLASH_DISPLAY_LENGTH = 1000; // 5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setContentView(layout);

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.splash);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonStr = new String(buffer, StandardCharsets.UTF_8);

            getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.colorPrimarfy));

            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject screen = jsonObject.getJSONObject("screen");
            JSONArray components = screen.getJSONArray("components");

            List<View> viewsToFocus = new ArrayList<>();
            for (int i = 0; i < components.length(); i++) {
                JSONObject component = components.getJSONObject(i);

                String type = component.getString("type");
                if ("text_splash".equals(type)){
                    TextView textView = new TextView(this);
                    textView.setText(component.getString("text"));
                    textView.setPadding(30,30,30,30);
                    textView.setGravity(Gravity.CENTER);
                    textView.setAllCaps(true);
                    textView.setTextSize(20);
                    textView.setTextColor(Color.parseColor(component.getString("textColor")));
                    layout.addView(textView);
                }
//                setContentView(textView);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(mainIntent);
                        finish();
                    }
                }, SPLASH_DISPLAY_LENGTH);
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
    }
}
}
