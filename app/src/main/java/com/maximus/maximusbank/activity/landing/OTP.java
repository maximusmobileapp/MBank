package com.maximus.maximusbank.activity.landing;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.maximus.maximusbank.R;
import com.maximus.maximusbank.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class OTP extends AppCompatActivity {

    public Button buttonconfirm;
    public EditText edit_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setGravity(Gravity.CENTER);
        rootLayout.setBackgroundColor(Color.WHITE);
        rootLayout.setPadding(32, 32, 32, 32);

        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        cardView.setPadding(8, 8, 8, 8);
        cardViewParams.setMargins(8, 8, 8, 8);
        cardView.setLayoutParams(cardViewParams);
        cardView.setCardBackgroundColor(Color.WHITE);
        cardView.setCardElevation(16.0f);
        cardView.setRadius(24.0f);
        rootLayout.addView(cardView);

        LinearLayout innerLayout = new LinearLayout(this);
        innerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        innerLayout.setOrientation(LinearLayout.VERTICAL);
        innerLayout.setGravity(Gravity.CENTER);
        cardView.addView(innerLayout);

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.otp);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonStr = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONObject screen = jsonObject.getJSONObject("screen");
            JSONArray components = screen.getJSONArray("components");

            List<View> viewsToFocus = new ArrayList<>();
            for (int i = 0; i < components.length(); i++) {
                JSONObject component = components.getJSONObject(i);
                String type = component.getString("type");

                if ("text".equals(type)) {
                    TextView textView = new TextView(this);
                    textView.setText(component.getString("value"));
                    textView.setTextColor(Color.parseColor(component.getString("textColor")));
                    textView.setGravity(Gravity.CENTER);
                    if ("header".equals(component.getString("style"))) {
                        textView.setTextSize(24);
                    }
                    innerLayout.addView(textView);
                }

                else if ("text_otp".equals(type)) {
                    TextView text_otp = new TextView(this);
                    text_otp.setText(component.getString("value"));
                    text_otp.setPadding(30,30,30,30);
                    text_otp.setTextColor(Color.parseColor(component.getString("textColor")));
                    innerLayout.addView(text_otp);
                }

                else if ("edit_otp".equals(type)) {
                    EditText editotp = new EditText(this);
                    editotp.setHint(component.getString("hint"));
                    editotp.setTextColor(Color.parseColor(component.getString("textColor")));
                    InputFilter[] filters = new InputFilter[] { new InputFilter.LengthFilter(component.getInt("maxlength")) };
                    editotp.setFilters(filters);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(30, 0, 30, 0);
                    editotp.setLayoutParams(params);
                    editotp.setInputType(getInputType(component.getString("input_type")));
                    applyBorder(editotp);
                    innerLayout.addView(editotp);
                    viewsToFocus.add(editotp);
                    if ("Enter OTP".equals(component.getString("hint"))) {
                        edit_otp = editotp;
                    }
                }

                else if ("buttonconfirm".equals(type)) {
//                    View buttonView = createButton(component);
                    Button button = new Button(this);
                    button.setPadding(20,20,20,30);
                    LinearLayout.LayoutParams paramss = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    paramss.setMargins(50, 50, 50, 50);
                    button.setLayoutParams(paramss);
                    button.setText(component.getString("text"));
                    button.setTextColor(Color.parseColor(component.getString("textColor")));
                    button.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
                    button.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.rounded_button_background));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (validateFields()) {
                                Intent intent = new Intent(OTP.this, SetMpin.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(OTP.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    innerLayout.addView(button);
                }
            }
            setContentView(rootLayout);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.setLocale(this);
    }

    private View createButton(JSONObject component)throws JSONException {
        ShimmerFrameLayout shimmerFrameLayout = new ShimmerFrameLayout(this);
        LinearLayout.LayoutParams shimmerParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        shimmerParams.setMargins(0, 16, 0, 16);
        shimmerFrameLayout.setLayoutParams(shimmerParams);

        buttonconfirm = new Button(this);
        buttonconfirm.setText(component.getString("text"));
        buttonconfirm.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        buttonconfirm.setBackground(getResources().getDrawable(R.drawable.rounded_button_bg));
        shimmerFrameLayout.addView(buttonconfirm);
        Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()
                .setDuration(1500) // Duration of shimmer animation
                .setBaseAlpha(0.7f) // Alpha of the base color
                .setHighlightAlpha(1f) // Alpha of the highlight color
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT) // Animation direction
                .build();
        shimmerFrameLayout.setShimmer(shimmer);

        buttonconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    Intent intent = new Intent(OTP.this, SetMpin.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(OTP.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return shimmerFrameLayout;
    }

    private boolean validateFields() {
        boolean isValid = true;

        if (edit_otp == null || edit_otp.getText().toString().trim().isEmpty()) {
            edit_otp.setError("Invalid OTP");
            isValid = false;
        } else if (edit_otp.getText().toString().trim().length() != 6) {
            edit_otp.setError("MPIN must be 6 characters");
            isValid = false;
        }
        return isValid;
    }

    private void applyBorder(View view) throws JSONException {
        view.setBackgroundResource(R.drawable.edit_text_border);
        view.setPadding(16, 16, 16, 16);
    }

    private int getInputType(String inputType) {
        switch (inputType) {
            case "textOTP":
                return InputType.TYPE_CLASS_NUMBER;
            default:
                return InputType.TYPE_CLASS_TEXT;
        }
    }
}