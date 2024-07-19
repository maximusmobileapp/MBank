package com.maximus.maximusbank;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
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
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ForgetPassword extends AppCompatActivity {

    public Button buttonforget;
    private EditText accnoet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_forget_password);

        LinearLayout rootlayout = new LinearLayout(this);
        rootlayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        rootlayout.setOrientation(LinearLayout.VERTICAL);
        rootlayout.setGravity(Gravity.CENTER);
        rootlayout.setBackgroundColor(Color.WHITE);
        rootlayout.setPadding(32, 32, 32, 32);

        CardView cardView = new CardView(this);
        LinearLayout.LayoutParams cardViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardView.setPadding(8, 8, 8, 8);
        cardViewParams.setMargins(8, 8, 8, 8);
        cardView.setLayoutParams(cardViewParams);
        cardView.setCardBackgroundColor(Color.WHITE);
        cardView.setCardElevation(16.0f);
        cardView.setRadius(24.0f);
        rootlayout.addView(cardView);

        LinearLayout innerLayout = new LinearLayout(this);
        innerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        innerLayout.setOrientation(LinearLayout.VERTICAL);
        innerLayout.setGravity(Gravity.CENTER);
        cardView.addView(innerLayout);

        try {
            InputStream inputStream = getResources().openRawResource(R.raw.forget);
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
                else if ("text_accno".equals(type)){
                    TextView text_mobile = new TextView(this);
                    text_mobile.setText(component.getString("value"));
                    text_mobile.setPadding(30,30,30,30);
                    text_mobile.setTextColor(Color.parseColor(component.getString("textColor")));
                    innerLayout.addView(text_mobile);
                }

                else if ("edittext_account".equals(type)) {
                    EditText editText = new EditText(this);
                    editText.setHint(component.getString("hint"));
                    editText.setTextColor(Color.parseColor(component.getString("textColor")));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(30, 0, 30, 0);
                    editText.setLayoutParams(params);
                    editText.setInputType(getInputType(component.getString("input_type")));
                    applyBorder(editText);
                    innerLayout.addView(editText);
                    viewsToFocus.add(editText);
                    if ("Enter Account No.".equals(component.getString("hint"))) {
                        accnoet = editText;
                    }
                }

                else if ("buttonforget".equals(type)){
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
                            if (validateFieldss()) {
                                Intent intent = new Intent(ForgetPassword.this, OTP.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(ForgetPassword.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    innerLayout.addView(button);
                }
            }
            //This will show the complete view of page
            setContentView(rootlayout);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    private View createButton(JSONObject component) throws JSONException {
            ShimmerFrameLayout shimmerFrameLayout = new ShimmerFrameLayout(this);
            LinearLayout.LayoutParams shimmerParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            shimmerParams.setMargins(0, 16, 0, 16);
            shimmerFrameLayout.setLayoutParams(shimmerParams);

            buttonforget = new Button(this);
            buttonforget.setText(component.getString("text"));
            buttonforget.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            ));
        buttonforget.setBackground(getResources().getDrawable(R.drawable.rounded_button_bg));

            shimmerFrameLayout.addView(buttonforget);
            Shimmer shimmer = new Shimmer.AlphaHighlightBuilder()
                    .setDuration(1500) // Duration of shimmer animation
                    .setBaseAlpha(0.7f) // Alpha of the base color
                    .setHighlightAlpha(1f) // Alpha of the highlight color
                    .setDirection(Shimmer.Direction.LEFT_TO_RIGHT) // Animation direction
                    .build();
            shimmerFrameLayout.setShimmer(shimmer);

        buttonforget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (validateFieldss()) {
                        Intent intent = new Intent(ForgetPassword.this, OTP.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ForgetPassword.this, "Please fill all details", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            return shimmerFrameLayout;
    }

    private boolean validateFieldss() {
        boolean isValid = true;
        if (accnoet == null || accnoet.getText().toString().trim().isEmpty()) {
            accnoet.setError("Invalid Account No.");
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
            case "textMobile":
                return InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
            default:
                return InputType.TYPE_CLASS_TEXT;
        }
    }
}